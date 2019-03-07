package eg.edu.guc.yugioh.gui;

import java.awt.GridLayout;
import java.util.ArrayList;

import javax.swing.JPanel;

import eg.edu.guc.yugioh.board.player.Field;

public class SpellAreaPanel extends JPanel {
	private ArrayList<CardButton> SpellAreaList;

	public SpellAreaPanel(Field field, boolean activePlayer, GUI gui) {
		super();
		SpellAreaList = new ArrayList<CardButton>();
		this.setLayout(new GridLayout(1, 5));
		int j= 0;
		for (int i=0;i<5;i++) {
			CardButton cardButton;
			if (j < field.getSpellArea().size()) 
			 cardButton = new CardButton(field.getSpellArea().get(i),
					activePlayer, gui);
			else
				cardButton = new CardButton(null, activePlayer,gui);
			j++;
			SpellAreaList.add(cardButton);
			add(cardButton);
		}
	}

	public ArrayList<CardButton> getSpellAreaList() {
		return SpellAreaList;
	}

}
