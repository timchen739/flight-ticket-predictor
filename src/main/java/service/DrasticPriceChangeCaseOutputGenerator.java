package service;

import interfaces.FakeOutputGenerator;

import java.util.Iterator;
import java.util.List;

public class DrasticPriceChangeCaseOutputGenerator extends FakeOutputGenerator {

    public DrasticPriceChangeCaseOutputGenerator(String outputName) {
        this.outputFile = outputName;
    }

    @Override
    protected List<String[]> createFakeOutput(Iterator<String> routeIterator) {
        List<String[]> result = createFakeOutputHelper(routeIterator, NO_ZERO);

        String[] lastRow = result.get(result.size() - 1);
        lastRow[4] = String.format("%.2f", Double.parseDouble(lastRow[4]) * 3);

        return result;
    }
}
