package model;

import controller.Controller.Drag;
import controller.Controller.DragStarter;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;

public class Triangle extends javafx.scene.shape.Polygon implements Drawable {
	private double startingX;
	private double startingY;
	
	private final int sides = 3;
	
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

	public Triangle() {
		setFill(Color.TRANSPARENT);
		setStroke(Color.BLACK);
	}

	public Triangle(double startingX, double startingY) {
		setFill(Color.TRANSPARENT);
		setStroke(Color.BLACK);
		this.startingX = startingX;
		this.startingY = startingY;
	}

}
