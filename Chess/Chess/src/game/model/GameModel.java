package game.model;

import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;

import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import AI.AIManager;
import AI.Move;
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
	
	
	public String movePiece(int x1,int y1, int x2, int y2){
		if(move == Color.WHITE){
			System.out.println("Turn = White");
			if(board.isFilled(x1,y1) && board.findPiece(x1, y1).getColor() != Color.WHITE){
				return "IncorrectColor";
			}
			board.tempMovePiece(x1,y1,x2,y2);
			Piece checkPiece = isWhiteInCheck();
			if(!(checkPiece == null)){
				board.undoTempMovePiece(x1, y1, x2, y2);
				if(!checkPiece.isSelected())
					checkPiece.toggleSelected();
				return ("InCheck from ("+ checkPiece.getX()+ ","+checkPiece.getY() + ")");
			}else{
				board.undoTempMovePiece(x1, y1, x2, y2);
			}
		}else{
			System.out.println("Turn = Black");
			if(board.isFilled(x1,y1) && board.findPiece(x1, y1).getColor() != Color.BLACK){
				return "IncorrectColor";
			}
			board.tempMovePiece(x1,y1,x2,y2);
			Piece checkPiece = isBlackInCheck();
			if(!(checkPiece == null)){
				board.undoTempMovePiece(x1, y1, x2, y2);
				if(!checkPiece.isSelected())
					checkPiece.toggleSelected();
				return ("InCheck from ("+ checkPiece.getX()+ ","+checkPiece.getY() + ")");
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
				return "Castled!";
			}	
			
		}
		
			
		if(allowedMove(x1,y1,x2,y2)){
			System.out.println("Move requested and allowed! x1 = " + x1 + ", y1 = " + y1 + ", x2 = " + x2 + ", y2 = " + y2);
			AbstractUndoableEdit op = new MoveOperation(board,x1,y1,x2,y2);
			manager.newOperation(op);
			manager.resetRedo();
			checkPawns(move);
			if(move == Color.WHITE){
				move = Color.BLACK;
				if(gameType==2){
					AI.move();
				}
			}else{
				move = Color.WHITE;
			}
			
		}else{
			System.out.println("Move requested and not allowed!");
		}
		return "Moved";
		
	}
	
	public boolean allowedMove(int x1,int y1,int x2,int y2){
		//System.out.println("Called with (" + x1 + "," + y1 + "-(" + x2+ "," + y2 + ")");
		if(x1 < 0 || x1 > 7){
			return false;
		}
		if(y1 < 0 || y1 > 7){
			return false;
		}
		if(x2 < 0 || x2 > 7){
			return false;
		}
		if(y2 < 0 || y2 > 7){
			return false;
		}
		Piece p = board.findPiece(x1,y1);
		if(p == null){
			return false;
		}
		
		
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
	
	Piece isBlackInCheck(){
		Set whiteSet = board.getSet(Color.WHITE);
		Set blackSet = board.getSet(Color.BLACK);
		Piece king = blackSet.getKing();
		Piece tempPiece;
		for(int i=0;i<whiteSet.size();i++){
				tempPiece = whiteSet.getPiece(i);
				if(allowedMove(tempPiece.getX(),tempPiece.getY(),king.getX(),king.getY())){
					return tempPiece;
				}
		}
		return null;
	}
	
	Piece isWhiteInCheck(){
		Set whiteSet = board.getSet(Color.WHITE);
		Set blackSet = board.getSet(Color.BLACK);
		Piece king = whiteSet.getKing();
		Piece tempPiece;
		for(int i=0;i<blackSet.size();i++){
				tempPiece = blackSet.getPiece(i);
				if(allowedMove(tempPiece.getX(),tempPiece.getY(),king.getX(),king.getY())){
					return tempPiece;
				}
		}
		return null;
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
			panel.add(new JLabel("Select promotion"));
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
	
	public List<Move> getMoves(Color color){
		List<Move> moves = new ArrayList<Move>();
		for(int i=0; i<8; i++){
			for(int j=0; j<8; j++){	
				if(board.isFilled(i, j) && board.findPiece(i, j).getColor() == color){
					moves.addAll(getMovesForPiece(board.findPiece(i,j), color));
				}
				
			}
		}
		return moves;
	}
	
	// This will return possible moves in the abstract, but will not account for the positioning of the board or checking etc.
	public List<Move> getMovesForPiece(Piece p, Color color){
		List<Move> moves = new ArrayList<Move>();		
		Move newMove;
		int n;
		switch(p.getType()){
			case "Pawn":
				
				// UpTo 4 moves (6 including en pessant)
				
				
				// If is hasn't moved it can move two forward.
				if(p.hasMoved() == false){
					if(color == Color.BLACK){
						newMove = new Move(p.getX(), p.getY(), p.getX(), p.getY()-2);
					}else{
						newMove = new Move(p.getX(), p.getY(), p.getX(), p.getY()+2);
					}
					moves.add(newMove);
				}	
				// Move one forward
				if(color == Color.BLACK){
					newMove = new Move(p.getX(), p.getY(), p.getX(), p.getY()-1);
				}else{
					newMove = new Move(p.getX(), p.getY(), p.getX(), p.getY()+1);
				}
				if(onBoard(newMove)){
					moves.add(newMove);
				}
				
				
				// Attack left
				if(color == Color.BLACK){
					newMove = new Move(p.getX(), p.getY(), p.getX()+1, p.getY()-1);
					if(onBoard(newMove)){
						moves.add(newMove);
					}
					newMove = new Move(p.getX(), p.getY(), p.getX()-1, p.getY()-1);
					if(onBoard(newMove)){
						moves.add(newMove);
					}
				}else{
					newMove = new Move(p.getX(), p.getY(), p.getX()+1, p.getY()+1);
					if(onBoard(newMove)){
						moves.add(newMove);
					}
					newMove = new Move(p.getX(), p.getY(), p.getX()-1, p.getY()+1);
					if(onBoard(newMove)){
						moves.add(newMove);
					}
					
				}
					
				
				

				break;
			case "Horse":
				//UpTo 8 moves in a circle around
				newMove = new Move(p.getX(), p.getY(), p.getX()+1, p.getY()+2);
				if(onBoard(newMove)){
					moves.add(newMove);
				}
				newMove = new Move(p.getX(), p.getY(), p.getX()-1, p.getY()+2);
				if(onBoard(newMove)){
					moves.add(newMove);
				}
				newMove = new Move(p.getX(), p.getY(), p.getX()+1, p.getY()-2);
				if(onBoard(newMove)){
					moves.add(newMove);
				}
				newMove = new Move(p.getX(), p.getY(), p.getX()-1, p.getY()-2);
				if(onBoard(newMove)){
					moves.add(newMove);
				}
				newMove = new Move(p.getX(), p.getY(), p.getX()+2, p.getY()+1);
				if(onBoard(newMove)){
					moves.add(newMove);
				}
				newMove = new Move(p.getX(), p.getY(), p.getX()-2, p.getY()+1);
				if(onBoard(newMove)){
					moves.add(newMove);
				}
				newMove = new Move(p.getX(), p.getY(), p.getX()+2, p.getY()-1);
				if(onBoard(newMove)){
					moves.add(newMove);
				}
				newMove = new Move(p.getX(), p.getY(), p.getX()-2, p.getY()-1);
				if(onBoard(newMove)){
					moves.add(newMove);
				}
				
				
				
				break;
				
			case "Bishop":
				//UpTo 13 moves i think
				n = 1;
				while(p.getX()+n < 7 && p.getY()+n < 7){
					newMove = new Move(p.getX(), p.getY(), p.getX()+n, p.getY()+n);
					moves.add(newMove);
					n++;
				}
				n = 1;
				while(p.getX()+n < 7 && p.getY()-n >= 0){
					newMove = new Move(p.getX(), p.getY(), p.getX()+n, p.getY()-n);
					moves.add(newMove);
					n++;
				}
				n = 1;
				while(p.getX()-n >= 0 && p.getY()+n < 7){
					newMove = new Move(p.getX(), p.getY(), p.getX()-n, p.getY()+n);
					moves.add(newMove);
					n++;
				}
				n = 1;
				while(p.getX()-n >= 0  && p.getY()-n >= 0){
					newMove = new Move(p.getX(), p.getY(), p.getX()-n, p.getY()-n);
					moves.add(newMove);
					n++;
				}
				break;
				
			case "Rook":
				//UpTo 14 moves i think
				n = 1;
				while(p.getX()+n < 7){
					newMove = new Move(p.getX(), p.getY(), p.getX()+n, p.getY());
					moves.add(newMove);
					n++;
				}
				n = 1;
				while(p.getX()-n >= 0){
					newMove = new Move(p.getX(), p.getY(), p.getX()-n, p.getY());
					moves.add(newMove);
					n++;
				}
				n = 1;
				while(p.getY()+n < 7){
					newMove = new Move(p.getX(), p.getY(), p.getX(), p.getY()+n);
					moves.add(newMove);
					n++;
				}
				n = 1;
				while(p.getY()-n >= 0){
					newMove = new Move(p.getX(), p.getY(), p.getX(), p.getY()-n);
					moves.add(newMove);
					n++;
				}
				
				
				break;
				
			case "Queen":
				//UpTo 27 moves i think
				n = 1;
				while(p.getX()+n < 7 && p.getY()+n < 7){
					newMove = new Move(p.getX(), p.getY(), p.getX()+n, p.getY()+n);
					moves.add(newMove);
					n++;
				}
				n = 1;
				while(p.getX()+n < 7 && p.getY()-n >= 0){
					newMove = new Move(p.getX(), p.getY(), p.getX()+n, p.getY()-n);
					moves.add(newMove);
					n++;
				}
				n = 1;
				while(p.getX()-n >= 0 && p.getY()+n < 7){
					newMove = new Move(p.getX(), p.getY(), p.getX()-n, p.getY()+n);
					moves.add(newMove);
					n++;
				}
				n = 1;
				while(p.getX()-n >= 0  && p.getY()-n >= 0){
					newMove = new Move(p.getX(), p.getY(), p.getX()-n, p.getY()-n);
					moves.add(newMove);
					n++;
				}
				while(p.getX()+n < 7){
					newMove = new Move(p.getX(), p.getY(), p.getX()+n, p.getY());
					moves.add(newMove);
					n++;
				}
				n = 1;
				while(p.getX()-n >= 0){
					newMove = new Move(p.getX(), p.getY(), p.getX()-n, p.getY());
					moves.add(newMove);
					n++;
				}
				n = 1;
				while(p.getY()+n < 7){
					newMove = new Move(p.getX(), p.getY(), p.getX(), p.getY()+n);
					moves.add(newMove);
					n++;
				}
				n = 1;
				while(p.getY()-n >= 0){
					newMove = new Move(p.getX(), p.getY(), p.getX(), p.getY()-n);
					moves.add(newMove);
					n++;
				}
				break;
				
			case "King":
				newMove = new Move(p.getX(), p.getY(), p.getX()+1, p.getY());
				if(onBoard(newMove)){
					moves.add(newMove);
				}
				newMove = new Move(p.getX(), p.getY(), p.getX()+1, p.getY()+1);
				if(onBoard(newMove)){
					moves.add(newMove);
				}
				newMove = new Move(p.getX(), p.getY(), p.getX()+1, p.getY()-1);
				if(onBoard(newMove)){
					moves.add(newMove);
				}
				newMove = new Move(p.getX(), p.getY(), p.getX(), p.getY()+1);
				if(onBoard(newMove)){
					moves.add(newMove);
				}
				newMove = new Move(p.getX(), p.getY(), p.getX(), p.getY()-1);
				if(onBoard(newMove)){
					moves.add(newMove);
				}
				newMove = new Move(p.getX(), p.getY(), p.getX()-1, p.getY());
				if(onBoard(newMove)){
					moves.add(newMove);
				}
				newMove = new Move(p.getX(), p.getY(), p.getX()-1, p.getY()+1);
				if(onBoard(newMove)){
					moves.add(newMove);
				}
				newMove = new Move(p.getX(), p.getY(), p.getX()-1, p.getY()-1);
				if(onBoard(newMove)){
					moves.add(newMove);
				}
				//UpTo 8 moves
				break;
		
		}
		
		
		//System.out.println("Number of moves added for this piece = " + moves.size());
		return moves;
		
	}
	
	public boolean onBoard(Move m){
		if(m.x2 < 0 || m.x2 > 7){
			return false;
		}
		if(m.y2 < 0 || m.y2 > 7){
			return false;
		}
		return true;
	}
	
	
	public boolean isBlocked(Move m){
		int xDiff = Math.abs(m.x1 - m.x2);
		int yDiff = Math.abs(m.y1 - m.y2);
		
		if(xDiff == yDiff){
			for(int i = 1; i<xDiff; i++){
				if(board.isFilled(Math.min(m.x2, m.x1)+i, Math.min(m.y1, m.y2)+i)){
					return true;
				}
			}
		}
		
		if(xDiff == 0){
			for(int i = 1; i<yDiff; i++){
				if(board.isFilled(m.x1, Math.min(m.y1, m.y2)+i)){
					return true;
				}
			}
			
		}
		if(yDiff == 0){
			for(int i = 1; i<xDiff; i++){
				if(board.isFilled(Math.min(m.x2, m.x1)+i, m.y1)){
					return true;
				}
			}
		}
		
		return false;
	}
	
	
}
