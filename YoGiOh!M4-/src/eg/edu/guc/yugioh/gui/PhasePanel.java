package eg.edu.guc.yugioh.gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class PhasePanel extends JPanel {
private JLabel PlayerTurn;
private JLabel Phase;
private JButton EndPhase;
private JButton EndTurn;

	public PhasePanel(){
	super();
	this.setPreferredSize(new Dimension(100,150));
	this.setLayout(new GridLayout(4,1));
	PlayerTurn=new JLabel();
	PlayerTurn.setOpaque(false);
	PlayerTurn.setForeground(Color.RED);
	PlayerTurn.setBorder(null);

	Phase=new JLabel();
	Phase.setOpaque(false);
	Phase.setForeground(Color.RED);
	Phase.setBorder(null);
	
	EndPhase=new JButton();
	EndPhase.setText("End Phase");
	EndPhase.setOpaque(false);
	EndPhase.setContentAreaFilled(false);
	EndPhase.setForeground(Color.RED);
	
    EndTurn=new JButton();
	EndTurn.setText("End Turn");
	EndTurn.setOpaque(false);
	EndTurn.setContentAreaFilled(false);
	EndTurn.setForeground(Color.RED);
	
	this.add(this.PlayerTurn);
	this.add(this.Phase);
	this.add(EndPhase);
	this.add(EndTurn);
	this.validate();
}

	public JLabel getPlayerTurn() {
		return PlayerTurn;
	}

	public JLabel getPhase() {
		return Phase;
	}

	public JButton getEndPhase() {
		return EndPhase;
	}

	public JButton getEndTurn() {
		return EndTurn;
	}
	
}
