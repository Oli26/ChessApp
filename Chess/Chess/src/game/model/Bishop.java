package game.model;

public class Bishop extends Piece {
	public Bishop(Color c,int xPos, int yPos){
		super(c,"Bishop",xPos,yPos);
	}
	@Override
	public boolean canMove(int newX,int newY){
		if(Math.abs(newX-x) != Math.abs(newY-y)){
			return false;
		}
		return true;
	}	
}
