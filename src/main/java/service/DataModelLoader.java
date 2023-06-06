package service;

import datamodel.DataModelContext;
import datamodel.TheDataModel;

public class DataModelLoader {
    private static DataModelLoader instance;

    private DataModelLoader() {

    }

    public static DataModelLoader getInstance() {
        if(instance == null)
            return new DataModelLoader();
        return instance;
    }

    public TheDataModel loadModel(String version, DataModelContext context) {
        if(version.trim().length() == 0) {
            return new TheDataModel(context);
        }

        return new TheDataModel(version, context);
    }
}
