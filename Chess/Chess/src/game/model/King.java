package game.model;

public class King extends Piece {
	public King(Color c,int xPos, int yPos){
		super(c,"King",xPos,yPos);
	}
	
	
	@Override
	public boolean canMove(int newX,int newY){
		int xMoved = Math.max(x,newX) - Math.min(x,newX);
		int yMoved = Math.max(y,newY) - Math.min(y,newY);
		if(xMoved == 1 && yMoved == 1){
			return true;
		}
		if(xMoved == 1 && yMoved == 0){
			return true;
		}
		if(xMoved == 0 && yMoved == 1){
			return true;
		}
		
		return false;
	}
}
