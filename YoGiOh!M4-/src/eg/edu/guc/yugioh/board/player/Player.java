package eg.edu.guc.yugioh.board.player;

import java.awt.Component;
import java.io.IOException;
import java.lang.invoke.WrongMethodTypeException;
import java.util.ArrayList;

import eg.edu.guc.yugioh.board.Board;
import eg.edu.guc.yugioh.cards.Card;
import eg.edu.guc.yugioh.cards.Location;
import eg.edu.guc.yugioh.cards.Mode;
import eg.edu.guc.yugioh.cards.MonsterCard;
import eg.edu.guc.yugioh.cards.spells.CardDestruction;
import eg.edu.guc.yugioh.cards.spells.GracefulDice;
import eg.edu.guc.yugioh.cards.spells.PotOfGreed;
import eg.edu.guc.yugioh.cards.spells.SpellCard;
import eg.edu.guc.yugioh.exceptions.DefenseMonsterAttackException;
import eg.edu.guc.yugioh.exceptions.MonsterMultipleAttackException;
import eg.edu.guc.yugioh.exceptions.MultipleMonsterAdditionException;
import eg.edu.guc.yugioh.exceptions.UnexpectedFormatException;
import eg.edu.guc.yugioh.exceptions.WrongPhaseException;

public class Player implements Duelist {

	private String name;
	private int lifePoints;
	private Field field;
    private ArrayList<MonsterCard> switchedMonsters;
    private ArrayList<MonsterCard> attackers;
    
	public Player(String name) throws IOException, UnexpectedFormatException {
		this.name = name;
		lifePoints = 8000;
		field = new Field();
		switchedMonsters=new ArrayList<MonsterCard>();
		attackers=new ArrayList<MonsterCard>();
	}

	public int getLifePoints() {
		
		return lifePoints;
	}

	public void setLifePoints(int lifePoints) {
		this.lifePoints = lifePoints;
	}

	public String getName() {
		return name;
	}

	public Field getField() {
		return field;
	}

	public boolean noWinnerAndActive() {
		
		return (Card.getBoard().getWinner()==null&&Card.getBoard().getActivePlayer()==this);
	}

	public boolean summonMonster(MonsterCard monster) {
		if (noWinnerAndActive() && (field.getPhase() == Phase.MAIN1 || field.getPhase() == Phase.MAIN2) && !field.isSummoned()&& field.getHand().contains(monster))
			field.addMonsterToField(monster, Mode.ATTACK, false);
		else if(field.getPhase()==Phase.BATTLE)
			throw new WrongPhaseException();
		else if(field.isSummoned())
			throw new MultipleMonsterAdditionException();
		return monster.getLocation() == Location.FIELD;
	}

	public boolean summonMonster(MonsterCard monster,
			ArrayList<MonsterCard> sacrifices) {
		if (noWinnerAndActive() && (field.getPhase() == Phase.MAIN1 || field.getPhase() == Phase.MAIN2) && !field.isSummoned()&& field.getHand().contains(monster)) {
			field.addMonsterToField(monster, Mode.ATTACK, sacrifices);
		}
			else if(field.getPhase()==Phase.BATTLE)
				throw new WrongPhaseException();	
			else if(field.isSummoned())
				throw new MultipleMonsterAdditionException();

		return monster.getLocation()==Location.FIELD;
	}

	public boolean setMonster(MonsterCard monster) {
		if (noWinnerAndActive()
				&& (field.getPhase() == Phase.MAIN1 || field.getPhase() == Phase.MAIN2)
				&& !field.isSummoned()&& field.getHand().contains(monster)) {
			field.addMonsterToField(monster, Mode.DEFENSE, true);
		}
		else if(field.getPhase()==Phase.BATTLE)
			throw new WrongPhaseException();
		else if(field.isSummoned())
			throw new MultipleMonsterAdditionException();
		return monster.getLocation() == Location.FIELD;
	}

	public boolean setMonster(MonsterCard monster,
			ArrayList<MonsterCard> sacrifices) {
		if (noWinnerAndActive()
				&& (field.getPhase() == Phase.MAIN1 || field.getPhase() == Phase.MAIN2)
				&& !field.isSummoned()&& field.getHand().contains(monster)) {
			field.addMonsterToField(monster, Mode.DEFENSE, sacrifices);
		}
		else if(field.getPhase()==Phase.BATTLE)
			throw new WrongPhaseException();
		else if(field.isSummoned())
			throw new MultipleMonsterAdditionException();
		return monster.getLocation() == Location.FIELD;
	}

	public boolean setSpell(SpellCard spell) {
		if (noWinnerAndActive()
				&& (field.getPhase() == Phase.MAIN1 || field.getPhase() == Phase.MAIN2)&& (spell.getLocation()==Location.HAND||this.getField().getHand().contains(spell))) {
			field.addSpellToField(spell, null, true);

		}
		else if(field.getPhase()==Phase.BATTLE)
			throw new WrongPhaseException();
		return spell.getLocation() == Location.FIELD;
	}

	public boolean activateSpell(SpellCard spell, MonsterCard monster) {
		if (noWinnerAndActive()&& (field.getPhase() == Phase.MAIN1 || field.getPhase() == Phase.MAIN2)) {
			if (spell.getLocation() == Location.HAND||this.getField().getHand().contains(spell)) {
				if (setSpell(spell)){
					field.activateSetSpell(spell, monster);
					
				}
			} else if(spell.getLocation() == Location.FIELD||this.getField().getSpellArea().contains(spell)){
				field.activateSetSpell(spell, monster);	
			}
		}
		else if(field.getPhase()==Phase.BATTLE)
			throw new WrongPhaseException();
		return spell.getLocation()==Location.GRAVEYARD;
	}

	
	public boolean declareAttack(MonsterCard activeMonster,
			MonsterCard opponentMonster) {
		if(noWinnerAndActive()&&field.getPhase()==Phase.BATTLE&&activeMonster.getMode()==Mode.ATTACK){
			if(!attackers.contains(activeMonster)){
				activeMonster.action(opponentMonster);
				attackers.add(activeMonster);
			    return true;
			}
			else throw new MonsterMultipleAttackException();
			
			
		}
		else if(field.getPhase()!=Phase.BATTLE)
			throw new WrongPhaseException();
		else if(activeMonster.getMode()==Mode.DEFENSE)
			throw new DefenseMonsterAttackException();
		return false;
	}


	public boolean declareAttack(MonsterCard activeMonster) {
		if(noWinnerAndActive()&&field.getPhase()==Phase.BATTLE&&Card.getBoard().getOpponentPlayer().getField().getMonstersArea().isEmpty()&&activeMonster.getMode()==Mode.ATTACK){
			if(!attackers.contains(activeMonster)){
				activeMonster.action();
				attackers.add(activeMonster);
				return true;
			}
			else throw new MonsterMultipleAttackException();
			
		}
		else if(field.getPhase()!=Phase.BATTLE)
			throw new WrongPhaseException();
		else if(activeMonster.getMode()==Mode.DEFENSE)
			throw new DefenseMonsterAttackException();
		return false;
	}

	
	public void addCardToHand() {
		if (noWinnerAndActive())
			field.addCardToHand();
    
	}

	public void addNCardsToHand(int n) {
		if (noWinnerAndActive())
			field.addNCardsToHand(n);

	}

	
	public void endPhase() {
		if(noWinnerAndActive()){
		if(field.getPhase()==Phase.MAIN1){
		field.setPhase(Phase.BATTLE);
	}
	else {if(field.getPhase()==Phase.BATTLE){
		  field.setPhase(Phase.MAIN2);
	      }
     	  else{
		  endTurn();
	      }
	}
	}
	}

	
	public boolean endTurn() {
		
	if(noWinnerAndActive()){
		field.setSummoned(false);
		switchedMonsters.clear();
		attackers.clear();
		field.setPhase(Phase.MAIN1);
		Card.getBoard().nextPlayer();
		return true;
	}
	return false;	
	}

	
	public boolean switchMonsterMode(MonsterCard monster) {
		if((field.getPhase()==Phase.MAIN1||field.getPhase()==Phase.MAIN2)&&(!switchedMonsters.contains(monster))&&monster.getLocation()==Location.FIELD){
			if(monster.getMode()==Mode.ATTACK)
				monster.setMode(Mode.DEFENSE);
			else
				{monster.setMode(Mode.ATTACK);
				monster.setHidden(false);}
			
			switchedMonsters.add(monster);
			return true;
		}
		else if(field.getPhase()==Phase.BATTLE)
			{throw new WrongPhaseException();}
		return false;
	}

	public ArrayList<MonsterCard> getSwitchedMonsters() {
		return switchedMonsters;
	}

	public ArrayList<MonsterCard> getAttackers() {
		return attackers;
	}

	
	
}