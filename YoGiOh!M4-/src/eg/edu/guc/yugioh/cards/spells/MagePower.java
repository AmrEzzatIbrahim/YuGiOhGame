package eg.edu.guc.yugioh.cards.spells;

import java.io.IOException;

import eg.edu.guc.yugioh.cards.MonsterCard;

public class MagePower extends SpellCard {

	public MagePower(String name, String description) {
		super(name, description);
	}
public void action(MonsterCard monster){
		int count=getBoard().getActivePlayer().getField().getSpellArea().size();
		increase(monster,500*count);
	}

}
