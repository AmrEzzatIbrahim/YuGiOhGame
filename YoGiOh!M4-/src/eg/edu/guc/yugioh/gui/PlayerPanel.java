package eg.edu.guc.yugioh.gui;

import java.awt.Color;
import java.io.ObjectInputStream.GetField;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import eg.edu.guc.yugioh.board.player.Deck;
import eg.edu.guc.yugioh.board.player.Player;

public class PlayerPanel extends JPanel {
	private MonsterAreaPanel monsterAreaPanel;
	private SpellAreaPanel spellAreaPanel;
	private HandPanel handPanel;
	private DeckPanel deckPanel;
	private GraveyardPanel graveyardPanel;
	private JLabel playerName;
	private JLabel lifePoints;

	public PlayerPanel(int x, int y, Player player, boolean activePlayer,
			GUI gui) {
		super();
		
		setLayout(null);
		setBounds(x, y, 1070, 361);

		monsterAreaPanel = new MonsterAreaPanel(player.getField(),
				activePlayer, gui);
		spellAreaPanel = new SpellAreaPanel(player.getField(), activePlayer,
				gui);
		handPanel = new HandPanel(player.getField(), activePlayer, gui);
		deckPanel = new DeckPanel(player.getField().getDeck().getDeck());
		playerName = new JLabel("Name: " + player.getName(),JLabel.CENTER);
		lifePoints = new JLabel("LP: " +player.getLifePoints(), JLabel.CENTER);
		
		monsterAreaPanel.setOpaque(false);
		spellAreaPanel.setOpaque(false);
		handPanel .setOpaque(false);
		deckPanel.setOpaque(false);
		playerName.setOpaque(false);
		lifePoints.setOpaque(false);
		
		
		playerName.setForeground(Color.WHITE);
		lifePoints.setForeground(Color.WHITE);
//		playerName.setF
		
		
		if (player.getField().getGraveyard().isEmpty())
			graveyardPanel = new GraveyardPanel(null,gui);
		else
			graveyardPanel = new GraveyardPanel(player.getField()
					.getGraveyard()
					.get(player.getField().getGraveyard().size()-1),gui);

		add(playerName);
		add(lifePoints);
		add(monsterAreaPanel);
		add(spellAreaPanel);
		add(handPanel);
		add(deckPanel);
		add(graveyardPanel);

		if (y!=0) {
			playerName.setBounds(0, 250, 200, 60);
			lifePoints.setBounds(0, 280, 200, 60);
			deckPanel.setBounds(60, 120, 80, 120);
			graveyardPanel.setBounds(60, 0, 80, 120);
			monsterAreaPanel.setBounds(270, 0, 560, 120);
			spellAreaPanel.setBounds(270, 120, 560, 120);
			handPanel.setBounds(200, 240, 700, 120);
		} else {
			playerName.setBounds(0, 10, 200, 60);
			lifePoints.setBounds(0, 40,200, 60);
			graveyardPanel.setBounds(60, 240, 80, 120);
			deckPanel.setBounds(60, 120, 80, 120);
			handPanel.setBounds(200, 0, 700, 120);
			spellAreaPanel.setBounds(270, 120, 560, 120);
			monsterAreaPanel.setBounds(270, 240, 560, 120);
		}
		deckPanel.setOpaque(false);
		graveyardPanel.setOpaque(false);
		this.setOpaque(false);
		this.validate();

	}

	public MonsterAreaPanel getMonsterAreaPanel() {
		return monsterAreaPanel;
	}

	public void setMonsterAreaPanel(MonsterAreaPanel monsterAreaPanel) {
		monsterAreaPanel = monsterAreaPanel;
	}

	public SpellAreaPanel getSpellAreaPanel() {
		return spellAreaPanel;
	}

	public void setSpellAreaPanel(SpellAreaPanel spellAreaPanel) {
		spellAreaPanel = spellAreaPanel;
	}

	public HandPanel getHandPanel() {
		return handPanel;
	}

	public void setHandPanel(HandPanel handPanel) {
		handPanel = handPanel;
	}

	public DeckPanel getDeckPanel() {
		return deckPanel;
	}

	public void setDeckPanel(DeckPanel deckPanel) {
		deckPanel = deckPanel;
	}

	public GraveyardPanel getGraveyardPanel() {
		return graveyardPanel;
	}

	public void setGraveyardPanel(GraveyardPanel graveyardPanel) {
		graveyardPanel = graveyardPanel;
	}

	
	public JLabel getPlayerName() {
		return playerName;
	}

	public void setPlayerName(JLabel name) {
		this.playerName = name;
	}

	public JLabel getLifePoints() {
		return lifePoints;
	}

	public void setLifePoints(JLabel lifePoints) {
		this.lifePoints = lifePoints;
	}

	
}
