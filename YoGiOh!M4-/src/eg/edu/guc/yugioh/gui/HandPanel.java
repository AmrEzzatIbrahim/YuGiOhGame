package eg.edu.guc.yugioh.gui;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.util.ArrayList;

import javax.swing.JPanel;

import eg.edu.guc.yugioh.board.player.Field;
import eg.edu.guc.yugioh.cards.Card;

public class HandPanel extends JPanel {

	private ArrayList<CardButton> handList;

	public HandPanel(Field field, boolean activePlayer, GUI gui) {
		super();
		handList = new ArrayList<CardButton>();
		int size = field.getHand().size();

		setLayout(new GridLayout(1, size));

		for (int i = 0; i < size; i++) {

			CardButton x = new CardButton(field.getHand().get(i), activePlayer,
					gui);
			handList.add(x);
			add(x);
		}
	}

	public ArrayList<CardButton> getHandList() {
		return handList;
	}

	public void setHandList(ArrayList<CardButton> handList) {
		this.handList = handList;
	}

}
