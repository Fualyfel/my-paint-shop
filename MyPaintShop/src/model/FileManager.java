package model;

 

import java.io.File;

 

import javafx.stage.FileChooser;
import javafx.stage.Window;

 
/**
 * A utility class that displays a file selection window
 * @author Waleed
 */
public class FileManager {
    
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
    
	public static File fileSaver() {
		Window loadingStage = null;
		FileChooser savingFileChooser = new FileChooser();
		savingFileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Picture (*.png)", "*.png"),
													   new FileChooser.ExtensionFilter("MyPaintShop Object (*.mps)", "*.mps"));
		File newFile = savingFileChooser.showSaveDialog(loadingStage);
		return newFile;
	}

 

}