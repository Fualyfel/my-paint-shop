package model;

import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;

public class Rectangle extends javafx.scene.shape.Rectangle implements Drawable {
	double startingX;
	double startingY;
	public Rectangle() {
		setFill(Color.TRANSPARENT);
		setStroke(Color.BLACK);
	}
	
	public Rectangle(double startingX, double startingY) {
		setFill(Color.TRANSPARENT);
		setStroke(Color.BLACK);
		this.startingX = startingX;
		this.startingY = startingY;
	}
	
	public double getStartingX() {
		return startingX;
	}

	public void setStartingX(double startingX) {
		this.startingX = startingX;
	}

	public double getStartingY() {
		return startingY;
	}

	public void setStartingY(double startingY) {
		this.startingY = startingY;
	}
}
