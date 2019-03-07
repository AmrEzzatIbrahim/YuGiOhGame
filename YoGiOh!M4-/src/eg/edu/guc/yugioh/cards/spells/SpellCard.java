package eg.edu.guc.yugioh.cards.spells;

import java.io.IOException;

import eg.edu.guc.yugioh.cards.Card;
import eg.edu.guc.yugioh.cards.MonsterCard;

public abstract class SpellCard extends Card {
    
	
	public SpellCard(String name, String description) {
		super(name, description);

	}
	public SpellCard copy() {
		switch(this.getName()){
		case"Card Destruction":return new CardDestruction(this.getName(),this.getDescription());
		case"Change Of Heart":return new ChangeOfHeart(this.getName(),this.getDescription());
		case"Dark Hole":return new DarkHole(this.getName(),this.getDescription());
		case"Graceful Dice":return new GracefulDice(this.getName(),this.getDescription());
		case"Harpie's Feather Duster":return new HarpieFeatherDuster(this.getName(),this.getDescription());
		case"Heavy Storm":return new HeavyStorm(this.getName(),this.getDescription());
		case"Mage Power":return new MagePower(this.getName(),this.getDescription());
		case"Monster Reborn":return new MonsterReborn(this.getName(),this.getDescription());
		case"Pot of Greed":return new PotOfGreed(this.getName(),this.getDescription());
		case"Raigeki":return new Raigeki(this.getName(),this.getDescription());
		default:return null;
		}  
	
	
	}
	public static void increase(MonsterCard monster, int increase) {
		monster.setAttackPoints(monster.getAttackPoints() + increase);
		monster.setDefensePoints(monster.getDefensePoints() + increase);
	}
	
}
