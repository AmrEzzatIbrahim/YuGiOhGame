package eg.edu.guc.yugioh.listeners;

import java.awt.Dialog;
import java.awt.FlowLayout;
import java.awt.Frame;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuItem;

public class MonsterInFeildInAttackActionWindow extends JDialog{
    private JLabel lable;
    private JMenu MonsterActions;
    private JMenuItem AttackMonster;
    private JMenuItem AttackOpponent;
    private JMenuItem ChangeMode;
	public MonsterInFeildInAttackActionWindow(JFrame frame) {
		super(frame,"Attacking Monster waiting for your command !", true);
		setLayout(null);
		setBounds(500, 300, 500, 200);
		lable=new JLabel("Please select an Action");
	    add(lable);
	    lable.setBounds(150, 0, 200, 20);
	    MonsterActions=new JMenu("Monster Actions");
	    AttackMonster=new JMenuItem("Attack Monster");
	    AttackOpponent=new JMenuItem("Attack Opponent");
	    ChangeMode=new JMenuItem("Change Mode");
	    MonsterActions.add(AttackMonster);
	    MonsterActions.add(AttackOpponent);
	    MonsterActions.add(ChangeMode);
	    add(MonsterActions);
	    MonsterActions.setBounds(190, 100, 120, 20);
		this.validate();
	}

}
