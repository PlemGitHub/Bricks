package MyElements;

import javax.swing.JLabel;

import mech.Constants;

@SuppressWarnings("serial")
public class MyLabel extends JLabel implements Constants{

	public MyLabel(String str) {
		switch (str) {
		case "score": 
			setBounds(SCORE_RECTANGLE);
			break;
		case "group score":
			setBounds(GROUP_SCORE_RECTANGLE);
			break;
		}
		setFont(FONT);
		setText("0");
		setHorizontalAlignment(JLabel.CENTER);
		setOpaque(true);
	}
}
