package datamodel;

import entity.ModelInput;
import entity.ModelOutput;
import util.CsvFileHelper;
import util.DateHelper;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class TheDataModel {

    public static final Double DATA_MODEL_VERSION = 1.1;
    private final Boolean NO_ZERO = Boolean.FALSE;
    private final Boolean WITH_ZERO = Boolean.TRUE;

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
                {"BOS-ATL", "5/8/2023", "11/3/2023-11/3/2023", "124", "$10"},
                {"BOS-DXB", "5/8/2023", "6/3/2023-6/9/2023", "1249", "$1249"},
                {"BOS-JFK", "5/8/2023", "9/9/2023-9/12/2023", "154", "$99"},
        };

        // put outputs into a csv file
        CsvFileHelper.createFile("output.csv", Arrays.stream(fakeOutputs).iterator());
    }


    public void run(String ...routes) throws IOException {
        String departDate = DateHelper.getDateFromAnotherDate(60, LocalDate.now());
        String returnDate = DateHelper.getDateFromAnotherDate(14, LocalDate.parse(departDate, DateTimeFormatter.ofPattern("MM/dd/yyyy")));

        List<String[]> fakeOutputs = new ArrayList<>();



        for(String route : routes) {
            double currentPrice = randomNumber(NO_ZERO);
            double lowestPrice = randomNumber(NO_ZERO);
            fakeOutputs.add(new String[] {
               route, departDate, returnDate, String.format("%.2f", currentPrice) , String.format("%.2f", lowestPrice)
            });
        }
        CsvFileHelper.createFile("output2.csv", fakeOutputs.iterator());
    }

    private Double randomNumber(boolean needZero) {
        Random random = new Random();
        double num;
        int randInt = random.nextInt(5);
        if (needZero && randInt == 0) {
            num = 0.00;
        } else {
            num = random.nextDouble(1951.00) + 50;
        }

        return num;
    }

}
