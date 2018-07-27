package mech.timers;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.Timer;

import visual.Brick;

@SuppressWarnings("serial")
public class TBrickDestroy extends Timer implements ActionListener{
	private ArrayList<Brick> group;

	public TBrickDestroy(int delay, ActionListener listener, ArrayList<Brick> group) {
		super(delay, listener);
		this.group = group;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		for (Brick brick : group) {
			brick.reduceSize();
		}
		if (group.get(0).getWidth() < 0)
			stop();
	}

}
