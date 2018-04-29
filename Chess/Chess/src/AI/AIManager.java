package AI;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.xml.ws.Service.Mode;

import game.model.Board;
import game.model.Color;
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
		Move move = generateMove();
		model.movePiece(move.x1, move.y1, move.x2, move.y2);
		
	}
	
	
	// Will return a move once finished!
	public Move generateMove(){
		List<Move> moves = getMoves();  // this will display all possible moves from all possible moves for player 
		// ValueMoves() //  this will use BoardAnalyser to evaluate a position and a move
		// PickBestMove() //  this will pick the best value returned
		// return bestMove
		
		if(moves.size() == 0){
			System.out.println("No moves generated");
			return null;
		}
		
		Random r = new Random();
		int n = r.nextInt(moves.size()-1)+1;
		System.out.println("Selected move number: " + n);
		moves.get(n).printMove();
		while(model.allowedMove(moves.get(n).x1, moves.get(n).y1, moves.get(n).x2, moves.get(n).y2) == false){
			System.out.println("Move denied");
			n = r.nextInt(moves.size());
			System.out.println("Selected move number: " + n);
			moves.get(n).printMove();
		}
		
		
		System.out.println("AI Moved!");
		return moves.get(n);
	}
	
	
	public List<Move> getMoves(){
		//Piece[][] allSquares = board.getPositions();
		List<Move> moves = new ArrayList<Move>();
		for(int i=0; i<8; i++){
			//System.out.println("Searching x pos = " + i);
			for(int j=0; j<8; j++){
				//System.out.println("Searching y pos = " + j);
				//System.out.println("Filled = " + board.isFilled(i, j));
				//if(board.isFilled(i, j)){
				//	System.out.println("Piece color =" +board.findPiece(i, j).getColor() );
				////	System.out.println("AI color =" +AIColor );
				//	System.out.println("Color is correct = " + (board.findPiece(i, j).getColor() == AIColor));	
				//}
				//			
				if(board.isFilled(i, j) && board.findPiece(i, j).getColor() == AIColor){
					moves.addAll(getMovesForPiece(board.findPiece(i,j)));
				}
				
			}
		}
		return moves;
	}
	
	// This will return possible moves in the abstract, but will not account for the positioning of the board or checking etc.
	public List<Move> getMovesForPiece(Piece p){
		List<Move> moves = new ArrayList<Move>();
		System.out.println("Searching for moves for piece (" + p.getX() + "," + p.getY() + ") of type " + p.getType());
		// About 86 moves max. Meaning we have evaluate 86 positions!
		Move newMove;
		switch(p.getType()){
			case "Pawn":
				
				// UpTo 4 moves (6 including en pessant)
				
				
				// If is hasn't moved it can move two forward.
				if(p.hasMoved() == false){
					if(AIColor == Color.BLACK){
						newMove = new Move(p.getX(), p.getY(), p.getX(), p.getY()-2);
					}else{
						newMove = new Move(p.getX(), p.getY(), p.getX(), p.getY()+2);
					}
					moves.add(newMove);
				}	
				// Move one forward
				if(AIColor == Color.BLACK){
					newMove = new Move(p.getX(), p.getY(), p.getX(), p.getY()-1);
				}else{
					newMove = new Move(p.getX(), p.getY(), p.getX(), p.getY()+1);
				}
				if(onBoard(newMove)){
					moves.add(newMove);
				}
				
				
				// Attack left
				if(AIColor == Color.BLACK){
					newMove = new Move(p.getX(), p.getY(), p.getX()+1, p.getY()-1);
					if(onBoard(newMove)){
						moves.add(newMove);
					}
					newMove = new Move(p.getX(), p.getY(), p.getX()-1, p.getY()-1);
					if(onBoard(newMove)){
						moves.add(newMove);
					}
				}else{
					newMove = new Move(p.getX(), p.getY(), p.getX()+1, p.getY()+1);
					if(onBoard(newMove)){
						moves.add(newMove);
					}
					newMove = new Move(p.getX(), p.getY(), p.getX()-1, p.getY()+1);
					if(onBoard(newMove)){
						moves.add(newMove);
					}
					
				}
					
				
				

				break;
			case "Horse":
				//UpTo 8 moves in a circle around
				newMove = new Move(p.getX(), p.getY(), p.getX()+1, p.getY()+2);
				if(onBoard(newMove)){
					moves.add(newMove);
				}
				newMove = new Move(p.getX(), p.getY(), p.getX()-1, p.getY()+2);
				if(onBoard(newMove)){
					moves.add(newMove);
				}
				newMove = new Move(p.getX(), p.getY(), p.getX()+1, p.getY()-2);
				if(onBoard(newMove)){
					moves.add(newMove);
				}
				newMove = new Move(p.getX(), p.getY(), p.getX()-1, p.getY()-2);
				if(onBoard(newMove)){
					moves.add(newMove);
				}
				newMove = new Move(p.getX(), p.getY(), p.getX()+2, p.getY()+1);
				if(onBoard(newMove)){
					moves.add(newMove);
				}
				newMove = new Move(p.getX(), p.getY(), p.getX()-2, p.getY()+1);
				if(onBoard(newMove)){
					moves.add(newMove);
				}
				newMove = new Move(p.getX(), p.getY(), p.getX()+2, p.getY()-1);
				if(onBoard(newMove)){
					moves.add(newMove);
				}
				newMove = new Move(p.getX(), p.getY(), p.getX()-2, p.getY()-1);
				if(onBoard(newMove)){
					moves.add(newMove);
				}
				
				
				
				break;
				
			case "Bishop":
				//UpTo 13 moves i think
				break;
				
			case "Rook":
				//UpTo 14 moves i think
				break;
				
			case "Queen":
				//UpTo 27 moves i think
				break;
				
			case "King":
				//UpTo 8 moves
				break;
		
		}
		
		
		System.out.println("Number of moves added for this piece = " + moves.size());
		return moves;
		
	}
	
	
	public boolean onBoard(Move m){
		if(m.x2 < 0 || m.x2 > 7){
			return false;
		}
		if(m.y2 < 0 || m.y2 > 7){
			return false;
		}
		return true;
	}
	
	
	
	
}
