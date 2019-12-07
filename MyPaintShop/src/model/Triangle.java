package model;

import java.util.ArrayList;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.collections.ObservableList;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;

public class Triangle extends javafx.scene.shape.Polygon implements Drawable, Cloneable {

	private DoubleProperty width = new SimpleDoubleProperty();
	private DoubleProperty height = new SimpleDoubleProperty();
	private DoubleProperty endingX = new SimpleDoubleProperty();
	private double startingX;
	private double startingY;

	public Triangle() {
		setFill(Color.TRANSPARENT);
		setStroke(Color.BLACK);
		EnableDrawing(this);
		width.addListener((ov, oldVal, newVal) -> {
			draw(newVal.doubleValue(), height.get(), endingX.get(), startingX, startingY);
		});
		height.addListener((ov, oldVal, newVal) -> {
			draw(width.get(), newVal.doubleValue(), endingX.get(), startingX, startingY);
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
		this.startingX = startingX;
		this.startingY = startingY;
		width.set(Math.abs(event.getX() - startingX));
		height.set(event.getY());
		endingX.set(event.getX());
		// draw(width.get(), height.get(), endingX.get(), startingX, startingY);
	}

	public void draw(double width, double height, double endX, double startingX, double startingY) {
		getPoints().clear();
		if ((endingX.get() > startingX)) {
			getPoints().addAll(startingX, startingY, startingX + width, startingY);
			getPoints().addAll((startingX + width / 2.0), height);
		} else if ((endingX.get() < startingX)) {
			getPoints().addAll(startingX, startingY, Math.abs(startingX-width), startingY);
			getPoints().addAll((startingX - width / 2.0), height);
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

	@Override
	public DoubleProperty getHeightProperty() {
		// TODO Auto-generated method stub
		return height;
	}

	@Override
	public void alternativeDraw(MouseEvent event, double startingX, double startingY) {
		draw(event, startingX, startingY);
	}

}
