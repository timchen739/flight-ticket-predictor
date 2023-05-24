package datamodel;

import interfaces.FakeOutputGenerator;
import service.FailingCaseOutputGenerator;
import service.PassingCaseOutputGenerator;
import service.ResultNotFoundOutputGenerator;

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
