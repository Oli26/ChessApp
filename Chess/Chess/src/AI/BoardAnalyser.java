package AI;

import java.util.List;

import game.model.Board;
import game.model.Color;
import game.model.GameModel;
import game.model.Piece;
import game.model.Set;

public class BoardAnalyser {
	private Board board;
	private Color color;
	private GameModel model;
	public BoardAnalyser(GameModel m, Color c){
		model = m;
		board = model.getBoard();
		color = c;
	}
	
	
	// Broken, allowedMove() doesn't allow a player to move its own unit ontop of its own unit. So it doesn't count defenses at the moment.
	public int[][] getHangingGrid(){
		int[][] grid = new int[8][8];
		

					
			List<Move> moves = model.getMoves(color);
							
			///// CHECK FOR BLOCKS
			for(int i = 0; i< moves.size(); i++){
				if(model.isBlocked(moves.get(i))){
					moves.remove(moves.get(i));
				}
			}
							
							
			///// CHECK FOR PINS
							
							
			for(int n=0;n<moves.size();n++){
					grid[moves.get(n).x2][moves.get(n).y2]++;
								
			}
				
						
			if(color == Color.BLACK){
				moves = model.getMoves(Color.WHITE);
			}else{
				moves = model.getMoves(Color.BLACK);
			}
						
						
			///// CHECK FOR BLOCKS
			for(int i = 0; i< moves.size(); i++){
				if(model.isBlocked(moves.get(i))){
					moves.remove(moves.get(i));
				}
			}		
						
						
			///// CHECK FOR PINS
						
						
			for(int n=0;n<moves.size();n++){
				grid[moves.get(n).x2][moves.get(n).y2]--;
			}
		

		for(int i=0; i<8;i++){
			for(int j=0; j<8;j++){
				System.out.print(grid[j][i] + "  ");
			}
			System.out.println("");
		}
		
		return grid;
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
