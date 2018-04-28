package game.model;

public class Pawn extends Piece {

	public Pawn(Color c,int xPos, int yPos){
		super(c,"Pawn",xPos,yPos);

	}
	
	@Override
	public boolean canMove(int newX,int newY){
		int allowedAmount;
		
		if(hasMoved == false){
			allowedAmount = 2;

		}else{
			allowedAmount = 1;
		}
		
		if(color == Color.WHITE){
			if(newY-y > 0){
				
				// MOVE
				
				if(newY-y < allowedAmount+1){

				}else{
					return false;
				}
				
				// ATTACK.
				if(newX != x){
					if(Math.abs(newX-x) ==1){
						return true;
					}else{
						return false;
					}
					
				}
				
				
			}else{
				return false;
			}
		}else{
			if(newY-y < 0){
				// MOVE
				if(y-newY < allowedAmount+1){
					
				}else{
					return false;
				}
				
				// ATTACK.
				if(newX != x){
					if(Math.abs(newX-x) ==1){
						return true;
					}else{
						return false;
					}
					
				}	
			}else{
				return false;
			}
		}
		return true;
	}	
}
