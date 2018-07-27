package visual;

import java.awt.Color;
import java.awt.Point;

import javax.swing.JPanel;
import mech.Constants;

@SuppressWarnings("serial")
public class Brick extends JPanel implements Constants{
	private Color brickColor;
	private int i,j;
	private int stepsToFall;
	private int cellSize, dSizeToBrick;
	
	public Brick(GameField gameField, int colorQuantity, Color previousColor) {
		setSize(gameField.getBrickSize(), gameField.getBrickSize());
		cellSize = gameField.getCellSize();
		dSizeToBrick = gameField.getDSizeToBrick();
		
		if (Math.random() < CHANCE_SAME_COLOR)
			brickColor = previousColor;
		else{
			int index = (int)(Math.random()*colorQuantity);
			brickColor = COLORS[index];
		}
		setBackground(brickColor);
		gameField.setPreviousColor(brickColor);
	}
	
	public Color getColor(){
		return brickColor;
	}

	public Point getIJpoint(){
		return new Point(i, j);
	}

	public void setIJpoint(Point ij){
		i = ij.x;
		j = ij.y;
	}
	
	public int getJ(){
		return j;
	}
	
	public void setJ(int newJ){
		j = newJ;
	}
	
	public void setStepsToFall(int newSteps){
		stepsToFall = newSteps;
	}
	
	public int getStepsToFall(){
		return stepsToFall;
	}
	
	public void moveOneStepRight(){
		setLocation(getX()+cellSize, getY());
		i++;
	}
	
	public void moveOneStepDown(){
		setLocation(getX(), getY()+cellSize);
	}
	
	public void reduceSize(){
		int newWidth = getWidth()-dSizeToBrick;
		int newHeight = getHeight()-dSizeToBrick;
		int newX = getX()+dSizeToBrick/2;
		int newY = getY()+dSizeToBrick/2;
		setBounds(newX, newY, newWidth, newHeight);
	}
	
	public void increaseSize(){
		int newWidth = getWidth()+dSizeToBrick/2;
		int newHeight = getHeight()+dSizeToBrick/2;
		int newX = getX()-dSizeToBrick/4;
		int newY = getY()-dSizeToBrick/4;
		setBounds(newX, newY, newWidth, newHeight);
	}
}
