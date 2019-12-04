package controller;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;
import javafx.scene.paint.Color;
import javafx.scene.shape.Shape;
import model.Model;

public class Controller {
	private Model model;
	@FXML
	public Pane mainPane;
	@FXML
	public Canvas canvas;
	@FXML
	ToggleButton circleToggle;
	@FXML
	ToggleButton rectangleToggle;
	@FXML
	ToggleButton triangleToggle;
	@FXML
	ToggleButton lineToggle;
	@FXML
	ToggleButton brushToggle;
	@FXML
	ColorPicker borderColor;
	@FXML
	ColorPicker fillColor;

	ToggleGroup shapeGroup = new ToggleGroup();

	public void setModel(Model model) {
		this.model = model;
	}

	/*
	 * initialize() is called whenever the application is launched. ( similar to
	 * start() )
	 */
	@FXML
	private void initialize() {
		setModel(new Model());
		model.setController(this);
		bindCanvasToPane();
		canvas.addEventHandler(MouseEvent.DRAG_DETECTED, new DragStarter());
		canvas.setOnMouseDragOver(new Drag());
		canvas.setOnMousePressed(new BrushDraw());
		clipChildren(mainPane);
		circleToggle.setToggleGroup(shapeGroup);
		triangleToggle.setToggleGroup(shapeGroup);
		rectangleToggle.setToggleGroup(shapeGroup);
		lineToggle.setToggleGroup(shapeGroup);
		brushToggle.setToggleGroup(shapeGroup);
		borderColor.setValue(Color.BLACK);
        mainPane.getParent().addEventFilter(KeyEvent.KEY_PRESSED, (KeyEvent event) -> {
            System.out.println("Key pressed");
            if (event.getCode() == KeyCode.F1) {
            	model.undo();
                System.out.println("F1 pressed");
            }
            event.consume();
        });
	}

	/*
	 * starts the dragging process for shape drawing
	 */
	public class DragStarter implements EventHandler<MouseEvent> {
		@Override
		public void handle(MouseEvent event) {
			((Node) event.getSource()).startFullDrag();
			if (shapeGroup.getSelectedToggle() != brushToggle) {
				if (shapeGroup.getSelectedToggle() == circleToggle) {
					model.addShapeToPane("ellipse", event, mainPane);
				} else if (shapeGroup.getSelectedToggle() == rectangleToggle) {
					model.addShapeToPane("rectangle", event, mainPane);
				} else if (shapeGroup.getSelectedToggle() == triangleToggle) {
					model.addShapeToPane("triangle", event, mainPane);
				} else if (shapeGroup.getSelectedToggle() == lineToggle) {
					model.addShapeToPane("line", event, mainPane);
				}
			}
		}
	}

	/*
	 * Handles user presses on the canvas with the brush tool selected.
	 */
	public class BrushDraw implements EventHandler<MouseEvent> {

		@Override
		public void handle(MouseEvent event) {
			System.out.println("Pressed");
			if (shapeGroup.getSelectedToggle() == brushToggle) {
				model.drawBrush(event, canvas.getGraphicsContext2D());
			}
		}

	}

	public class Drag implements EventHandler<MouseEvent> {
		@Override
		public void handle(MouseEvent event) {
			if (shapeGroup.getSelectedToggle() != brushToggle) {
				model.drawShape(event, model.getShape());
			} else {
				model.drawBrush(event, canvas.getGraphicsContext2D());
			}
		}

	}

	/*
	 * method that helps with resizing the canvas
	 */
	public void bindCanvasToPane() {
		canvas.widthProperty().bind(mainPane.widthProperty());
		canvas.heightProperty().bind(mainPane.heightProperty());
	}

	/*
	 * handles clipping (when the shape is out of the pane.)
	 */
	static void clipChildren(Region pane) {

		final javafx.scene.shape.Rectangle outputClip = new javafx.scene.shape.Rectangle();
		pane.setClip(outputClip);

		pane.layoutBoundsProperty().addListener((ov, oldValue, newValue) -> {
			outputClip.setWidth(newValue.getWidth());
			outputClip.setHeight(newValue.getHeight());
		});
	}

}
