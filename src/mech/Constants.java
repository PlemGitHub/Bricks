package mech;

import java.awt.Color;
import java.awt.Font;
import java.awt.GraphicsEnvironment;
import java.awt.Rectangle;

public interface Constants {
	
	int WINDOW_WIDTH = GraphicsEnvironment.getLocalGraphicsEnvironment().getMaximumWindowBounds().width;
	int WINDOW_HEIGHT = GraphicsEnvironment.getLocalGraphicsEnvironment().getMaximumWindowBounds().height;
	
	int ELEMENT_WIDTH = WINDOW_HEIGHT/5;
	int ELEMENT_HEIGHT = ELEMENT_WIDTH/5;
	Rectangle BUTTON_RECTANGLE = new Rectangle(0, 0, ELEMENT_WIDTH, ELEMENT_HEIGHT);
	Rectangle SCORE_RECTANGLE = new Rectangle(0, ELEMENT_HEIGHT, ELEMENT_WIDTH, ELEMENT_HEIGHT);
	Rectangle GROUP_SCORE_RECTANGLE = new Rectangle(WINDOW_WIDTH/2-ELEMENT_WIDTH/2, 0, ELEMENT_WIDTH, ELEMENT_HEIGHT);
	Rectangle COLORBOX_RECTANGLE = new Rectangle(0, ELEMENT_HEIGHT*2, ELEMENT_WIDTH, ELEMENT_HEIGHT);
	Rectangle COLORBOX_RECTANGLE_LABEL = new Rectangle(ELEMENT_WIDTH, ELEMENT_HEIGHT*2, ELEMENT_WIDTH, ELEMENT_HEIGHT);
	Rectangle SIZEBOX_RECTANGLE = new Rectangle(0, ELEMENT_HEIGHT*3, ELEMENT_WIDTH, ELEMENT_HEIGHT);
	Rectangle SIZEBOX_RECTANGLE_LABEL = new Rectangle(ELEMENT_WIDTH, ELEMENT_HEIGHT*3, ELEMENT_WIDTH, ELEMENT_HEIGHT);
	
	int FONT_SIZE = (int)Math.round(ELEMENT_HEIGHT*0.6);
	Font FONT = new Font("Verdana", Font.PLAIN, FONT_SIZE);
	
	double CHANCE_SAME_COLOR = 0.5;
	
	int DELAY_DESTROY = 33;
	int DELAY_FALLING = 33;
	int DELAY_TO_RIGHT = 33;

	Color[] COLORS = {Color.RED, Color.BLUE, Color.GREEN, Color.ORANGE, Color.BLACK};
}