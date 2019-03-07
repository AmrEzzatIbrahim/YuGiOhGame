package eg.edu.guc.yugioh.board.player;

import eg.edu.guc.yugioh.cards.Card;
import eg.edu.guc.yugioh.cards.Location;
import eg.edu.guc.yugioh.cards.MonsterCard;
import eg.edu.guc.yugioh.cards.spells.CardDestruction;
import eg.edu.guc.yugioh.cards.spells.ChangeOfHeart;
import eg.edu.guc.yugioh.cards.spells.DarkHole;
import eg.edu.guc.yugioh.cards.spells.GracefulDice;
import eg.edu.guc.yugioh.cards.spells.HarpieFeatherDuster;
import eg.edu.guc.yugioh.cards.spells.HeavyStorm;
import eg.edu.guc.yugioh.cards.spells.MagePower;
import eg.edu.guc.yugioh.cards.spells.MonsterReborn;
import eg.edu.guc.yugioh.cards.spells.PotOfGreed;
import eg.edu.guc.yugioh.cards.spells.Raigeki;
import eg.edu.guc.yugioh.cards.spells.SpellCard;
import eg.edu.guc.yugioh.exceptions.EmptyFieldException;
import eg.edu.guc.yugioh.exceptions.MissingFieldException;
import eg.edu.guc.yugioh.exceptions.UnexpectedFormatException;
import eg.edu.guc.yugioh.exceptions.UnknownCardTypeException;
import eg.edu.guc.yugioh.exceptions.UnknownSpellCardException;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class Deck {

	private boolean NoException;
	private static ArrayList<Card> monsters;
	private static ArrayList<Card> spells;
	private ArrayList<Card> deck;
	private boolean loadedDeck = false;
	private static String monstersPath = "Database-Monsters.csv";
	private static String spellsPath = "Database-Spells.csv";
 /*public static void main(String[]args) throws UnexpectedFormatException, IOException {
	  setSpellsPath("ecEFvceecefc.csv");
	  new Deck();
}*/
	public Deck() throws  UnexpectedFormatException, IOException{
		Scanner sc = new Scanner(System.in);
		deck = new ArrayList<Card>();
		
		if (!loadedDeck) {			
			for (int i = 0; i <= 3; i++) {
				NoException=true;
				monsters = new ArrayList<Card>();
				try {
					monsters = loadCardsFromFile(monstersPath);
				} catch (FileNotFoundException e) {
					NoException=false;
					if (i < 3) {
						System.out.println("File not found");
						System.out.println("Please enter a correct path:");
						setMonstersPath(sc.nextLine());
					} else {
						e.printStackTrace();
						throw e;
					}
				} catch (MissingFieldException e) {
					NoException=false;
					if (i < 3) {
						// System.out.println(e.getMessage());
						System.out.println("Please enter a correct path:");
						setMonstersPath(sc.nextLine());
					} else {
						e.printStackTrace();
						throw e;
					}
				} catch (EmptyFieldException e) {
					NoException=false;
					if (i < 3) {
						// System.out.println(e.getMessage());
						System.out.println("Please enter a correct path:");
						setMonstersPath(sc.nextLine());
					} else {
						e.printStackTrace();
						throw e;
					}
				} catch (UnknownCardTypeException e) {
					NoException=false;
					if (i < 3) {
						// System.out.println(e.getMessage());
						System.out.println("Please enter a correct path:");
						setMonstersPath(sc.nextLine());
					} else {
						e.printStackTrace();
						throw e;
					}
				} 
				finally{
					if(NoException)
						break;
				}
			}
			for (int i = 0; i <= 3; i++) {
				NoException=true;
				spells = new ArrayList<Card>();
				try {
					spells = loadCardsFromFile(spellsPath);
				} catch (FileNotFoundException e) {
					NoException=false;
					if (i < 3) {
						System.out.println("File not found");
						System.out.println("Please enter a correct path:");
						setSpellsPath(sc.nextLine());
					} else {
						e.printStackTrace();
						throw e;
					}
				} catch (MissingFieldException e) {
					NoException=false;
					if (i < 3) {
						// System.out.println(e.getMessage());
						System.out.println("Please enter a correct path:");
						setSpellsPath(sc.nextLine());
					} else {
						e.printStackTrace();
						throw e;
					}
				}

				catch (EmptyFieldException e) {
					NoException=false;
					if (i < 3) {
						// System.out.println(e.getMessage());
						System.out.println("Please enter a correct path:");
						setSpellsPath(sc.nextLine());
					} else {
						e.printStackTrace();
						throw e;
					}
				} catch (UnknownCardTypeException e) {
					NoException=false;
					if (i < 3) {
						// System.out.println(e.getMessage());
						System.out.println("Please enter a correct path:");
						setSpellsPath(sc.nextLine());
					} else {
						e.printStackTrace();
						throw e;
					}
				} catch (UnknownSpellCardException e) {
					NoException=false;
					if (i < 3) {
						//System.out.println(e.getMessage());
						System.out.println("Please enter a correct path:");
						setSpellsPath(sc.nextLine());
					} else {
						e.printStackTrace();
						throw e;
					}
				}
				finally{
					if(NoException)
						break;
				}
			}

			loadedDeck = true;
		}

		buildDeck(monsters, spells);
	}

	public static ArrayList<Card> getMonsters() {
		return monsters;
	}

	public static void setMonsters(ArrayList<Card> monsters) {
		Deck.monsters = monsters;
	}

	public static ArrayList<Card> getSpells() {
		return spells;
	}

	public static void setSpells(ArrayList<Card> spells) {
		Deck.spells = spells;
	}

	public ArrayList<Card> getDeck() {
		return deck;
	}

	public static String getMonstersPath() {
		return monstersPath;
	}

	public static void setMonstersPath(String monstersPath) {
		Deck.monstersPath = monstersPath;
	}

	public static String getSpellsPath() {
		return spellsPath;
	}

	public static void setSpellsPath(String spellsPath) {
		Deck.spellsPath = spellsPath;
	}

	public ArrayList<Card> loadCardsFromFile(String path)
			throws UnexpectedFormatException, IOException {

		String currentLine = "";
		int z = 0;
		FileReader fileReader = new FileReader(path);
		BufferedReader br = new BufferedReader(fileReader);
		ArrayList<Card> a = new ArrayList<Card>();

		while ((currentLine = br.readLine()) != null) {
			z++;
			String[] result = currentLine.split(",");
			if (result[0].compareTo("Monster") != 0
					&& result[0].compareTo("Spell") != 0) {
				throw new UnknownCardTypeException(path, z, result[0]);
			}
			if (path.compareTo(monstersPath) == 0) {
				if (result.length < 6) {
					throw new MissingFieldException(path, z);
				}
				for (int i = 0; i < 6; i++) {
					if (result[i].compareTo("")==0 || result[i].compareTo(" ")==0)
						throw new EmptyFieldException(path, z, i + 1);
				}
				MonsterCard x = new MonsterCard(result[1], result[2],
						Integer.parseInt(result[5]),
						Integer.parseInt(result[3]),
						Integer.parseInt(result[4]));
				a.add(x);
			} else if (path.compareTo(spellsPath) == 0) {
				if (result.length < 3) {
					throw new MissingFieldException(path, z);
				}
				for (int i = 0; i < 3; i++) {
					if (result[i].compareTo("")==0 || result[i].compareTo(" ")==0)
						throw new EmptyFieldException(path, z, i + 1);
				}
				SpellCard y = null;

				switch (result[1]) {
				case "Card Destruction":
					y = new CardDestruction(result[1], result[2]);
					break;
				case "Change Of Heart":
					y = new ChangeOfHeart(result[1], result[2]);
					break;
				case "Dark Hole":
					y = new DarkHole(result[1], result[2]);
					break;
				case "Graceful Dice":
					y = new GracefulDice(result[1], result[2]);
					break;
				case "Harpie's Feather Duster":
					y = new HarpieFeatherDuster(result[1], result[2]);
					break;
				case "Heavy Storm":
					y = new HeavyStorm(result[1], result[2]);
					break;
				case "Mage Power":
					y = new MagePower(result[1], result[2]);
					break;
				case "Monster Reborn":
					y = new MonsterReborn(result[1], result[2]);
					break;
				case "Pot of Greed":
					y = new PotOfGreed(result[1], result[2]);
					break;
				case "Raigeki":
					y = new Raigeki(result[1], result[2]);
					break;
				default:
					throw new UnknownSpellCardException(path, z, result[1]);
				}
				if (y != null)
					a.add(y);
			}

		}

		return a;
	}

	private void buildDeck(ArrayList<Card> monsters, ArrayList<Card> spells) {
		
		
            ArrayList<Integer> a=new ArrayList<Integer>(); 
		for (int i = 0; i < 15 ; i++) {
			int x = (int) Math.floor(Math.random() * monsters.size());
			if(!a.isEmpty()){
				while(a.contains(x))
					x = (int) Math.floor(Math.random() * monsters.size());
			}
			a.add(x);
			MonsterCard m = (MonsterCard) monsters.get(x);
			deck.add(m.copy());
		}
		a=new ArrayList<Integer>(); 
		for (int i = 0; i < 5 ; i++) {
			int x = (int) Math.floor(Math.random() * spells.size());
			if(!a.isEmpty()){
				while(a.contains(x))
					x = (int) Math.floor(Math.random() * spells.size());
			}
			a.add(x);
			SpellCard m = (SpellCard) spells.get(x);
			deck.add(m.copy());
		}
		shuffleDeck();
	}

	private void shuffleDeck() {
		ArrayList<Card> temp = new ArrayList<Card>();
		while (!deck.isEmpty()) {
			int x = (int) Math.floor(Math.random() * deck.size());
			temp.add(deck.remove(x));
		}
		deck = temp;
	}

	public ArrayList<Card> drawNCards(int n) {
		ArrayList<Card> nCards = new ArrayList<Card>();

		for (int i = 0; i < n && !deck.isEmpty(); i++) {
			nCards.add(drawOneCard());
		}
		
		return nCards;
	}

	public Card drawOneCard() {

		Card x = deck.remove(deck.size() - 1);
		x.setLocation(Location.DECK);
		return x;
	}

}
