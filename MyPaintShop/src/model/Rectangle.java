package model;

import javafx.beans.property.DoubleProperty;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;

/**
 * Defines a JavaFX Rectangle that implements the Drawable interface and the Cloneable interface
 * @see Cloneable
 * @see Drawable
 * @author Fawaz
 * @see javafx.scene.shape.Rectangle
 */
public class Rectangle extends javafx.scene.shape.Rectangle implements Drawable, Cloneable {
	
    /**
     * Creates an empty instance of Rectangle with a default transparent fill and a black border
     */
	public Rectangle() {
		super();
		setFill(Color.TRANSPARENT);
		setStroke(Color.BLACK);
		EnableDrawing(this);
	}
	
    /**
     * Creates a new instance of Rectangle with the given position and size.
     * @param x horizontal position of the rectangle
     * @param y vertical position of the rectangle
     * @param width width of the rectangle
     * @param height height of the rectangle
     */
	public Rectangle(double x, double y, double width, double height) {
		super(x, y, width, height);
		setFill(Color.TRANSPARENT);
		setStroke(Color.BLACK);
		EnableDrawing(this);
	}
	
    /**
     * Creates a new instance of Rectangle with the given position, size, and color
     * @param x horizontal position of the rectangle
     * @param y vertical position of the rectangle
     * @param width width of the rectangle
     * @param height height of the rectangle
     * @param fill fill color of the rectangle
     * @param border border color of the rectangle
     */
	public Rectangle(double x, double y, double width, double height, Paint fill, Paint border) {
		this(x, y, width, height);
		setFill(fill);
		setStroke(border);
	}
	
	public Rectangle(Rectangle otherRectangle) {
		this(otherRectangle.getX(), otherRectangle.getY(), otherRectangle.getWidth(), otherRectangle.getHeight()
			, otherRectangle.getFill(), otherRectangle.getStroke());
	}

	public Rectangle(Paint fill, Paint border) {
		this();
		setFill(fill);
		setStroke(border);
	}

	 /** Draws a rectangle.
	 * @param startingX the X coordinate of the user mouse when a press is registered
	 * @param startingY the Y coordinate of the user mouse when a press is registered
	 * @param event the MouseEvent provided by the handler
	 * @see MouseEvent
	 */
	public void draw(MouseEvent event, double startingX, double startingY) {
		double currentX = event.getX();
		double currentY = event.getY();
		setWidth(Math.abs(currentX - startingX));
		setHeight(Math.abs(currentY - startingY));
		if (currentX > startingX && currentY > startingY) {
			setX(startingX);
			setY(startingY);
		}
		else if (currentX < startingX && currentY > startingY) {
			setX(currentX);
			setY(startingY);
		}
		else if (currentX > startingX && currentY < startingY) {
			setX(startingX);
			setY(currentY);
		}
		else if (currentX < startingX && currentY < startingY) {
			setX(currentX);
			setY(currentY);
		}
	}
	
	 /** Draws the rectangle as a square.
	 * @param startingX the X coordinate of the user mouse when a press is registered
	 * @param startingY the Y coordinate of the user mouse when a press is registered
	 * @param event the MouseEvent provided by the handler
	 * @see MouseEvent
	 */
	public void alternativeDraw(MouseEvent event, double startingX, double startingY) {
		double currentX = event.getX();
		double currentY = event.getY();
		if (currentX > startingX && currentY > startingY) {
			setWidth(Math.abs(currentX - startingX));
			setHeight(getWidth());
			setX(startingX);
			setY(startingY);
		}
		else if (currentX < startingX && currentY > startingY) {
			setWidth(Math.abs(currentX - startingX));
			setHeight(getWidth());
			setX(currentX);
			setY(startingY);
		}
		else if (currentX > startingX && currentY < startingY) {
			setHeight(Math.abs(currentY - startingY));
			setWidth(getHeight());
			setX(startingX);
			setY(currentY);
		}
		else if (currentX < startingX && currentY < startingY) {
			setHeight(Math.abs(currentX - startingX));
			setWidth(getHeight());
			setX(currentX);
			setY(currentY);
		}
	}

	@Override
	protected Rectangle clone() {
		return new Rectangle(this);
	}
	
	/**
	 * clones the rectangle and adds a positive shift (bottom-right) to it's center coordinates. used by duplicate()
	 * @param offset the shift 
	 * @see Model.duplicate
	 */
	protected Rectangle clone(double offset) {
		Rectangle offsetRectangle = new Rectangle(this);
		offsetRectangle.setX(offsetRectangle.getX()+offset);
		offsetRectangle.setY(offsetRectangle.getY()+offset);
		return offsetRectangle;
	}

	@Override
	public DoubleProperty getWidthProperty() {
		return super.widthProperty();
	}

	@Override
	public DoubleProperty getHeightProperty() {
		return super.heightProperty();
	}
	
	
}
