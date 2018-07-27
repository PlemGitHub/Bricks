package MyElements;

import javax.swing.JComboBox;

import mech.Constants;

@SuppressWarnings("serial")
public class ColorComboBox extends JComboBox<String> implements Constants{

	public ColorComboBox() {
		setBounds(COLORBOX_RECTANGLE);
		for (int i = 3; i <= COLORS.length; i++) {
			addItem(makeObj(Integer.toString(i)).toString());
		}
	}
	
	private Object makeObj(final String item){
	     return new Object() {
	    	 public String toString(){
	    		 return item; 
	    	 }
	     };
	}
	
	public int getColorQuantity(){
		return getSelectedIndex()+3;
	}
}
