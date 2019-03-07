package eg.edu.guc.yugioh.listeners;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;

import eg.edu.guc.yugioh.board.Board;
import eg.edu.guc.yugioh.cards.Card;
import eg.edu.guc.yugioh.gui.GUI;

public class MessageWindow extends JDialog implements ActionListener{
JLabel lable;
JButton NewGame;
JButton Exit;
GUI gui;
	public MessageWindow(GUI gui,String txt){
		super(gui,"",true);
		this.gui=gui;
	    setLayout(null);
	    this.setBounds(450, 250, 450, 250);
	    this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
	 
	    lable=new JLabel(txt,JLabel.CENTER);
   	    lable.setFont(new Font(txt, 50, 30));
   	    lable.setBounds(0, 0, 450, 100);
	    NewGame =new JButton("New Game");
	    NewGame.setBounds(50, 150, 100, 30);
	    Exit=new JButton("Exit");
	    Exit.setBounds(250, 150, 100, 30);
        NewGame.addActionListener(this);
        Exit.addActionListener(this);
	    getContentPane().add(lable);
	    getContentPane().add(NewGame);
	    getContentPane().add(Exit);
	 this.validate();
		gui.setEnabled(false);
		gui.dispose();
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource()==NewGame){
			
			Board b=new Board();
			GUI x=new GUI(b); 
//			gui.setEnabled(false);
//			gui.dispose();
			this.dispose();
		new StartController(x, b);
			
		}
		if(e.getSource()==Exit){
//			gui.setEnabled(false);
//			gui.dispose();
			this.dispose();
		}
		
	}
	
}
