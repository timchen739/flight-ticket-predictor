package datamodel;

import entity.ModelInput;
import entity.ModelOutput;
import util.CsvFileHelper;

import java.io.IOException;

public class TheDataModel {

    public static final Double DATA_MODEL_VERSION = 1.1;

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

    public void run() throws IOException {
        // create  outputs
        String[][] fakeOutputs = {
                {"BOS-DXB", "5/8/2023", "6/3/2023-6/9/2023", "1249.00", "$1,249.00"},
                {"BOS-LAX", "5/8/2023", "11/22/2023-1/2/2024", "999.67", "$649.67"},
                {"BOS-ATL", "5/8/2023", "11/3/2023-11/3/2023", "124", "$100"},
                {"BOS-DXB", "5/8/2023", "6/3/2023-6/9/2023", "1249", "$1249"},
                {"BOS-JFK", "5/8/2023", "9/9/2023-9/12/2023", "154", "$99"},
        };

        // put outputs into a csv file
        CsvFileHelper.createFile("src/main/resources/output.csv", fakeOutputs);
    }

}
