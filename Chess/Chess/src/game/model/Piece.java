package game.model;

public class Piece {
	protected Color color;
	private String type;
	protected int x;
	protected int y;
	protected boolean hasMoved;
	protected boolean selected;
	public Piece(Color c, String t,int x,int y){
		color = c;
		type = t;
		this.x = x;
		this.y = y;
		hasMoved = false;
		selected = false;
		
	}
	
	public boolean canMove(int newX, int newY){
		return true;
		
	}
	
	public void setMoved(boolean t){
		hasMoved = t;
	}
	
	public boolean hasMoved(){
		return hasMoved;
		
	}
	public Color getColor(){
		return color;
	}
	
	public String getType(){
		return type;
		
	}
	
	public int getX(){
		return x;
	}
	
	public int getY(){
		return y;
	}
	
	public void setX(int n){
		x= n;
	}
	public void setY(int n){
		y= n;
	}
	
	public boolean isSelected(){
		return selected;
	}
	public void toggleSelected(){
		if(selected){
			selected = false;
		}else{
			selected = true;
		}
	}
}
