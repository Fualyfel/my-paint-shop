package model;

import javafx.beans.property.DoubleProperty;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;

/**
 * Defines a JavaFX line that implements the Drawable interface and Cloneable interface
 * @see javafx.scene.shape.Line
 * @see Drawable
 * @see Cloneable
 * @author Fawaz
 */
public class Line extends javafx.scene.shape.Line implements Drawable, Cloneable {

	/**
	 * Creates an empty instance of Line, with a transparent fill color and a black border
	 */
	public Line() {
		super();
		setFill(Color.TRANSPARENT);
		setStroke(Color.BLACK);
	}
	
    /**
     * Creates a new instance of Line.
     * @param startX the horizontal coordinate of the start point of the line segment
     * @param startY the vertical coordinate of the start point of the line segment
     * @param endX the horizontal coordinate of the end point of the line segment
     * @param endY the vertical coordinate of the end point of the line segment
     */
	public Line(double startX, double startY, double endX, double endY) {
		super(startX, startY, endX, endY);
		setFill(Color.TRANSPARENT);
		setStroke(Color.BLACK);
	}
	
    /**
     * Creates a new instance of Line.
     * @param startX the horizontal coordinate of the start point of the line segment
     * @param startY the vertical coordinate of the start point of the line segment
     * @param endX the horizontal coordinate of the end point of the line segment
     * @param endY the vertical coordinate of the end point of the line segment
     * @param fill the fill color of the line
     * @param border the border color of the line
     */
	public Line(double startX, double startY, double endX, double endY, Paint fill, Paint border) {
		super(startX, startY, endX, endY);
		setFill(fill);
		setStroke(border);
	}

	/**
	 * Creates an empty instance of Line with given fill and border colors
	 * @param fill the fill color of the line
	 * @param border the border color of the line
	 */
	public Line(Paint fill, Paint border) {
		this();
		setFill(fill);
		setStroke(border);
	}




	public Line(Line otherLine) {
		// TODO Auto-generated constructor stub
		this(otherLine.getStartX(), otherLine.getStartY(), otherLine.getEndX(), otherLine.getEndY(),
			otherLine.getFill(), otherLine.getStroke());
	}




	@Override
	protected Line clone() {
		return new Line(this);
	}
	


	/**
	 * Draws the line
	 * @param startingX the X coordinate of the user mouse when a press is registered
	 * @param startingY the Y coordinate of the user mouse when a press is registered
	 * @param event the MouseEvent provided by the handler
	 * @see MouseEvent
	 */
	@Override
	public void draw(MouseEvent event, double startingX, double startingY) {
		double currentX = event.getX();
		double currentY = event.getY();
		this.setStartX(startingX);
		this.setStartY(startingY);
		this.setEndX(currentX);
		this.setEndY(currentY);
	}




	@Override
	public DoubleProperty getWidthProperty() {
		return this.endXProperty();
	}




	@Override
	public DoubleProperty getHeightProperty() {
		return this.endYProperty();
	}




	/**
	 * Draws the line (No alternative form of the line)
	 * @param startingX the X coordinate of the user mouse when a press is registered
	 * @param startingY the Y coordinate of the user mouse when a press is registered
	 * @param event the MouseEvent provided by the handler
	 * @see MouseEvent
	 */
	@Override
	public void alternativeDraw(MouseEvent event, double startingX, double startingY) {
		draw(event, startingX, startingY);
	}

}
