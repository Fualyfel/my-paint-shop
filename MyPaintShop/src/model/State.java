package model;

import java.util.ArrayList;

import javafx.scene.image.Image;
import javafx.scene.shape.Shape;
/**
 * A State is defined by two objects. an ArrayList of shapes and an Image object.
 * It is approximately what the canvas and pane look like at any given moment.
 * <p>
 * the ArrayList holds the Shape objects of the pane, and the Image objects holds an image of a canvas.
 * This class is used to implement the undo functions of the program by cycling and saving states after any action
 * <p>
 * See {@link Model#saveState undo} to see how states are saved. And
 * <p>
 * {@link Model#undo undo} to see how the undo functionality is implementing
 * 
 * @author Fawaz
 */
public class State {
	private final ArrayList<Shape> shapeList;
	private final Image image;

	public State(ArrayList<Shape> shapeList, Image image) {
		this.shapeList = shapeList;
		this.image = image;
	}

	public ArrayList<Shape> getShapeList() {
		return shapeList;
	}

	public Image getImage() {
		return image;
	}

}