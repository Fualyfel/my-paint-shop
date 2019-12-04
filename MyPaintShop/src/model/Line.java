package model;

import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;

public class Line extends javafx.scene.shape.Line implements Drawable, Cloneable {

	
	public Line() {
		super();
		setFill(Color.TRANSPARENT);
		setStroke(Color.BLACK);
	}
	
	
	@Override
	public void draw(MouseEvent event, double startingX, double startingY) {
		double currentX = event.getX();
		double currentY = event.getY();
		this.setStartX(startingX);
		this.setStartY(startingY);
		this.setEndX(currentX);
		this.setEndY(currentY);
	}

}
