package eg.edu.guc.yugioh.cards.spells;

import java.io.IOException;

import eg.edu.guc.yugioh.cards.Card;
import eg.edu.guc.yugioh.cards.Location;
import eg.edu.guc.yugioh.cards.MonsterCard;

public class Raigeki extends SpellCard {

	public Raigeki(String name, String description) {
		super(name, description);
	}

	public void action(MonsterCard monster) {
	
		
		while(!Card.getBoard().getOpponentPlayer().getField().getMonstersArea().isEmpty()){
			MonsterCard x=getBoard().getOpponentPlayer().getField().getMonstersArea().remove(0);
		    Card.getBoard().getOpponentPlayer().getField().getGraveyard().add(x);
		    x.setLocation(Location.GRAVEYARD);
		}
	}
}
