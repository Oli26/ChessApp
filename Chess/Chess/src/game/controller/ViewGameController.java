package game.controller;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.util.Observable;
import java.util.Observer;

import javax.swing.AbstractAction;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import game.model.GameModel;
import game.view.GamePanel;

public class ViewGameController extends Observable implements Observer{
	
	private JFrame frame;
	private GamePanel panelGame;
	private JPanel panelButtons;
	private GameModel model;
	private UndoManager manager;
	private String[] loadedMoves;
	private int currentMove;
	public ViewGameController(String loadedGame){
		
		
		
		loadedMoves = loadedGame.split("\n");
		manager = new UndoManager();
		model = new GameModel(manager, 1);
		frame = new JFrame();
		currentMove = 0;
		
		
		
		panelButtons = new JPanel();
		panelButtons.setBorder(new EmptyBorder(5,5,5,5));
		
		//////////Forward button ///////////////////////////
		AbstractAction forwardAction = new AbstractAction(){

			@Override
			public void actionPerformed(ActionEvent arg0) {
				int x1,x2,y1,y2;
				
				try{
					x1 = Integer.parseInt(loadedMoves[currentMove].charAt(1) + "");
					x2 = Integer.parseInt(loadedMoves[currentMove].charAt(8) + "");
					y1 = Integer.parseInt(loadedMoves[currentMove].charAt(3) + "");
					y2 = Integer.parseInt(loadedMoves[currentMove].charAt(10) + "");
					model.movePiece(x1, y1, x2, y2);
					currentMove++;
					updateCustom();
				}catch(Exception e){
					System.out.println("Move is not an integer!");
				}
				
				
			}

			
		};
		
		JButton forwardButton = new JButton(forwardAction);
		forwardButton.setText("Next move");
		panelButtons.add(forwardButton);

		//////////Forward button ///////////////////////////
		AbstractAction backwardAction = new AbstractAction(){

			@Override
			public void actionPerformed(ActionEvent arg0) {
				if(currentMove !=0){
					currentMove--;
				}
				manager.undo();
				model.flipMove();
				updateCustom();
			}

			
		};
		
		JButton backwardButton = new JButton(backwardAction);
		backwardButton.setText("Previous move");
		panelButtons.add(backwardButton);

		
		frame.add(panelButtons, BorderLayout.NORTH);
		
		
		////// Create Game panel /////
		panelGame = new GamePanel(this,model);
		frame.add(panelGame, BorderLayout.CENTER);
		/////////////////////////////////////
		

        frame.pack();
        
        
		
		frame.setSize(535, 600);
		frame.setVisible(true);
		
		
		setChanged();
        notifyObservers();
	}
	
	
	public void updateCustom(){
		setChanged();
		notifyObservers();
	}
	
	@Override
	public void update(Observable o, Object arg) {
		// TODO Auto-generated method stub

	}
	
}
