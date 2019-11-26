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
	
	public void draw(MouseEvent event, double startingX, double startingY) {
		double currentX = event.getX();
		double currentY = event.getY();
		setWidth(Math.abs(currentX - startingX));
		setHeight(Math.abs(currentY - startingY));
		if (currentX > startingX && currentY > startingY) {
			setX(startingX);
			setY(startingY);
		}
		else if (currentX < startingX && currentY > startingY) {
			setX(currentX);
			setY(startingY);
		}
		else if (currentX > startingX && currentY < startingY) {
			setX(startingX);
			setY(currentY);
		}
		else if (currentX < startingX && currentY < startingY) {
			setX(currentX);
			setY(currentY);
		}
	}
}
