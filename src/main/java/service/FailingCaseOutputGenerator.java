package service;

import interfaces.FakeOutputGenerator;
import util.DateHelper;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static util.DateHelper.getDateFromAnotherDate;

public class FailingCaseOutputGenerator extends FakeOutputGenerator {

    public FailingCaseOutputGenerator(String outputName) {
        this.outputFile = outputName;
    }
    @Override
    protected List<String[]> createFakeOutput(Iterator<String> routeIterator) {
        return createFakeOutputHelper(routeIterator, WITH_ZERO);
    }
}
