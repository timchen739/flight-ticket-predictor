package interfaces;

import util.CsvFileHelper;
import util.DateHelper;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public abstract class FakeOutputGenerator {
    protected final Boolean NO_ZERO = Boolean.FALSE;
    protected final Boolean WITH_ZERO = Boolean.TRUE;

    protected String outputFile = "default.csv";

    public void generateOutput(String ...routes) {
        List<String[]> fakeOutputs = createFakeOutput(routes);

        try {
            String[] headers = {"Route", "Search Date", "Flight Dates", "Current Price", "Lowest Price"};
            CsvFileHelper.createFile(this.outputFile, fakeOutputs.iterator(), Arrays.stream(headers).iterator());
        } catch (IOException ex) {
            System.out.println("Error creating file" + getClass().getName());
        }
    }

    protected abstract List<String[]> createFakeOutput(String ...routes);

    protected Double randomNumber(boolean needZero) {
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
