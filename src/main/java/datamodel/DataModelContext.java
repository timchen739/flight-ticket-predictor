package datamodel;

import interfaces.FakeOutputGenerator;
import service.DrasticPriceChangeCaseOutputGenerator;
import service.ZeroDollarCaseOutputGenerator;
import service.PassingCaseOutputGenerator;
import service.ResultNotFoundOutputGenerator;

import static constants.TestScenario.*;

/*
* This class serves the purpose of configuring the model to generate needed fake outputs
*
* testScenario: indicates what type of fake outputs we want the model to create. Refer to TestScenario class for available scenarios we are mocking
*
* */
public class DataModelContext {
    private boolean goodData = true;
    private boolean noResultFoundForARoute = false;

    private String testScenario;

    public void setTestScenario(String testScenario) {
        this.testScenario = testScenario;
    }

    public FakeOutputGenerator createOutputGenerator(String outputName) {
        if (testScenario.equals(RESULT_NOT_FOUND_SCENARIO)) {
            return new ResultNotFoundOutputGenerator(outputName);
        }
        else if (testScenario.equals(PASSING_SCENARIO)) {
            return new PassingCaseOutputGenerator(outputName);
        }
        else if (testScenario.equals(ZERO_DOLLAR_SCENARIO)) {
            return new ZeroDollarCaseOutputGenerator(outputName);
        }
        else if (testScenario.equals(DRASTIC_PRICE_CHANGE_SCENARIO)) {
            return new DrasticPriceChangeCaseOutputGenerator(outputName);
        }
        else {
            throw new Error("No Test Scenario Defined");
        }
    }
}
