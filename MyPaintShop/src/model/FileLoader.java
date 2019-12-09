package model;

 

import java.io.File;

 

import javafx.stage.FileChooser;
import javafx.stage.Window;

 
/**
 * A utility class that displays a file selection window
 * @author Waleed
 */
public class FileLoader {
    
	/**
	 * displays a file selection window. and returns the file selected by the user.
	 * @author Waleed
	 * @return File selected
	 */
    public static File ImageLoader() {
        
        Window loadingStage = null;
        FileChooser loadingFileChooser = new FileChooser();
        loadingFileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Picture (*.png)", "*.png"));
        File newImage = loadingFileChooser.showOpenDialog(loadingStage); 
        
        
        return newImage;
    }

 

}