package visual;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.KeyStroke;

import MyElements.MyButton;
import MyElements.ColorComboBox;
import MyElements.MyLabel;
import MyElements.SizeComboBox;
import mech.Constants;
import mech.Mech;

@SuppressWarnings("serial")
public class MainPanel extends JPanel implements Constants, ItemListener{
	private Mech mech;
	private GameField gameField;
	private final String exitStr = "exitStr";
	private MyButton newGameBtn;
	private MyLabel scoreLbl;
	private MyLabel groupScoreLbl;
	private ColorComboBox colorBox;
	private SizeComboBox sizeBox;
	private JLabel colorBoxLbl, sizeBoxLbl;
	private int colorBoxIndex;
	private int sizeBoxIndex;
	
	public MainPanel() {
		mech = new Mech(this);
		setLayout(null);
		setBackground(Color.WHITE);
				
		getInputMap(WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("ESCAPE"), exitStr);
		getActionMap().put(exitStr, exitAction);
		
		createNewgameButton();
		createScoreLabels();
		createColorCombobox();
		createSizeCombobox();
		
		newGameBtn.doClick();
	}

	private void createNewgameButton() {
		newGameBtn = new MyButton(this, "New Game");
		add(newGameBtn);
	}

	private void createScoreLabels() {
		scoreLbl = new MyLabel("score");
		add(scoreLbl);
		groupScoreLbl = new MyLabel("group score");
		add(groupScoreLbl);
	}

	private void createColorCombobox() {
		colorBox = new ColorComboBox();
		colorBox.addItemListener(this);
		add(colorBox);
		
		colorBoxLbl = new JLabel("  COLORS");
		colorBoxLbl.setBounds(COLORBOX_RECTANGLE_LABEL);
		add(colorBoxLbl);
	}

	private void createSizeCombobox() {
		sizeBox = new SizeComboBox();
		sizeBox.addItemListener(this);
		add(sizeBox);
		
		sizeBoxLbl = new JLabel("  SIZE");
		sizeBoxLbl.setBounds(SIZEBOX_RECTANGLE_LABEL);
		add(sizeBoxLbl);
	}

	private Action exitAction = new AbstractAction() {
		
		@Override
		public void actionPerformed(ActionEvent e) {
			System.exit(0);
		}
	};

	public void createNewGameField() {
		createField();
		mech.setScore(0);
	}

	private void createField() {
		if (gameField != null){
			gameField.disableMouseListeners();
			remove(gameField);
		}
		gameField = new GameField(this);
		add(gameField);
		repaint();
	}
	
	public void setTextScoreLabel(int i){
		scoreLbl.setText(Integer.toString(i));
	}
	
	public void setTextGroupScoreLabel(int i){
		groupScoreLbl.setText(Integer.toString(i));
	}
	
	public Mech getMech(){
		return mech;
	}
	
	public GameField getGameField(){
		return gameField;
	}
	
	public ColorComboBox getColorBox(){
		return colorBox;
	}
	
	public SizeComboBox getSizeBox(){
		return sizeBox;
	}

	@Override
	public void itemStateChanged(ItemEvent e) {
		if (e.getSource().equals(colorBox))
			if (colorBox.getSelectedIndex() != colorBoxIndex){
				colorBoxIndex = colorBox.getSelectedIndex();
				createNewGameField();
			}
		
		if (e.getSource().equals(sizeBox))
			if (sizeBox.getSelectedIndex() != sizeBoxIndex){
				sizeBoxIndex = sizeBox.getSelectedIndex();
				createNewGameField();
			}
	}
}