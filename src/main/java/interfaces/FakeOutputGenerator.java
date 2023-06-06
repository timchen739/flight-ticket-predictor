package interfaces;

import util.CsvFileHelper;
import util.DateHelper;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

import static util.DateHelper.getDateFromAnotherDate;

public abstract class FakeOutputGenerator {
    protected final Boolean NO_ZERO = Boolean.FALSE;
    protected final Boolean WITH_ZERO = Boolean.TRUE;

    protected String outputFile = "default.csv";

    public void generateOutput(Iterator<String> routeIterator) {
        List<String[]> fakeOutputs = createFakeOutput(routeIterator);

        try {
            String[] headers = {"Route", "Search Date", "Flight Dates", "Current Price", "Lowest Price"};
            CsvFileHelper.createFile(this.outputFile, fakeOutputs.iterator(), Arrays.stream(headers).iterator());
        } catch (IOException ex) {
            System.out.println("Error creating file" + getClass().getName());
        }
    }

    protected abstract List<String[]> createFakeOutput(Iterator<String> routeIterator);

    protected List<String[]> createFakeOutputHelper(Iterator<String> routeIterator, boolean lowestPriceIsZero) {
        String departDate = getDateFromAnotherDate(60, LocalDate.now());
        String returnDate = getDateFromAnotherDate(14, LocalDate.parse(departDate, DateTimeFormatter.ofPattern("MM/dd/yyyy")));

        List<String[]> fakeOutputs = new ArrayList<>();
        while(routeIterator.hasNext()) {
            double currentPrice = randomNumber(NO_ZERO);
            double lowestPrice = randomNumber(lowestPriceIsZero ? WITH_ZERO : NO_ZERO);
            String route = routeIterator.next();
            fakeOutputs.add(new String[] {
                    route, departDate, returnDate, String.format("%.2f", currentPrice) , String.format("%.2f", lowestPrice)
            });
        }

        return fakeOutputs;
    }

    protected Double randomNumber(boolean needZero) {
        Random random = new Random();
        double num;
        int randInt = random.nextInt(5);
        if (needZero && randInt == 0) {
            num = 0.00;
        } else {
            num = random.nextDouble(1451.00) + 500;
        }

        return num;
    }

}
