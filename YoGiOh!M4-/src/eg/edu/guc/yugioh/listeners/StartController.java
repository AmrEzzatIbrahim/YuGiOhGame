package eg.edu.guc.yugioh.listeners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import eg.edu.guc.yugioh.board.Board;
import eg.edu.guc.yugioh.board.player.Player;
import eg.edu.guc.yugioh.exceptions.UnexpectedFormatException;
import eg.edu.guc.yugioh.gui.GUI;
import eg.edu.guc.yugioh.gui.Player1NameTxt;
import eg.edu.guc.yugioh.gui.Player2NameTxt;

public class StartController implements ActionListener {
	private GUI gui;
	private Board board;
	private String firstName;
	private String SecondName;

	public StartController(GUI gui, Board board) {
		this.gui = gui;
		this.board = board;
		gui.startPanel();
		StartActionListeners();

	}

	private void StartActionListeners() {
		gui.getStartPanel().getPlayerName1().addActionListener(this);
		gui.getStartPanel().getPlayerName2().addActionListener(this);
		gui.getStartPanel().getStartGame().addActionListener(this);

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() instanceof Player1NameTxt)
			if(SecondName!=null&&SecondName.equals(((Player1NameTxt)e.getSource()).getText()))
				firstName = ((Player1NameTxt)e.getSource()).getText()+"*";
				else
					firstName = ((Player1NameTxt)e.getSource()).getText();
					
		else if (e.getSource() instanceof Player2NameTxt){
			
			if(firstName!=null&&firstName.equals(((Player2NameTxt)e.getSource()).getText()))
			SecondName = ((Player2NameTxt)e.getSource()).getText()+"*";
			else
				SecondName = ((Player2NameTxt)e.getSource()).getText();
				}
		else if (e.getSource() instanceof JButton && firstName != null
				&& SecondName != null) {
			try {
				gui.setP1(firstName);
				board.startGame(new Player(firstName), new Player(
						SecondName));
				new GameController(gui, board);

			} catch (IOException | UnexpectedFormatException e1) {
				e1.printStackTrace();
			}
		} else {
			String message = "";
			if (firstName == null)
				message = "please enter first player's name";
			else
				message = "please enter second player's name";

			JOptionPane.showMessageDialog(gui, message);
		}
	}

	public static void main(String[] args) {
		Board x = new Board();
		new StartController(new GUI(x), x);
	}
}
