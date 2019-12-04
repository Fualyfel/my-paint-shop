package model;

import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;

public class Triangle extends javafx.scene.shape.Polygon implements Drawable, Cloneable {
	
	private double width;
	private double height;
	
	public Triangle() {
		setFill(Color.TRANSPARENT);
		setStroke(Color.BLACK);
	}
	
	public void draw(MouseEvent event, double startingX, double startingY) {
		width = Math.abs(event.getX() - startingX);
		height = event.getY();
		getPoints().clear();
		getPoints().addAll(startingX, startingY, event.getX(), startingY);
		getPoints().addAll(Math.abs(event.getX() + startingX)/2.0, height);
	}
	
}
