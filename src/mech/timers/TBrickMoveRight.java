package mech.timers;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.Timer;
import mech.Constants;
import mech.Mech;
import visual.Brick;

@SuppressWarnings("serial")
public class TBrickMoveRight extends Timer implements ActionListener, Constants{
	private int leftSide;
	private Brick[][] bricks;
	private boolean noEmptyColumns;
	private int[] columnFirstIndexes;
	private Mech mech;
	private int gameFieldCells;

	public TBrickMoveRight(int delay, ActionListener listener, Mech mech) {
		super(delay, listener);
		this.mech = mech;
		leftSide = mech.getLeftSide();
		bricks = mech.getBricks();
		columnFirstIndexes = mech.getColumnFirstIndexes();
		gameFieldCells = mech.getMainPanel().getGameField().getGameFieldCells();
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		noEmptyColumns = true;
		for (int i = gameFieldCells-1; i > leftSide; i--) {
			if (bricks[i][gameFieldCells-1] == null ){
				moveAllbricksRight(i);
				noEmptyColumns = false;
				leftSide++;
				break;
			}
		}
		
		if (noEmptyColumns)
			stop();
	}

	private void moveAllbricksRight(int emptyColumn) {
		for (int i = emptyColumn-1; i >= leftSide; i--) {
			for (int j = columnFirstIndexes[i]; j < gameFieldCells; j++) {
				if (j < gameFieldCells) {
					bricks[i][j].moveOneStepRight();
					bricks[i+1][j] = bricks[i][j];
					bricks[i][j] = null;
				}
			}
		}
		mech.findLeftSide();
		mech.findColumnFirstIndexes();
	}
}
