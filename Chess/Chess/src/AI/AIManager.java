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
		boardAnalyser = new BoardAnalyser(model, AIColor);
	}
	
	
	
	
	
	
	public void move(){
		boardAnalyser.getHangingGrid();
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
		List<Move> moves = model.getMoves(AIColor);
		
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
		moves = model.getMoves(AIColor);
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
		List<Move> moves = model.getMoves(AIColor);  // this will display all possible moves from all possible moves for player 
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
