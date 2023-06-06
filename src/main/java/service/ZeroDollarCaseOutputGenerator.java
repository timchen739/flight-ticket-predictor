package service;

import interfaces.FakeOutputGenerator;

import java.util.Iterator;
import java.util.List;

public class ZeroDollarCaseOutputGenerator extends FakeOutputGenerator {

    public ZeroDollarCaseOutputGenerator(String outputName) {
        this.outputFile = outputName;
    }
    @Override
    protected List<String[]> createFakeOutput(Iterator<String> routeIterator) {
        return createFakeOutputHelper(routeIterator, WITH_ZERO);
    }
}
