package eg.edu.guc.yugioh.gui;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

import eg.edu.guc.yugioh.cards.Card;
import eg.edu.guc.yugioh.cards.MonsterCard;

public class GraveyardPanel extends JPanel implements MouseListener{
	private GUI gui;
	private JLabel cardPicture;
	private Card card;
	public GraveyardPanel(Card card,GUI gui) {
		this.gui=gui;
		this.card=card;
        if(card==null)
		cardPicture = new JLabel(new ImageIcon("EmptyGraveyard.jpg"));
        else
        	cardPicture = new JLabel(new ImageIcon(card.getName()+".jpg"));
		this.setOpaque(true);
		cardPicture.addMouseListener(this);
		this.add(cardPicture);
		this.validate();
	}
	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void mouseEntered(MouseEvent e) {
		gui.getBackGroundPic().setVisible(false);
		gui.getCardPicture().setVisible(false);
		gui.getCardDescriptionPanel().setVisible(false);
		gui.cardPicturePanel(card);
		gui.backGround();
		gui.validate();
	}
	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

}
