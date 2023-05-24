package datamodel;

import interfaces.FakeOutputGenerator;
import service.FailingCaseOutputGenerator;
import service.PassingCaseOutputGenerator;
import service.ResultNotFoundOutputGenerator;

/*
* This class serves the purpose of configuring the model to generate needed fake outputs
*
* goodData: indicates if we want to generate a passing case outputs, true means passing output is needed
*
* noResultFoundForARoute: controls whether we want to include a mock result when the model
* is not able to get calculated prices for a specific route, in another word no result found for a route
* True means include no result in the generated output
*
* */
public class DataModelContext {
    private boolean goodData = true;
    private boolean noResultFoundForARoute = false;

    public boolean isGoodData() {
        return goodData;
    }

    public void setGoodData(boolean goodData) {
        this.goodData = goodData;
    }

    public void setNoResultFoundForARoute(boolean noResultFoundForARoute) {
        this.noResultFoundForARoute = noResultFoundForARoute;
    }

    public FakeOutputGenerator createOutputGenerator(String outputName) {
        if(goodData && noResultFoundForARoute) {
            return new ResultNotFoundOutputGenerator(outputName);
        }

        if(goodData) {
            return new PassingCaseOutputGenerator(outputName);
        }

        return new FailingCaseOutputGenerator(outputName);
    }
}
