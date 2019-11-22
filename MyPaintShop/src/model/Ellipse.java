package model;

import javafx.scene.paint.Color;

public class Ellipse extends javafx.scene.shape.Ellipse implements Drawable {

	double startingX;
	double startingY;
	public Ellipse() {
		setFill(Color.TRANSPARENT);
		setStroke(Color.BLACK);
	}
	
	public Ellipse(double startingX, double startingY) {
		setFill(Color.TRANSPARENT);
		setStroke(Color.BLACK);
		this.startingX = startingX;
		this.startingY = startingY;
	}
	@Override
	public void setHeight(double abs) {
		setRadiusY(abs);
	}

	@Override
	public void setWidth(double abs) {
		setRadiusX(abs);
	}

	@Override
	public void setY(double startingY) {
		setCenterY(startingY);
	}

	@Override
	public void setX(double startingX) {
		setCenterX(startingX);
	}

}
