package model;

import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
/*
 * Defines an Ellipse with a 
 */
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

	
	public void draw(MouseEvent event, double startingX, double startingY) {
		double currentX = event.getX();
		double currentY = event.getY();
		setRadiusX(Math.abs(currentX - startingX));
		setRadiusY(Math.abs(currentY - startingY));
		setCenterX(startingX);
		setCenterY(startingY);
	}

}
