package eg.edu.guc.yugioh.cards;

import java.io.IOException;

public class MonsterCard extends Card {

	private int level;
	private int defensePoints;
	private int attackPoints;
	private Mode mode;
    
	public MonsterCard(String name, String description, int level,int attackPoints, int defensePoints) {
		super(name, description);
		this.level = level;
		this.defensePoints = defensePoints;
		this.attackPoints = attackPoints;
		this.mode = Mode.DEFENSE;

	}

	public Mode getMode() {
		return mode;
	}

	public void setMode(Mode mode) {
		this.mode = mode;
	}

	public int getLevel() {
		return level;
	}

	public void setDefensePoints(int defensePoints) {
		this.defensePoints = defensePoints;
	}

	public void setAttackPoints(int attackPoints) {
		this.attackPoints = attackPoints;
	}

	public int getDefensePoints() {
		return defensePoints;
	}

	public int getAttackPoints() {
		return attackPoints;
	}
	
	public MonsterCard copy() {
		return new MonsterCard(this.getName(),this.getDescription(),level,attackPoints,defensePoints);
	}

	
	public void action(MonsterCard monster) {
		if(this.mode==Mode.ATTACK){
	 if(monster.getMode()==Mode.ATTACK){
		 if(attackPoints>monster.attackPoints){
			 getBoard().getOpponentPlayer().setLifePoints(getBoard().getOpponentPlayer().getLifePoints()-Math.abs(attackPoints-monster.attackPoints));
		     getBoard().getOpponentPlayer().getField().removeMonsterToGraveyard(monster);
		 }
		 else if(attackPoints==monster.attackPoints){
			 getBoard().getOpponentPlayer().getField().removeMonsterToGraveyard(monster);
			 getBoard().getActivePlayer().getField().removeMonsterToGraveyard(this);
	 }
		 else{
			 getBoard().getActivePlayer().setLifePoints(getBoard().getActivePlayer().getLifePoints()-Math.abs(attackPoints-monster.attackPoints));
			 getBoard().getActivePlayer().getField().removeMonsterToGraveyard(this);
		 }
	}
	 else{
		 if(attackPoints>monster.getDefensePoints()){
			 getBoard().getOpponentPlayer().getField().removeMonsterToGraveyard(monster);
	 }
	 else if(attackPoints<monster.defensePoints){
		 getBoard().getActivePlayer().setLifePoints(getBoard().getActivePlayer().getLifePoints()-Math.abs(attackPoints-monster.defensePoints));
		 monster.setHidden(false);
	 }
}
   checkWinner();
      }
		}
	public static void checkWinner(){
		 if(getBoard().getActivePlayer().getLifePoints()<=0){
			 getBoard().setWinner(getBoard().getOpponentPlayer());
		}
		 if(getBoard().getOpponentPlayer().getLifePoints()<=0){
			 getBoard().setWinner(getBoard().getActivePlayer());
		 }		
	}
	public void action(){
		if(this.mode==Mode.ATTACK){
    getBoard().getOpponentPlayer().setLifePoints(getBoard().getOpponentPlayer().getLifePoints()-getAttackPoints());		
	checkWinner();
	}
	}
}
