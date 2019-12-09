package model;

import javafx.beans.property.DoubleProperty;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Shape;
/**
 * 
 * @author Fawaz
 * The Interface that enables drawing of JavaFX shapes
 */
public interface Drawable {
	
	/**
	 * this method defines how the shape should be drawn.
	 * each shape has a different implementation of draw();
	 */
	public void draw(MouseEvent event, double startingX, double startingY);
	
	/**
	 * Draws the "alternative" form of the shape
	 * Rectangle: Square
	 * Ellipse: Circle
	 * Triangle: Triangle
	 * Line: Line
	 * @param event the event 
	 * @param startingX the X coordinate of the user mouse when a press is registered
	 * @param startingY the Y coordinate of the user mouse when a press is registered
	 */
	public void alternativeDraw(MouseEvent event, double startingX, double startingY);
	
	/**
	 * enables drawing, and clicking on the shapes.
	 * @param starti
	 */
	public default void EnableDrawing(Shape shape) {
		shape.addEventHandler(MouseEvent.DRAG_DETECTED, Model.controller.new DragStarter());
		shape.setOnMouseDragOver(Model.controller.new Drag());
		shape.setOnMousePressed(Model.controller.new BrushDraw());
		shape.setOnMouseClicked(Model.controller.new Click());
	}
	
	/**
	 * returns the widthProperty of each shape.
	 * each shape has it's own width property
	 * @return
	 */
	public DoubleProperty getWidthProperty();
	
	/**
	 * returns the heightProperty of each shape.
	 * each shape has it's own width property
	 * @return
	 */
	public DoubleProperty getHeightProperty();
	
}
