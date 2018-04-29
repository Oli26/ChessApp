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
		//System.out.println(boardAnalyser.rateBoardBasic());
		Move move = generateMove();
		String result =model.movePiece(move.x1, move.y1, move.x2, move.y2);
		if(result.contains("InCheck")){
			inCheckMove(result);
		}
	}
	
	
	public void inCheckMove(String checkMessage){
		System.out.println("This will try to move out of check!");
		String[] split = checkMessage.split(",");
		
		System.out.printf("I am checked by piece (%c,%c)\n", checkMessage.charAt(14), checkMessage.charAt(16));
		
		Move move = takePiece((int)checkMessage.charAt(14), (int)checkMessage.charAt(16));
		
		String test;
		if(move == null){
			System.out.println("Checking saveKing");
			test = saveKing();
			if(test == "Moved"){
				return;
			}
		}
		
		
		// TEST FOR BLOCKING CHECK!
		if(move == null){
			System.out.println("Checking blocking");
			move = blockCheck((int)checkMessage.charAt(14), (int)checkMessage.charAt(16));
		}
		////////////////////////////////////
		
		if(move == null){
			System.out.println("CHECKMATE");
		}
		String result =model.movePiece(move.x1, move.y1, move.x2, move.y2);
		if(result.contains("InCheck")){
			System.out.println("CHECKMATE");
		}
		
	}
	
	
	public Move blockCheck(int x, int y){
		Move move = null;
		// get possible moves
		List<Move> moves = getMoves();
		
		// find king
		Piece p = null;
		for(int i=0; i<8; i++){
			for(int j=0; j<8; j++){	
				if(board.isFilled(i, j) && board.findPiece(i, j).getColor() == AIColor && board.findPiece(i, j).getType() == "King"){
					p = board.findPiece(i, j);
				}
				
			}
		}
		
		// find difference
		int xDiff = Math.abs(x-p.getX());
		int yDiff = Math.abs(y-p.getY());
		
		if(xDiff == yDiff){
			for(int i=1;i<xDiff;i++){
				for(int n=0;n<moves.size();n++){
					if(moves.get(n).x2 == Math.min(x, p.getX()+n) && moves.get(n).y2 == Math.min(y, p.getY()+n)){
						move = moves.get(n);
					}
				}	
			}
		}else if(xDiff == 0){
			for(int i=1;i<yDiff;i++){
				for(int n=0;n<moves.size();n++){
					if(moves.get(n).y2 == Math.min(y, p.getY()+y) && moves.get(n).x2 == x){
						move = moves.get(n);
					}
				}	
			}
		}else if(yDiff == 0){
			for(int i=1;i<xDiff;i++){
				for(int n=0;n<moves.size();n++){
					if(moves.get(n).x2 == Math.min(x, p.getX()+y) && moves.get(n).y2 == y){
						move = moves.get(n);
					}
				}	
			}
		}
		
		
		return move;
	}
	
	
	// This will be called to try and get out of check!
	public Move takePiece(int x, int y){
		List<Move> moves = new ArrayList<Move>();
		List<Move> acceptedMoves = new ArrayList<Move>();
		moves = getMoves();
		for(int i = 0; i < moves.size(); i++){
			if(moves.get(i).x2 == x && moves.get(i).y2 == y){
				acceptedMoves.add(moves.get(i));
			}
		}
		
		
		int bestMoveValue = -1;
		Move bestMove = null;
		for(int i=0;i<acceptedMoves.size();i++){
			if(boardAnalyser.rateMove(acceptedMoves.get(i)) > bestMoveValue){
				bestMove = acceptedMoves.get(i);
				bestMoveValue = boardAnalyser.rateMove(acceptedMoves.get(i));
			}
		}
		
		
		return bestMove;
	}
	
	// Move king out of check!
	public String saveKing(){
		Piece p = null;
		for(int i=0; i<8; i++){
			for(int j=0; j<8; j++){	
				if(board.isFilled(i, j) && board.findPiece(i, j).getColor() == AIColor && board.findPiece(i, j).getType() == "King"){
					p = board.findPiece(i, j);
				}
				
			}
		}
		
		List<Move> moves = new ArrayList<Move>();
		moves.add(new Move(p.getX(), p.getY(), p.getX()+1, p.getY()));
		moves.add(new Move(p.getX(), p.getY(), p.getX()+1, p.getY()+1));
		moves.add(new Move(p.getX(), p.getY(), p.getX()+1, p.getY()-1));
		moves.add(new Move(p.getX(), p.getY(), p.getX(), p.getY()+1));
		moves.add(new Move(p.getX(), p.getY(), p.getX(), p.getY()-1));
		moves.add(new Move(p.getX(), p.getY(), p.getX()-1, p.getY()));
		moves.add(new Move(p.getX(), p.getY(), p.getX()-1, p.getY()+1));
		moves.add(new Move(p.getX(), p.getY(), p.getX()-1, p.getY()-1));
		
		List<Move> acceptedMoves = new ArrayList<Move>();
		for(int i=0;i<moves.size();i++){
			if(onBoard(moves.get(i)) && model.allowedMove(moves.get(i).x1, moves.get(i).y1, moves.get(i).x2, moves.get(i).y2)){
				String result =model.movePiece(moves.get(i).x1, moves.get(i).y1, moves.get(i).x2, moves.get(i).y2);
				if(!result.contains("InCheck")){
					return "Moved";
				}
			}
		}
		return "Failed";
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
			int n = r.nextInt(moves.size()-1)+1;
			while(model.allowedMove(moves.get(n).x1, moves.get(n).y1, moves.get(n).x2, moves.get(n).y2) == false){
				n = r.nextInt(moves.size());
			}
			return moves.get(n);
		}

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
		//System.out.println("Searching for moves for piece (" + p.getX() + "," + p.getY() + ") of type " + p.getType());
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
