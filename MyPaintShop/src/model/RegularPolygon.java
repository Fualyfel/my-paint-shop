package model;

import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;

public class RegularPolygon extends javafx.scene.shape.Polygon implements Drawable, Cloneable {

	
	private int sides;
	
	public RegularPolygon() {
		setFill(Color.TRANSPARENT);
		setStroke(Color.BLACK);
		this.sides = 3;
	}
	public RegularPolygon(int sides) {
		setFill(Color.TRANSPARENT);
		setStroke(Color.BLACK);
		this.sides = sides;
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

}
