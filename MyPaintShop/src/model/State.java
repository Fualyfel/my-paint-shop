package model;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

import javax.imageio.ImageIO;

import javafx.embed.swing.SwingFXUtils;
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
public class State /* implements Serializable */ {
	private ArrayList<Shape> shapeList;
	private transient Image image;

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

	/*
	 * Serialization to be implemented at a later date.
	 */
//    private void readObject(ObjectInputStream s) throws IOException, ClassNotFoundException {
//        s.defaultReadObject();
//        shapeList = (ArrayList<Shape>) s.readObject();
//        image = SwingFXUtils.toFXImage(ImageIO.read(s), null);
//    }
//
//    private void writeObject(ObjectOutputStream s) throws IOException {
//        s.defaultWriteObject();
//        s.writeObject(shapeList);
//        ImageIO.write(SwingFXUtils.fromFXImage(image, null), "png", s);
//    }

}