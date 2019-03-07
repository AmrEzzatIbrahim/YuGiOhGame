package eg.edu.guc.yugioh.listeners;

import java.awt.Menu;
import java.awt.MenuItem;
import java.awt.PopupMenu;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPopupMenu;
import javax.swing.event.MenuKeyEvent;
import javax.swing.event.MenuKeyListener;
import javax.swing.event.PopupMenuEvent;
import javax.swing.event.PopupMenuListener;

import eg.edu.guc.yugioh.cards.Card;
import eg.edu.guc.yugioh.cards.Mode;
import eg.edu.guc.yugioh.cards.MonsterCard;
import eg.edu.guc.yugioh.cards.spells.ChangeOfHeart;
import eg.edu.guc.yugioh.cards.spells.GracefulDice;
import eg.edu.guc.yugioh.cards.spells.MagePower;
import eg.edu.guc.yugioh.cards.spells.SpellCard;
import eg.edu.guc.yugioh.exceptions.NoMonsterSpaceException;
import eg.edu.guc.yugioh.exceptions.NoSpellSpaceException;
import eg.edu.guc.yugioh.gui.CardButton;
import eg.edu.guc.yugioh.gui.GUI;

public class ChoicesMenu extends JPopupMenu implements ActionListener{

	private JMenuItem summonMonster;
	private JMenuItem setMonster;
	private JMenuItem activateSpell;
	private JMenuItem setSpell;
	private JMenuItem attack;
	private JMenuItem changePosition;
	private GUI gui;
	private Card card;
	private boolean set=false;
	private boolean oneBs=false;
	public ChoicesMenu(boolean spell, boolean inHand, boolean battlePhase,String mode,Card card,GUI gui){
        this.card=card;
        this.gui=gui;
      
		if(spell){
			activateSpell();
			if(inHand)
				setSpell();
		}
			else{
				if(inHand){
					summonMonster();
					setMonster();
				}
				else
					if(battlePhase){
						if(((MonsterCard)card).getMode()==Mode.ATTACK){
						attack();
						}
					}
					else{
						changePosition();
					}
				
			}
		this.setLocation(java.awt.MouseInfo.getPointerInfo().getLocation().x-30,java.awt.MouseInfo.getPointerInfo().getLocation().y-20);
		this.setVisible(true);
		
	}
	public void summonMonster(){
		summonMonster = new JMenuItem("Summon");
		summonMonster.addActionListener(this);
		this.add(summonMonster);
	}
	public void setMonster(){
		setMonster = new JMenuItem("Set");
		setMonster.addActionListener(this);
		this.add(setMonster);
	}
	public void activateSpell(){
		activateSpell = new JMenuItem("Activate");
		activateSpell.addActionListener(this);
	this.add(activateSpell);
	}
	public void setSpell(){
		setSpell = new JMenuItem("Set");
	    setSpell.addActionListener(this);
	this.add(setSpell);
	}
	public void attack(){
		attack = new JMenuItem("Attack");
		attack.addActionListener(this);
	this.add(attack);
	}
	public void changePosition(){
		changePosition = new JMenuItem("Switch");
		changePosition.addActionListener(this);
	this.add(changePosition);
	}
	
	public void actionPerformed(ActionEvent e) {
		this.setVisible(false);
	try{	if(e.getSource() == summonMonster){
			gui.setSet(false);
	         if(((MonsterCard)card).getLevel()>4){
	        	 if(((MonsterCard)card).getLevel()<7)
					 JOptionPane.showMessageDialog(null, "chose 1 monster to sacrifice");
				 else
					 JOptionPane.showMessageDialog(null, "chose 2 monsters to sacrifice");
	        	
	        	 }
	         else
			Card.getBoard().getActivePlayer().summonMonster((MonsterCard)card);
		}
		else if(e.getSource()==setMonster){
			gui.setSet(true);
			 if(((MonsterCard)card).getLevel()>4){
				 if(((MonsterCard)card).getLevel()<7)
					 JOptionPane.showMessageDialog(gui, "chose 1 monster to sacrifice");
				 else
					 JOptionPane.showMessageDialog(gui, "chose 2 monsters to sacrifice");
	        	 
	        	 }
	         else
			Card.getBoard().getActivePlayer().setMonster((MonsterCard)card);
		}
		else if(e.getSource()==activateSpell){
			if((SpellCard)card instanceof ChangeOfHeart ){
				if (!Card.getBoard().getOpponentPlayer().getField().getMonstersArea()
						.isEmpty()){
					 JOptionPane.showMessageDialog(null, "chose an opponent monster to spell");
					 this.setVisible(false);
					return;}


				else{
					 JOptionPane.showMessageDialog(null, "no monsters for Change of hearts to spell");
					 this.setVisible(false);
				}
				}
				else if((SpellCard)card instanceof MagePower){
					if(!Card.getBoard().getActivePlayer().getField().getMonstersArea().isEmpty()){
						 JOptionPane.showMessageDialog(null, "chose a monsters of yours to spell");
						 this.setVisible(false);
						 return;
					}
					else{
						 JOptionPane.showMessageDialog(null, "no monsters for Mage power to spell");
						 this.setVisible(false);
					}
					}
				
					else{
						Card.getBoard().getActivePlayer().activateSpell((SpellCard)card, null);
						}
			
		}
		else if(e.getSource()==setSpell){
			Card.getBoard().getActivePlayer().setSpell((SpellCard)card);
		}
		else if(e.getSource()==attack){
			if(Card.getBoard().getOpponentPlayer().getField().getMonstersArea().isEmpty()){
				Card.getBoard().getActivePlayer().declareAttack((MonsterCard)card);
			}
			else{
				return ;
				}
			
		}
		else if(e.getSource()==changePosition){
			Card.getBoard().getActivePlayer().switchMonsterMode((MonsterCard)card);
			this.setVisible(false);
		}
	
	if(Card.getBoard().getWinner()!=null){
		MessageWindow m =new MessageWindow(gui,Card.getBoard().getWinner().getName()+" WINS");
		m.setLocation(500,300);
		m.setVisible(true);
		return;
	}
	this.setVisible(false);	
	gui.RemoveAndFix();
	}
	catch(NoMonsterSpaceException x){
		JOptionPane.showMessageDialog(gui,"No enough Space in your Monsters Area");
		this.setVisible(false);
	}
	catch(NoSpellSpaceException x){
		JOptionPane.showMessageDialog(gui,"No enough Space in your Spells Area");
         this.setVisible(false);
	}
	}


	public JMenuItem getSummonMonster() {
		return summonMonster;
		
	}
	public JMenuItem getSetMonster() {
		return setMonster;
	}
	public JMenuItem getActivateSpell() {
		return activateSpell;
	}
	public JMenuItem getSetSpell() {
		return setSpell;
	}
	public JMenuItem getAttack() {
		return attack;
	}
	public JMenuItem getChangePosition() {
		return changePosition;
	}
	public boolean isSet() {
		return set;
	}
	public boolean isOneBs() {
		return oneBs;
	}	
	
	}
	
	

