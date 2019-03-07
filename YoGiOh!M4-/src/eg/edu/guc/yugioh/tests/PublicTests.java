package eg.edu.guc.yugioh.tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Random;

import org.junit.Test;

import eg.edu.guc.yugioh.board.Board;
import eg.edu.guc.yugioh.board.player.Deck;
import eg.edu.guc.yugioh.board.player.Phase;
import eg.edu.guc.yugioh.board.player.Player;
import eg.edu.guc.yugioh.cards.Location;
import eg.edu.guc.yugioh.cards.MonsterCard;
import eg.edu.guc.yugioh.cards.spells.*;
import eg.edu.guc.yugioh.exceptions.*;

public class PublicTests {

	@Test(timeout = 1000)
	public void testDefenseMonsterAttackException() throws Exception {

		Board board = new Board();
		Player p1 = new Player("Yugi");
		Player p2 = new Player("Kaiba");
		board.startGame(p1, p2);

		MonsterCard vorseRaider = new MonsterCard("Vorse Raider",
				"A warrior beast", 4, 1900, 1200);
		board.getActivePlayer().getField().getHand().add(vorseRaider);
		vorseRaider.setLocation(Location.HAND);

		board.getActivePlayer().summonMonster(vorseRaider);

		board.getActivePlayer().endPhase();

		try {

			board.getActivePlayer().declareAttack(vorseRaider);

		} catch (DefenseMonsterAttackException e) {

			fail("Any monster can attack while being in attack mode.\n"
					+ e.getMessage());

		}

	}

	@Test(timeout = 1000)
	public void testDefenseMonsterAttackExceptionInheritance() {
		assertEquals(
				"DefenseMonsterAttackException should extend from the appropriate super class.",
				RuntimeException.class,
				DefenseMonsterAttackException.class.getSuperclass());
	}

	@Test(timeout = 1000)
	public void testEmptyFieldExceptionInheritance() throws Exception {

		assertEquals(
				"EmptyFieldException should extend from the appropriate super class.",
				UnexpectedFormatException.class,
				EmptyFieldException.class.getSuperclass());

		Field fieldName1 = EmptyFieldException.class
				.getDeclaredField("sourceField");

		assertTrue(
				"UnexpectedFormatException should contain the stated variables.",
				fieldName1 != null);

		assertEquals(
				"UnexpectedFormatException should contain the stated variables.",
				int.class, fieldName1.getType());
	}

	@Test(timeout = 1000)
	public void testFileNotFound1TimeMonsters() throws Exception {

		for (int d = 0; d < 20; d++) {

			String monstersPath = Deck.getMonstersPath();
			String spellsPath = Deck.getSpellsPath();

			Field monstersField = Deck.class.getDeclaredField("monsters");
			monstersField.setAccessible(true);

			Field spellsField = Deck.class.getDeclaredField("spells");
			spellsField.setAccessible(true);

			monstersField.set(null, null);
			spellsField.set(null, null);

			try {

				Deck.setMonstersPath("NoFile.csv");

				ArrayList<String> monsters = new ArrayList<String>();
				monsters.add("Monster,Fence Guard Apprentice,Trains very hard to be the future Fence Guard,1700,1300,4");
				monsters.add("Monster,Gemini Elf,Elf twins that alternate their attacks.,1900,900,4");
				monsters.add("Monster,Vorse Raider,Wicked Beast-Warrior.,1900,1200,4");
				monsters.add("Monster,Dark Magician Girl,Gains her skill by learning from her master the Dark Magician,2000,1700,6");
				monsters.add("Monster,Gemini Elf,Elf twins that alternate their attacks.,1900,900,4");

				PrintWriter monstersWriter = new PrintWriter(
						"DoNotChangeThisFile1.csv");

				Random r = new Random();
				int size = r.nextInt(monsters.size()) + 1;
				for (int i = 0; i < size; i++)
					monstersWriter.println(monsters.remove(0));

				monstersWriter.close();

				String input = "DoNotChangeThisFile1.csv";
				ByteArrayInputStream br = new ByteArrayInputStream(
						input.getBytes("UTF-8"));
				System.setIn(br);

				new Deck();

				assertEquals(
						"Monster cards should be loaded from the path specified by the user.",
						size, Deck.getMonsters().size());

			} catch (FileNotFoundException e) {

				fail("File not found exceptions should be handled in Deck.\n"
						+ e.getMessage());

			} finally {

				monstersField.set(null, null);
				spellsField.set(null, null);

				Deck.setMonstersPath(monstersPath);
				Deck.setSpellsPath(spellsPath);

			}
		}

	}

	@Test(timeout = 1000)
	public void testMissingFieldExceptionInheritance() {

		assertEquals(
				"MissingFieldException should extend from an appropriate super class.",
				UnexpectedFormatException.class,
				MissingFieldException.class.getSuperclass());

	}

	@Test(timeout = 1000)
	public void testMissingFieldExceptionMonsters() throws Exception {

		for (int d = 0; d < 20; d++) {

			String monstersPath = Deck.getMonstersPath();
			String spellsPath = Deck.getSpellsPath();

			Field monstersField = Deck.class.getDeclaredField("monsters");
			monstersField.setAccessible(true);

			Field spellsField = Deck.class.getDeclaredField("spells");
			spellsField.setAccessible(true);

			monstersField.set(null, null);
			spellsField.set(null, null);

			try {

				Deck.setMonstersPath("DoNotChangeThisFile1.csv");

				ArrayList<String> monsters = new ArrayList<String>();
				monsters.add("Monster,Fence Guard Apprentice,Trains very hard to be the future Fence Guard,1700,1300,4");
				monsters.add("Monster,Gemini Elf,Elf twins that alternate their attacks.,1900,900,4");
				monsters.add("Monster,Vorse Raider,Wicked Beast-Warrior.,1900,1200,4");
				monsters.add("Monster,Dark Magician Girl,Gains her skill by learning from her master the Dark Magician,2000,1700,6");

				ArrayList<String> missingMonsters = new ArrayList<String>();
				missingMonsters
						.add("Monster,Elf twins that alternate their attacks.,1900,900,4");
				missingMonsters
						.add("Monster,Gemini Elf,Elf twins that alternate their attacks.,900,4");
				missingMonsters
						.add("Monster,Gemini Elf,Elf twins that alternate their attacks.,1900,900");

				PrintWriter monstersWriter = new PrintWriter(
						"DoNotChangeThisFile1.csv");

				Random r = new Random();
				int size = r.nextInt(monsters.size()) + 1;
				int missingFieldLine = r.nextInt(size);
				int missingField = r.nextInt(3);

				for (int i = 0; i < size; i++)
					if (i == missingFieldLine)
						monstersWriter.println(missingMonsters
								.get(missingField));
					else
						monstersWriter.println(monsters.get(i));

				monstersWriter.close();

				monstersWriter = new PrintWriter("DoNotChangeThisFile2.csv");

				size = r.nextInt(monsters.size() - 1) + 1;
				for (int i = 0; i < size; i++)
					monstersWriter.println(monsters.remove(0));

				monstersWriter.close();

				String input = "DoNotChangeThisFile2.csv";
				ByteArrayInputStream br = new ByteArrayInputStream(
						input.getBytes("UTF-8"));
				System.setIn(br);

				new Deck();

				assertEquals(
						"Monster cards should be loaded from the path specified by the user.",
						size, Deck.getMonsters().size());

			} catch (MissingFieldException e) {

				fail("Missing field exceptions should be handled in Deck.\n"
						+ e.getMessage());

			} finally {

				monstersField.set(null, null);
				spellsField.set(null, null);

				Deck.setMonstersPath(monstersPath);
				Deck.setSpellsPath(spellsPath);

			}
		}

	}

	@Test(timeout = 1000)
	public void testMonsterMultipleAttackException() throws Exception {

		Board board = new Board();
		Player p1 = new Player("Yugi");
		Player p2 = new Player("Kaiba");
		board.startGame(p1, p2);

		MonsterCard vorseRaider = new MonsterCard("Vorse Raider",
				"A warrior beast", 4, 1900, 1200);

		board.getActivePlayer().getField().getHand().add(vorseRaider);
		vorseRaider.setLocation(Location.HAND);

		MonsterCard vorseRaider1 = new MonsterCard("Vorse Raider",
				"A warrior beast", 4, 1900, 1200);

		board.getActivePlayer().getField().getHand().add(vorseRaider1);
		vorseRaider1.setLocation(Location.HAND);

		board.getActivePlayer().summonMonster(vorseRaider);

		board.getActivePlayer().endPhase();

		try {

			board.getActivePlayer().declareAttack(vorseRaider);

		} catch (MonsterMultipleAttackException e) {

			fail("A player should be able to summon one monster in a turn.\n"
					+ e.getMessage());

		}

	}

	@Test(timeout = 1000)
	public void testMonsterMultipleAttackExceptionAttackingTwice()
			throws Exception {

		Board board = new Board();
		Player p1 = new Player("Yugi");
		Player p2 = new Player("Kaiba");
		board.startGame(p1, p2);

		MonsterCard vorseRaider = new MonsterCard("Vorse Raider",
				"A warrior beast", 4, 1900, 1200);

		board.getActivePlayer().getField().getHand().add(vorseRaider);
		vorseRaider.setLocation(Location.HAND);

		board.getActivePlayer().summonMonster(vorseRaider);

		board.getActivePlayer().endPhase();

		board.getActivePlayer().declareAttack(vorseRaider);

		try {

			board.getActivePlayer().declareAttack(vorseRaider);

		} catch (MonsterMultipleAttackException e) {

			return;

		}

		fail("A player should not be able to attack more than once per turn.");

	}

	@Test(timeout = 1000)
	public void testMonsterMultipleAttackExceptionInheritance() {

		assertEquals(
				"MonsterMultipleAttackException should extend from an appropriate super class.",
				RuntimeException.class,
				MonsterMultipleAttackException.class.getSuperclass());

	}

	@Test(timeout = 1000)
	public void testMultipleMonsterAdditionExceptionInheritance() {

		assertEquals(
				"MultipleMonsterAdditionException should extend from an appropriate super class.",
				RuntimeException.class,
				MultipleMonsterAdditionException.class.getSuperclass());

	}

	@Test(timeout = 1000)
	public void testMultipleMonsterAdditionExceptionSetDifferentTrun()
			throws Exception {

		Board board = new Board();
		Player p1 = new Player("Yugi");
		Player p2 = new Player("Kaiba");
		board.startGame(p1, p2);

		MonsterCard m = new MonsterCard("Vorse Raider", "A warrior beast", 4,
				1900, 1200);
		MonsterCard m1 = new MonsterCard("Blue Eyes", "A warrior beast", 4,
				1900, 1200);

		board.getActivePlayer().getField().getHand().add(m);
		m.setLocation(Location.HAND);
		board.getActivePlayer().getField().getHand().add(m1);
		m1.setLocation(Location.HAND);
		board.getActivePlayer().setMonster(m);

		board.getActivePlayer().endTurn();
		board.getActivePlayer().endTurn();

		try {

			board.getActivePlayer().setMonster(m1);

		} catch (MultipleMonsterAdditionException e) {

			fail("The player should be allowed to set one monster per turn.\n"
					+ e.getMessage());

		}

	}

	@Test(timeout = 1000)
	public void testMultipleMonsterAdditionExceptionSummon() throws Exception {

		Board board = new Board();
		Player p1 = new Player("Yugi");
		Player p2 = new Player("Kaiba");
		board.startGame(p1, p2);

		MonsterCard m = new MonsterCard("Vorse Raider", "A warrior beast", 4,
				1900, 1200);
		MonsterCard m1 = new MonsterCard("Blue Eyes", "A warrior beast", 4,
				1900, 1200);

		board.getActivePlayer().getField().getHand().add(m);
		m.setLocation(Location.HAND);
		board.getActivePlayer().getField().getHand().add(m1);
		m1.setLocation(Location.HAND);
		board.getActivePlayer().summonMonster(m);
		try {

			board.getActivePlayer().summonMonster(m1);
			fail("Summoning multiple monsters in the same turn should throw an exception.");

		} catch (MultipleMonsterAdditionException e) {

		}

	}

	@Test(timeout = 1000)
	public void testMultipleMonsterAdditionExceptionSummonDifferentTrun()
			throws Exception {

		Board board = new Board();
		Player p1 = new Player("Yugi");
		Player p2 = new Player("Kaiba");
		board.startGame(p1, p2);

		MonsterCard m = new MonsterCard("Vorse Raider", "A warrior beast", 4,
				1900, 1200);
		MonsterCard m1 = new MonsterCard("Blue Eyes", "A warrior beast", 4,
				1900, 1200);

		board.getActivePlayer().getField().getHand().add(m);
		m.setLocation(Location.HAND);
		board.getActivePlayer().getField().getHand().add(m1);
		m1.setLocation(Location.HAND);
		board.getActivePlayer().summonMonster(m);

		board.getActivePlayer().endTurn();
		board.getActivePlayer().endTurn();

		try {
			board.getActivePlayer().summonMonster(m1);

		} catch (MultipleMonsterAdditionException e) {
			fail("The player should be allowed to summon one monster per turn.\n"
					+ e.getMessage());
		}

	}

	@Test(timeout = 1000)
	public void testNoMonsterSpaceExceptionInheritance() {

		assertEquals(
				"NoMonsterSpaceException should extend from the appropriate super class.",
				NoSpaceException.class,
				NoMonsterSpaceException.class.getSuperclass());

	}

	@Test(timeout = 1000)
	public void testNoMonsterSpaceExceptionSet() throws Exception {

		Board board = new Board();
		Player p1 = new Player("Yugi");
		Player p2 = new Player("Kaiba");
		board.startGame(p1, p2);

		MonsterCard vorseRaider1 = new MonsterCard("Vorse Raider",
				"A warrior beast", 4, 1900, 1200);
		MonsterCard vorseRaider2 = new MonsterCard("Vorse Raider",
				"A warrior beast", 4, 1900, 1200);
		MonsterCard vorseRaider3 = new MonsterCard("Vorse Raider",
				"A warrior beast", 4, 1900, 1200);
		MonsterCard vorseRaider4 = new MonsterCard("Vorse Raider",
				"A warrior beast", 4, 1900, 1200);
		MonsterCard vorseRaider5 = new MonsterCard("Vorse Raider",
				"A warrior beast", 4, 1900, 1200);
		MonsterCard geminiElf = new MonsterCard("Gemini Elf", "the twins", 4,
				1900, 900);

		board.getActivePlayer().getField().getMonstersArea().add(vorseRaider1);
		board.getActivePlayer().getField().getMonstersArea().add(vorseRaider2);
		board.getActivePlayer().getField().getMonstersArea().add(vorseRaider3);
		board.getActivePlayer().getField().getMonstersArea().add(vorseRaider4);
		board.getActivePlayer().getField().getMonstersArea().add(vorseRaider5);

		board.getActivePlayer().getField().getHand().add(geminiElf);
		geminiElf.setLocation(Location.HAND);

		try {

			board.getActivePlayer().setMonster(geminiElf);
			fail("Setting a monster onto a full monsters area should throw the correct exception.");

		} catch (NoMonsterSpaceException e) {
			return;
		}

	}

	@Test(timeout = 1000)
	public void testNoMonsterSpaceExceptionSummon() throws Exception {

		Board board = new Board();
		Player p1 = new Player("Yugi");
		Player p2 = new Player("Kaiba");
		board.startGame(p1, p2);

		MonsterCard vorseRaider1 = new MonsterCard("Vorse Raider",
				"A warrior beast", 4, 1900, 1200);
		MonsterCard vorseRaider2 = new MonsterCard("Vorse Raider",
				"A warrior beast", 4, 1900, 1200);
		MonsterCard vorseRaider3 = new MonsterCard("Vorse Raider",
				"A warrior beast", 4, 1900, 1200);
		MonsterCard vorseRaider4 = new MonsterCard("Vorse Raider",
				"A warrior beast", 4, 1900, 1200);
		MonsterCard vorseRaider5 = new MonsterCard("Vorse Raider",
				"A warrior beast", 4, 1900, 1200);
		MonsterCard geminiElf = new MonsterCard("Gemini Elf", "the twins", 4,
				1900, 900);

		board.getActivePlayer().getField().getMonstersArea().add(vorseRaider1);
		board.getActivePlayer().getField().getMonstersArea().add(vorseRaider2);
		board.getActivePlayer().getField().getMonstersArea().add(vorseRaider3);
		board.getActivePlayer().getField().getMonstersArea().add(vorseRaider4);
		board.getActivePlayer().getField().getMonstersArea().add(vorseRaider5);

		board.getActivePlayer().getField().getHand().add(geminiElf);
		geminiElf.setLocation(Location.HAND);

		try {

			board.getActivePlayer().summonMonster(geminiElf);

		} catch (NoMonsterSpaceException e) {
			return;
		}

		fail("Summoning a monster onto a full monsters area should throw the correct exception.");

	}

	@Test(timeout = 1000)
	public void testNoMonsterSpaceExceptionSummonWithSacrifice()
			throws Exception {

		Board board = new Board();
		Player p1 = new Player("Yugi");
		Player p2 = new Player("Kaiba");
		board.startGame(p1, p2);

		MonsterCard vorseRaider1 = new MonsterCard("Vorse Raider",
				"A warrior beast", 4, 1900, 1200);
		MonsterCard vorseRaider2 = new MonsterCard("Vorse Raider",
				"A warrior beast", 4, 1900, 1200);
		MonsterCard vorseRaider3 = new MonsterCard("Vorse Raider",
				"A warrior beast", 4, 1900, 1200);
		MonsterCard vorseRaider4 = new MonsterCard("Vorse Raider",
				"A warrior beast", 4, 1900, 1200);
		MonsterCard vorseRaider5 = new MonsterCard("Vorse Raider",
				"A warrior beast", 4, 1900, 1200);
		MonsterCard geminiElf = new MonsterCard("Gemini Elf", "the twins", 6,
				1900, 900);

		board.getActivePlayer().getField().getMonstersArea().add(vorseRaider1);
		board.getActivePlayer().getField().getMonstersArea().add(vorseRaider2);
		board.getActivePlayer().getField().getMonstersArea().add(vorseRaider3);
		board.getActivePlayer().getField().getMonstersArea().add(vorseRaider4);
		board.getActivePlayer().getField().getMonstersArea().add(vorseRaider5);

		board.getActivePlayer().getField().getHand().add(geminiElf);
		geminiElf.setLocation(Location.HAND);

		ArrayList<MonsterCard> sacrifices = new ArrayList<MonsterCard>();
		sacrifices.add(vorseRaider1);

		try {

			board.getActivePlayer().summonMonster(geminiElf, sacrifices);

		} catch (NoMonsterSpaceException e) {
			return;
		}

		fail("Summoning monsters to a full monsters area should throw the correct exception.");

	}

	@Test(timeout = 1000)
	public void testNoSpaceExceptionInheritance() {

		assertEquals(
				"NoSpaceException should extend from an appropriate super class.",
				RuntimeException.class, NoSpaceException.class.getSuperclass());

	}

	@Test(timeout = 1000)
	public void testNoSpellSpaceExceptionInheritance() {

		assertEquals(
				"NoSpellSpaceException should extend from an appropriate super class",
				NoSpaceException.class,
				NoSpellSpaceException.class.getSuperclass());

	}

	@Test(timeout = 1000)
	public void testNoSpellSpaceExceptionSet() throws Exception {

		Board board = new Board();
		Player p1 = new Player("Yugi");
		Player p2 = new Player("Kaiba");
		board.startGame(p1, p2);

		MonsterReborn mr = new MonsterReborn("Monster Reborn",
				"Revive the monster");
		ChangeOfHeart coh = new ChangeOfHeart("Change of Heart", "Control");
		PotOfGreed pog = new PotOfGreed("Pot of Greed", "Draw cards");
		Raigeki r = new Raigeki("Raigeki", "Destroy");
		HarpieFeatherDuster hfd = new HarpieFeatherDuster(
				"Harpie's Feather Duster", "Destroy spells");
		HeavyStorm hs = new HeavyStorm("Heavy Storm", "Destroy all spells");

		board.getActivePlayer().getField().getSpellArea().add(mr);
		board.getActivePlayer().getField().getSpellArea().add(coh);
		board.getActivePlayer().getField().getSpellArea().add(pog);
		board.getActivePlayer().getField().getSpellArea().add(r);
		board.getActivePlayer().getField().getSpellArea().add(hfd);

		board.getActivePlayer().getField().getHand().add(hs);
		hs.setLocation(Location.HAND);

		try {

			board.getActivePlayer().setSpell(hs);

		} catch (NoSpellSpaceException e) {
			return;
		}

		fail("Setting a spell onto a full spell area should throw the correct exception.");

	}

	@Test(timeout = 1000)
	public void testReadFileTrials() throws Exception {

		String monstersPath = Deck.getMonstersPath();
		String spellsPath = Deck.getSpellsPath();

		Field monstersField = Deck.class.getDeclaredField("monsters");
		monstersField.setAccessible(true);

		Field spellsField = Deck.class.getDeclaredField("spells");
		spellsField.setAccessible(true);

		monstersField.set(null, null);
		spellsField.set(null, null);

		Deck.setMonstersPath("InvalidPath.csv");

		PrintWriter input = new PrintWriter("input");
		input.println("NoFile.csv");
		input.println("StillNoFile.csv");
		input.println("AgainNoFile.csv");
		input.close();

		System.setIn(new FileInputStream("input"));

		try {

			new Deck();

		} catch (FileNotFoundException e) {

			assertTrue(
					"The user must be given three trials before throwing the exception.",
					e.getMessage().contains("AgainNoFile.csv"));

		} finally {

			monstersField.set(null, null);
			spellsField.set(null, null);

			Deck.setMonstersPath(monstersPath);
			Deck.setSpellsPath(spellsPath);

		}

		assertEquals(
				"Monster's csv file does not exist, Monsters should be null.",
				null, Deck.getMonsters());

	}

	@Test(timeout = 1000)
	public void testUnexpectedFormatExceptionInheritance() throws Exception {

		assertEquals(
				"UnexpectedFormatException should extend from an appropriate super class.",
				Exception.class,
				UnexpectedFormatException.class.getSuperclass());

		Field fieldName1 = UnexpectedFormatException.class
				.getDeclaredField("sourceFile");
		Field fieldName2 = UnexpectedFormatException.class
				.getDeclaredField("sourceLine");

		assertTrue(
				"UnexpectedFormatException should contain the stated variables.",
				fieldName1 != null);
		assertTrue(
				"UnexpectedFormatException should contain the stated variables.",
				fieldName2 != null);

		assertEquals(
				"UnexpectedFormatException's variables should have the appropriate types.",
				String.class, fieldName1.getType());
		assertEquals(
				"UnexpectedFormatException variables should have the appropriate types.",
				int.class, fieldName2.getType());

	}

	@Test(timeout = 1000)
	public void testUnknownCardTypeExceptionInheritance() throws Exception {

		assertEquals(
				"UnknownCardTypeException should extend from an appropriate super class.",
				UnexpectedFormatException.class,
				UnknownCardTypeException.class.getSuperclass());

		Field fieldName1 = UnknownCardTypeException.class
				.getDeclaredField("unknownType");

		assertTrue(
				"UnknownCardTypeException should contain the stated variables.",
				fieldName1 != null);

		assertEquals(
				"UnknownCardTypeException variables should have the appropriate types.",
				String.class, fieldName1.getType());

	}

	@Test(timeout = 1000)
	public void testUnknownCardTypeExceptionSpells() throws Exception {

		for (int d = 0; d < 20; d++) {

			String monstersPath = Deck.getMonstersPath();
			String spellsPath = Deck.getSpellsPath();

			Field monstersField = Deck.class.getDeclaredField("monsters");
			monstersField.setAccessible(true);

			Field spellsField = Deck.class.getDeclaredField("spells");
			spellsField.setAccessible(true);

			monstersField.set(null, null);
			spellsField.set(null, null);

			try {

				Deck.setSpellsPath("DoNotChangeThisFile1.csv");

				ArrayList<String> spells = new ArrayList<String>();
				spells.add("Spell,Card Destruction,Each player discards their entire hand then draws the same number of cards they discarded.");
				spells.add("Spell,Change Of Heart,You choose one monster from your opponent and add it to your own monsters.");
				spells.add("Spell,Heavy Storm,Destroy all spell cards on the board for both players");
				spells.add("Spell,Pot of Greed,Draw 2 extra cards.");

				ArrayList<String> unknownTypes = new ArrayList<String>();
				unknownTypes.add("Trap,Pot of Greeed,Draw 2 extra cards.");
				unknownTypes
						.add("Montser,Blue eyes Dragon,Super Awesome Dragon.");
				unknownTypes
						.add("Spells,Heavy Strom,Destroy all spell cards on the board for both players.");

				PrintWriter monstersWriter = new PrintWriter(
						"DoNotChangeThisFile1.csv");

				Random r = new Random();
				int size = r.nextInt(spells.size()) + 1;
				int missingFieldLine = r.nextInt(size);
				int missingField = r.nextInt(3);

				for (int i = 0; i < size; i++)
					if (i == missingFieldLine)
						monstersWriter.println(unknownTypes.get(missingField));
					else
						monstersWriter.println(spells.get(i));

				monstersWriter.close();

				monstersWriter = new PrintWriter("DoNotChangeThisFile2.csv");

				size = r.nextInt(spells.size() - 1) + 1;
				for (int i = 0; i < size; i++)
					monstersWriter.println(spells.remove(0));

				monstersWriter.close();

				String input = "DoNotChangeThisFile2.csv";
				ByteArrayInputStream br = new ByteArrayInputStream(
						input.getBytes("UTF-8"));
				System.setIn(br);

				new Deck();

				assertEquals(
						"Spell cards should be loaded from the path specified by the user.",
						size, Deck.getSpells().size());

			} catch (UnknownCardTypeException e) {

				fail("Unknown Card Type exceptions should be handled in Deck.\n"
						+ e.getMessage());

			} finally {

				monstersField.set(null, null);
				spellsField.set(null, null);

				Deck.setMonstersPath(monstersPath);
				Deck.setSpellsPath(spellsPath);

			}
		}

	}

	@Test(timeout = 1000)
	public void testUnknownSpellCardException() throws Exception {

		for (int d = 0; d < 20; d++) {

			String monstersPath = Deck.getMonstersPath();
			String spellsPath = Deck.getSpellsPath();

			Field monstersField = Deck.class.getDeclaredField("monsters");
			monstersField.setAccessible(true);

			Field spellsField = Deck.class.getDeclaredField("spells");
			spellsField.setAccessible(true);

			monstersField.set(null, null);
			spellsField.set(null, null);

			try {

				Deck.setSpellsPath("DoNotChangeThisFile2.csv");

				ArrayList<String> spells = new ArrayList<String>();
				spells.add("Spell,Card Destruction,Each player discards their entire hand then draws the same number of cards they discarded.");
				spells.add("Spell,Change Of Heart,You choose one monster from your opponent and add it to your own monsters.");
				spells.add("Spell,Harpie's Feather Duster,Destroy all spell cards your opponent controls.");
				spells.add("Spell,Heavy Storm,Destroy all spell cards on the board for both players");
				spells.add("Spell,Pot of Greed,Draw 2 extra cards.");

				ArrayList<String> unknownSpells = new ArrayList<String>();
				unknownSpells.add("Spell,Pot of Greeed,Draw 2 extra cards.");
				unknownSpells.add("Spell,Amulet Of Ambition,spell card.");
				unknownSpells
						.add("Spell,Heavy Strom,Destroy all spell cards on the board for both players.");

				PrintWriter monstersWriter = new PrintWriter(
						"DoNotChangeThisFile2.csv");

				Random r = new Random();
				int size = r.nextInt(spells.size()) + 1;
				int missingFieldLine = r.nextInt(size);
				int missingField = r.nextInt(3);

				for (int i = 0; i < size; i++)
					if (i == missingFieldLine)
						monstersWriter.println(unknownSpells.get(missingField));
					else
						monstersWriter.println(spells.get(i));

				monstersWriter.close();

				monstersWriter = new PrintWriter("DoNotChangeThisFile1.csv");

				size = r.nextInt(spells.size() - 1) + 1;
				for (int i = 0; i < size; i++)
					monstersWriter.println(spells.remove(0));

				monstersWriter.close();

				String input = "DoNotChangeThisFile1.csv";
				ByteArrayInputStream br = new ByteArrayInputStream(
						input.getBytes("UTF-8"));
				System.setIn(br);

				new Deck();

				assertEquals(
						"Spell cards should be loaded from the path specified by the user.",
						size, Deck.getSpells().size());

			} catch (UnknownSpellCardException e) {

				fail("Unknown Spell Card exceptions should be handled in Deck.\n"
						+ e.getMessage());

			} finally {

				monstersField.set(null, null);
				spellsField.set(null, null);

				Deck.setMonstersPath(monstersPath);
				Deck.setSpellsPath(spellsPath);

			}
		}

	}

	@Test(timeout = 1000)
	public void testUnknownSpellExceptionInheritance() throws Exception {

		assertEquals(
				"UnknownSpellException should extend from an appropriate super class.",
				UnexpectedFormatException.class,
				UnknownSpellCardException.class.getSuperclass());

		Field fieldName1 = UnknownSpellCardException.class
				.getDeclaredField("unknownSpell");

		assertTrue(
				"UnknownSpellException should contain the stated variables.",
				fieldName1 != null);

		assertEquals(
				"UnknownSpellException variables should have the appropriate types.",
				String.class, fieldName1.getType());

	}

	@Test(timeout = 1000)
	public void testWrongPhaseExceptionActivateSpellInBattle() throws Exception {

		Board board = new Board();
		Player p1 = new Player("Yugi");
		Player p2 = new Player("Kaiba");
		board.startGame(p1, p2);

		Raigeki s = new Raigeki("Raigeki", "Destroy");

		board.getActivePlayer().endPhase();

		board.getActivePlayer().getField().getHand().add(s);
		s.setLocation(Location.HAND);

		try {

			board.getActivePlayer().activateSpell(s, null);

		} catch (WrongPhaseException e) {
			return;
		}

		fail("Activating a spell in the wrong phase should throw the appropriate exception.");

	}

	@Test(timeout = 1000)
	public void testWrongPhaseExceptionActivateSpellInMain1() throws Exception {

		Board board = new Board();
		Player p1 = new Player("Yugi");
		Player p2 = new Player("Kaiba");
		board.startGame(p1, p2);

		Raigeki s = new Raigeki("Raigeki", "Destroy");

		board.getActivePlayer().getField().getHand().add(s);
		s.setLocation(Location.HAND);

		try {

			board.getActivePlayer().activateSpell(s, null);

		} catch (WrongPhaseException e) {

			fail("Activating a spell in Main Phase 1 should not throw an exception.\n"
					+ e.getMessage());

		}

	}

	@Test(timeout = 1000)
	public void testWrongPhaseExceptionAttackingMonsterInBattle()
			throws Exception {

		Board board = new Board();
		Player p1 = new Player("Yugi");
		Player p2 = new Player("Kaiba");
		board.startGame(p1, p2);

		MonsterCard BlueEyes = new MonsterCard("Blue Eyes White Dragon",
				"Legendary Dragon", 3, 3000, 2500);
		MonsterCard vorseRaider = new MonsterCard("Vorse Raider",
				"Beast Warrior", 4, 1900, 1200);

		board.getActivePlayer().getField().getHand().add(BlueEyes);
		BlueEyes.setLocation(Location.HAND);
		board.getActivePlayer().summonMonster(BlueEyes);
		board.getActivePlayer().endTurn();

		board.getActivePlayer().getField().getHand().add(vorseRaider);
		vorseRaider.setLocation(Location.HAND);
		board.getActivePlayer().summonMonster(vorseRaider);
		board.getActivePlayer().endTurn();

		board.getActivePlayer().endPhase();

		try {

			board.getActivePlayer().declareAttack(BlueEyes, vorseRaider);

		} catch (WrongPhaseException e) {

			fail("Attacking in battle phase shouldn't throw exceptions.\n"
					+ e.getMessage());

		}
	}

	@Test(timeout = 1000)
	public void testWrongPhaseExceptionAttackingMonsterInMain1()
			throws Exception {

		Board board = new Board();
		Player p1 = new Player("Yugi");
		Player p2 = new Player("Kaiba");
		board.startGame(p1, p2);

		MonsterCard BlueEyes = new MonsterCard("Blue Eyes White Dragon",
				"Legendary Dragon", 3, 3000, 2500);
		MonsterCard vorseRaider = new MonsterCard("Vorse Raider",
				"Beast Warrior", 4, 1900, 1200);

		board.getActivePlayer().getField().getHand().add(BlueEyes);
		BlueEyes.setLocation(Location.HAND);
		board.getActivePlayer().summonMonster(BlueEyes);
		board.getActivePlayer().endTurn();

		board.getActivePlayer().getField().getHand().add(vorseRaider);
		vorseRaider.setLocation(Location.HAND);
		board.getActivePlayer().summonMonster(vorseRaider);
		board.getActivePlayer().endTurn();

		try {

			board.getActivePlayer().declareAttack(BlueEyes, vorseRaider);

		} catch (WrongPhaseException e) {
			return;
		}
		fail("Attacking outside battle phase should throw the appropriate exception.");

	}

	@Test(timeout = 1000)
	public void testWrongPhaseExceptionDirectAttackInBattle() throws Exception {

		Board board = new Board();
		Player p1 = new Player("Yugi");
		Player p2 = new Player("Kaiba");
		board.startGame(p1, p2);

		MonsterCard BlueEyes = new MonsterCard("Blue Eyes White Dragon",
				"Legendary Dragon", 3, 3000, 2500);

		board.getActivePlayer().getField().getHand().add(BlueEyes);
		BlueEyes.setLocation(Location.HAND);
		board.getActivePlayer().summonMonster(BlueEyes);

		board.getActivePlayer().endPhase();

		try {

			board.getActivePlayer().declareAttack(BlueEyes);

		} catch (WrongPhaseException e) {

			fail("Attacking in battle phase shouldn't throw exceptions.\n"
					+ e.getMessage());

		}
	}

	@Test(timeout = 1000)
	public void testWrongPhaseExceptionDirectAttackInMain2() throws Exception {

		Board board = new Board();
		Player p1 = new Player("Yugi");
		Player p2 = new Player("Kaiba");
		board.startGame(p1, p2);

		MonsterCard BlueEyes = new MonsterCard("Blue Eyes White Dragon",
				"Legendary Dragon", 3, 3000, 2500);

		board.getActivePlayer().getField().getHand().add(BlueEyes);
		BlueEyes.setLocation(Location.HAND);
		board.getActivePlayer().summonMonster(BlueEyes);

		board.getActivePlayer().endPhase();
		board.getActivePlayer().endPhase();

		try {

			board.getActivePlayer().declareAttack(BlueEyes);

		} catch (WrongPhaseException e) {
			return;
		}

		fail("Attacking in Main Phase 2 should throw the appropriate exception.");

	}

	@Test(timeout = 1000)
	public void testWrongPhaseExceptionEndTurnInBattle() throws Exception {

		Board board = new Board();
		Player p1 = new Player("Yugi");
		Player p2 = new Player("Kaiba");
		board.startGame(p1, p2);

		Player player = board.getActivePlayer();

		player.getField().setPhase(Phase.MAIN1);
		player.endPhase();

		try {

			player.endTurn();

		} catch (WrongPhaseException e) {

			fail("A player should be allowed to end his turn during any phase.\n"
					+ e.getMessage());

		}

	}

	@Test(timeout = 1000)
	public void testWrongPhaseExceptionInheritance() {

		assertEquals(
				"WrongPhaseException should extend from the appropriate super class.",
				RuntimeException.class,
				WrongPhaseException.class.getSuperclass());

	}

	@Test(timeout = 1000)
	public void testWrongPhaseExceptionSetMonsterInBattle() throws Exception {

		Board board = new Board();
		Player p1 = new Player("Yugi");
		Player p2 = new Player("Kaiba");
		board.startGame(p1, p2);

		MonsterCard geminiElf = new MonsterCard("Gemini Elf", "the twins", 4,
				1900, 900);

		board.getActivePlayer().getField().getHand().add(geminiElf);
		geminiElf.setLocation(Location.HAND);

		board.getActivePlayer().endPhase();

		try {

			board.getActivePlayer().setMonster(geminiElf);
			fail("Setting a monster in battle phase should throw the appropriate exception.");

		} catch (WrongPhaseException e) {

		}

	}

	@Test(timeout = 1000)
	public void testWrongPhaseExceptionSetMonsterInMain1() throws Exception {

		Board board = new Board();
		Player p1 = new Player("Yugi");
		Player p2 = new Player("Kaiba");
		board.startGame(p1, p2);

		MonsterCard geminiElf = new MonsterCard("Gemini Elf", "the twins", 4,
				1900, 900);

		board.getActivePlayer().getField().getHand().add(geminiElf);
		geminiElf.setLocation(Location.HAND);

		try {

			board.getActivePlayer().setMonster(geminiElf);

		} catch (WrongPhaseException e) {

			fail("Setting a monster in Main Phase 1 should not throw an exception.\n"
					+ e.getMessage());

		}

	}

	@Test(timeout = 1000)
	public void testWrongPhaseExceptionSetSpellInMain2() throws Exception {

		Board board = new Board();
		Player p1 = new Player("Yugi");
		Player p2 = new Player("Kaiba");
		board.startGame(p1, p2);

		board.getActivePlayer().endPhase();
		board.getActivePlayer().endPhase();

		Raigeki s = new Raigeki("Raigeki", "Destroy");

		board.getActivePlayer().getField().getHand().add(s);
		s.setLocation(Location.HAND);

		try {

			board.getActivePlayer().setSpell(s);

		} catch (WrongPhaseException e) {

			fail("Setting a spell in Main Phase 2 should not throw an exception.\n"
					+ e.getMessage());

		}

	}

	@Test(timeout = 1000)
	public void testWrongPhaseExceptionSetWithSacrificeInMain2()
			throws Exception {

		Board board = new Board();
		Player p1 = new Player("Yugi");
		Player p2 = new Player("Kaiba");
		board.startGame(p1, p2);

		MonsterCard vorseRaider1 = new MonsterCard("Vorse Raider",
				"A warrior beast", 4, 1900, 1200);
		MonsterCard vorseRaider2 = new MonsterCard("Vorse Raider",
				"A warrior beast", 4, 1900, 1200);

		board.getActivePlayer().getField().getMonstersArea().add(vorseRaider1);
		board.getActivePlayer().getField().getMonstersArea().add(vorseRaider2);

		ArrayList<MonsterCard> sacrifices = new ArrayList<MonsterCard>();
		sacrifices.add(vorseRaider1);
		sacrifices.add(vorseRaider2);

		board.getActivePlayer().endPhase();
		board.getActivePlayer().endPhase();

		MonsterCard geminiElf = new MonsterCard("Gemini Elf", "the twins", 8,
				1900, 900);

		board.getActivePlayer().getField().getHand().add(geminiElf);
		geminiElf.setLocation(Location.HAND);

		try {

			board.getActivePlayer().setMonster(geminiElf, sacrifices);

		} catch (WrongPhaseException e) {

			fail("Setting a monster in Main Phase 2 should not throw an exception.\n"
					+ e.getMessage());

		}

	}

	@Test(timeout = 1000)
	public void testWrongPhaseExceptionSummonMonsterInBattle() throws Exception {

		Board board = new Board();
		Player p1 = new Player("Yugi");
		Player p2 = new Player("Kaiba");
		board.startGame(p1, p2);

		MonsterCard geminiElf = new MonsterCard("Gemini Elf", "the twins", 4,
				1900, 900);

		board.getActivePlayer().getField().getHand().add(geminiElf);
		geminiElf.setLocation(Location.HAND);

		board.getActivePlayer().endPhase();

		try {

			board.getActivePlayer().summonMonster(geminiElf);
			fail("Summoning a monster in battle phase should throw exception.");

		} catch (WrongPhaseException e) {
			return;
		}

	}

	@Test(timeout = 1000)
	public void testWrongPhaseExceptionSummonMonsterInMain1() throws Exception {

		Board board = new Board();
		Player p1 = new Player("Yugi");
		Player p2 = new Player("Kaiba");
		board.startGame(p1, p2);

		MonsterCard geminiElf = new MonsterCard("Gemini Elf", "the twins", 4,
				1900, 900);

		board.getActivePlayer().getField().getHand().add(geminiElf);
		geminiElf.setLocation(Location.HAND);

		try {

			board.getActivePlayer().summonMonster(geminiElf);

		} catch (WrongPhaseException e) {

			fail("Summoning a monster in Main Phase 1 should not throw an exception.\n"
					+ e.getMessage());

		}

	}

	@Test(timeout = 1000)
	public void testWrongPhaseExceptionSummonMonsterInMain2() throws Exception {

		Board board = new Board();
		Player p1 = new Player("Yugi");
		Player p2 = new Player("Kaiba");
		board.startGame(p1, p2);

		MonsterCard geminiElf = new MonsterCard("Gemini Elf", "the twins", 4,
				1900, 900);

		board.getActivePlayer().getField().getHand().add(geminiElf);
		geminiElf.setLocation(Location.HAND);

		board.getActivePlayer().endPhase();
		board.getActivePlayer().endPhase();

		try {

			board.getActivePlayer().summonMonster(geminiElf);

		} catch (WrongPhaseException e) {

			fail("Summoning a monster in Main Phase 2 should not throw an exception.\n"
					+ e.getMessage());

		}

	}

	@Test(timeout = 1000)
	public void testWrongPhaseExceptionSummonWithSacrificeInBattle()
			throws Exception {

		Board board = new Board();
		Player p1 = new Player("Yugi");
		Player p2 = new Player("Kaiba");
		board.startGame(p1, p2);

		MonsterCard vorseRaider1 = new MonsterCard("Vorse Raider",
				"A warrior beast", 4, 1900, 1200);
		MonsterCard vorseRaider2 = new MonsterCard("Vorse Raider",
				"A warrior beast", 4, 1900, 1200);

		board.getActivePlayer().getField().getMonstersArea().add(vorseRaider1);
		board.getActivePlayer().getField().getMonstersArea().add(vorseRaider2);

		ArrayList<MonsterCard> sacrifices = new ArrayList<MonsterCard>();
		sacrifices.add(vorseRaider1);
		sacrifices.add(vorseRaider2);

		MonsterCard geminiElf = new MonsterCard("Gemini Elf", "the twins", 7,
				1900, 900);
		board.getActivePlayer().getField().getHand().add(geminiElf);
		geminiElf.setLocation(Location.HAND);

		board.getActivePlayer().endPhase();

		try {

			board.getActivePlayer().summonMonster(geminiElf, sacrifices);

		} catch (WrongPhaseException e) {

			return;

		}

		fail("Summoning a monster in battle phase should throw the appropriate exception.");

	}

	@Test(timeout = 1000)
	public void testWrongPhaseExceptionSummonWithSacrificeInMain1()
			throws Exception {

		Board board = new Board();
		Player p1 = new Player("Yugi");
		Player p2 = new Player("Kaiba");
		board.startGame(p1, p2);

		MonsterCard vorseRaider1 = new MonsterCard("Vorse Raider",
				"A warrior beast", 4, 1900, 1200);
		MonsterCard vorseRaider2 = new MonsterCard("Vorse Raider",
				"A warrior beast", 4, 1900, 1200);

		board.getActivePlayer().getField().getMonstersArea().add(vorseRaider1);
		board.getActivePlayer().getField().getMonstersArea().add(vorseRaider2);

		ArrayList<MonsterCard> sacrifices = new ArrayList<MonsterCard>();
		sacrifices.add(vorseRaider1);
		sacrifices.add(vorseRaider2);

		MonsterCard geminiElf = new MonsterCard("Gemini Elf", "the twins", 7,
				1900, 900);

		board.getActivePlayer().getField().getHand().add(geminiElf);
		geminiElf.setLocation(Location.HAND);

		try {

			board.getActivePlayer().summonMonster(geminiElf, sacrifices);

		} catch (WrongPhaseException e) {

			fail("Summoning a monster in Main Phase 1 should not throw an exception.\n"
					+ e.getMessage());

		}

	}

	@Test(timeout = 1000)
	public void testWrongPhaseExceptionSummonWithSacrificeInMain2()
			throws Exception {

		Board board = new Board();
		Player p1 = new Player("Yugi");
		Player p2 = new Player("Kaiba");
		board.startGame(p1, p2);

		MonsterCard vorseRaider1 = new MonsterCard("Vorse Raider",
				"A warrior beast", 4, 1900, 1200);
		MonsterCard vorseRaider2 = new MonsterCard("Vorse Raider",
				"A warrior beast", 4, 1900, 1200);

		board.getActivePlayer().getField().getMonstersArea().add(vorseRaider1);
		board.getActivePlayer().getField().getMonstersArea().add(vorseRaider2);

		ArrayList<MonsterCard> sacrifices = new ArrayList<MonsterCard>();
		sacrifices.add(vorseRaider1);
		sacrifices.add(vorseRaider2);

		MonsterCard geminiElf = new MonsterCard("Gemini Elf", "the twins", 7,
				1900, 900);

		board.getActivePlayer().getField().getHand().add(geminiElf);
		geminiElf.setLocation(Location.HAND);

		board.getActivePlayer().endPhase();
		board.getActivePlayer().endPhase();

		try {

			board.getActivePlayer().summonMonster(geminiElf, sacrifices);

		} catch (WrongPhaseException e) {

			fail("Summoning a monster in Main Phase 2 should not throw an exception.\n"
					+ e.getMessage());

		}

	}

	@Test(timeout = 1000)
	public void testWrongPhaseSwitchingModeInBattle() throws Exception {

		Board board = new Board();
		Player p1 = new Player("Yugi");
		Player p2 = new Player("Kaiba");
		board.startGame(p1, p2);

		MonsterCard m = new MonsterCard("Vorse Raider", "A warrior beast", 4,
				1900, 1200);

		board.getActivePlayer().getField().getHand().add(m);
		m.setLocation(Location.HAND);

		board.getActivePlayer().summonMonster(m);

		board.getActivePlayer().endPhase();

		try {

			board.getActivePlayer().switchMonsterMode(m);

		} catch (WrongPhaseException e) {
			return;
		}

		fail("Switching a monster\'s mode in battle phase should throw the appropriate exception.");

	}

	@Test(timeout = 1000)
	public void testWrongPhaseSwitchingModeInMain1() throws Exception {

		Board board = new Board();
		Player p1 = new Player("Yugi");
		Player p2 = new Player("Kaiba");
		board.startGame(p1, p2);

		MonsterCard m = new MonsterCard("Vorse Raider", "A warrior beast", 4,
				1900, 1200);

		board.getActivePlayer().getField().getHand().add(m);
		m.setLocation(Location.HAND);

		board.getActivePlayer().summonMonster(m);

		try {

			board.getActivePlayer().switchMonsterMode(m);

		} catch (WrongPhaseException e) {

			fail("Switching a monster\'s mode in Main phase 1 should not throw an exception.\n"
					+ e.getMessage());

		}

	}

}
