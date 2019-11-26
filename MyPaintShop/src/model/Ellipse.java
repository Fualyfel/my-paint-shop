package model;

import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;

public class Ellipse extends javafx.scene.shape.Ellipse implements Drawable {

	double startingX;
	double startingY;
	
	public Ellipse() {
		super();
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
	
	public void draw(MouseEvent event, double startingX, double startingY) {
		double currentX = event.getX();
		double currentY = event.getY();
		setWidth(Math.abs(currentX - startingX));
		setHeight(Math.abs(currentY - startingY));
		setX(startingX);
		setY(startingY);
	}

}
