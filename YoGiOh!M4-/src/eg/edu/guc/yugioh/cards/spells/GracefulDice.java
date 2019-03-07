package eg.edu.guc.yugioh.cards.spells;

import java.io.IOException;

import eg.edu.guc.yugioh.cards.MonsterCard;

public class GracefulDice extends SpellCard {

	public GracefulDice(String name, String description) {
		super(name, description);
	}
public void action(MonsterCard monster){
		int increase=(int) Math.floor(Math.random()*10)+1;
		for(int i=0;i<getBoard().getActivePlayer().getField().getMonstersArea().size();i++){
			increase(getBoard().getActivePlayer().getField().getMonstersArea().get(i),100*increase);
		}
			
	}


}
