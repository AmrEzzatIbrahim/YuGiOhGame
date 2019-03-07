package eg.edu.guc.yugioh.cards.spells;

import java.io.IOException;

import eg.edu.guc.yugioh.board.Board;
import eg.edu.guc.yugioh.board.player.Player;
import eg.edu.guc.yugioh.cards.Card;
import eg.edu.guc.yugioh.cards.MonsterCard;

public class MonsterReborn extends SpellCard {

	public MonsterReborn(String name, String description){
		super(name, description);
	}

	public void action(MonsterCard monster){
		MonsterCard max = new MonsterCard("","",0,0,0);
		boolean flag = false;
	    boolean monsterfound=false;
	    if(!getBoard().getActivePlayer().getField().getGraveyard().isEmpty())
		for (int i = 0; i < getBoard().getActivePlayer().getField()
				.getGraveyard().size(); i++) {
			if (getBoard().getActivePlayer().getField().getGraveyard().get(i) instanceof MonsterCard) {
				monsterfound=true;
				if (((MonsterCard) (getBoard().getActivePlayer().getField()
						.getGraveyard().get(i))).getAttackPoints() > max
						.getAttackPoints()) {
					max = (MonsterCard) getBoard().getActivePlayer().getField()
							.getGraveyard().get(i);
					
				}
			}
		}
	    if(!getBoard().getOpponentPlayer().getField().getGraveyard().isEmpty())
		for (int i = 0; i < getBoard().getOpponentPlayer().getField()
				.getGraveyard().size(); i++) {
			if (getBoard().getOpponentPlayer().getField().getGraveyard().get(i) instanceof MonsterCard) {
				monsterfound=true;
				if (((MonsterCard) (getBoard().getOpponentPlayer().getField()
						.getGraveyard().get(i))).getAttackPoints() > max
						.getAttackPoints()) {
					max = (MonsterCard) getBoard().getOpponentPlayer().getField()
							.getGraveyard().get(i);
					flag = true;
				}
			}
		}
	    if(monsterfound){
		getBoard().getActivePlayer().getField().addMonsterToField(max, max.getMode(), false);
		getBoard().getActivePlayer().getField().setSummoned(false);
		if (!flag) {
			getBoard()
					.getActivePlayer()
					.getField()
					.getGraveyard()
					.remove(getBoard().getActivePlayer().getField()
							.getGraveyard().indexOf(max));
		} else {
			getBoard()
					.getOpponentPlayer()
					.getField()
					.getGraveyard()
					.remove(getBoard().getOpponentPlayer().getField()
							.getGraveyard().indexOf(max));
			
		}
		}
		
		
		

	}
	

}
