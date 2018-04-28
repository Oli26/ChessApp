package game.controller;

import game.model.Board;
import game.model.Piece;
import game.model.Set;

public class MoveOperation extends AbstractUndoableEdit {
	private Piece fromPiece;
	private Piece toPiece;
	private Board board;
	private int oldX;
	private int oldY;
	private int newX;
	private int newY;
	
	
	
	public MoveOperation(Board b,int x1,int y1,int x2,int y2){
		board = b;
		fromPiece = board.findPiece(x1, y1);
		toPiece = board.findPiece(x2, y2);
		oldX = x1;
		oldY = y1;
		newX = x2;
		newY = y2;
		fromPiece.setMoved(true);
		board.movePiece(x1, y1, x2, y2);
		
	}
	
	
	
	public void undo(){
		board.movePiece(newX, newY, oldX, oldY);
		fromPiece.setMoved(false);
		if(toPiece != null){
			Set set = board.getSet(toPiece.getColor());
			set.addPiece(toPiece);
			board.setPiece(toPiece, newX, newY);
			
		}
	}
    
	
	public void redo(){
		fromPiece.setMoved(true);
		board.movePiece(oldX, oldY, newX, newY);
		
		
	}
}


