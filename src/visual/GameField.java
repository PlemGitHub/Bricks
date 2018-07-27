package visual;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;

import javax.swing.JPanel;
import mech.Constants;
import mech.Mech;

@SuppressWarnings("serial")
public class GameField extends JPanel implements Constants{
	private MainPanel mainPanel;
	private Mech mech;
	private Color previousColor = COLORS[0];
	private int gameFieldCells;
	private int cellSize, dSizeToBrick, brickSize;
	private Point[][] coords;
	
	public GameField(MainPanel mainPanel) {
		this.mainPanel = mainPanel;
		mech = mainPanel.getMech();
		setLayout(null);
		setOpaque(true);
		setMyBounds();
		generateAllBricks();
			mech.setUpNewGame();
		enableMouseListeners();
	}
	
	private void setMyBounds() {
		gameFieldCells = mainPanel.getSizeBox().getSelectedIndex()*10+10;
			mech.setGameFieldCells(gameFieldCells);
		cellSize = (int)((WINDOW_HEIGHT-ELEMENT_HEIGHT*2)/gameFieldCells);
		dSizeToBrick = (int)(cellSize*0.2);
		brickSize = cellSize-dSizeToBrick;
		mech.setNewBricksArray(gameFieldCells);
		int gameFieldSize = cellSize * gameFieldCells + 1;
		Rectangle gameFieldRect = new Rectangle(WINDOW_WIDTH/2-gameFieldSize/2,
												ELEMENT_HEIGHT,
												gameFieldSize,
												gameFieldSize);
		setBounds(gameFieldRect);
		
		coords = new Point[gameFieldCells][gameFieldCells];
		initCellCoords();
	}

	private void generateAllBricks() {
		for (int i = 0; i < gameFieldCells; i++) {
			for (int j = 0; j < gameFieldCells; j++) {
				Brick brick = new Brick(this, mainPanel.getColorBox().getColorQuantity(), previousColor);
				brick.setLocation(i*cellSize+dSizeToBrick/2, j*cellSize+dSizeToBrick/2);
				add(brick);
				mech.setBrick(brick, i, j);
				mech.setLeftSide(0);
			}
		}
	}

	@Override
	public void paint(Graphics g) {
		super.paint(g);
		g.setColor(Color.LIGHT_GRAY);
		drawHorizontalLines(g);
		drawVerticalLines(g);
	}

	private void drawHorizontalLines(Graphics g) {
		for (int i = 0; i <= gameFieldCells; i++) {
			g.drawLine(0, i*cellSize, getWidth(), i*cellSize);
		}
	}

	private void drawVerticalLines(Graphics g) {
		for (int i = 0; i <= gameFieldCells; i++) {
			g.drawLine(i*cellSize, 0, i*cellSize, getHeight());
		}
	}
	
	public void setPreviousColor(Color newColor){
		previousColor = newColor;
	}
	
	public void disableMouseListeners(){
		removeMouseListener(mech);
		removeMouseMotionListener(mech);
		mainPanel.removeMouseMotionListener(mech);
	}
	
	public void enableMouseListeners(){
		if (getMouseListeners().length == 0){
			addMouseListener(mech);
			addMouseMotionListener(mech);
			mainPanel.addMouseMotionListener(mech);
		}
	}
	
	public int getBrickSize(){
		return brickSize;
	}
	
	public int getCellSize(){
		return cellSize;
	}
	
	public int getDSizeToBrick(){
		return dSizeToBrick;
	}
	
	private void initCellCoords(){
		for (int i = 0; i < gameFieldCells; i++) {
			for (int j = 0; j < gameFieldCells; j++) {
				coords[i][j] = new Point();
				coords[i][j].x = i*cellSize;
				coords[i][j].y = j*cellSize;
			}
		}
	}
	
	public Point[][] getCoords(){
		return coords;
	}
	
	public int getGameFieldCells(){
		return gameFieldCells;
	}
}
