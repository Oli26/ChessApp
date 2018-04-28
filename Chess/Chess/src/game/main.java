package game;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import game.controller.GameController;

public class main {
	private JFrame frame;
	private JPanel panel;
	private GridLayout layout;
	private JButton play;
	private JButton playAI;
	public static void main(String[] args){
		main menu = new main();
	}
	
	
	public main(){
		frame = new JFrame();
		panel = new JPanel();

		
		JLabel titleLabel = new JLabel("Oli's Chess App");
		titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
		frame.add(titleLabel);
		
		
		
		AbstractAction playAction = new AbstractAction(){

			@Override
			public void actionPerformed(ActionEvent arg0) {
				GameController newGame = new GameController(1);
				
			}

			
		};
		
		play = new JButton(playAction);
		play.setText("Play");
		frame.add(play);

		AbstractAction playAIAction = new AbstractAction(){

			@Override
			public void actionPerformed(ActionEvent arg0) {
				GameController newGame = new GameController(2);
				
			}

			
		};
		
		playAI = new JButton(playAIAction);
		playAI.setText("Play computer");
		frame.add(playAI);
				
		frame.setSize(200, 300);
		layout = new GridLayout(3,1);
		panel.setLayout(layout);
		frame.setLayout(layout);
		frame.setVisible(true);
	}
	
}
