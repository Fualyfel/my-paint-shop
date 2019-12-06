package model;

import java.util.ArrayList;

import javafx.scene.image.Image;
import javafx.scene.shape.Shape;

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