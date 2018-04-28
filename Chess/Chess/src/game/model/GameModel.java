package game.model;

import java.awt.GridLayout;
import java.util.Observable;

import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import AI.AIManager;
import game.controller.AbstractUndoableEdit;
import game.controller.CastleOperation;
import game.controller.MoveOperation;
import game.controller.UndoManager;

public class GameModel extends Observable {
	private Board board;
	private Color move;
	private UndoManager manager;
	private int gameType;
	private AIManager AI;
	public GameModel(UndoManager m, int gameT){
		manager = m;
		board = new Board();
		move = Color.WHITE;
		gameType = gameT;
		if(gameType == 2){
			AI = new AIManager(this);
		}
	}
	
	public void flipMove(){
		if(move == Color.WHITE){
			move = Color.BLACK;
		}else{
			move = Color.WHITE;
		}
	}
	
	public Board getBoard(){
		return board;
	}
	
	
	public void movePiece(int x1,int y1, int x2, int y2){

		if(move == Color.WHITE){
			if(board.isFilled(x1,y1) && board.findPiece(x1, y1).getColor() != Color.WHITE){
				return;
			}
			board.tempMovePiece(x1,y1,x2,y2);
			if(isWhiteInCheck()){
				board.undoTempMovePiece(x1, y1, x2, y2);
				return;
			}else{
					board.undoTempMovePiece(x1, y1, x2, y2);
			}
		}else{
			
			if(board.isFilled(x1,y1) && board.findPiece(x1, y1).getColor() != Color.BLACK){
				return;
			}
			board.tempMovePiece(x1,y1,x2,y2);
			if(isBlackInCheck()){
				board.undoTempMovePiece(x1, y1, x2, y2);
				return;
			}else{
				board.undoTempMovePiece(x1, y1, x2, y2);
			}
				
					
		}	
		
		
		Piece p = board.findPiece(x1,y1);	
		// Castling special case //
		boolean pass;
		if(p.getType().equals("King") && 
		(     (p.getColor() == Color.WHITE)    &&  ((y2==0 && x2 == 2)  ||  (y2==0 && x2==6))      )
		||
		(      (p.getColor() == Color.BLACK)   &&   ((y2==7 && x2 == 2) || (y2==7 && x2==6))       )             
	    ){
			pass = true;
			if(p.getColor() == Color.WHITE &&  p.hasMoved() == false){
				if(x2 == 2){
					if(board.isFilled(0, 0) && board.findPiece(0, 0).getType().equals("Rook") && board.findPiece(0, 0).hasMoved()==false){
						Piece op = board.findPiece(0, 0);
						for(int i =1; i < Math.abs(p.getX()-op.getX()); i++){
								if(board.isFilled(Math.min(p.getX(),op.getX())+i, p.getY())){
										pass = false;
									}
								}
						if(pass == true){
							AbstractUndoableEdit operation = new CastleOperation(board,0,0,3,0,x1,y1,x2,y2);
							manager.newOperation(operation);
							
						}		
								
						}else{
							pass = false;
						}
					
					
				}else{
					if(board.isFilled(7, 0) && board.findPiece(7, 0).getType().equals("Rook") && board.findPiece(7, 0).hasMoved()==false){
						Piece op = board.findPiece(7, 0);
						for(int i =1; i < Math.abs(p.getX()-op.getX()); i++){
								if(board.isFilled(Math.min(p.getX(),op.getX())+i, p.getY())){
										pass = false;
									}
						}
						if(pass == true){
							AbstractUndoableEdit operation = new CastleOperation(board,7,0,5,0,x1,y1,x2,y2);
							manager.newOperation(operation);
							
						}			
								
						}else{
							pass = false;
						}
				}
				
				
			}
			if(p.getColor() == Color.BLACK &&  p.hasMoved() == false){
				if(x2 == 2){
					if(board.isFilled(0, 7) && board.findPiece(0, 7).getType().equals("Rook") && board.findPiece(0, 7).hasMoved()==false){
						Piece op = board.findPiece(0, 7);
						for(int i =1; i < Math.abs(p.getX()-op.getX()); i++){
								if(board.isFilled(Math.min(p.getX(),op.getX())+i, p.getY())){
										pass = false;
									}
						}
						if(pass == true){
							AbstractUndoableEdit operation = new CastleOperation(board,0,7,3,7,x1,y1,x2,y2);
							manager.newOperation(operation);
							
						}		
								
						}else{
							pass = false;
						}
						
					
				}else{
					if(board.isFilled(7, 7) && board.findPiece(7, 7).getType().equals("Rook") && board.findPiece(7, 7).hasMoved()==false){
						Piece op = board.findPiece(7, 7);
						for(int i =1; i < Math.abs(p.getX()-op.getX()); i++){
								if(board.isFilled(Math.min(p.getX(),op.getX())+i, p.getY())){
										pass = false;
									}
								}
						if(pass == true){
							AbstractUndoableEdit operation = new CastleOperation(board,7,7,5,7,x1,y1,x2,y2);
							manager.newOperation(operation);
							
						}			
								
						}else{
							pass = false;
						}
						
				}
				
				
			}
			if(pass == true){
				if(move == Color.WHITE){
					
					move = Color.BLACK;
				}else{
					move = Color.WHITE;
				}
				return;
			}	
			
		}
		
		
			
		if(allowedMove(x1,y1,x2,y2)){

			AbstractUndoableEdit op = new MoveOperation(board,x1,y1,x2,y2);
			manager.newOperation(op);
			manager.resetRedo();
			checkPawns(move);
			if(move == Color.WHITE){
				
				if(gameType==2){
					AI.move();
					move = Color.WHITE;
				}else{
					move = Color.BLACK;
				}
			}else{
				move = Color.WHITE;
			}
			
		}
		
	}
	
	public boolean allowedMove(int x1,int y1,int x2,int y2){
		
		Piece p = board.findPiece(x1,y1);
		//Piece op = board.findPiece(x2, y2);
		

		
		
		
		
		if(!p.canMove(x2,y2)){ //Handles abstract movement only.
			return false;
		}
		if(!p.getType().equals("Horse")){
			if(x1 == x2){
				if(y1>y2){
					for(int n = y2+1;n<y1;n++){
						if(board.isFilled(x1,n)){
							//System.out.println("Can't jump over pieces.");
							return false;
						}
					}
				}else{
					for(int n = y1+1;n<y2;n++){
						if(board.isFilled(x1,n)){
							//System.out.println("Can't jump over pieces.");
							return false;
						}
					}
				}
				
			}
			if(y1 == y2){
				if(x1>x2){
					for(int n = x2+1;n<x1;n++){
						if(board.isFilled(n,y1)){
							//System.out.println("Can't jump over pieces.");
							return false;
						}
					}
				}else{
					for(int n = x1+1;n<x2;n++){
						if(board.isFilled(n,y1)){
							//System.out.println("Can't jump over pieces.");
							return false;
						}
					}
				}
				
			}
			if(x1!=x2 && y1!=y2){
				int xdiff= Math.max(x1,x2)-Math.min(x1, x2);
				int ydiff= Math.max(y1,y2)-Math.min(y1, y2);
				

				if(xdiff != ydiff){
					return false;
				}else{
					
					if(x1>x2){
						if(y1>y2){
							for(int i = 1;i<xdiff;i++){
								if(board.isFilled(x2+i, y2+i)){
									return false;
								}
							}
						}else{
							for(int i = 1;i<xdiff;i++){
								
								if(board.isFilled(x1-i, y1+i)){
									return false;
								}
							}
						}
						
					}else{
						if(y1>y2){
							for(int i = 1;i<xdiff;i++){
								
								if(board.isFilled(x1+i, y1-i)){
									return false;
								}
							}
						}else{
							for(int i = 1;i<xdiff;i++){
								
								if(board.isFilled(x1+i, y1+i)){
									return false;
								}
							}
						}
						
						
					}
					
				
					
				}
					
			}
		}
		
		
		// SAME COLOR
		if(board.isFilled(x2,y2) && p.getColor() == board.findPiece(x2, y2).getColor()){
			//System.out.println("Can't kill same color.");
			return false;
			
		}
		
		
		
		if(p.getType().equals("Pawn")){
			// PAWN FORWARD ATTACK
			if(x1 == x2){
				if(board.isFilled(x2,y2)){
					//System.out.println("Pawns can't kill forward.");
					return false;
				}
			}
			
			// PAWN ATTACK NOTHING
			if(x1 != x2){
				if(!board.isFilled(x2, y2)){
					return false;
				}
				
			}
		}
		
		
		
		
		
		return true;
	}
	
	boolean isBlackInCheck(){
		Set whiteSet = board.getSet(Color.WHITE);
		Set blackSet = board.getSet(Color.BLACK);
		Piece king = blackSet.getKing();
		Piece tempPiece;
		for(int i=0;i<whiteSet.size();i++){
				tempPiece = whiteSet.getPiece(i);
				if(allowedMove(tempPiece.getX(),tempPiece.getY(),king.getX(),king.getY())){
					return true;
				}
		}
		return false;
	}
	
	boolean isWhiteInCheck(){
		Set whiteSet = board.getSet(Color.WHITE);
		Set blackSet = board.getSet(Color.BLACK);
		Piece king = whiteSet.getKing();
		Piece tempPiece;
		for(int i=0;i<blackSet.size();i++){
				tempPiece = blackSet.getPiece(i);
				if(allowedMove(tempPiece.getX(),tempPiece.getY(),king.getX(),king.getY())){
					return true;
				}
		}
		return false;
	}
		
	void checkPawns(Color c){
		JPanel panel = new JPanel(new GridLayout(0,1));
		Piece p = board.getSet(c).checkPawns();
		if(p != null){
			String[] options = new String[4];
			options[0]= "Queen";
			options[1] = "Rook";
			options[2] = "Bishop";
			options[3] = "Horse";
			

			JComboBox<String> box1 = new JComboBox<String>(options);
		    box1.setVisible(true);
			panel.add(new JLabel("Vertex"));
			panel.add(box1);
			
			JOptionPane.showConfirmDialog(null, panel, "Premote your pawn.", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
			String name = box1.getSelectedItem().toString();
			Piece newPiece;
			int tempX = p.getX();
			int tempY = p.getY();
			board.removePiece(p.getX(),p.getY());
			switch(name){
				case "Queen":
							
							newPiece = new Queen(c,tempX,tempY);
							break;
				case "Rook":
							newPiece = new Rook(c,tempX,tempY);
							break;
				case "Horse":
							newPiece = new Horse(c,tempX,tempY);
							break;
				case "Bishop":
							newPiece = new Bishop(c,tempX,tempY);
							break;
				default: 
							newPiece = new Queen(c,tempX,tempY);
							break;
			}
			board.setPiece(newPiece, tempX, tempY);
			board.getSet(c).addPiece(newPiece);
			setChanged();
			notifyObservers();
		}
	}
	
	
	
}
