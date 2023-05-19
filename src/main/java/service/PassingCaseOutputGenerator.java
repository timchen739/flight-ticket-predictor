package service;

import interfaces.FakeOutputGenerator;
import util.DateHelper;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class PassingCaseOutputGenerator extends FakeOutputGenerator {

    public PassingCaseOutputGenerator(String outputName) {
        this.outputFile = outputName;
    }
    @Override
    protected List<String[]> createFakeOutput(String ...routes) {
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

        return fakeOutputs;
    }
}
