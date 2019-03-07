package eg.edu.guc.yugioh.listeners;

import java.awt.Dimension;
import java.awt.GridBagLayout;
import java.awt.MenuItem;
import java.awt.PopupMenu;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javafx.scene.control.MenuButton;

import javax.swing.JButton;
import javax.swing.JDialog;

import eg.edu.guc.yugioh.board.Board;
import eg.edu.guc.yugioh.cards.spells.SpellCard;
import eg.edu.guc.yugioh.gui.GUI;

public class SpellInFieldActionWindow extends JDialog implements ActionListener{

	private JButton activate;
	private Board board;
	private SpellCard spell;
	public SpellInFieldActionWindow(GUI gui, Board board, SpellCard spell) {
		activate = new JButton("Activate");
		this.board = board;
		this.spell = spell;
		this.add(activate);

		activate.addActionListener(this);
		this.pack();
		this.setVisible(true);
	}

	public JButton getActivate() {
		return activate;
	}

	public void setActivate(JButton activate) {
		this.activate = activate;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		
	}
	
}
