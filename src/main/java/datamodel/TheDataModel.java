package datamodel;

import entity.ModelInput;
import entity.ModelOutput;
import interfaces.FakeOutputGenerator;
import util.CsvFileHelper;

import java.io.IOException;
import java.util.*;

public class TheDataModel {

    private DataModelContext context;
    private String version;
    public TheDataModel(DataModelContext context) {
        this.context = context;
    }

    public TheDataModel(String version, DataModelContext context) {
        this.context = context;
        this.version = version;
    }

    public ModelOutput predict(ModelInput input) {
        if(input.getSearchDate().equals("05/08/2023")) {
            return new ModelOutput.Builder()
                    .currentPrice(1299.00)
                    .lowestPrice(0.00)
                    .advice("Wait")
                    .build();
        } else {
            return new ModelOutput.Builder()
                    .currentPrice(1199.00)
                    .lowestPrice(999.00)
                    .advice("Buy")
                    .build();
        }
    }

    public String getModelVersion() {
        return version.trim().length() == 0 ? "Latest" : this.version;
    }

    public void run() throws IOException {
        // create  outputs
        String[][] fakeOutputs = {
                {"BOS-DXB", "5/8/2023", "6/3/2023-6/9/2023", "1249.00", "$1,249.00"},
                {"BOS-LAX", "5/8/2023", "11/22/2023-1/2/2024", "999.67", "$649.67"},
                {"BOS-ATL", "5/8/2023", "11/3/2023-11/3/2023", "124", "$10"},
                {"BOS-DXB", "5/8/2023", "6/3/2023-6/9/2023", "1249", "$1249"},
                {"BOS-JFK", "5/8/2023", "9/9/2023-9/12/2023", "154", "$99"},
        };

        String[] headers = {"Route", "Search Date", "Flight Dates", "Current Price", "Lowest Price"};
        // put outputs into a csv file
        CsvFileHelper.createFile("output.csv", Arrays.stream(fakeOutputs).iterator(), Arrays.stream(headers).iterator());
    }


    public void run(String outputName, Iterator<String> routeIterator) {
        FakeOutputGenerator outputGenerator;
        outputGenerator = context.createOutputGenerator(outputName);
        outputGenerator.generateOutput(routeIterator);
    }
}
