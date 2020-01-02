package model;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

import javax.imageio.ImageIO;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.shape.Shape;
/**
 * A State is defined by an observable list of nodes
 * It is what the canvas and pane look like at any given moment.
 * <p>
 * the observable list holds the children of the pane. 
 * This class is used to implement the undo functions of the program by cycling and saving states after any action
 * <p>
 * See {@link Model#saveState undo} to see how states are saved. And
 * <p>
 * {@link Model#undo undo} to see how the undo functionality is implementing
 * 
 * @author Fawaz
 */
public class State /* implements Serializable */ {
	private transient ObservableList<Node> nodes;
	
	public State(ObservableList<Node> nodes) {
		this.nodes = FXCollections.<Node>observableArrayList(nodes);
	}
	
	public ObservableList<Node> getNodes() {
		return nodes;
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