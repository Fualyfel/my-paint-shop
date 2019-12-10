package model;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Stack;

import javax.imageio.ImageIO;
import controller.Controller;
import javafx.scene.Node;
import javafx.scene.SnapshotParameters;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.WritableImage;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Shape;
import javafx.util.StringConverter;
import javafx.util.converter.NumberStringConverter;
import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.StringProperty;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.Event;
import javafx.geometry.Bounds;

/**
 * The Model handles most of the logic in the application, creating shapes and
 * adding them to the pane. selecting shapes, saving the image, and so on.
 */
public class Model {

	/**
	 * 
	 * startingX the X position of the starting coordinate of the shape drawn
	 *                  (the starting x position of the mouse.)
	 */
	double startingX = 0.0;

	/**
	 * startingY the Y position of the starting coordinate of the shape drawn
	 *                  (the starting Y position of the mouse.)
	 */
	double startingY = 0.0;

	/*
	 * the shape that is currently being drawn.
	 */
	private Shape shape;
//	private ArrayList<Shape> selectedShapes = new ArrayList<Shape>();
	/**
	 * the selected shape in the pane. selected by the controller's Click event
	 * handler
	 * 
	 * @see controller.Controller.Click
	 */
	public Shape selectedShape;
	/**
	 * the copiedShape. instantiated by the method copyShape or cutShape
	 * 
	 * @see model.Model.copyShape
	 * @see model.Model.cutShape
	 */
	public Shape copiedShape;
	public static Controller controller;
	/**
	 * the boundary of the selected shape. its width and height are
	 *                 set according to the selected shape
	 * @see attachBoundingRectangle
	 * @see selectedShape
	 */
	final javafx.scene.shape.Rectangle boundary = new javafx.scene.shape.Rectangle();
	public Stack<State> states = new Stack<State>();

	/**
	 * Creates a shape based on the controller's input of name, fill, and border
	 * 
	 * @param shapeName the name of the created shape
	 * @param fill      the fill color of the created shape
	 * @param border    the border color of the created shape
	 * @return the shape created
	 */
	public Shape createShape(String shapeName, Paint fill, Paint border) {
		if (shapeName.equalsIgnoreCase("rectangle"))
			return new Rectangle(fill, border);
		else if (shapeName.equalsIgnoreCase("ellipse"))
			return new Ellipse(fill, border);
		else if (shapeName.equalsIgnoreCase("triangle"))
			return new Triangle(fill, border);
		else if (shapeName.equalsIgnoreCase("line"))
			return new Line(fill, border);
		else
			return null;
	}

	/**
	 * adds the specified shape to the pane with 0 initial dimensions.
	 * 
	 * @param shapeName the name of the shape that is added to the pane
	 * @param border    the border of the shape that is added to the pane
	 * @param fill      the fill color of the shape that is added to the pane
	 * @param event     a MouseEvent to indicate where is the starting point of the
	 *                  shape
	 * @param pane      the pane onto which the shape will be added.
	 */
	public void addShapeToPane(String shapeName, Paint border, Paint fill, MouseEvent event, Pane pane) {
		startingX = event.getX();
		startingY = event.getY();
		System.out.println("Drag Detected: X : " + startingX + " Y: " + startingY);
		shape = createShape(shapeName, fill, border);
		pane.getChildren().add(shape);
		saveState(pane, controller.canvas);
	}

	/**
	 * Draws the shape according to its implementation of the draw method
	 * 
	 * @param event a MouseEvent to extract the current location of the mouse.
	 * @param shape the shape to be drawn.
	 * @see model.Drawable
	 */
	public void drawShape(MouseEvent event, Shape shape) {
		((Drawable) shape).draw(event, startingX, startingY);
	}

	/**
	 * Draws the shape according to its implementation of the alternativeDraw method
	 * 
	 * @param event a MouseEvent to extract the current location of the mouse.
	 * @param shape the shape to be drawn.
	 * @see model.Drawable
	 */
	public void alternativeDrawShape(MouseEvent event, Shape shape) {
		((Drawable) shape).alternativeDraw(event, startingX, startingY);
	}

	/**
	 * Draws on the canvas on the place where the MouseEvent was triggered
	 * 
	 * @param event       the MouseEvent which occurred
	 * @param context     the GraphicContext of the canvas
	 * @param strokeColor the brush color that will be used.
	 * @see MouseEvent
	 * @see GraphicsContext
	 */
	public void drawBrush(MouseEvent event, GraphicsContext context, Paint strokeColor) {
		context.setStroke(strokeColor);
		if (event.getEventType() == MouseEvent.MOUSE_PRESSED) {
			context.setLineWidth(3);
			context.beginPath();
			context.moveTo(event.getX(), event.getY());
			context.lineTo(event.getX(), event.getY());
			context.stroke();
		} else {
			context.lineTo(event.getX(), event.getY());
			context.setLineWidth(3);
			context.stroke();
		}
	}

	/**
	 * selects the shape and attach a border to it.
	 */
	public void selectShape(Event event) {
		if (event.getSource() instanceof Shape) {
			/*
			 * The selection process 1. Unbind the TextFields properties from the previous
			 * shape 2. Update the selectedShape 3. attach the bound to the shape. 4. set
			 * the color picker's value to the selected shape's color.
			 */
			unbindPropertyToShape(controller.horizontalField.textProperty(), controller.verticalField.textProperty(),
					selectedShape);
			selectedShape = (Shape) event.getSource();
			System.out.println("Selected: " + selectedShape.getClass());
			attachBoundingRectangle((Node) selectedShape);
			bindPropertyToShape(controller.horizontalField.textProperty(), controller.verticalField.textProperty(),
					selectedShape);
			controller.selectedFill.valueProperty().setValue((Color) selectedShape.fillProperty().getValue());
			controller.selectedBorder.valueProperty().setValue((Color) selectedShape.strokeProperty().getValue());
		}
	}

	/**
	 * Binds the shape width and height properties to the TextField's
	 * StringProperty. Enables real-time viewing and editing of the shape's
	 * properties
	 * 
	 * @param width  the StringProperty of the width TextField
	 * @param height the StringProperty of the height TextField
	 * @param shape  the shape to be binded with the TextField's properties
	 */
	public void bindPropertyToShape(StringProperty width, StringProperty height, Shape shape) {
		DoubleProperty shapeWidthProperty = ((Drawable) shape).getWidthProperty();
		DoubleProperty shapeHeightProperty = ((Drawable) shape).getHeightProperty();
		StringConverter<Number> converter = new NumberStringConverter();
		Bindings.bindBidirectional(width, shapeWidthProperty, converter);
		Bindings.bindBidirectional(height, shapeHeightProperty, converter);
		width.addListener((observable, oldVal, newVal) -> {
			attachBoundingRectangle(selectedShape);
		});
		height.addListener((observable, oldVal, newVal) -> {
			attachBoundingRectangle(selectedShape);
		});
	}

	/**
	 * Unbinds the shape width and height properties from the TextField's
	 * StringProperty
	 * 
	 * @param width  the StringProperty of the width TextField
	 * @param height the StringProperty of the height TextField
	 * @param shape  the shape to be unbinded from the TextField's properties
	 */
	private void unbindPropertyToShape(StringProperty width, StringProperty height, Shape shape) {
		try {
			DoubleProperty shapeWidthProperty = ((Drawable) shape).getWidthProperty();
			DoubleProperty shapeHeightProperty = ((Drawable) shape).getHeightProperty();
			Bindings.unbindBidirectional(width, shapeWidthProperty);
			Bindings.unbindBidirectional(height, shapeHeightProperty);
		} catch (Exception e) {
			System.out.println("No shape to unbind");
		}
	}

	/**
	 * deselects the current shape selected
	 */
	public void deSelectShape() {
		unbindPropertyToShape(controller.horizontalField.textProperty(), controller.verticalField.textProperty(),
				selectedShape);
		selectedShape = null;
		controller.mainPane.getChildren().remove(boundary);
	}

	/**
	 * Attaches a Bounds node to the selected shape
	 * 
	 * @param node the node to be bounded (defaultValue: selectedShape)
	 * @see Bounds
	 */
	public void attachBoundingRectangle(Node node) {
		Bounds bounds = node.getBoundsInParent();

		boundary.setStyle("-fx-stroke: forestgreen; " + "-fx-stroke-width: 2px; " + "-fx-stroke-dash-array: 12 2 4 2; "
				+ "-fx-stroke-dash-offset: 6; " + "-fx-stroke-line-cap: butt; " + "-fx-fill: rgba(255, 228, 118, .5);");

		boundary.setX(bounds.getMinX());
		boundary.setY(bounds.getMinY());
		boundary.setWidth(bounds.getWidth());
		boundary.setHeight(bounds.getHeight());
		if (controller.mainPane.getChildren().contains((boundary))) {
			controller.mainPane.getChildren().remove(boundary);
		}
		controller.mainPane.getChildren().add(boundary);
	}

	/**
	 * Copies the shape passed to this method. The copied shape is saved in the
	 * model
	 * 
	 * @param shape the shape to be copied
	 * @param pane  the pane where the shape is located
	 */
	public void copyShape(Shape shape, Pane pane) {
		if (shape instanceof Ellipse) {
			copiedShape = ((Ellipse) shape).clone(20);
		} else if (shape instanceof Rectangle) {
			copiedShape = ((Rectangle) shape).clone(20);
		} else if (shape instanceof Triangle) {
			copiedShape = new Triangle((Triangle) shape);
		} else if (shape instanceof Line) {
			copiedShape = new Line((Line) shape);
		}
	}

	/**
	 * Copies and deletes the shape passed to this method. The copied shape is saved
	 * in the model
	 * 
	 * @param shape the shape to be copied and deleted
	 * @param pane  the pane wher ethe shape is located
	 */
	public void cutShape(Shape shape, Pane pane) {
		copyShape(shape, pane);
		deleteShape(shape, pane);
	}

	/**
	 * A wrapper method for the duplicateShape method. This method is solely made
	 * for consistency with the controller naming scheme
	 * 
	 * @see Model.duplicateShape
	 */
	public void pasteShape(Shape shape, Pane pane) {
		duplicateShape(shape, pane);
	}

	/**
	 * deletes the shape by removing it from the pane.
	 */
	public void deleteShape(Shape shape, Pane pane) {
		saveState(pane, controller.canvas);
		deSelectShape();
		pane.getChildren().remove(shape);
	}

	/**
	 * deletes all the shapes in the pane.
	 */
	public void deleteAllShapes(ArrayList<Shape> shapes, Pane pane) {
		for (Shape s : shapes) {
			deSelectShape();
			pane.getChildren().remove(s);
		}
	}

	public void saveFile() {
		File file = FileSaver.fileSaver();
		String ext = getFileExtension(file);
		if (ext.equals("png")) {
			saveAsImage(controller.mainPane, file);
		} else if (ext.equals("mps")) {
			saveAsObject(file);
		} else {
			System.out.println("No extenstion provided.");
		}
	}

	private static String getFileExtension(File file) {
		String fileName = file.getName();
		if (fileName.lastIndexOf(".") != -1 && fileName.lastIndexOf(".") != 0)
			return fileName.substring(fileName.lastIndexOf(".") + 1);
		else
			return "";
	}

	/**
	 * saves all of the pane's contents as an image. This includes the canvas and
	 * all of the shapes
	 * 
	 * @param pane the pane to be saved
	 */
	public void saveAsImage(Pane pane, File file) {
		try {
			WritableImage image = getImagefromNode(pane);
			BufferedImage bImage = SwingFXUtils.fromFXImage(image, null);
			ImageIO.write(bImage, "png", file);
		} catch (Exception e) {
			System.out.println("Could not save file");
		}
	}

	/**
	 * Saves the file as a serialized MPS object. Currently not working.
	 * @param file the file to be saved
	 */
	public void saveAsObject(File file) {

		try {
			State currentState = states.peek();
			FileOutputStream fileStream = new FileOutputStream(file);
			ObjectOutputStream objStream = new ObjectOutputStream(fileStream);
			objStream.writeObject(currentState);
			objStream.close();
		} catch (Exception e) {
			System.out.println("Could not save file");
			e.printStackTrace();
		}
	}

	/**
	 * loads an image into the canvas
	 */
	public void loadImage(Pane pane, Canvas canvas) {
		BackgroundImage myBI;
		resetPaneAndCanvas(pane, canvas);
		try {
			myBI = new BackgroundImage(
					new Image(new FileInputStream(FileLoader.ImageLoader()), canvas.getWidth(), canvas.getHeight(),
							false, true),
					BackgroundRepeat.REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT,
					BackgroundSize.DEFAULT);
			pane.setBackground(new Background(myBI));
			canvas.getGraphicsContext2D().clearRect(0, 0, canvas.getWidth(), canvas.getHeight());

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Adds an exact copy of a shape to the pane at a location relative to the shape
	 * 
	 * @param selectedShape the shape to be duplicated
	 * @param pane          the pane where the shape is located
	 * @throws Exception
	 */
	public void duplicateShape(Shape selectedShape, Pane pane) {
		saveState(pane, controller.canvas);
		final double offset = 40;
		System.out.println("selected Shape name: " + selectedShape.getClass().getName());
		if (selectedShape instanceof Ellipse) {
			pane.getChildren().add(((Ellipse) selectedShape).clone(offset));
		} else if (selectedShape instanceof Rectangle) {
			pane.getChildren().add(((Rectangle) selectedShape).clone(offset));
		} else if (selectedShape instanceof Line) {
			Line selectedLine = (Line) selectedShape;
			Line newLine = selectedLine.clone();
			newLine.setStartX(newLine.getStartX() + offset);
			newLine.setStartY(newLine.getStartY() + offset);
			newLine.setEndX(newLine.getEndX() + offset);
			newLine.setEndY(newLine.getEndY() + offset);
			pane.getChildren().add(newLine);
		} else if (selectedShape instanceof Triangle) {
			pane.getChildren().add(((Triangle) selectedShape).clone(offset));
		}

	}

	/**
	 * loads the previous State object. and adds its shapes to the pane after
	 * clearing it, and prints its image to the canvas
	 * 
	 * @param pane   the pane to be Undoed
	 * @param canvas the canvas to be cleared and redrawn
	 * @see State
	 */
	public void undo(Pane pane, Canvas canvas) {
		try {
			if (states.size() > 1)
				states.pop();
			State previousState = states.get(states.size() - 1);
			ArrayList<Shape> previousShapes = previousState.getShapeList();
			WritableImage previousImage = (WritableImage) previousState.getImage();
			deleteAllShapes(getShapes(pane), pane);
			pane.getChildren().addAll(previousShapes);
			GraphicsContext graphicsCon = canvas.getGraphicsContext2D();
			graphicsCon.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
			graphicsCon.drawImage(previousImage, 0, 0);
			System.out.println("previous state restored");

		} catch (Exception e) {
			System.out.println("Nothing to undo. " + e.getMessage());
		}
	}

	/**
	 * creates a new State object from the current pane and canvas, and pushes it to
	 * the state stack.
	 * 
	 * @param pane   the pane which will get its objects saved onto State
	 * @param canvas the canvas which will get its image saved onto State
	 * @see State
	 * @see Model.undo
	 */
	public void saveState(Pane pane, Canvas canvas) {
		WritableImage image = getImagefromNode(canvas);
		ArrayList<Shape> shapes = new ArrayList<Shape>();
		shapes = getShapes(pane);
		State s = new State(shapes, image);
		getStates().push(s);
		System.out.println("state is saved.");
	}

	/**
	 * deletes all the shapes from the pane and clears the canvas
	 * 
	 * @param mainPane
	 * @param canvas
	 */
	public void resetPaneAndCanvas(Pane mainPane, Canvas canvas) {
		saveState(mainPane, canvas);
		deleteAllShapes(getShapes(mainPane), mainPane);
		GraphicsContext graphicsCon = canvas.getGraphicsContext2D();
		graphicsCon.clearRect(canvas.getLayoutX(), canvas.getLayoutY(), canvas.getWidth(), canvas.getHeight());
		canvas.addEventHandler(MouseEvent.DRAG_DETECTED, controller.new DragStarter());
		canvas.setOnMouseDragOver(controller.new Drag());
		canvas.setOnMousePressed(controller.new BrushDraw());
		controller.bindCanvasToPane();
	}

	public void quit() {
		Platform.exit();
		System.exit(0);
	}

	public Shape getShape() {
		return shape;
	}

	public Stack<State> getStates() {
		return states;
	}

	public Controller getController() {
		return controller;
	}

	public void setController(Controller controller) {
		this.controller = controller;
	}

	public ArrayList<Shape> getShapes(Pane pane) {
		ArrayList<Shape> shapes = new ArrayList<Shape>();
		for (Node n : pane.getChildren()) {
			if (n instanceof Shape)
				shapes.add((Shape) n);
		}
		return shapes;
	}

	/**
	 * returns an image from a node by taking a snapshot of it
	 * 
	 * @param node
	 * @return image snapshot of the node
	 * @see javafx.scene.Node.snapshot
	 */
	public WritableImage getImagefromNode(Node node) {
		SnapshotParameters params = new SnapshotParameters();
		params.setFill(Color.TRANSPARENT);
		WritableImage image = node.snapshot(params, null);
		return image;
	}
}
