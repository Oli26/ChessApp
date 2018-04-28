package game.model;

import java.util.ArrayList;
import java.util.List;


public class Set {
	private Color color;
	private List<Piece> pieces;
	
	public Set(Color c){
		color = c;
		loadPieces(color);
	}
	
	public void loadPieces(Color c){
		pieces = new ArrayList<Piece>();
		
		if(c == Color.WHITE){
			for(int i = 0;i<8;i++){
				pieces.add(new Pawn(c,i,1));
			}
			pieces.add(new Horse(c,1,0));
			pieces.add(new Horse(c,6,0));
			pieces.add(new Bishop(c,2,0));
			pieces.add(new Bishop(c,5,0));
			pieces.add(new Rook(c,0,0));
			pieces.add(new Rook(c,7,0));
			pieces.add(new Queen(c,3,0));
			pieces.add(new King(c,4,0));
			
			
			
		}else{
			for(int i = 0;i<8;i++){
				pieces.add(new Pawn(c,i,6));
			}
			pieces.add(new Horse(c,1,7));
			pieces.add(new Horse(c,6,7));
			pieces.add(new Bishop(c,2,7));
			pieces.add(new Bishop(c,5,7));
			pieces.add(new Rook(c,0,7));
			pieces.add(new Rook(c,7,7));
			pieces.add(new Queen(c,3,7));
			pieces.add(new King(c,4,7));
			
			
		}
		
		
		
	}
	public int size(){
		return pieces.size();
	}
	
	public void removePiece(Piece p){
		for(int i=0;i<pieces.size();i++){
			if(pieces.get(i) == p){
				pieces.remove(i);
			}
		}
	}
	
	public void addPiece(Piece p){
		pieces.add(p);
	}
	
	public Piece getPiece(int index){
		return pieces.get(index);
	}
	
	public Piece getKing(){
		for(int i=0;i<pieces.size();i++){
			if(pieces.get(i).getType().equals("King")){
				return pieces.get(i);
			}
		}
		return null;
	}
	
	public Piece checkPawns(){
		for(int i=0;i<pieces.size();i++){
			if(pieces.get(i).getType().equals("Pawn") && (pieces.get(i).getY() == 0 || pieces.get(i).getY()== 7)){
				return pieces.get(i);
			}
		}
		return null;
		
	}
	
}
