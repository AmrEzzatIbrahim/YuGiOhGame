package eg.edu.guc.yugioh.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

import eg.edu.guc.yugioh.board.player.Deck;
import eg.edu.guc.yugioh.cards.Card;

public class DeckPanel extends JPanel {
	
	private JLabel NumOfCardsInDeck;
	private BufferedImage myPicture;
	private JLabel cardPicture;
	
	public DeckPanel(ArrayList<Card> deck) {
		super();
		
		setLayout(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();
		
		gbc.gridx = 0;
		gbc.gridy = 0;
		if(!deck.isEmpty())
		NumOfCardsInDeck = new JLabel(deck.size() + "",JLabel.CENTER);
		else
			NumOfCardsInDeck = new JLabel(0 + "",JLabel.CENTER);
		add(NumOfCardsInDeck,gbc);
		NumOfCardsInDeck.setForeground(Color.WHITE);
		setDeckPicture(gbc);
		
		this.validate();

	}
	public void setDeckPicture(GridBagConstraints gbc){
		try {
			myPicture = ImageIO.read(new File("BackCard.jpg"));
			cardPicture = new JLabel(new ImageIcon(myPicture));

			this.add(cardPicture, gbc);
			this.setVisible(true);
		} catch (IOException e) {
			System.out.println("there is no display card");
		}
	}

	public JLabel getNumOfCardsInDeck() {
		return NumOfCardsInDeck;
	}

}
