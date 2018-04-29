package game.controller;

import java.awt.event.MouseEvent;

import javax.swing.event.MouseInputAdapter;

import game.model.GameModel;
import game.view.GamePanel;

public class MouseInput extends MouseInputAdapter {
	private GameController controller;
	private GameModel model;
	private GamePanel panel;
	private int xOldSquare;
	private int yOldSquare;
	public MouseInput(GameController c,GameModel m, GamePanel p){
		model = m;
		panel =p;
		controller = c;
		panel.addMouseListener(this);
		panel.addMouseMotionListener(this);
	}

	
	@Override
	public void mousePressed(MouseEvent event) {
			xOldSquare = event.getX()/65;
			yOldSquare = event.getY()/65;
			model.getBoard().findPiece(xOldSquare, yOldSquare).toggleSelected();
			controller.updateCustom();
	}
	@Override
	public void mouseDragged(MouseEvent event) {
	        
	}
		
	@Override
	public void mouseReleased(MouseEvent event) {
	        int xSquare = event.getX()/65;
	        int ySquare = event.getY()/65;
	        //System.out.println(xSquare + "," + ySquare);
	        if(-1<xSquare && xSquare<8 && ySquare>-1 && ySquare<8 && !(xOldSquare==xSquare && yOldSquare==ySquare)){
	        	model.getBoard().findPiece(xOldSquare, yOldSquare).toggleSelected();
	        	model.movePiece(xOldSquare,yOldSquare,xSquare,ySquare);
	        	controller.updateCustom();
	        }
	}
		
}
