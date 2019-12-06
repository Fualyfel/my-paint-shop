package model;

import java.io.File;

import javafx.stage.FileChooser;
import javafx.stage.Window;

public class FileSaver {
	public static File ImageSaver() {
		Window loadingStage = null;
		FileChooser savingFileChooser = new FileChooser();
		savingFileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Picture (*.png)", "*.png"));
		File newImage = savingFileChooser.showSaveDialog(loadingStage);

		return newImage;
	}

}
