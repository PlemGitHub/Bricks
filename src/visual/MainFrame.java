package visual;

import java.awt.Frame;
import javax.swing.JFrame;

public class MainFrame {
	
	private JFrame fr;
	private MainPanel mp;
	
	public MainFrame() {
		mp = new MainPanel();
		
		fr = new JFrame("Bricks");
		fr.setContentPane(mp);
		fr.setExtendedState(Frame.MAXIMIZED_BOTH);
		fr.setVisible(true);
		fr.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	public static void main(String[] args) {
		@SuppressWarnings("unused")
		MainFrame scr = new MainFrame();
	}
}