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
	public static File fileSaver() {
		Window loadingStage = null;
		FileChooser savingFileChooser = new FileChooser();
		savingFileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Picture (*.png)", "*.png"),
													   new FileChooser.ExtensionFilter("MyPaintShop Object (*.mps)", "*.mps"));
		File newFile = savingFileChooser.showSaveDialog(loadingStage);
		return newFile;
	}

}
