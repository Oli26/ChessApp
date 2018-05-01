package game.view;

import java.awt.Color;
import java.awt.Graphics;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JPanel;

import game.controller.GameController;
import game.controller.ViewGameController;
import game.model.GameModel;
import game.model.Piece;

public class GamePanel extends JPanel implements Observer {
	private GameModel model;
	private GameController controller;
	public GamePanel(GameController c,GameModel m) {
		model = m;
		controller = c;
		controller.addObserver(this);
		model.addObserver(this);
        setBackground(new Color(200, 200, 200));
        setVisible(true);
        setOpaque(true);
    }
	public GamePanel(ViewGameController c,GameModel m) {
		model = m;
		c.addObserver(this);
		model.addObserver(this);
        setBackground(new Color(200, 200, 200));
        setVisible(true);
        setOpaque(true);
    }
	
	
	
	  private void paintAreas(Graphics g) {
		  //g.drawString(System.getProperty("user.dir"), 10, 10);
		  
		  int i=0,j =0;
		  Piece[][] pieces = model.getBoard().getPositions();
		  for(Piece[] r : pieces){
			  for(Piece p: r){
				  
				  if((i+j)%2 == 0){
					  g.setColor(Color.WHITE);
				  }else{
					  g.setColor(Color.GRAY);
				  }
				  g.fillRect(i*65, j*65, 60, 60);
				  //g.drawRect(i*65, j*65, 60, 60);
				  g.setColor(Color.BLACK);
				  
				  
				  
				  
				  
				  if(p != null){
					  if(p.isSelected()){
						  g.setColor(Color.RED);
						  g.drawRect(i*65+5, j*65+5, 50, 50);
						  g.setColor(Color.BLACK);
					  }
					  g.drawImage(PieceTextures.getTexture(pieceNameLookup(p)), p.getX()*65, p.getY()*65, 60,60,this); 
					  
					  
					 // g.drawString("p", p.getX()*65+30,p.getY()*65+30);
					  
				  }
				  j++;
				  if(j==8){
					  j=0;
				  }
			  }
			  i++;
			  
		  }
		   

	  }
	 
	  String pieceNameLookup(Piece p){
		  switch(p.getColor()){
		  		case BLACK:
		  			switch(p.getType()){
		  				case "Pawn":
		  					return "BPawn";
		  				case "King":
		  					return "BKing";
		  				case "Queen":
		  					return "BQueen";
		  				case "Bishop":
		  					return "BBishop";
		  				case "Rook":
		  					return "BRook";
		  				case "Horse":
		  					return "BHorse";
		  				}
		  			break;
		  		case WHITE:
		  			switch(p.getType()){
  						case "Pawn":
  							return "WPawn";
  						case "King":
  							return "WKing";
  						case "Queen":
  							return "WQueen";
  						case "Bishop":
  							return "WBishop";
  						case "Rook":
  							return "WRook";
  						case "Horse":
  							return "WHorse";
		  			}
		  			break;
		  
		  }
		  return "BPawn";
	  }
	

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        paintAreas(g);
    }
	
	@Override
	public void update(Observable arg0, Object arg1) {
		// TODO Auto-generated method stub
		repaint();
	}

}
