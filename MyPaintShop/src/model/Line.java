package model;

import javafx.beans.property.DoubleProperty;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;

public class Line extends javafx.scene.shape.Line implements Drawable, Cloneable {

	
	public Line() {
		super();
		setFill(Color.TRANSPARENT);
		setStroke(Color.BLACK);
	}
	
	
	
	
	/**
	 * @param startX
	 * @param startY
	 * @param endX
	 * @param endY
	 */
	public Line(double startX, double startY, double endX, double endY) {
		super(startX, startY, endX, endY);
		setFill(Color.TRANSPARENT);
		setStroke(Color.BLACK);
	}
	
	public Line(double startX, double startY, double endX, double endY, Paint fill, Paint border) {
		super(startX, startY, endX, endY);
		setFill(fill);
		setStroke(border);
	}

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

}
