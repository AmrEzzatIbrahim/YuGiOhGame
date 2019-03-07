package eg.edu.guc.yugioh.board.player;

import eg.edu.guc.yugioh.cards.Card;
import eg.edu.guc.yugioh.cards.Location;
import eg.edu.guc.yugioh.cards.Mode;
import eg.edu.guc.yugioh.cards.MonsterCard;
import eg.edu.guc.yugioh.cards.spells.SpellCard;
import eg.edu.guc.yugioh.exceptions.NoMonsterSpaceException;
import eg.edu.guc.yugioh.exceptions.NoSpellSpaceException;
import eg.edu.guc.yugioh.exceptions.UnexpectedFormatException;

import java.io.IOException;
import java.util.ArrayList;

public class Field {

	private Phase phase;
	private ArrayList<MonsterCard> monstersArea;
	private ArrayList<SpellCard> spellArea;
	private ArrayList<Card> hand;
	private ArrayList<Card> graveyard;
	private Deck deck;
	private boolean summoned; 	

	public Field() throws IOException, UnexpectedFormatException {
		phase = Phase.MAIN1;
		this.monstersArea = new ArrayList<>();
		this.spellArea = new ArrayList<>();
		this.hand = new ArrayList<>();
		this.graveyard = new ArrayList<>();
		deck = new Deck();
	}
	public boolean isSummoned() {
		return summoned;
	}

	public void setSummoned(boolean summoned) {
		this.summoned = summoned;
	}
	public Deck getDeck() {
		return deck;
	}

	public Phase getPhase() {
		return phase;
	}

	public void setPhase(Phase phase) {
		this.phase = phase;
	}

	public ArrayList<MonsterCard> getMonstersArea() {
		return monstersArea;
	}

	public ArrayList<SpellCard> getSpellArea() {
		return spellArea;
	}

	public ArrayList<Card> getHand() {
		return hand;
	}

	public ArrayList<Card> getGraveyard() {
		return graveyard;
	}

	public void addMonsterToField(MonsterCard monster, Mode m, boolean isHidden) {
		
			if (monstersArea.size() < 5) {
				monstersArea.add(monster);
				monster.setMode(m);
				monster.setHidden(isHidden);
				monster.setLocation(Location.FIELD);
				Card.getBoard().getActivePlayer().getField().getHand().remove(monster);
				summoned=true;
			}
			else{
				throw new NoMonsterSpaceException();
			}
		
	}

	

	public void addMonsterToField(MonsterCard monster, Mode mode,      
			ArrayList<MonsterCard> sacrifices) {
		
		int n = sacrifices.size();
		int level=monster.getLevel();
		if(level<=4){
			if(n==0)
			addMonsterToField(monster, mode, mode == Mode.ATTACK ? false : true);
		}
		else if(level<=6){
			if(n==1){
			addMonsterToField(monster, mode, mode == Mode.ATTACK ? false : true);
			removeMonsterToGraveyard(sacrifices.get(0));}
		}
		else if(level<=8){
			if(n==2){
				addMonsterToField(monster, mode, mode == Mode.ATTACK ? false : true);
				removeMonsterToGraveyard(sacrifices.get(0));
				removeMonsterToGraveyard(sacrifices.get(1));}
		}
		}
		


	public void removeMonsterToGraveyard(MonsterCard monster) {
		if(monstersArea.contains(monster)){
		MonsterCard x = monstersArea.remove(monstersArea.indexOf(monster));
		x.setLocation(Location.GRAVEYARD);
		graveyard.add(x);
		}
		
		
		}
	

	public void removeMonsterToGraveyard(ArrayList<MonsterCard> monsters) {
		while (!monsters.isEmpty())
			removeMonsterToGraveyard(monsters.remove(0));
	}

	public void addSpellToField(SpellCard action, MonsterCard monster, boolean hidden) {
		if (spellArea.size() < 5) {
			spellArea.add(action);
			hand.remove(action);
			action.setLocation(Location.FIELD);
			if (!hidden) {
				activateSetSpell(action, monster);
			}
		}
		else{
			throw new NoSpellSpaceException();
			
		}
	}

	public void activateSetSpell(SpellCard action, MonsterCard monster)  {
		if (action.getLocation()==Location.FIELD||spellArea.contains(action)) {
			action.setHidden(false);
			action.action(monster);
			removeSpellToGraveyard(action);
		
		}
	}

	public void removeSpellToGraveyard(SpellCard spell) {
		if(spell.getLocation()==Location.FIELD||spellArea.contains(spell)){
		spellArea.remove(spell);
		spell.setLocation(Location.GRAVEYARD);
		graveyard.add(spell);
		}
		
	
	}

	public void removeSpellToGraveyard(ArrayList<SpellCard> spells) {
		while (!spells.isEmpty())
			removeSpellToGraveyard(spells.remove(0));
	}

	public void addCardToHand() {
		if(!deck.getDeck().isEmpty()){
		Card x = deck.drawOneCard();
		hand.add(x);
	x.setLocation(Location.HAND);
		}
		else
		{
			Card.getBoard().setWinner(Card.getBoard().getOpponentPlayer());
		}
		
	}

	public void addNCardsToHand(int n) {
		for(int i=0;i<n;i++)
			addCardToHand();
	}

}
