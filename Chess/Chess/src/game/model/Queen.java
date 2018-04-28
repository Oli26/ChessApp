package game.model;

public class Queen extends Piece {
	public Queen(Color c,int xPos, int yPos){
		super(c,"Queen",xPos,yPos);
	}
	
	
	@Override
	public boolean canMove(int x,int y){
		return true;
	}	
}
