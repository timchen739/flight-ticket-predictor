package service;

import datamodel.TheDataModel;
import entity.ModelInput;
import entity.ModelOutput;
import interfaces.Predictor;

public class FlightTicketPredictor implements Predictor {
    private TheDataModel dataModel;

    public FlightTicketPredictor() {
        this.dataModel = new TheDataModel();
    }

    @Override
    public ModelOutput predict(ModelInput input) {
        return dataModel.predict(input);
    }
}
