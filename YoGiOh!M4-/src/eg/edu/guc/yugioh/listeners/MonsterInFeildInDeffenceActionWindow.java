package eg.edu.guc.yugioh.listeners;

import java.awt.Dialog;
import java.awt.FlowLayout;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuItem;

public class MonsterInFeildInDeffenceActionWindow extends Dialog {
	 private JLabel lable;
	    private JMenu menu;
	    private JMenuItem menuItem;
		public MonsterInFeildInDeffenceActionWindow(JFrame frame, String txt) {
			super(frame,"", true);
			setLayout(new FlowLayout());
			lable=new JLabel(txt);
		    add(lable);
		    menu=new JMenu("Monster Actions");
		    menuItem=new JMenuItem("");
		    add(menu);
		    
			
		}
}
