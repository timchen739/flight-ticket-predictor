package service;

import interfaces.FakeOutputGenerator;

import java.util.Iterator;
import java.util.List;

public class ResultNotFoundOutputGenerator extends FakeOutputGenerator {
    private FakeOutputGenerator passingCaseOutputGenerator;
    public ResultNotFoundOutputGenerator(String outputFile) {
        this.outputFile = outputFile;
        passingCaseOutputGenerator = new PassingCaseOutputGenerator(this.outputFile);
    }

    @Override
    protected List<String[]> createFakeOutput(Iterator<String> routeIterator) {
        List<String[]> result = createFakeOutputHelper(routeIterator, NO_ZERO);

       String[] lastRow = result.get(result.size() - 1);

       lastRow[3] = "N/A";
       lastRow[4] = "N/A";

        return result;
    }
}
