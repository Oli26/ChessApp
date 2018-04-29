package AI;

import java.util.ArrayList;
import java.util.List;

import game.model.Color;
import game.model.Board;
import game.model.GameModel;
import game.model.Piece;

public class AIManager {
	private Color AIColor;
	private GameModel model;
	private Board board;
	private BoardAnalyser boardAnalyser;
	
	public AIManager(GameModel m){
		model = m;
		board = model.getBoard();
		boardAnalyser = new BoardAnalyser(board);
		AIColor = Color.BLACK;
	}
	
	
	
	
	
	
	public void move(){
		
		
	}
	
	public void generateMove(){
		
	}
	
	
	public void getMoves(){
		//Piece[][] allSquares = board.getPositions();
		List<Move> moves = new ArrayList<Move>();
		for(int i=0; i<9; i++){
			for(int j=0; j<9; j++){
				if(board.isFilled(i, j) && board.findPiece(i, j).getColor() == AIColor){
					moves.addAll(getMovesForPiece(board.findPiece(i,j)));
				}
				
			}
		}
	}
	
	public List<Move> getMovesForPiece(Piece p){
		List<Move> moves = new ArrayList<Move>();
		
		switch(p.getType()){
			case "Pawn":
				
				break;
			case "Horse":
				
				break;
				
			case "Bishop":
				
				break;
				
			case "Rook":
				
				break;
				
			case "Queen":
				
				break;
				
			case "King":
				
				break;
		
		}
		
		
		
		return moves;
		
	}
	
	
	
	
	
	
}
