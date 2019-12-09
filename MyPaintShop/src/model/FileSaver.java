package model;

import java.io.File;

import javafx.stage.FileChooser;
import javafx.stage.Window;

/**
 * A utility class that displays a file selection window
 * @author Waleed
 */
public class FileSaver {
	
	
	/**
	 * displays a file selection window, and saves the file in the specified path.
	 * @author Waleed
	 * @return File selected
	 */
	public static File ImageSaver() {
		Window loadingStage = null;
		FileChooser savingFileChooser = new FileChooser();
		savingFileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Picture (*.png)", "*.png"));
		File newImage = savingFileChooser.showSaveDialog(loadingStage);

		return newImage;
	}

}
