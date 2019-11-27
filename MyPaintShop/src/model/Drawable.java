package model;

import javafx.scene.input.MouseEvent;
/*
 * Defines the draw method for each shape 
 * Each shape has its own implementation of the draw method
 */
public interface Drawable {
	
	public void draw(MouseEvent event, double startingX, double startingY);
	
}
