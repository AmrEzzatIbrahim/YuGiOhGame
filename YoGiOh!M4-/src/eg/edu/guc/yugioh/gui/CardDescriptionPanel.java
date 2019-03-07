package eg.edu.guc.yugioh.gui;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.JLabel;
import javax.swing.JPanel;

import org.hamcrest.core.IsInstanceOf;

import eg.edu.guc.yugioh.cards.Card;
import eg.edu.guc.yugioh.cards.MonsterCard;

@SuppressWarnings("serial")
public class CardDescriptionPanel extends JPanel {

	private JLabel name;
	private JLabel description;
	private JLabel attackAndDefense;

	public CardDescriptionPanel(Card card) {
		super();
		
		if (card != null){
		name = new JLabel(" " + card.getName());
		name.setPreferredSize(new Dimension(300, 40));
		description = new JLabel("<html>" + card.getDescription() +"</html>" );
		
		if (card instanceof MonsterCard)
			attackAndDefense = new JLabel("ATK "
					+ ((MonsterCard) card).getAttackPoints() + " / " + "DEF "
					+ ((MonsterCard) card).getDefensePoints() + " ", JLabel.RIGHT);

		GridBagLayout layout = new GridBagLayout();

		this.setLayout(layout);
		GridBagConstraints gbc = new GridBagConstraints();

		gbc.fill = GridBagConstraints.BOTH;
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.weightx = 1;
		gbc.weighty = 1;
		this.add(name, gbc);

		gbc.anchor = GridBagConstraints.CENTER;
		gbc.gridx = 0;
		gbc.gridy = 1;
		this.add(description, gbc);

		gbc.gridx = 0;
		gbc.gridy = 2;

		if (card instanceof MonsterCard)
			this.add(attackAndDefense, gbc);
		}
		
		setVisible(true);
		
	}
}
