package com.flight.ticket.predictor.model;

import datamodel.DataModelContext;
import datamodel.TheDataModel;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import service.DataModelLoader;
import util.AirportCodeSelector;
import util.CsvFileHelper;

import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.*;

import static constants.TestScenario.*;

@SpringBootTest
public class TheDataModelTest {

    private TheDataModel dataModel;
    private AirportCodeSelector airportCodeSelector;

    private DataModelContext context;

    @BeforeEach
    public void init() {
        context = new DataModelContext();
        dataModel = new TheDataModel(context);
        airportCodeSelector = new AirportCodeSelector();
    }

    @Test
    public void lowestPriceShouldNotBeZero() throws IOException {
        dataModel.run();
        validateOutput("output.csv");
    }

    @Test
    public void theseSpecificRoutesShouldNotHaveZeroDollarLowestPrice() {
        String[] routes = new String[]{
               "BOS-DBX",
                "BOS-LAX",
                "NYC-HKG",
                "ATL-NYC",
                "NYC-LAX"
        };
        String outputName = "output2.csv";
        context.setTestScenario(PASSING_SCENARIO);
        dataModel.run(outputName, Arrays.stream(routes).iterator());
        validateOutput(outputName);
    }

    @Test
    public void theseRandomizedRoutesShouldNotHaveZeroDollarLowestPrice() {
        String[] routes = randomRoutesBuilder(5);
        String outputName = "output3.csv";
        context.setTestScenario(PASSING_SCENARIO);
//        context.setTestScenario(ZERO_DOLLAR_SCENARIO);
        dataModel.run(outputName, Arrays.stream(routes).iterator());
        validateOutput(outputName);
    }
    @Test
    public void whenNoResultFoundForARouteItShouldNotStopTheModel() {
        String[] routes = randomRoutesBuilder(5);
        String outputName = "output_with_no_result.csv";
        context.setTestScenario(RESULT_NOT_FOUND_SCENARIO);
        dataModel.run(outputName, Arrays.stream(routes).iterator());
        validateOutput(outputName);
    }

    @Test
    public void noDrasticPriceChangeShouldHappenWhenComparing() {
        String[] routes = randomRoutesBuilder(5);
        TheDataModel latestModel = DataModelLoader.getInstance().loadModel("", context);
        TheDataModel previousModel = DataModelLoader.getInstance().loadModel("old-model", context);

//        context.setTestScenario(DRASTIC_PRICE_CHANGE_SCENARIO);
        context.setTestScenario(PASSING_SCENARIO);
        latestModel.run("latest_model_output.csv", Arrays.stream(routes).iterator());

        context.setTestScenario(PASSING_SCENARIO);
        previousModel.run("previous_model_output.csv", Arrays.stream(routes).iterator());

        compareOutputs("latest_model_output.csv", "previous_model_output.csv");
    }

    /*
     * this is disabled on purpose, enable to run the failed routes logged from
     * theseRandomizedRoutesShouldNotHaveZeroDollarLowestPrice test
     * */

    @Test
    @Disabled
    public void rerunFailedRoutes() {
        List<String> failedRoutes = getFailedRoutes("failed-routes-for-output3.csv");
        if(!failedRoutes.isEmpty()) {

            String outputName = "rerun-failed-routes-output.csv";
            dataModel.run(outputName,failedRoutes.iterator());
            validateOutput(outputName);
        }
    }

    private String[] randomRoutesBuilder(int numOfRoutes) {
        String[] routes = new String[numOfRoutes];
        for(int i=0; i<numOfRoutes; i++) {
            String from = airportCodeSelector.selectAirportCodeExcept(Collections.emptyList());
            String to = airportCodeSelector.selectAirportCodeExcept(List.of(from));

            routes[i] = String.format("%s-%s", from, to);
        }

        return routes;
    }

    private void validateOutput(String fileName)  {
        String filePath = String.format("src/main/resources/%s", fileName);
        String columnName = "Lowest Price";

        try(Reader in = new FileReader(filePath)) {
            CSVFormat.Builder builder = CSVFormat.Builder.create();
            CSVFormat csvFormat = builder.setHeader("Route", "Search Date", "Flight Dates", "Current Price", "Lowest Price").setSkipHeaderRecord(true).build();

            Iterable<CSVRecord> records = csvFormat.parse(in);
            List<String[]> failedRoutes = new ArrayList<>();
            for (CSVRecord record : records) {
                String parsedPrice = record.get(columnName).replaceAll("\\$|,", "");
                if("N/A".equals(parsedPrice)) continue;
                double lowestPrice = Double.parseDouble(parsedPrice);
                String route = record.get("Route");
                System.out.println(lowestPrice);
                if(lowestPrice == 0.0) {
                    failedRoutes.add(new String[]{route});
                }
            }
            if(failedRoutes.size() > 0 ) {
                CsvFileHelper.createFile(String.format("failed-routes-for-%s", fileName) , failedRoutes.iterator(), List.of("Routes").iterator());
            }
            Assertions.assertTrue(failedRoutes.size() == 0, "Found $0 Lowest Price");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private List<String> getFailedRoutes(String fileName) {
        String filePath = String.format("src/main/resources/%s", fileName);
        List<String> failedRoutes = new ArrayList<>();

        try(Reader in = new FileReader(filePath)) {
            CSVFormat.Builder builder = CSVFormat.Builder.create();
            CSVFormat csvFormat = builder.setHeader("Routes").setSkipHeaderRecord(true).build();

            Iterable<CSVRecord> records = csvFormat.parse(in);
            for (CSVRecord record : records) {
                String route = record.get("Routes");
                failedRoutes.add(route);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

         return failedRoutes;
    }

    private void compareOutputs(String output1, String output2) {
        String file1 = String.format("src/main/resources/%s", output1);
        String file2 = String.format("src/main/resources/%s", output2);

        String columnName = "Lowest Price";

        try(Reader in1 = new FileReader(file1); Reader in2 = new FileReader(file2)) {
            CSVFormat.Builder builder = CSVFormat.Builder.create();
            CSVFormat csvFormat = builder.setHeader("Route", "Search Date", "Flight Dates", "Current Price", "Lowest Price").setSkipHeaderRecord(true).build();

            Iterable<CSVRecord> output1Records = csvFormat.parse(in1);
            Iterable<CSVRecord> output2Records = csvFormat.parse(in2);

            Iterator<CSVRecord> it1 = output1Records.iterator();
            Iterator<CSVRecord> it2 = output2Records.iterator();

            while(it1.hasNext() && it2.hasNext()) {
                CSVRecord record1 = it1.next();
                CSVRecord record2 = it2.next();

                String route = record1.get("Route");

                String parsedPrice1 = record1.get(columnName).replaceAll("\\$|,", "");
                double lowestPrice1 = Double.parseDouble(parsedPrice1);

                String parsedPrice2 = record2.get(columnName).replaceAll("\\$|,", "");
                double lowestPrice2 = Double.parseDouble(parsedPrice2);

                double lowerPriceAfter200PercentMore = Math.min(lowestPrice1, lowestPrice2) * 3;
                double threshold = Math.max(lowestPrice1, lowestPrice2);


                Assertions.assertTrue(lowerPriceAfter200PercentMore > threshold, "Lowest Price for Route " + route + " changed more than 200%");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
