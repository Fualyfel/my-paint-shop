package model;

import javafx.beans.property.DoubleProperty;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;

public class Rectangle extends javafx.scene.shape.Rectangle implements Drawable, Cloneable {
	
	public Rectangle() {
		setFill(Color.TRANSPARENT);
		setStroke(Color.BLACK);
		EnableDrawing(this);
	}
	
	/**
	 * @param x
	 * @param y
	 * @param width
	 * @param height
	 */
	public Rectangle(double x, double y, double width, double height) {
		super(x, y, width, height);
		setFill(Color.TRANSPARENT);
		setStroke(Color.BLACK);
		EnableDrawing(this);
	}
	
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

	@Override
	protected Rectangle clone() {
		return new Rectangle(this);
	}
	
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
