package model;

import java.util.ArrayList;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.collections.ObservableList;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.transform.Rotate;

/**
 * Defines a JavaFX {@link javafx.scene.shape.Polygon Polygon} of 3 points that
 * implements the {@link Drawable}, and Cloneable interfaces
 * 
 * @see Cloneable
 * @author Fawaz
 */
public class Triangle extends javafx.scene.shape.Polygon implements Drawable, Cloneable {

	private DoubleProperty width = new SimpleDoubleProperty();
	private DoubleProperty height = new SimpleDoubleProperty();
	private DoubleProperty endingX = new SimpleDoubleProperty();
	private DoubleProperty endingY = new SimpleDoubleProperty();
	private DoubleProperty startingX = new SimpleDoubleProperty();
	private DoubleProperty startingY = new SimpleDoubleProperty();

	public double getX() {
		return startingX.get();
	}

	public void setX(double newX) {
		double someX = newX;
		

		if (endingX.get() > startingX.get()) {
			this.endingX.set(this.endingX.get() + this.getWidth());
		} else if (endingX.get() < startingX.get()) {
			someX += this.getWidth();
			this.endingX.set(this.endingX.get() - this.getWidth());
		}
		this.startingX.set(someX);
	}

	public double getY() {
		return startingY.get();
	}
	
	public void setWidth(double width) {
		this.width.set(width);
	}
	
	public void setHeight(double height) {
		this.height.set(height);
	}

	public void setY(double newY) {
		double someY = newY;
		if (endingY.get() > startingY.get()) {
			this.endingY.set(this.endingY.get() + this.getHeight());
		}  else if (endingY.get() < startingY.get()) {
			someY += this.getHeight();
			this.endingY.set(this.endingY.get() - this.getHeight());
		}
		this.startingY.set(someY);
	}

	public Triangle() {
		setFill(Color.TRANSPARENT);
		setStroke(Color.BLACK);
		EnableDrawing(this);
		width.addListener((ov, oldVal, newVal) -> {
			draw(newVal.doubleValue(), height.get(), startingX.get(), startingY.get(), endingX.get(), endingY.get());
		});
		height.addListener((ov, oldVal, newVal) -> {
			draw(width.get(), newVal.doubleValue(), startingX.get(), startingY.get(), endingX.get(), endingY.get());
		});
		startingX.addListener((ov, oldVal, newVal) -> {
			double offset = newVal.doubleValue() - oldVal.doubleValue();

			for (int i = 0; i < getPoints().size(); i += 2) {
				getPoints().set(i, getPoints().get(i) + offset);
			}
		});
		startingY.addListener((ov, oldVal, newVal) -> {
			double offset = newVal.doubleValue() - oldVal.doubleValue();

			for (int i = 1; i < getPoints().size(); i += 2) {
				getPoints().set(i, getPoints().get(i) + offset);
			}
		});

	}

	public Triangle(Triangle otherTriangle) {
		this(otherTriangle.getPoints());
		setFill(otherTriangle.getFill());
		setStroke(otherTriangle.getStroke());
	}

	public Triangle(ObservableList<Double> points) {
		this();
		this.getPoints().addAll(points);
	}

	public Triangle(Paint fill, Paint border) {
		this();
		setFill(fill);
		setStroke(border);
	}

	public void draw(MouseEvent event, double startingX, double startingY) {
		this.startingX.set(startingX);
		this.startingY.set(startingY);
		width.set(Math.abs(event.getX() - startingX));
		height.set(Math.abs(startingY - event.getY()));
		endingX.set(event.getX());
		endingY.set(event.getY());
	}

	public void draw(double width, double height, double startingX, double startingY, double currentX,
			double currentY) {
		getPoints().clear();

		this.endingX.set(currentX);
		this.endingY.set(currentY);
		getPoints().addAll(startingX, startingY);

		// reflection point is startingX and startingY
		if (currentX > startingX && currentY > startingY) {
			getPoints().addAll(startingX + width, startingY);
			getPoints().addAll((startingX + (width / 2.0)), startingY + height);
		} else if (currentX < startingX && currentY > startingY) {
			getPoints().addAll(startingX - width, startingY);
			getPoints().addAll((startingX - (width / 2.0)), startingY + height);
		} else if (currentX > startingX && currentY < startingY) {
			getPoints().addAll(startingX + width, startingY);
			getPoints().addAll((startingX + (width / 2.0)), startingY - height);
		} else if (currentX < startingX && currentY < startingY) {
			getPoints().addAll(startingX - width, startingY);
			getPoints().addAll((startingX - (width / 2.0)), startingY - height);
		}

	}

	@Override
	protected Triangle clone() {
		return new Triangle(this);
	}

	protected Triangle clone(double offset) {
		Triangle newTriangle = new Triangle(this);
		ArrayList<Double> offsetPoints = new ArrayList<Double>();
		newTriangle.getPoints().forEach((point) -> offsetPoints.add(point + offset));
		newTriangle.getPoints().setAll(offsetPoints);
		return newTriangle;
	}

	@Override
	public DoubleProperty getWidthProperty() {
		// TODO Auto-generated method stub
		return width;
	}

	public double getWidth() {
		return width.get();
	}

	@Override
	public DoubleProperty getHeightProperty() {
		// TODO Auto-generated method stub
		return height;
	}

	public double getHeight() {
		return height.get();
	}

	@Override
	public void alternativeDraw(MouseEvent event, double startingX, double startingY) {
		draw(event, startingX, startingY);
	}

}
