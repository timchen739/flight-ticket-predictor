package service;

import interfaces.FakeOutputGenerator;
import util.DateHelper;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class PassingCaseOutputGenerator extends FakeOutputGenerator {

    public PassingCaseOutputGenerator(String outputName) {
        this.outputFile = outputName;
    }
    @Override
    protected List<String[]> createFakeOutput(Iterator<String> routeIterator) {
        return createFakeOutputHelper(routeIterator, NO_ZERO);
    }
}
