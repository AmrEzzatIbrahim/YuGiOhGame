package eg.edu.guc.yugioh.cards.spells;

import java.io.IOException;

import eg.edu.guc.yugioh.cards.Card;
import eg.edu.guc.yugioh.cards.MonsterCard;

public class ChangeOfHeart extends SpellCard {

	public ChangeOfHeart(String name, String description) {
		super(name, description);
	}

	public void action(MonsterCard monster) {
              	getBoard()
				.getActivePlayer()
				.getField()
				.getMonstersArea()
				.add(getBoard()
						.getOpponentPlayer()
						.getField()
						.getMonstersArea()
						.remove(getBoard()
								.getOpponentPlayer()
								.getField()
								.getMonstersArea()
								.indexOf(monster))); 
	
	}
}
