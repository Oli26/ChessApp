package AI;

import game.model.Board;
import game.model.Color;
import game.model.Piece;
import game.model.Set;

public class BoardAnalyser {
	private Board board;
	private Color color;
	public BoardAnalyser(Board b, Color c){
		board = b;
		color = c;
	}
	
	public int rateMove(Move m){
		//int value = board.getSet(color).getValue();
		int value;
		Piece tempPiece =board.findPiece(m.x2, m.y2);
		value = Set.valuePiece(tempPiece);
		
		return value;
	}
	
	
	public int rateBoardBasic(){
		Set set = board.getSet(color);
		int valueOfPieces = set.getValue();
		return valueOfPieces;
		
	}
	
	
}
