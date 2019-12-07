package model;

import javafx.beans.property.DoubleProperty;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;

/*
 * Defines an Ellipse with a 
 */
public class Ellipse extends javafx.scene.shape.Ellipse implements Drawable, Cloneable {

	public Ellipse() {
		super();
		setFill(Color.TRANSPARENT);
		setStroke(Color.BLACK);
		EnableDrawing(this);
	}

	public Ellipse(Paint fill, Paint border) {
		this();
		setFill(fill);
		setStroke(border);
	}

	/**
	 * @param centerX
	 * @param centerY
	 * @param radiusX
	 * @param radiusY
	 */
	public Ellipse(double centerX, double centerY, double radiusX, double radiusY) {
		this();
		setCenterX(centerX);
		setCenterY(centerY);
		setRadiusX(radiusX);
		setRadiusY(radiusY);
	}

	public Ellipse(double centerX, double centerY, double radiusX, double radiusY, Paint fill, Paint stroke) {
		this(centerX, centerY, radiusX, radiusY);
		setFill(fill);
		setStroke(stroke);
	}

	/**
	 * @param radiusX
	 * @param radiusY
	 */
	public Ellipse(double radiusX, double radiusY) {
		this();
		setRadiusX(radiusX);
		setRadiusY(radiusY);
	}

	public Ellipse(Ellipse otherEllipse) {
		this(otherEllipse.getCenterX(), otherEllipse.getCenterY(), otherEllipse.getRadiusX(), otherEllipse.getRadiusY(),
				otherEllipse.getFill(), otherEllipse.getStroke());
	}

	public void draw(MouseEvent event, double startingX, double startingY) {
		double currentX = event.getX();
		double currentY = event.getY();
		setRadiusX(Math.abs(currentX - startingX));
		setRadiusY(Math.abs(currentY - startingY));
		setCenterX(startingX);
		setCenterY(startingY);
	}

	public Ellipse clone() {
		return new Ellipse(this.getCenterX(), this.getCenterY(), this.getRadiusX(), this.getRadiusY(), this.getFill(),
				this.getStroke());
	}
	
	public Ellipse clone(double offset) {
		return new Ellipse(this.getCenterX()+offset, this.getCenterY()+offset, this.getRadiusX(), this.getRadiusY(), this.getFill(),
				this.getStroke());
	}

	@Override
	public DoubleProperty getWidthProperty() {
		return this.radiusXProperty();
	}

	@Override
	public DoubleProperty getHeightProperty() {
		return this.radiusYProperty();
	}

	@Override
	public void alternativeDraw(MouseEvent event, double startingX, double startingY) {
		double currentX = event.getX();
		double currentY = event.getY();
		setRadiusX(Math.abs(currentX - startingX));
		setRadiusY(getRadiusX());
		setCenterX(startingX);
		setCenterY(startingY);
	}

}
