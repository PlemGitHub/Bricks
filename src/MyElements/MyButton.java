package MyElements;

import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JButton;
import mech.Constants;
import visual.MainPanel;

@SuppressWarnings("serial")
public class MyButton extends JButton implements Constants{
	private MainPanel mainPanel;
	
	public MyButton(MainPanel mainPanel, String str) {
		this.mainPanel = mainPanel;
		setBounds(BUTTON_RECTANGLE);
		setFocusable(false);
		setFont(FONT);
		setAction(newGameAction);
		setText(str);
		setHorizontalAlignment(JButton.CENTER);
	}
	
	private Action newGameAction = new AbstractAction() {
		
		@Override
		public void actionPerformed(ActionEvent e) {
			mainPanel.createNewGameField();
		}
	};
	
}
