package eg.edu.guc.yugioh.listeners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import eg.edu.guc.yugioh.board.Board;
import eg.edu.guc.yugioh.board.player.Phase;
import eg.edu.guc.yugioh.cards.Card;
import eg.edu.guc.yugioh.cards.Mode;
import eg.edu.guc.yugioh.cards.MonsterCard;
import eg.edu.guc.yugioh.gui.CardButton;
import eg.edu.guc.yugioh.gui.GUI;
import eg.edu.guc.yugioh.gui.HandPanel;
import eg.edu.guc.yugioh.gui.PlayerPanel;

public class GameController implements ActionListener {
	private GUI gui;
	private Board board;
	private PlayerPanel ActivePlayerPanel;

	public GameController(GUI gui, Board board) {
		this.gui = gui;
		this.board = board;
		gui.getStartPanel().setVisible(false);
		gui.phasePanel();
		 gui.getPhasePanel().getPlayerTurn().setText(board.getActivePlayer().getName());
    	 gui.getPhasePanel().getPhase().setText(board.getActivePlayer().getField().getPhase()+"");
		gui.getPhasePanel().getEndPhase().addActionListener(this);
		gui.getPhasePanel().getEndTurn().addActionListener(this);
		gui.Fix();
	}

	

	public void actionPerformed(ActionEvent e) {
	
     if(e.getSource()==gui.getPhasePanel().getEndPhase()){
    	
    	 if(board.getActivePlayer().getField().getPhase()==Phase.MAIN2){
    		pauseForSwitch p= new pauseForSwitch(gui);
    		p.setVisible(true);
    		
    	 }
    	
    	 board.getActivePlayer().endPhase();
    	 if(Card.getBoard().getWinner()!=null){
 			MessageWindow m =new MessageWindow(gui,Card.getBoard().getWinner().getName().toUpperCase()+" WINS");
 			m.setVisible(true);
 			return;
 		}
    	
    	 gui.getPhasePanel().getPlayerTurn().setText(board.getActivePlayer().getName());
    	 gui.getPhasePanel().getPhase().setText(board.getActivePlayer().getField().getPhase()+"");
    	 gui.RemoveAndFix();}
    
     
     if(e.getSource()==gui.getPhasePanel().getEndTurn()){
    		pauseForSwitch p= new pauseForSwitch(gui);
    		p.setVisible(true);
    	  board.getActivePlayer().endTurn();
    	 if(Card.getBoard().getWinner()!=null){
 			MessageWindow m =new MessageWindow(gui,Card.getBoard().getWinner().getName().toUpperCase()+" WINS");
 			m.setVisible(true);
 			return;
 		}
	   
	     gui.getPhasePanel().getPlayerTurn().setText(board.getActivePlayer().getName());
    	 gui.getPhasePanel().getPhase().setText(board.getActivePlayer().getField().getPhase()+"");
    	 gui.RemoveAndFix();
	     }
     
	}
}
