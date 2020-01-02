package model;

import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.StrokeType;
/*
 * Component made by Jewelsea https://stackoverflow.com/questions/41267418/how-to-change-a-shape-property-using-its-border-in-javafx
 */
public class Anchor extends Circle {
	Anchor(Color color, boolean canDragX, boolean canDragY, DragHandler dragHandler) {
        super(0, 0, 5);
        setFill(color.deriveColor(1, 1, 1, 0.5));
        setStroke(color);
        setStrokeWidth(2);
        setStrokeType(StrokeType.OUTSIDE);

        Util.enableDrag(this, canDragX, canDragY, dragHandler);
    }
}
