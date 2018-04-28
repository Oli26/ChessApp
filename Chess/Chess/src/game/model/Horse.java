package game.model;

public class Horse extends Piece {

	public Horse(Color c,int xPos, int yPos){
		super(c,"Horse",xPos,yPos);
	}
	
	@Override
	public boolean canMove(int newX,int newY){
		float xf = x-newX;
		float yf = y-newY;
		float distance = (float)Math.sqrt(xf*xf+yf*yf);
		if(distance != (float)Math.sqrt(5)){
			return false;
			
		}else{
			return true;
		}
	}
	
}
