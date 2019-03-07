package eg.edu.guc.yugioh.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.LayoutManager;
import java.awt.Rectangle;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class StartPanel extends JPanel {
	private JLabel Player1;
	private JLabel Player2;
	private Player1NameTxt PlayerName1;
	private Player2NameTxt PlayerName2;
	private JLabel Background;
	private JButton StartGame;
public StartPanel() {
	super();
	setLayout(null);
	setSize(getMaximumSize());
	Player1=new JLabel("Player One:");
	Player1.setForeground(Color.white);
	Player1.setOpaque(false);
	this.add(Player1);
	
	Player1.setBounds(530,260,100,50 );
	PlayerName1=new Player1NameTxt();
	this.add(PlayerName1);
	
	PlayerName1.setBounds(640,260,200,50);
	PlayerName1.setOpaque(false);
	PlayerName1.setForeground(Color.white);
	PlayerName1.setBorder(BorderFactory.createLineBorder(Color.black, 2, true));
	Player2=new JLabel("Player Two:");
	this.add(Player2);
	
	Player2.setBounds(530,320,150,50);	
	PlayerName2=new Player2NameTxt();
	Player2.setForeground(Color.white);
	Player2.setOpaque(false);
	this.add(PlayerName2);
	PlayerName2.setBounds(640, 320, 200,50);
	PlayerName2.setOpaque(false);
	PlayerName2.setForeground(Color.white);
	PlayerName2.setBorder(BorderFactory.createLineBorder(Color.black, 2, true));
	
	StartGame=new JButton("Start Game");
	this.add(StartGame);
	StartGame.setBounds(650, 400, 100, 50);
	StartGame.setOpaque(true);
	StartGame.setForeground(Color.white);
	StartGame.setBackground(Color.blue);
	Background = new JLabel();
	Background.setBounds(0, 0,1370,720);;
Background.setIcon(new ImageIcon("startBackground.jpg"));
	this.add(Background);
   this.validate();
}
public JLabel getPlayer1() {
	return Player1;
}
public JLabel getPlayer2() {
	return Player2;
}
public JTextField getPlayerName1() {
	return PlayerName1;
}
public JTextField getPlayerName2() {
	return PlayerName2;
}
public JLabel getBackgroung() {
	return Background;
}
public JButton getStartGame() {
	return StartGame;
}

public static void main(String[] args){
	JFrame f=new JFrame();
	f.setVisible(true);
	f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	f.setLayout(null);
	f.setSize(f.getMaximumSize());
	
	StartPanel s=new StartPanel();
	f.add(s);
	s.setBounds(0,0, f.getWidth(), f.getHeight());
	
	System.out.println(f.getHeight());
	System.out.println(f.getWidth());
	f.validate();
    
}

}
