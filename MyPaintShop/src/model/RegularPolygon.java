package model;

import javafx.beans.property.DoubleProperty;
import javafx.collections.ObservableList;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;


/*
 * A regular polygon with n sides
 * to be fully implemented and docnumented after the project finishes.
 */
public class RegularPolygon extends javafx.scene.shape.Polygon implements Drawable, Cloneable {

	
	private int sides;
	
	public RegularPolygon() {
		setFill(Color.TRANSPARENT);
		setStroke(Color.BLACK);
		this.sides = 3;
		EnableDrawing(this);
	}
	public RegularPolygon(int sides) {
		setFill(Color.TRANSPARENT);
		setStroke(Color.BLACK);
		this.sides = sides;
		EnableDrawing(this);
	}
	
	public RegularPolygon(double... points) {
		super(points);
	}
	
	
	public RegularPolygon(ObservableList<Double> points) {
		this.getPoints().addAll(points);
		setFill(Color.TRANSPARENT);
		setStroke(Color.BLACK);
	}
	
	@Override
	protected RegularPolygon clone() throws CloneNotSupportedException {
		RegularPolygon clonedRegularPolygon = new RegularPolygon(this.getPoints());
		return clonedRegularPolygon;	
	}
	
	@Override
	public void draw(MouseEvent event, double startingX, double startingY) {
		getPoints().clear();
		final double angleStep = Math.PI * 2 / sides;
		double angle = Math.PI; // assuming one point above center
		for (int i = 0; i < sides; i++, angle += angleStep) {
		getPoints().addAll(Math.sin(angle) * Math.abs(event.getX() - startingX) + startingX, // x coordinate of the corner
				Math.cos(angle) * Math.abs(event.getY() - startingY) + startingY // y coordinate of the corner
		);
	}
		
	}
	@Override
	public DoubleProperty getWidthProperty() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public DoubleProperty getHeightProperty() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public void alternativeDraw(MouseEvent event, double startingX, double startingY) {
		alternativeDraw(event, startingX, startingY);
	}

}
