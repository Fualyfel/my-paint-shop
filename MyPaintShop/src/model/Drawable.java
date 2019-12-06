package model;

import javafx.beans.property.DoubleProperty;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Shape;
/*
 * Defines the draw method for each shape 
 * Each shape has its own implementation of the draw method
 */
public interface Drawable {
	
	public void draw(MouseEvent event, double startingX, double startingY);
	
	public default void EnableDrawing(Shape shape) {
		shape.addEventHandler(MouseEvent.DRAG_DETECTED, Model.controller.new DragStarter());
		shape.setOnMouseDragOver(Model.controller.new Drag());
		shape.setOnMousePressed(Model.controller.new BrushDraw());
		shape.setOnMouseClicked(Model.controller.new Click());
	}
	
	public DoubleProperty getWidthProperty();
	
	public DoubleProperty getHeightProperty();
	
}
