package com.flight.ticket.predictor.model;

import datamodel.TheDataModel;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.List;

@SpringBootTest
public class TheDataModelTest {

    private TheDataModel dataModel;

    @BeforeEach
    public void init() {
        dataModel = new TheDataModel();
    }

    @Test
    public void lowestPriceShouldNotBeZero() throws IOException {
        dataModel.run();
        validateOutput();
    }

    private void validateOutput()  {
        String filePath = "src/main/resources/output.csv";
        String columnName = "Lowest Price";

        try(Reader in = new FileReader(filePath)) {
            CSVFormat.Builder builder = CSVFormat.Builder.create();
            CSVFormat csvFormat = builder.setHeader("Route", "Search Date", "Flight Dates", "Current Price", "Lowest Price").setSkipHeaderRecord(true).build();

            Iterable<CSVRecord> records = csvFormat.parse(in);
            for (CSVRecord record : records) {
                String parsedPrice = record.get(columnName).replaceAll("\\$|,", "");
                double lowestPrice = Double.parseDouble(parsedPrice);
                System.out.println(lowestPrice);
                Assertions.assertTrue(lowestPrice > 0, "Found $0 Lowest Price");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
