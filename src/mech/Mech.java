package mech;

import java.awt.Color;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;

import javax.swing.JOptionPane;
import javax.swing.Timer;

import mech.timers.TBrickDestroy;
import mech.timers.TBrickFalling;
import mech.timers.TBrickMoveRight;
import visual.Brick;
import visual.MainPanel;

public class Mech implements Constants, MouseMotionListener, MouseListener{
	private MainPanel mainPanel;
	private int score;
	private Brick[][] bricks;
	private Brick currentBrick;
	private ArrayList<Brick> group = new ArrayList<>();
	private boolean unenlarged;
	private int leftSide;
	private int[] columnFirstIndex;
	private int gameFieldCells, dSizeToBrick, cellSize;

	public Mech(MainPanel mainPanel) {
		this.mainPanel = mainPanel;
	}
	
	public void setUpNewGame(){
		setScore(0);
		leftSide = 0;
		columnFirstIndex = new int[gameFieldCells];
		findColumnFirstIndexes();
	}
	
	public void findColumnFirstIndexes() {
		for (int i = leftSide; i < gameFieldCells; i++) {
			for (int j = 0; j < gameFieldCells; j++) {
				if (bricks[i][j] != null){
					columnFirstIndex[i] = j;
					break;
				}
				columnFirstIndex[i] = gameFieldCells;
			}
		}
	}
	
	public void findLeftSide(){
		for (int i = 0; i < gameFieldCells; i++) {
			if (bricks[i][gameFieldCells-1] != null){
				leftSide = i;
				break;
			}
		}
	}

	public void setScore(int newScore){
		score = newScore;
		mainPanel.setTextScoreLabel(newScore);
	}
	
	public void setBrick(Brick brick, int i, int j){
		bricks[i][j] = brick;
		brick.setIJpoint(new Point(i, j));
	}
		
	public MainPanel getMainPanel(){
		return mainPanel;
	}

	@Override
	public void mouseDragged(MouseEvent e) {}
	public void mouseMoved(MouseEvent e) {
		int x, y;
		if (e.getSource().equals(mainPanel)){
			x = e.getX()-mainPanel.getGameField().getX();
			y = e.getY()-mainPanel.getGameField().getY();
		}else{
			x = e.getX();
			y = e.getY();
		}
		findBrickUnderMouseAndResizeIt(x, y);
	}

		private void findBrickUnderMouseAndResizeIt(int x, int y) {
			Brick brickUnderMouse = findBrickUnderMouse(x, y);
			if (brickUnderMouse != null){
				if (brickUnderMouse != currentBrick){
					unenlargeBricksUnderMouse();
					setCurrentBrickAndFindWholeGroup(brickUnderMouse);
					mainPanel.setTextGroupScoreLabel(calculateScoreFromDeadGroup());
				}
			}else{
				unenlargeBricksUnderMouse();
				group = null;
				currentBrick = null;
				mainPanel.setTextGroupScoreLabel(0);
			}	
		}

	public Brick findBrickUnderMouse(int x, int y) {
		cellSize = mainPanel.getGameField().getCellSize();
		Point[][] coords = mainPanel.getGameField().getCoords();
		for (int i = 0; i < gameFieldCells; i++) {
			for (int j = 0; j < gameFieldCells; j++) {
				if (bricks[i][j] != null){
					int brickX1 = coords[i][j].x;
					int brickX2 = coords[i][j].x+cellSize;
					int brickY1 = coords[i][j].y;
					int brickY2 = coords[i][j].y+cellSize;
					if (x > brickX1 && x <= brickX2
						&& y > brickY1 && y <= brickY2)
							return bricks[i][j];
				}
			}
		}
		return null;
	}
	
		private void setCurrentBrickAndFindWholeGroup(Brick brickUnderMouse){
			currentBrick = brickUnderMouse;
			group = new ArrayList<>();
			group.add(brickUnderMouse);
			findWholeGroup(brickUnderMouse);
			if (group.size() != 1)
				enlargeBricksUnderMouse();
		}
		
		public void enlargeBricksUnderMouse(){
			dSizeToBrick = mainPanel.getGameField().getDSizeToBrick();
			for (Brick brick : group) {
				brick.increaseSize();
			}
			unenlarged = false;
		}
		
		public void unenlargeBricksUnderMouse(){
			dSizeToBrick = mainPanel.getGameField().getDSizeToBrick();
			if (group != null
				&& !unenlarged)
				for (Brick brick : group) {
					int newWidth = brick.getWidth()-dSizeToBrick/2;
					int newHeight = brick.getHeight()-dSizeToBrick/2;
					int newX = brick.getX()+dSizeToBrick/4;
					int newY = brick.getY()+dSizeToBrick/4;
					brick.setBounds(newX, newY, newWidth, newHeight);
				}
			unenlarged = true;
		}

		@Override
		public void mouseClicked(MouseEvent e) {
			TBrickDestroy tBrickDestroy = null;
			
			if (group != null
				&& group.size() > 1){
				mainPanel.getGameField().disableMouseListeners();
				calculateAndAddScoreFromDeadGroup();
				tBrickDestroy = new TBrickDestroy(DELAY_DESTROY, null, group);
				tBrickDestroy.addActionListener(tBrickDestroy);
				tBrickDestroy.start();
				clearBricksFromArray();
			}
			
			if (tBrickDestroy == null)
				findAllBricksToFall();
			else
				createDestroyPauseThread(tBrickDestroy);
		}

		private void clearBricksFromArray() {
			for (Brick brick : group) {
				int i = brick.getIJpoint().x;
				int j = brick.getIJpoint().y;
				bricks[i][j] = null;
			}
		}

		private void createDestroyPauseThread(Timer tBrickDestroy) {
			Thread pauseThread = new Thread(new Runnable() {
				@Override
				public void run() {
					do {
						Thread.yield();
					} while (tBrickDestroy.isRunning());
					findAllBricksToFall();
				}
			});
			pauseThread.start();
		}

		protected void findAllBricksToFall() {
			ArrayList<Timer> fallingTimers = new ArrayList<>();
			for (int i = leftSide; i < gameFieldCells; i++) {
				for (int j = gameFieldCells-1; j > columnFirstIndex[i]; j--) {
					if (bricks[i][j] == null){
						Brick brickToFall = findAboveBrickToFall(i, j);
						if (brickToFall != null){
							int oldI = brickToFall.getIJpoint().x;
							int oldJ = brickToFall.getIJpoint().y;
							bricks[i][j] = brickToFall;
							bricks[oldI][oldJ] = null;
							brickToFall.setJ(j);
							fallingTimers.add(createAndStartBrickFallingTimer(brickToFall));
						}
					}
				}
			}
			createFallingPauseThread(fallingTimers);
		}

		private Brick findAboveBrickToFall(int a, int b) {
			for (int j = b-1; j >= columnFirstIndex[a]; j--) {
				if (bricks[a][j] != null){
					bricks[a][j].setStepsToFall(b-j);
					return bricks[a][j];
				}
			}
			return null;
		}

		private Timer createAndStartBrickFallingTimer(Brick brickToFall) {
			TBrickFalling tBrickFalling = new TBrickFalling(DELAY_FALLING, null, brickToFall);
			tBrickFalling.addActionListener(tBrickFalling);
			tBrickFalling.start();
			return tBrickFalling;
		}

		private void createFallingPauseThread(ArrayList<Timer> fallingTimers) {
			Thread pauseThread = new Thread(new Runnable() {
				private boolean allTimersStopped;
				@Override
				public void run() {
					do {
						allTimersStopped = true;
						if (fallingTimers.size() > 0)
							for (Timer timer : fallingTimers) {
								if (timer.isRunning())
									allTimersStopped = false;
							}
						Thread.yield();
					} while (!allTimersStopped);
					findLeftSide();
					findColumnFirstIndexes();
					createAndStartMoveRightTimer();
				}
			});
			pauseThread.start();
		}

		protected void createAndStartMoveRightTimer() {
			TBrickMoveRight tBrickMoveRight = new TBrickMoveRight(DELAY_TO_RIGHT, null, this);
			tBrickMoveRight.addActionListener(tBrickMoveRight);
			tBrickMoveRight.start();
			createToRightPauseThread(tBrickMoveRight);
		}

		private void createToRightPauseThread(Timer tBrickMoveRight) {
			Thread pauseThread = new Thread(new Runnable() {
				@Override
				public void run() {
					do {
						Thread.yield();
					} while (tBrickMoveRight.isRunning());
					mainPanel.getGameField().enableMouseListeners();
					group = null;
					if (checkEndOfGame())
						releaseEndOfGame();
				}
			});
			pauseThread.start();
		}

		protected boolean checkEndOfGame() {
			for (int i = 0; i < gameFieldCells; i++) {
				if (checkDoubleBricksDown(i))
						return false;
			}
			for (int j = 0; j < gameFieldCells; j++) {
				if (checkDoubleBricksRight(j))
					return false;
			}
			return true;
		}

		private boolean checkDoubleBricksDown(int i) throws NullPointerException{
			for (int j = 0; j < gameFieldCells-1; j++) {
				if (bricks[i][j] == null
					|| bricks[i][j+1] == null)
					continue;
				
				Color brickColor = bricks[i][j].getColor();
				Color nextBrickColor = bricks[i][j+1].getColor();
				
				if (brickColor.equals(nextBrickColor))
					return true;
			}
			return false;
		}

		private boolean checkDoubleBricksRight(int j){
			for (int i = 0; i < gameFieldCells-1; i++) {
				if (bricks[i][j] == null
					|| bricks[i+1][j] == null)
					continue;
				
				Color brickColor = bricks[i][j].getColor();
				Color nextBrickColor = bricks[i+1][j].getColor();
				
				if (brickColor.equals(nextBrickColor))
					return true;
			}
			return false;
		}

		private void releaseEndOfGame() {
			if (checkEmptyGameField()){
				setScore(score*2);
				JOptionPane.showMessageDialog(mainPanel, "End of game! No more moves."+System.lineSeparator()
						+ "Your total score was doubled due to empty gamefield.");
			}else
				JOptionPane.showMessageDialog(mainPanel, "End of game! No more moves.");
			mainPanel.createNewGameField();
			mainPanel.setTextGroupScoreLabel(0);
		}

		private boolean checkEmptyGameField() {
			for (int i = 0; i < gameFieldCells; i++)
				for (int j = 0; j < gameFieldCells; j++)
					if (bricks[i][j] != null)
						return false;
			return true;
		}

		@Override
		public void mousePressed(MouseEvent e) {}
		public void mouseReleased(MouseEvent e) {}
		public void mouseEntered(MouseEvent e) {}
		public void mouseExited(MouseEvent e) {}

		public void findWholeGroup(Brick brick){
			int i = brick.getIJpoint().x;
			int j = brick.getIJpoint().y;
			if(i > 0){
				Brick nextBrick = bricks[i-1][j];
				checkNextBrick(brick, nextBrick);
			}
			if(i < gameFieldCells-1){
				Brick nextBrick = bricks[i+1][j];
				checkNextBrick(brick, nextBrick);
			}
			if(j > 0){
				Brick nextBrick = bricks[i][j-1];
				checkNextBrick(brick, nextBrick);
			}
			if(j < gameFieldCells-1){
				Brick nextBrick = bricks[i][j+1];
				checkNextBrick(brick, nextBrick);
			}
		}
		
			private void checkNextBrick(Brick brick, Brick nextBrick){
				if (nextBrick != null
					&& nextBrick.getColor().equals(brick.getColor())
						&& !isBrickAlreadyInGroup(nextBrick)){
						group.add(nextBrick);
						findWholeGroup(nextBrick);
					}
			}
		
				private boolean isBrickAlreadyInGroup(Brick brick){
					for (Brick br : group) {
						if (brick.getIJpoint().equals(br.getIJpoint()))
							return true;
					}
					return false;
				}

	public void setLeftSide(int newLeftSide) {
		leftSide = newLeftSide;
	}
	
	public Brick[][] getBricks(){
		return bricks;
	}
	
	public int getLeftSide(){
		return leftSide;
	}
	
	public int[] getColumnFirstIndexes(){
		return columnFirstIndex;
	}
	
	private void calculateAndAddScoreFromDeadGroup(){
		setScore(score+calculateScoreFromDeadGroup());
	}
	
		private int calculateScoreFromDeadGroup(){
			int k = (mainPanel.getColorBox().getSelectedIndex()+3)*7;
			int addScore = 0;
			int q = 0;
			for (@SuppressWarnings("unused") Brick brick : group) {
				q++;
				addScore += q;
			}
			if (q == 1)
				return 0;
			else
				return addScore*k;
		}
	
	public void setNewBricksArray(int gameFieldCells){
		 bricks = new Brick[gameFieldCells][gameFieldCells];
	}
	
	public void setGameFieldCells(int newCells){
		gameFieldCells = newCells;
	}
}
