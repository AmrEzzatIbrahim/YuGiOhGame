package eg.edu.guc.yugioh.cards.spells;

import java.io.IOException;

import eg.edu.guc.yugioh.cards.Card;
import eg.edu.guc.yugioh.cards.Location;
import eg.edu.guc.yugioh.cards.MonsterCard;

public class CardDestruction extends SpellCard {

	public CardDestruction(String name, String description) {
		super(name, description);
	}

	public void action(MonsterCard monster) {
		int count = 0;
		while (!getBoard().getActivePlayer().getField().getHand().isEmpty()) {
			Card x=getBoard().getActivePlayer().getField().getHand().remove(0);
			x.setLocation(Location.GRAVEYARD);
			getBoard().getActivePlayer().getField().getGraveyard().add(x);
			count++;
		
		}
		if(count>getBoard().getActivePlayer().getField().getDeck().getDeck().size())
			Card.getBoard().setWinner(Card.getBoard().getOpponentPlayer());
		else{
			getBoard().getActivePlayer().getField().addNCardsToHand(count);
		}
		count = 0;
		while (!getBoard().getOpponentPlayer().getField().getHand().isEmpty()) {
			Card x=getBoard().getOpponentPlayer().getField().getHand().remove(0);
			x.setLocation(Location.GRAVEYARD);
			getBoard().getOpponentPlayer().getField().getGraveyard().add(x);
			count++;
		}
		if(count>getBoard().getOpponentPlayer().getField().getDeck().getDeck().size())
			Card.getBoard().setWinner(Card.getBoard().getOpponentPlayer());
		else{
			getBoard().getOpponentPlayer().getField().addNCardsToHand(count);
		}
	}

}