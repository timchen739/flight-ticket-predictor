package com.flight.ticket.predictor.model;

import datamodel.DataModelContext;
import datamodel.TheDataModel;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.junit.Ignore;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import util.AirportCodeSelector;
import util.CsvFileHelper;

import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.*;

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
        context.setGoodData(true);
        dataModel.run(outputName, Arrays.stream(routes).iterator());
        validateOutput(outputName);
    }

    @Test
    public void theseRandomizedRoutesShouldNotHaveZeroDollarLowestPrice() {
        String[] routes = randomRoutesBuilder(5);
        String outputName = "output3.csv";
        context.setGoodData(true);
        dataModel.run(outputName, Arrays.stream(routes).iterator());
        validateOutput(outputName);
    }
    @Test
    public void whenNoResultFoundForARouteItShouldNotStopTheModel() {
        String[] routes = randomRoutesBuilder(5);
        String outputName = "output_with_no_result.csv";
        context.setGoodData(true);
        context.setNoResultFoundForARoute(true);
        dataModel.run(outputName, Arrays.stream(routes).iterator());
        validateOutput(outputName);
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
}
