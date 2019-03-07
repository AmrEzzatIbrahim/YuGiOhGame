package eg.edu.guc.yugioh.listeners;

import eg.edu.guc.yugioh.board.Board;
import eg.edu.guc.yugioh.gui.GUI;

public class Main {
	
public Main(){
	Board b=new Board();
	GUI gui=new GUI(b);
	new StartController(gui, b);
}
public static void main(String[] args) {
	Main m=new Main();
}
}
