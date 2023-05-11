package interfaces;

import entity.ModelInput;
import entity.ModelOutput;

public interface Predictor {
    ModelOutput predict(ModelInput input);
}
