package eg.edu.guc.yugioh.gui;

import java.awt.GridLayout;
import java.util.ArrayList;

import javax.swing.JPanel;

import eg.edu.guc.yugioh.board.player.Field;

public class MonsterAreaPanel extends JPanel {
	private ArrayList<CardButton> MonsterAreaList;

	public MonsterAreaPanel(Field field, boolean activePlayer, GUI gui) {
		super();
		MonsterAreaList = new ArrayList<CardButton>();
		this.setLayout(new GridLayout(1, 5));
		int j = 0;
		for (int i=0;i<5;i++) {
			CardButton cardButton;
			if (j < field.getMonstersArea().size()) 
			 cardButton = new CardButton(field.getMonstersArea().get(i),
					activePlayer, gui);
			else
				cardButton = new CardButton(null, activePlayer,gui);
			j++;
			MonsterAreaList.add(cardButton);
			add(cardButton);
		}
	}

	public ArrayList<CardButton> getMonsterAreaList() {
		return MonsterAreaList;
	}

}
