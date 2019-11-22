package model;

import javafx.scene.input.MouseEvent;
/*
 * 
 */
public interface Drawable {
	
	public default void draw(MouseEvent event, double startingX, double startingY) {
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

	public void setHeight(double abs);

	public void setWidth(double abs);

	public void setY(double startingY);

	public void setX(double startingX);
	
}
