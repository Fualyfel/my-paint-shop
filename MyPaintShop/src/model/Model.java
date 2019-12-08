package model;

import java.awt.image.BufferedImage;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
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
import javafx.beans.value.ObservableValue;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.Event;
import javafx.geometry.Bounds;

/*
 * The Model handles most of the logic in the application, creating shapes and adding them to the
 * pane. selecting shapes, saving the image, and so on.
 */
public class Model {

	double startingX = 0.0;
	double startingY = 0.0;

	private Shape shape;
//	private ArrayList<Shape> selectedShapes = new ArrayList<Shape>();
	public Shape selectedShape;
	public Shape copiedShape;
	public static Controller controller;
	final javafx.scene.shape.Rectangle boundary = new javafx.scene.shape.Rectangle();
	public Stack<State> states = new Stack<State>();

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

	public void addShapeToPane(String shapeName, Paint border, Paint fill, MouseEvent event, Pane pane) {
		startingX = event.getX();
		startingY = event.getY();
		System.out.println("Drag Detected: X : " + startingX + " Y: " + startingY);
		shape = createShape(shapeName, fill, border);
		pane.getChildren().add(shape);
	}

	public void drawShape(MouseEvent event, Shape shape) {
		((Drawable) shape).draw(event, startingX, startingY);
	}
	
	public void alternativeDrawShape(MouseEvent event, Shape shape) {
		((Drawable) shape).alternativeDraw(event, startingX, startingY);
	}
	
	

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

	/*
	 * selects the shape by marking it with a border
	 */
	public void selectShape(Event event) {
		if (event.getSource() instanceof Shape) {
			unbindPropertyToShape(controller.horizontalField.textProperty(), controller.verticalField.textProperty(),
					selectedShape);
			selectedShape = (Shape) event.getSource();
			System.out.println("Selected: " + selectedShape.getClass());
			attachBoundingRectangle((Node) selectedShape);
			bindPropertyToShape(controller.horizontalField.textProperty(), controller.verticalField.textProperty(),
					selectedShape);
		}
	}

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

	public void deSelectShape() {
		unbindPropertyToShape(controller.horizontalField.textProperty(), controller.verticalField.textProperty(),
				selectedShape);
		selectedShape = null;
		controller.mainPane.getChildren().remove(boundary);
	}

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

	public void cutShape(Shape shape, Pane pane) {
		copyShape(shape, pane);
		deleteShape(shape, pane);
	}

	public void pasteShape(Shape shape, Pane pane) {

	}

	/*
	 * deletes the shape by removing it from the pane.
	 */
	public void deleteShape(Shape shape, Pane pane) {
		deSelectShape();
		pane.getChildren().remove(shape);
	}

	public void deleteAllShapes(ArrayList<Shape> shapes, Pane pane) {
		for (Shape s : shapes) {
			deleteShape(s, pane);
		}
	}

	public void deleteCanvas(Pane pane) {
		for (Node n : pane.getChildren()) {
			if (n instanceof Canvas) {
				pane.getChildren().remove(n);
			}
		}
	}

	/*
	 * saves the pane and canvas as an image
	 */
	public void save(Pane pane) {
		try {
			WritableImage image = getImagefromNode(pane);
			BufferedImage bImage = SwingFXUtils.fromFXImage(image, null);
		    ImageIO.write(bImage, "png", FileSaver.ImageSaver());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/*
	 * loads an image into the canvas
	 */
	public void load(Pane pane, Canvas canvas) {
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

	public void duplicateShape(Shape selectedShape, Pane pane) throws Exception {
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

	public void saveState(Pane pane, Canvas canvas) {
		WritableImage image = getImagefromNode(canvas);
		ArrayList<Shape> shapes = new ArrayList<Shape>();
		shapes = getShapes(pane);
		State s = new State(shapes, image);
		getStates().push(s);
		System.out.println("state is saved.");
	}

	public void resetPaneAndCanvas(Pane mainPane, Canvas canvas) {
		deleteAllShapes(getShapes(mainPane), mainPane);
		GraphicsContext graphicsCon = canvas.getGraphicsContext2D();
		graphicsCon.clearRect(canvas.getLayoutX(), canvas.getLayoutY(), canvas.getWidth(), canvas.getHeight());
//		canvas.addEventHandler(MouseEvent.DRAG_DETECTED, controller.new DragStarter());
//		canvas.setOnMouseDragOver(controller.new Drag());
//		canvas.setOnMousePressed(controller.new BrushDraw());
//		controller.bindCanvasToPane();
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

	public WritableImage getImagefromNode(Node node) {
		SnapshotParameters params = new SnapshotParameters();
		params.setFill(Color.TRANSPARENT);
		WritableImage image = node.snapshot(params, null);
		return image;
	}
}
