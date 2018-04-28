package game.controller;




import game.model.Board;


public class CastleOperation extends AbstractUndoableEdit {
	private Board board;
	private int x1,x2,x3,x4;
	private int y1,y2,y3,y4;
	
	
	public CastleOperation(Board b, int x1, int y1,int x2,int y2,int x3,int y3,int x4,int y4){
		board = b;
		this.x1 = x1;
		this.y1 = y1;
		this.x2 = x2;
		this.y2 = y2;
		this.x3 = x3;
		this.y3 = y3;
		this.x4 = x4;
		this.y4 = y4;
		board.movePiece(x1, y1, x2, y2);
		board.movePiece(x3, y3, x4, y4);
		board.findPiece(x2, y2).setMoved(true);
		board.findPiece(x4, y4).setMoved(true);
	}
	
	
	
	
	
	
	public void undo(){
		board.movePiece(x2, y2, x1, y1);
		board.movePiece(x4, y4, x3, y3);
		board.findPiece(x1, y1).setMoved(false);
		board.findPiece(x3, y3).setMoved(false);
		
	}
    
	
	public void redo(){
		board.movePiece(x1, y1, x2, y2);
		board.movePiece(x3, y3, x4, y4);
		board.findPiece(x2, y2).setMoved(true);
		board.findPiece(x4, y4).setMoved(true);
	}
	
	
	
	
	
	
	

}
