package service;

import interfaces.FakeOutputGenerator;
import util.DateHelper;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import static util.DateHelper.getDateFromAnotherDate;

public class FailingCaseOutputGenerator extends FakeOutputGenerator {

    public FailingCaseOutputGenerator(String outputName) {
        this.outputFile = outputName;
    }
    @Override
    protected List<String[]> createFakeOutput(String ...routes) {
        String departDate = getDateFromAnotherDate(60, LocalDate.now());
        String returnDate = getDateFromAnotherDate(14, LocalDate.parse(departDate, DateTimeFormatter.ofPattern("MM/dd/yyyy")));

        List<String[]> fakeOutputs = new ArrayList<>();
        for(String route : routes) {
            double currentPrice = randomNumber(NO_ZERO);
            double lowestPrice = randomNumber(WITH_ZERO);
            fakeOutputs.add(new String[] {
                    route, departDate, returnDate, String.format("%.2f", currentPrice) , String.format("%.2f", lowestPrice)
            });
        }
        return fakeOutputs;
    }
}
