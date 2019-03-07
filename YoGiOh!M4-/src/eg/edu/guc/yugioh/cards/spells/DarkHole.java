package eg.edu.guc.yugioh.cards.spells;

import java.io.IOException;

import eg.edu.guc.yugioh.cards.Card;
import eg.edu.guc.yugioh.cards.Location;
import eg.edu.guc.yugioh.cards.MonsterCard;

public class DarkHole extends Raigeki {

	public DarkHole(String name, String description) {
		super(name, description);
	}

	public void action(MonsterCard monster) {
			super.action(monster);
			while(!Card.getBoard().getActivePlayer().getField().getMonstersArea().isEmpty()){
				MonsterCard x=getBoard().getActivePlayer().getField().getMonstersArea().remove(0);
			    Card.getBoard().getActivePlayer().getField().getGraveyard().add(x);
			    x.setLocation(Location.GRAVEYARD);
			}
		
	}
	
}
