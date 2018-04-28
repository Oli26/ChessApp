package game.model;

public class Rook extends Piece {
	public Rook(Color c,int xPos, int yPos){
		super(c,"Rook",xPos,yPos);
	}
	
	@Override
	public boolean canMove(int newX,int newY){
		if(Math.abs(newX-x) != 0 && Math.abs(newY-y) !=0 ){
			return false;
		}
		return true;
	}	
}
