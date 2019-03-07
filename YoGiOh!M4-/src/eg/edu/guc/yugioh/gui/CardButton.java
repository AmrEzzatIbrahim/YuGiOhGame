package eg.edu.guc.yugioh.gui;

import java.awt.Insets;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import eg.edu.guc.yugioh.board.player.Phase;
import eg.edu.guc.yugioh.board.player.Player;
import eg.edu.guc.yugioh.cards.Card;
import eg.edu.guc.yugioh.cards.Location;
import eg.edu.guc.yugioh.cards.Mode;
import eg.edu.guc.yugioh.cards.MonsterCard;
import eg.edu.guc.yugioh.cards.spells.ChangeOfHeart;
import eg.edu.guc.yugioh.cards.spells.GracefulDice;
import eg.edu.guc.yugioh.cards.spells.MagePower;
import eg.edu.guc.yugioh.cards.spells.SpellCard;
import eg.edu.guc.yugioh.exceptions.NoMonsterSpaceException;
import eg.edu.guc.yugioh.exceptions.NoSpellSpaceException;
import eg.edu.guc.yugioh.listeners.ChoicesMenu;
import eg.edu.guc.yugioh.listeners.MessageWindow;

public class CardButton extends JButton implements ActionListener,
		MouseListener {

	private JLabel cardPicture;
	private boolean activePlayer;
	private Card card;
	private GUI gui;
	private  ChoicesMenu choices;
    private static MonsterCard AttackerMonster;
    private static SpellCard currentSpell;
    private static MonsterCard SummonedMonster;
    private static MonsterCard SacrificedMonster1;
    private static MonsterCard SacrificedMonster2;
    private static ArrayList<MonsterCard> Sacrifices=new ArrayList<MonsterCard>();
	public CardButton(Card card, boolean activePlayer, GUI gui) {
		super();
		this.setMargin(new Insets(0, 0, 0, 0));
		
		this.activePlayer = activePlayer;
		this.card = card;
		this.gui = gui;
		this.setEnabled(false);
		this.setContentAreaFilled(false);
		this.setBorderPainted(false);
		this.setOpaque(false);
		if (card != null)
			initialize();
		

	}

	public void initialize() {
		String path = "";
		if (card.getLocation() == Location.FIELD) {
			if (card.isHidden()) {
				if (card instanceof MonsterCard)
					path = "BackCard Defense.jpg";
				else
					path = "BackCard.jpg";
			} else if (card instanceof MonsterCard) {
				if (((MonsterCard) card).getMode() == Mode.DEFENSE)
					path = card.getName() + " Defense.jpg";
				else
					path = card.getName() + ".jpg";
			}
		} else {
			if (activePlayer)
				path = card.getName() + ".jpg";
			else
				path = "BackCard.jpg";
		}
		if(card.getLocation()==Location.GRAVEYARD){
			
		}
		cardPicture = new JLabel(new ImageIcon(path));
		cardPicture.setOpaque(false);
		cardPicture.setBounds(0, 0, 200, 200);

		if (activePlayer) {
			this.setEnabled(true);
			 this.addActionListener(this);
			 this.addMouseListener(this);
		} else {
			if (card.getLocation()==Location.FIELD){
				this.setEnabled(true);
			 
			 if(card instanceof MonsterCard) {
				 this.addMouseListener(this);
				 this.addActionListener(this);}
		}
		}

		this.add(cardPicture);

	}

	public Card getCard() {
		return card;
	}

	public void cardDescriptionFix(Card card) {
		gui.getBackGroundPic().setVisible(false);
		gui.getCardDescriptionPanel().setVisible(false);
		gui.cardPicturePanel(card);
		gui.backGround();
		gui.validate();
	}

	public void actionPerformed(ActionEvent e) {
	try{	boolean donotPop=false;
		PlayerPanel activePlayerPanel;
		PlayerPanel opponentPlayerPanel;

		if (gui.getP1().equals(Card.getBoard().getActivePlayer().getName())) {
			 activePlayerPanel = gui.getPlayer1();
			 opponentPlayerPanel = gui.getPlayer2();
		} else {
			 activePlayerPanel = gui.getPlayer2();
			 opponentPlayerPanel = gui.getPlayer1();
		}

		Player activePlayer = Card.getBoard().getActivePlayer();
		Player opponentPlayer = Card.getBoard().getOpponentPlayer();
		if(AttackerMonster!=null){
			for(int i=0;i<opponentPlayer.getField().getMonstersArea().size();i++){
				if(e.getSource()==opponentPlayerPanel.getMonsterAreaPanel().getMonsterAreaList().get(i)){
					Card.getBoard().getActivePlayer().declareAttack(AttackerMonster,(MonsterCard)card);
					AttackerMonster=null;
					if(Card.getBoard().getWinner()!=null){
						MessageWindow m =new MessageWindow(gui,Card.getBoard().getWinner().getName()+" WINS");
						m.setVisible(true);
						return;
					
					}	
					if(choices!=null){
						choices.setVisible(false);	
						choices=null;
					}
					gui.RemoveAndFix();		
				}
			}
			
		}
		if(SummonedMonster!=null){
			
			
				if(activePlayerPanel.getMonsterAreaPanel().getMonsterAreaList().contains(e.getSource())){
					donotPop=true;
					if(SacrificedMonster1==null){
						SacrificedMonster1=(MonsterCard)card;
						Sacrifices.add(SacrificedMonster1);
						if(SummonedMonster.getLevel()<=6){
							if(gui.isSet()){
							activePlayer.setMonster(SummonedMonster, Sacrifices);
							SummonedMonster=null;
							SacrificedMonster1=null;
							Sacrifices.clear();
							}
								else{
									activePlayer.summonMonster(SummonedMonster, Sacrifices);
									SummonedMonster=null;
									SacrificedMonster1=null;
									Sacrifices.clear();
									}
						}
						}
					else if(SacrificedMonster2==null){
						SacrificedMonster2=(MonsterCard)card;
						Sacrifices.add(SacrificedMonster2);
						if(gui.isSet()){
							activePlayer.setMonster(SummonedMonster, Sacrifices);
							SummonedMonster=null;
							SacrificedMonster1=null;
							SacrificedMonster2=null;
							Sacrifices.clear();
						}
								else{
									activePlayer.summonMonster(SummonedMonster, Sacrifices);
									SummonedMonster=null;
									SacrificedMonster1=null;
									SacrificedMonster2=null;	
									Sacrifices.clear();		
									
								}
						}
					
					if(Card.getBoard().getWinner()!=null){
						MessageWindow m =new MessageWindow(gui,Card.getBoard().getWinner().getName()+" WINS");
						m.setVisible(true);
						return;
					
					}	
					if(choices!=null){
						choices.setVisible(false);
						choices=null;
					}
					gui.RemoveAndFix();		
				}
			}
			
		
        
		if(currentSpell!=null){
			if(currentSpell instanceof ChangeOfHeart){
				if(opponentPlayerPanel.getMonsterAreaPanel().getMonsterAreaList().contains(e.getSource())){
					activePlayer.activateSpell(currentSpell,(MonsterCard)card);
				}
			}
			else if(currentSpell instanceof MagePower){
				if(activePlayerPanel.getMonsterAreaPanel().getMonsterAreaList().contains(e.getSource())){
					activePlayer.activateSpell(currentSpell,(MonsterCard)card);
				}
			}
			currentSpell=null;
			if(Card.getBoard().getWinner()!=null){
				MessageWindow m =new MessageWindow(gui,Card.getBoard().getWinner().getName()+" WINS");
				m.setVisible(true);
				return;
			
			}	
			if(choices!=null){
				choices.setVisible(false);	
				choices=null;
				}
			gui.RemoveAndFix();
	}
			
		
		
	
        if(choices==null&&!donotPop){
		for (int i = 0; i < activePlayerPanel.getHandPanel().getHandList().size(); i++)
			if (e.getSource() == activePlayerPanel.getHandPanel().getHandList().get(i)
					&& activePlayer.getField().getPhase() != Phase.BATTLE) {
				SummonedMonster=null;
				SacrificedMonster1=null;
				SacrificedMonster2=null;
				Sacrifices.clear();
				Card card = activePlayerPanel.getHandPanel().getHandList().get(i).getCard();
				if (card instanceof MonsterCard
						&& !activePlayer.getField().isSummoned()) {
					choices=new ChoicesMenu(false, true, false, ((MonsterCard)card).getMode()+"",card,gui);
					choices.addMouseListener(this);
					
					if(((MonsterCard)card).getLevel()>4){
				    SummonedMonster=(MonsterCard)card;
					}
					//output a panel with set or summon choices
				}
				if (card instanceof SpellCard) {
					currentSpell=null;
					choices=new ChoicesMenu(true,true,false,"",card,gui);
					choices.addMouseListener(this);
					currentSpell=(SpellCard)card;
					// output a panel with activate or set choices
				}
			}

		for (int j = 0; j < activePlayerPanel.getSpellAreaPanel().getSpellAreaList().size(); j++) {
			if (e.getSource() == activePlayerPanel.getSpellAreaPanel().getSpellAreaList().get(j)
					&& activePlayer.getField().getPhase() != Phase.BATTLE) {
				currentSpell=null;
				choices=new ChoicesMenu(true, false, false, "",card,gui);
				choices.addMouseListener(this);
				currentSpell=(SpellCard)card;

				
			}

		}

		for (int k = 0; k < activePlayerPanel.getMonsterAreaPanel().getMonsterAreaList().size(); k++)
			if (e.getSource() == activePlayerPanel.getMonsterAreaPanel().getMonsterAreaList().get(k)) {
				Card card = activePlayerPanel.getMonsterAreaPanel().getMonsterAreaList().get(k).getCard();
				if (activePlayer.getField().getPhase() == Phase.BATTLE) {
					if (!activePlayer.getAttackers().contains(card)
							&& ((MonsterCard) card).getMode() == Mode.ATTACK && !Card.getBoard().isFirstTurn() ) {
						choices=new ChoicesMenu(false, false, true, ((MonsterCard)card).getMode()+"",(MonsterCard)card,gui);
						choices.addMouseListener(this);
						// output a panel with attack choice
						if (!opponentPlayer.getField().getMonstersArea()
								.isEmpty()){
							AttackerMonster=(MonsterCard)card;
						}
						
					}
				} else {
					if (!activePlayer.getSwitchedMonsters().contains(card)){ 
						choices=new ChoicesMenu(false, false, false, ((MonsterCard)card).getMode()+"",card,gui);
					choices.addMouseListener(this);}
						// output a panel with switch monster poisition

				}

			}
        }
	}
	catch(NoMonsterSpaceException x){
		JOptionPane.showMessageDialog(gui,"No enough Space in your Monsters Area");
		choices.setVisible(false);
	}
	catch(NoSpellSpaceException x){
		JOptionPane.showMessageDialog(gui,"No enough Space in your Spells Area");
		choices.setVisible(false);
	}
	}	

	public void mouseClicked(java.awt.event.MouseEvent e) {
		
	}

 	public void mouseEntered(java.awt.event.MouseEvent e) {
		if(choices!=null){
			choices.setVisible(false);
			choices=null;
		}
		PlayerPanel activePlayerPanel;
		PlayerPanel OpponentPlayerPanel;
		if (gui.getP1().equals(Card.getBoard().getActivePlayer().getName())) {
			
			 activePlayerPanel = gui.getPlayer1();
			 OpponentPlayerPanel = gui.getPlayer2();
		} else {
			 activePlayerPanel = gui.getPlayer2();
			 OpponentPlayerPanel = gui.getPlayer1();
		}
		
		for (int k = 0; k < OpponentPlayerPanel.getHandPanel().getHandList().size(); k++) {
			if (e.getSource() != OpponentPlayerPanel.getHandPanel().getHandList().get(k)) {
				if(card instanceof MonsterCard){
					if(OpponentPlayerPanel.getMonsterAreaPanel().getMonsterAreaList().contains(e.getSource())){
						if(((MonsterCard)card).isHidden()==false){
							gui.getCardPicture().setVisible(false);
							gui.getCardDescriptionPanel().setVisible(false);
							cardDescriptionFix(card);	
						}
					}
					else{
						gui.getCardPicture().setVisible(false);
						gui.getCardDescriptionPanel().setVisible(false);
						cardDescriptionFix(card);	
					}
				}
				else{
					gui.getCardPicture().setVisible(false);
					gui.getCardDescriptionPanel().setVisible(false);
					cardDescriptionFix(card);	
				}
					
			}
		}
		
		
			
			
		

	}

	@Override
	public void mouseExited(java.awt.event.MouseEvent e) {
	
		if(choices!=null){		
	 		if(e.getSource()==choices){
	 			choices.setVisible(false);
	 			choices=null;
	 		}
		}
	}
	
	public void mousePressed(java.awt.event.MouseEvent e) {

	}

	@Override
	public void mouseReleased(java.awt.event.MouseEvent e) {
	//	ChoicesMenu choices=new ChoicesMenu(;

	}

	public GUI getGui() {
		return gui;
	}

}
