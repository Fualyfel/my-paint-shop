package model;

import javafx.scene.input.MouseEvent;
/*
 * 
 */
public interface Drawable {
	
	public void draw(MouseEvent event, double startingX, double startingY);

	public void setHeight(double abs);

	public void setWidth(double abs);

	public void setY(double startingY);

	public void setX(double startingX);
	
}
