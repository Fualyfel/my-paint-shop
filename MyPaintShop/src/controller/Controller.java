package controller;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Shape;
import model.Drawable;
import model.Ellipse;
import model.Rectangle;
import model.Triangle;

public class Controller {

	@FXML
	Pane mainPane;
	@FXML
	Canvas canvas;
	Shape rec;
	double startingX = 0.0;
	double startingY = 0.0;
	double terminalX = 0.0;
	double terminalY = 0.0;
	/*
	 * initialize() is called whenever the application is launched. ( similar to start() )
	 */
	@FXML
	private void initialize() {
		bindCanvasToPane();
		final GraphicsContext context = canvas.getGraphicsContext2D();
		canvas.addEventHandler(MouseEvent.DRAG_DETECTED, new DragStarter());
		canvas.setOnMouseDragOver(new Drag());
	}
	/*
	 * starts the dragging process for shape drawing
	 */
	class DragStarter implements EventHandler<MouseEvent> {
		@Override
		public void handle(MouseEvent event) {
			((Node) event.getSource()).startFullDrag();
			startingX = event.getX();
			startingY = event.getY();
			System.out.println("Drag Detected: X : " + startingX + " Y: " + startingY);
			rec = new Triangle(startingX, startingY);
			rec.addEventHandler(MouseEvent.DRAG_DETECTED, new DragStarter());
			rec.setOnMouseDragOver(new Drag());
			mainPane.getChildren().add(rec);
		}}
	
	class Drag implements EventHandler<MouseEvent> {

		@Override
		public void handle(MouseEvent event) {
			((Drawable) rec).draw(event, startingX, startingY);
		}
		
	}

	/*
	 * method that helps with resizing the canvas
	 */
	private void bindCanvasToPane() {
		canvas.widthProperty().bind(mainPane.widthProperty());
		canvas.heightProperty().bind(mainPane.heightProperty());
	}

}
