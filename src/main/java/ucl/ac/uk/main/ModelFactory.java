package ucl.ac.uk.main;

import java.io.File;

public class ModelFactory {

    private static Model model;


    //When model is first accessed from a feature, a model is made. Only one model object exists throughout runtime
    public static Model getModel() throws Exception {
        if(model == null){
            model = new Model(new File(("./data/notes.csv")));
        }
        return model;
    }
}
