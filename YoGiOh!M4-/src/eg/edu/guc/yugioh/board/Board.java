package eg.edu.guc.yugioh.board;

import java.io.IOException;

import eg.edu.guc.yugioh.board.player.Player;
import eg.edu.guc.yugioh.cards.Card;

public class Board {

	private boolean firstTurn;
	private Player activePlayer;
	private Player opponentPlayer;
	private Player winner;
	
	public Board() {
		firstTurn=true;
		Card.setBoard(this);
	}
	public Player getActivePlayer() {
		return activePlayer;
	}

	public void setActivePlayer(Player activePlayer) {
		this.activePlayer = activePlayer;
	}

	public Player getOpponentPlayer() {
		return opponentPlayer;
	}

	public void setOpponentPlayer(Player opponentPlayer) {
		this.opponentPlayer = opponentPlayer;
	}

	public Player getWinner() {
		return winner;
	}

	public void setWinner(Player winner) {
		this.winner = winner;
	}

	
	public void whoStarts(Player p1, Player p2){
	int x=(int)Math.floor(Math.random()*2);
	if(x==0){
		activePlayer=p1;
		opponentPlayer=p2;}
	else{
		activePlayer=p2;
		opponentPlayer=p1;
		
	}
	
	}
	
	public void startGame(Player p1, Player p2){
		p1.getField().addNCardsToHand(5);
		p2.getField().addNCardsToHand(5);
		whoStarts(p1,p2);
		activePlayer.getField().addCardToHand();
	}
	
	public void nextPlayer(){
		firstTurn=false;
		if(Card.getBoard().getActivePlayer().getField().getDeck().getDeck().isEmpty()){
			Card.getBoard().setWinner(Card.getBoard().getOpponentPlayer());
		}
		Player temp;
		temp=activePlayer;
		activePlayer=opponentPlayer;
		opponentPlayer=temp;
		activePlayer.getField().addCardToHand();
		
	}
	public boolean isFirstTurn() {
		return firstTurn;
	}
	

}
