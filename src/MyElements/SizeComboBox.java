package MyElements;

import javax.swing.JComboBox;

import mech.Constants;

@SuppressWarnings("serial")
public class SizeComboBox extends JComboBox<String> implements Constants{

	public SizeComboBox() {
		setBounds(SIZEBOX_RECTANGLE);
		for (int i = 10; i <= 50; i+=10) {
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
