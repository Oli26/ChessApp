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

		AIColor = Color.BLACK;
		boardAnalyser = new BoardAnalyser(board, AIColor);
	}
	
	
	
	
	
	
	public void move(){
		System.out.println(boardAnalyser.rateBoardBasic());
		Move move = generateMove();
		String result =model.movePiece(move.x1, move.y1, move.x2, move.y2);
		if(result.contains("InCheck")){
			inCheckMove(result);
		}
	}
	
	
	public void inCheckMove(String checkMessage){
		System.out.println("This will try to move out of check!");
		String[] split = checkMessage.split(",");
		
		System.out.printf("I am checked by piece (%c,%c)", checkMessage.charAt(14), checkMessage.charAt(16));
		
		Move move = takePiece((int)checkMessage.charAt(14), (int)checkMessage.charAt(16));
		if(move == null){
			move = saveKing();
			
		}
	}
	
	// This will be called to try and get out of check!
	public Move takePiece(int x, int y){
		List<Move> moves = new ArrayList<Move>();
		Move bestMove = null;
		
		
		return bestMove;
	}
	
	// Move king out of check!
	public Move saveKing(){
		Move bestMove = null;
		
		return bestMove;
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
		
		
		// Find best valued move.
		int bestMoveValue = -1;
		Move bestMove = null;
		for(int n=0; n<moves.size(); n++){
			if(model.allowedMove(moves.get(n).x1, moves.get(n).y1, moves.get(n).x2, moves.get(n).y2) == true){
				if(boardAnalyser.rateMove(moves.get(n)) > bestMoveValue){
					bestMoveValue = boardAnalyser.rateMove(moves.get(n));
					bestMove = moves.get(n);
				}
			}
		}
		
		// If there is no best valued move, do something random!
		if(bestMoveValue == 0){
			Random r = new Random();
			int n = r.nextInt(moves.size())+1;
			while(model.allowedMove(moves.get(n).x1, moves.get(n).y1, moves.get(n).x2, moves.get(n).y2) == false){
				n = r.nextInt(moves.size());
			}
			return moves.get(n);
		}
		
		
		System.out.println("AI Moved!");
		return bestMove;
	}
	
	
	public List<Move> getMoves(){
		List<Move> moves = new ArrayList<Move>();
		for(int i=0; i<8; i++){
			for(int j=0; j<8; j++){	
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
		int n;
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
				n = 1;
				while(p.getX()+n < 7 && p.getY()+n < 7){
					newMove = new Move(p.getX(), p.getY(), p.getX()+n, p.getY()+n);
					moves.add(newMove);
					n++;
				}
				n = 1;
				while(p.getX()+n < 7 && p.getY()-n >= 0){
					newMove = new Move(p.getX(), p.getY(), p.getX()+n, p.getY()-n);
					moves.add(newMove);
					n++;
				}
				n = 1;
				while(p.getX()-n >= 0 && p.getY()+n < 7){
					newMove = new Move(p.getX(), p.getY(), p.getX()-n, p.getY()+n);
					moves.add(newMove);
					n++;
				}
				n = 1;
				while(p.getX()-n >= 0  && p.getY()-n >= 0){
					newMove = new Move(p.getX(), p.getY(), p.getX()-n, p.getY()-n);
					moves.add(newMove);
					n++;
				}
				break;
				
			case "Rook":
				//UpTo 14 moves i think
				n = 1;
				while(p.getX()+n < 7){
					newMove = new Move(p.getX(), p.getY(), p.getX()+n, p.getY());
					moves.add(newMove);
					n++;
				}
				n = 1;
				while(p.getX()-n >= 0){
					newMove = new Move(p.getX(), p.getY(), p.getX()-n, p.getY());
					moves.add(newMove);
					n++;
				}
				n = 1;
				while(p.getY()+n < 7){
					newMove = new Move(p.getX(), p.getY(), p.getX(), p.getY()+n);
					moves.add(newMove);
					n++;
				}
				n = 1;
				while(p.getY()-n >= 0){
					newMove = new Move(p.getX(), p.getY(), p.getX(), p.getY()-n);
					moves.add(newMove);
					n++;
				}
				
				
				break;
				
			case "Queen":
				//UpTo 27 moves i think
				n = 1;
				while(p.getX()+n < 7 && p.getY()+n < 7){
					newMove = new Move(p.getX(), p.getY(), p.getX()+n, p.getY()+n);
					moves.add(newMove);
					n++;
				}
				n = 1;
				while(p.getX()+n < 7 && p.getY()-n >= 0){
					newMove = new Move(p.getX(), p.getY(), p.getX()+n, p.getY()-n);
					moves.add(newMove);
					n++;
				}
				n = 1;
				while(p.getX()-n >= 0 && p.getY()+n < 7){
					newMove = new Move(p.getX(), p.getY(), p.getX()-n, p.getY()+n);
					moves.add(newMove);
					n++;
				}
				n = 1;
				while(p.getX()-n >= 0  && p.getY()-n >= 0){
					newMove = new Move(p.getX(), p.getY(), p.getX()-n, p.getY()-n);
					moves.add(newMove);
					n++;
				}
				while(p.getX()+n < 7){
					newMove = new Move(p.getX(), p.getY(), p.getX()+n, p.getY());
					moves.add(newMove);
					n++;
				}
				n = 1;
				while(p.getX()-n >= 0){
					newMove = new Move(p.getX(), p.getY(), p.getX()-n, p.getY());
					moves.add(newMove);
					n++;
				}
				n = 1;
				while(p.getY()+n < 7){
					newMove = new Move(p.getX(), p.getY(), p.getX(), p.getY()+n);
					moves.add(newMove);
					n++;
				}
				n = 1;
				while(p.getY()-n >= 0){
					newMove = new Move(p.getX(), p.getY(), p.getX(), p.getY()-n);
					moves.add(newMove);
					n++;
				}
				break;
				
			case "King":
				newMove = new Move(p.getX(), p.getY(), p.getX()+1, p.getY());
				if(onBoard(newMove)){
					moves.add(newMove);
				}
				newMove = new Move(p.getX(), p.getY(), p.getX()+1, p.getY()+1);
				if(onBoard(newMove)){
					moves.add(newMove);
				}
				newMove = new Move(p.getX(), p.getY(), p.getX()+1, p.getY()-1);
				if(onBoard(newMove)){
					moves.add(newMove);
				}
				newMove = new Move(p.getX(), p.getY(), p.getX(), p.getY()+1);
				if(onBoard(newMove)){
					moves.add(newMove);
				}
				newMove = new Move(p.getX(), p.getY(), p.getX(), p.getY()-1);
				if(onBoard(newMove)){
					moves.add(newMove);
				}
				newMove = new Move(p.getX(), p.getY(), p.getX()-1, p.getY());
				if(onBoard(newMove)){
					moves.add(newMove);
				}
				newMove = new Move(p.getX(), p.getY(), p.getX()-1, p.getY()+1);
				if(onBoard(newMove)){
					moves.add(newMove);
				}
				newMove = new Move(p.getX(), p.getY(), p.getX()-1, p.getY()-1);
				if(onBoard(newMove)){
					moves.add(newMove);
				}
				//UpTo 8 moves
				break;
		
		}
		
		
		//System.out.println("Number of moves added for this piece = " + moves.size());
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
