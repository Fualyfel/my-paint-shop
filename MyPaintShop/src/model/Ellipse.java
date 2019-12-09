package model;

import javafx.beans.property.DoubleProperty;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;

/**
 * Defines a JavaFX {@link javafx.scene.shape.Ellipse Ellipse} that implements the {@link Drawable} interface
 * @author Fawaz
 */
public class Ellipse extends javafx.scene.shape.Ellipse implements Drawable, Cloneable {

    /**
     * Creates an empty instance of Ellipse with a default transparent fill and a black border
     */
	public Ellipse() {
		super();
		setFill(Color.TRANSPARENT);
		setStroke(Color.BLACK);
		EnableDrawing(this);
	}

	/**
	 * Creates an empty instance of Line with given fill and border colors
	 * @param fill the fill color of the line
	 * @param border the border color of the line
	 */
	public Ellipse(Paint fill, Paint border) {
		this();
		setFill(fill);
		setStroke(border);
	}

    /**
     * Creates a new instance of Rectangle with the given position and size.
     * @param centerX horizontal position of the ellipse's center
     * @param centerX vertical position of the ellipse's center
     * @param raidusX the horizontal radius of the ellipse
     * @param radiusY the vertical radius of the ellipse
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

	public Ellipse(double radiusX, double radiusY) {
		this();
		setRadiusX(radiusX);
		setRadiusY(radiusY);
	}

	// Copy constructor
	public Ellipse(Ellipse otherEllipse) {
		this(otherEllipse.getCenterX(), otherEllipse.getCenterY(), otherEllipse.getRadiusX(), otherEllipse.getRadiusY(),
				otherEllipse.getFill(), otherEllipse.getStroke());
	}

	/**
	 * Draws the ellipse
	 * @param startingX the X coordinate of the user mouse when a press is registered
	 * @param startingY the Y coordinate of the user mouse when a press is registered
	 * @param event the MouseEvent provided by the handler
	 * @see MouseEvent
	 */
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
	
	/**
	 * clones the Ellipse and adds a positive shift (bottom-right) to it's center coordinates. used by duplicate
	 * @param offset the shift 
	 */
	public Ellipse clone(double offset) {
		return new Ellipse(this.getCenterX()+offset, this.getCenterY()+offset, this.getRadiusX(), this.getRadiusY(), this.getFill(),
				this.getStroke());
	}

	/**
	 * a getter method for radiusXProperty()
	 * @see radiusXProperty
	 */
	@Override
	public DoubleProperty getWidthProperty() {
		return this.radiusXProperty();
	}

	/**
	 * a getter method for radiusYProperty()
	 * @see radiusYProperty
	 */
	@Override
	public DoubleProperty getHeightProperty() {
		return this.radiusYProperty();
	}

	/**
	 * Draws the ellipse as a circle.
	 * @param startingX the X coordinate of the user mouse when a press is registered
	 * @param startingY the Y coordinate of the user mouse when a press is registered
	 * @param event the MouseEvent provided by the handler
	 * @see MouseEvent
	 */
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
