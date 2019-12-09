package model;

 

import java.io.File;

 

import javafx.stage.FileChooser;
import javafx.stage.Window;

 
/**
 * A utility class that returns the image that the user wants to open
 * @author Waleed
 */
public class FileLoader {
    
    public static File ImageLoader() {
        
        Window loadingStage = null;
        FileChooser loadingFileChooser = new FileChooser();
        loadingFileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Picture (*.png)", "*.png"));
        File newImage = loadingFileChooser.showOpenDialog(loadingStage); 
        
        
        return newImage;
    }

 

}