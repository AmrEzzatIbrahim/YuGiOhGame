package eg.edu.guc.yugioh.gui;

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.plaf.basic.BasicTreeUI.SelectionModelPropertyChangeHandler;

import eg.edu.guc.yugioh.board.Board;
import eg.edu.guc.yugioh.board.player.Phase;
import eg.edu.guc.yugioh.board.player.Player;
import eg.edu.guc.yugioh.cards.Card;
import eg.edu.guc.yugioh.exceptions.UnexpectedFormatException;
import eg.edu.guc.yugioh.listeners.MessageWindow;

public class GUI extends JFrame {
	
	
	private StartPanel startPanel;
	private PhasePanel phasePanel;
	private PlayerPanel player1;
	private PlayerPanel player2;
	private JLabel cardPicture;
	private CardDescriptionPanel cardDescriptionPanel;
	private Board board;
	private String p1;
    private boolean set;
    private JLabel backGroundPic;

    
    
    public GUI(Board board) {
		this.board = board;
		setLayout(null);
		setTitle("YuGiUh by Amr Ezzat and Hussein El Zomor :D");
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setSize(getMaximumSize());
		
		this.validate();
	}
public void backGround(){
//	if(board.getActivePlayer().getField().getPhase()==Phase.BATTLE){
		backGroundPic=new JLabel();
		backGroundPic.setBounds(0, 0,1370,722);
		backGroundPic.setIcon(new ImageIcon("gameBackgroung.jpg"));
		this.getContentPane().add(backGroundPic);
		this.validate();
//	}
//	else{
//		backGroundPic=new JLabel();
//		backGroundPic.setBounds(0, 0,1370,722);
//		backGroundPic.setIcon(new ImageIcon("YamiStart.jpg"));
//		this.add(backGroundPic);
//	}
}
	public void startPanel() {
		
		startPanel = new StartPanel();
		p1 = "";
		startPanel.setBounds(0, 0, getWidth(), getHeight());
		add(startPanel);
		setVisible(true);
	}

	public void cardPicturePanel(Card card) {
		String myPicture;

		if (card != null)

			myPicture =card.getName() + " Big.jpg";
		else
			myPicture ="BigBackCard.jpg";
		cardPicture = new JLabel(new ImageIcon(myPicture));
		cardPicture.setBackground(Color.gray);
		cardPicture.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
		cardPicture.setBounds(5,5,290,429);
		this.add(cardPicture);
		this.setVisible(true);
		cardDescriptionPanel(card);
        this.validate();

	}
	public void cardDescriptionPanel(Card card) {
		cardDescriptionPanel = new CardDescriptionPanel(card);
		cardDescriptionPanel.setBackground(Color.LIGHT_GRAY);
		cardDescriptionPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
		cardDescriptionPanel.setBounds(5,435,290,300);
		cardDescriptionPanel.setBorder(null);
		
		this.add(cardDescriptionPanel);
	}

	public void phasePanel() {
		phasePanel = new PhasePanel();
		phasePanel.setBounds(1230, 270, 100, 120);
		phasePanel.setOpaque(false);
		add(phasePanel);
        this.validate();
	}

	public void player1() {
		if(p1.equals(board.getActivePlayer().getName()))
			player1 = new PlayerPanel(300, 0, board.getActivePlayer(), true, this);
		else
			player1 = new PlayerPanel(300, 0, board.getOpponentPlayer(), false, this);
	
		
		add(player1);
        this.validate();

	}

	public void player2() {
		if(!p1.equals(board.getActivePlayer().getName()))
		player2 = new PlayerPanel(300, 361, board.getActivePlayer(), true,
				this);
		else
			player2 = new PlayerPanel(300, 361, board.getOpponentPlayer(), false,
					this);
			
		add(player2);
        this.validate();

	}

	public void RemoveAndFix(){
		
		this.getBackGroundPic().setVisible(false);
		this.getCardPicture().setVisible(false);
		this.getCardDescriptionPanel().setVisible(false);
		this.getPlayer1().setVisible(false);
		this.getPlayer2().setVisible(false);
 		Fix();
	}

	public void Fix() {
	synchronized (this) {
		if(board.getActivePlayer().getField().getPhase()==Phase.MAIN1&&Card.getBoard().getWinner()==null){
			try {
				this.wait(150);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		this.cardPicturePanel(null);
		this.player1();
		this.player2();
		this.backGround(); 
		this.validate();}
	}
	
	public static void main(String[] args) {
		Board x = new Board();
		try {
			x.startGame(new Player("Hussein"), new Player("Amr"));
		} catch (IOException e) {
			e.printStackTrace();
		} catch (UnexpectedFormatException e) {
			e.printStackTrace();
		}
		new GUI(x);
	}

	public PlayerPanel getPlayer1() {
		return player1;
	}

	public PlayerPanel getPlayer2() {
		return player2;
	}

	public PhasePanel getPhasePanel() {
		return phasePanel;
	}

	public StartPanel getStartPanel() {
		return startPanel;
	}

	
	public void setP1(String p1) {
		this.p1 = p1;
	}

	public JLabel getCardPicture() {
		return cardPicture;
	}

	public String getP1() {
		return p1;
	}

	public CardDescriptionPanel getCardDescriptionPanel() {
		return cardDescriptionPanel;
	}

	public boolean isSet() {
		return set;
	}

	public void setSet(boolean set) {
		this.set = set;
	}
	public JLabel getBackGroundPic() {
		return backGroundPic;
	}

    
	
}
