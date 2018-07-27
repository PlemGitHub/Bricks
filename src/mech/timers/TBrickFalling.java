package mech.timers;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Timer;

import visual.Brick;

@SuppressWarnings("serial")
public class TBrickFalling extends Timer implements ActionListener{
	private Brick brick;
	private int stepsDone;

	public TBrickFalling(int delay, ActionListener listener, Brick brick) {
		super(delay, listener);
		this.brick = brick;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		brick.moveOneStepDown();
		stepsDone++;
		if (stepsDone == brick.getStepsToFall())
			stop();
	}

}
