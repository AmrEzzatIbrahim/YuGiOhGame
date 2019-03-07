package eg.edu.guc.yugioh.listeners;

import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;

import eg.edu.guc.yugioh.gui.GUI;

public class pauseForSwitch extends JDialog implements ActionListener{
     JLabel label;
	JButton button;
	GUI gui;
	public pauseForSwitch(GUI gui){
		super(gui,"Change Player");
		this.gui=gui;
		this.setBounds(0, 0, gui.getWidth(),gui.getHeight());
		this.setLayout(null);
	   button =new JButton("Done");
	   label = new JLabel("Please Switch Players then press done If it's your turn!",JLabel.CENTER);
	   button.setBounds(gui.getContentPane().getWidth()/2-50,gui.getContentPane().getHeight()/2+50,100, 50);
	   label.setBounds(450,300,600,20);
		this.setDefaultCloseOperation(HIDE_ON_CLOSE);
		this.add(button);
		this.add(label);
		button.addActionListener(this);
		this.setVisible(true);
		this.validate();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		this.dispose();
	}

}
