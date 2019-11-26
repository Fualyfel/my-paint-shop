package model;

import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;

public class Triangle extends javafx.scene.shape.Polygon implements Drawable {
	private double startingX;
	private double startingY;
	private double currentX;
	private double currentY;
	private final int sides = 3;
	private double horizontalLength;
	private double verticalLength;
	
	public void draw(MouseEvent event, double startingX, double startingY) {
		currentX = event.getX();
		currentY = event.getY();
		getPoints().clear();
		final double angleStep = Math.PI * 2 / sides;
		double angle = Math.PI; // assuming one point above center
		for (int i = 0; i < sides; i++, angle += angleStep) {
			getPoints().addAll(Math.sin(angle) * Math.abs(currentX - startingX) + startingX, // x coordinate of the corner
					Math.cos(angle) * Math.abs(currentY - startingY) + startingY // y coordinate of the corner
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
	}

	@Override
	public void setHeight(double abs) {
		horizontalLength = abs;
	}

	@Override
	public void setWidth(double abs) {
		verticalLength = abs;
	}

	@Override
	public void setY(double startingY) {
		return;

	}

	@Override
	public void setX(double startingX) {
		return;
	}

}
