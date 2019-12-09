package controller;

import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Button;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;
import javafx.scene.paint.Color;
import javafx.scene.shape.Shape;
import model.Model;
import model.State;
/**
 * The controller handles all user interaction, such as clicks, drags, keyboard presses, and calls the
 * appropriate methods in the model.
 */
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
	@FXML
	Button duplicateButton;
	@FXML
	Button removeButton;
	@FXML
	MenuItem newFile;
	@FXML
	MenuItem openFile;
	@FXML
	MenuItem saveFile;
	@FXML
	MenuItem quit;
	@FXML
	MenuItem undo;
	@FXML
	MenuItem cut;
	public TextField verticalField;
	@FXML
	public TextField horizontalField;
	@FXML
	public ColorPicker selectedBorder;
	@FXML
	public ColorPicker selectedFill;
	ToggleGroup shapeGroup = new ToggleGroup();
	
	public void setModel(Model model) {
		this.model = model;
	}

	/**
	 * initialize() is called whenever the controller is initilaized (application starts.)
	 */
	@FXML
	private void initialize() {
		setModel(new Model());
		model.setController(this);
		bindCanvasToPane();
		clipChildren(mainPane);
		mainPane.getParent().setOnKeyPressed(new ShortcutController());
		model.getStates().push(new State(model.getShapes(mainPane), model.getImagefromNode(canvas)));
		mainPane.addEventHandler(MouseEvent.MOUSE_RELEASED, new StateController());
		mainPane.addEventHandler(MouseEvent.MOUSE_CLICKED, new Click());
		canvas.addEventHandler(MouseEvent.DRAG_DETECTED, new DragStarter());
		canvas.setOnMouseDragOver(new Drag());
		canvas.setOnMousePressed(new BrushDraw());
		duplicateButton.setOnAction(new ButtonController());
		removeButton.setOnAction(new ButtonController());
		openFile.setOnAction(new MenuController());
		newFile.setOnAction(new MenuController());
		saveFile.setOnAction(new MenuController());
		quit.setOnAction(new MenuController());
		undo.setOnAction(new MenuController());
		verticalField.textProperty();
		circleToggle.setToggleGroup(shapeGroup);
		triangleToggle.setToggleGroup(shapeGroup);
		rectangleToggle.setToggleGroup(shapeGroup);
		lineToggle.setToggleGroup(shapeGroup);
		brushToggle.setToggleGroup(shapeGroup);
		borderColor.setValue(Color.BLACK);
		fillColor.setValue(Color.TRANSPARENT);

		horizontalField.textProperty().addListener((ov, oldValue, newValue) -> {
			if (newValue.matches("\\d*")) {
				try {
					int value = Integer.parseInt(newValue);
				} catch (NumberFormatException e) {
					System.out.println("Illegal Dimension");
				}
			} else {
				horizontalField.setText(oldValue);
				horizontalField.positionCaret(horizontalField.getLength());
			}
		});
		verticalField.textProperty().addListener((ov, oldValue, newValue) -> {
			if (newValue.matches("\\d*")) {
				try {
					int value = Integer.parseInt(newValue);
				} catch (NumberFormatException e) {
					System.out.println("Illegal Dimension");
				}
			} else {
				verticalField.setText(oldValue);
				verticalField.positionCaret(verticalField.getLength());
			}
		});
		selectedFill.valueProperty().addListener((ov, oldVal, newVal) -> {
			try {
			model.selectedShape.setFill(newVal);
			} catch (Exception e) {
				System.out.println("No shape selected: " + e);
			}
		});
		
		selectedBorder.valueProperty().addListener((ov, oldVal, newVal) -> {
			try {
			model.selectedShape.setStroke(newVal);
			} catch (Exception e) {
				System.out.println("No shape selected: " + e);
			}
		});
	}

	/**
	 * Handler for shape dragging process.
	 */
	public class DragStarter implements EventHandler<MouseEvent> {
		@Override
		public void handle(MouseEvent event) {
			((Node) event.getSource()).startFullDrag();
			if (shapeGroup.getSelectedToggle() != brushToggle) {
				if (shapeGroup.getSelectedToggle() == circleToggle) {
					model.addShapeToPane("ellipse", borderColor.getValue(), fillColor.getValue(), event, mainPane);
				} else if (shapeGroup.getSelectedToggle() == rectangleToggle) {
					model.addShapeToPane("rectangle", borderColor.getValue(), fillColor.getValue(), event, mainPane);
				} else if (shapeGroup.getSelectedToggle() == triangleToggle) {
					model.addShapeToPane("triangle", borderColor.getValue(), fillColor.getValue(), event, mainPane);
				} else if (shapeGroup.getSelectedToggle() == lineToggle) {
					model.addShapeToPane("line", borderColor.getValue(), fillColor.getValue(), event, mainPane);
				}
			}
		}
	}

	/**
	 * Handle 
	 */
	public class BrushDraw implements EventHandler<MouseEvent> {
		@Override
		public void handle(MouseEvent event) {
			System.out.println("Pressed");
			if (shapeGroup.getSelectedToggle() == brushToggle) {
				model.drawBrush(event, canvas.getGraphicsContext2D(), fillColor.getValue());
			}
		}

	}

	public class Drag implements EventHandler<MouseEvent> {

		@Override
		public void handle(MouseEvent event) {
			if (shapeGroup.getSelectedToggle() == null) {
				System.out.println("No shape selected");
			} else if (shapeGroup.getSelectedToggle() != brushToggle) {
				if (event.isShiftDown()) {
					model.alternativeDrawShape(event, model.getShape());
				} else {
					model.drawShape(event, model.getShape());
				}
			} else {
				model.drawBrush(event, canvas.getGraphicsContext2D(), fillColor.getValue());
			}
		}

	}

	public class Click implements EventHandler<MouseEvent> {
		@Override
		public void handle(MouseEvent event) {
			System.out.println("Target: " + event.getTarget());
			if (event.getTarget() instanceof Shape && (shapeGroup.getSelectedToggle() != brushToggle)) {
				System.out.println("selected");
				model.selectShape(event);
			} else {
				System.out.println("deselected");
				model.deSelectShape();
			}
		}
	}

	public class MenuController implements EventHandler<ActionEvent> {
		@Override
		public void handle(ActionEvent event) {
			if (event.getSource() == openFile) {
				model.load(mainPane, canvas);
			} else if (event.getSource() == newFile) {
				model.resetPaneAndCanvas(mainPane, canvas);
			} else if (event.getSource() == saveFile) {
				model.save(mainPane);
			} else if (event.getSource() == quit) {
				model.quit();
			} else if (event.getSource() == undo) {
				model.undo(mainPane, canvas);
			} else if (event.getSource() == cut) {
				model.cutShape(model.selectedShape, mainPane);
			}
		}
	}

	public class ButtonController implements EventHandler<ActionEvent> {
		@Override
		public void handle(ActionEvent event) {
			if (event.getSource() == duplicateButton) {
				try {
					model.duplicateShape(model.selectedShape, mainPane);
				} catch (Exception e) {
					System.out.println("No Shape Selected: " + e.getMessage());
				}
			} else if (event.getSource() == removeButton) {
				model.deleteShape(model.selectedShape, mainPane);
			}

		}
	}

	public class ShortcutController implements EventHandler<KeyEvent> {
		KeyCombination undoShortcut = new KeyCodeCombination(KeyCode.Z, KeyCombination.CONTROL_DOWN);
		KeyCombination newFileShortcut = new KeyCodeCombination(KeyCode.N, KeyCombination.CONTROL_DOWN);
		KeyCombination saveFileShortcut = new KeyCodeCombination(KeyCode.S, KeyCombination.CONTROL_DOWN);
		KeyCombination copyShapeShortcut = new KeyCodeCombination(KeyCode.C, KeyCombination.CONTROL_DOWN);
		KeyCombination cutShapeShortcut = new KeyCodeCombination(KeyCode.X, KeyCombination.CONTROL_DOWN);
		KeyCombination pasteShapeShortcut = new KeyCodeCombination(KeyCode.V, KeyCombination.CONTROL_DOWN);
		KeyCombination openFileShortcut = new KeyCodeCombination(KeyCode.O, KeyCombination.CONTROL_DOWN);

		@Override
		public void handle(KeyEvent event) {
			if (undoShortcut.match(event)) {
				model.undo(mainPane, canvas);
			} else if (newFileShortcut.match(event)) {
				model.resetPaneAndCanvas(mainPane, canvas);
			} else if (saveFileShortcut.match(event)) {
				model.save(mainPane);
			} else if (copyShapeShortcut.match(event)) {
				model.copyShape(model.selectedShape, mainPane);
			} else if (cutShapeShortcut.match(event)) {
				model.cutShape(model.selectedShape, mainPane);
			}
		}
	}
	
	public class StateController<T extends Event> implements EventHandler<T> {

		@Override
		public void handle(T event) {
			if (shapeGroup.getSelectedToggle() == brushToggle && !(event.getTarget() instanceof Shape)) {
				model.saveState(mainPane, canvas);
				System.out.println("from Stroke");
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
	public void clipChildren(Region pane) {

		final javafx.scene.shape.Rectangle outputClip = new javafx.scene.shape.Rectangle();
		pane.setClip(outputClip);

		pane.layoutBoundsProperty().addListener((ov, oldValue, newValue) -> {
			outputClip.setWidth(newValue.getWidth());
			outputClip.setHeight(newValue.getHeight());
		});
	}
}
