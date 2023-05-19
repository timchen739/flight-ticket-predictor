package com.flight.ticket.predictor.model;

import datamodel.DataModelContext;
import datamodel.TheDataModel;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import util.AirportCodeSelector;
import util.CsvFileHelper;

import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

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
        dataModel.run(outputName, routes);
        validateOutput(outputName);
    }

    @Test
    public void theseRandomizedRoutesShouldNotHaveZeroDollarLowestPrice() {
        String[] routes = randomRoutesBuilder(5);
        String outputName = "output3.csv";
        dataModel.run(outputName, routes);
        validateOutput(outputName);
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
                double lowestPrice = Double.parseDouble(parsedPrice);
                String route = record.get("Route");
                System.out.println(lowestPrice);
                if(lowestPrice == 0.0) {
                    failedRoutes.add(new String[]{route});
                }
            }
            if(failedRoutes.size() > 0 ) {
                CsvFileHelper.createFile("failedRoutes.csv", failedRoutes.iterator(), List.of("Routes").iterator());
            }
            Assertions.assertTrue(failedRoutes.size() == 0, "Found $0 Lowest Price");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
