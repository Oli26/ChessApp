package game.model;

public class Board {
	private Piece[][]  pieces;
	private Color[][] squares;
	private Set whiteSet;
	private Set blackSet;
	
	private Piece tempPiece;
	
	public Board(){
		 pieces= new Piece[8][8];
		 squares = new Color[8][8];
		 whiteSet = new Set(Color.WHITE);
		 blackSet = new Set(Color.BLACK);
		 initialiseBoard();
	}
	
	private void initialiseBoard(){
		initialiseBlack();
		initialiseWhite();
		initialiseColor();

	}
	
	
	private void initialiseColor(){
		for(int i = 0;i<8;i++){
			for(int j=0;j<8;j++){
				squares[i][j] = Color.BLACK;
			}
		}
	}
	private void initialiseWhite(){
		for(int x = 0; x<8;x++){
				pieces[x][1] = whiteSet.getPiece(x);
				
		}
		pieces[0][0] = whiteSet.getPiece(12); //first castle
		pieces[7][0] = whiteSet.getPiece(13); // second castle
		pieces[1][0] = whiteSet.getPiece(8);  // first horse
		pieces[6][0] = whiteSet.getPiece(9);  // second horse
		pieces[2][0] = whiteSet.getPiece(10); // first bishop
		pieces[5][0] = whiteSet.getPiece(11); // second bishop
		pieces[4][0] = whiteSet.getPiece(14); // queen
		pieces[3][0] = whiteSet.getPiece(15); // king 
		
	}
	
	private void initialiseBlack(){
		for(int x = 0; x<8;x++){
			pieces[x][6] = blackSet.getPiece(x);
			
		}
		pieces[0][7] = blackSet.getPiece(12); //first castle
		pieces[7][7] = blackSet.getPiece(13); // second castle
		pieces[1][7] = blackSet.getPiece(8);  // first horse
		pieces[6][7] = blackSet.getPiece(9);  // second horse
		pieces[2][7] = blackSet.getPiece(10); // first bishop
		pieces[5][7] = blackSet.getPiece(11); // second bishop
		pieces[4][7] = blackSet.getPiece(14); // queen
		pieces[3][7] = blackSet.getPiece(15); // king 
	}
	
	public Set getSet(Color c){
		if(c == Color.WHITE){
			return whiteSet;
		}else{
			return blackSet;
		}
		
	}
	
	
	public Piece[][] getPositions(){
		return pieces;
	}
	
	public Color[][] getColors(){
		return squares;
	}
	
	public void setPiece(Piece p,int x,int y){
		pieces[x][y] = p;
		
	}
	
	public void movePiece(int x1,int y1, int x2, int y2){
		if(isFilled(x2,y2)){
			removePiece(x2,y2);
		}
		System.out.println("moved");
		pieces[x1][y1].setX(x2);
		pieces[x1][y1].setY(y2);
		pieces[x2][y2] = pieces[x1][y1];
		pieces[x1][y1] = null;
		
		
	}
	
	public void removePiece(int x,int y){
		if(pieces[x][y].getColor() == Color.WHITE){
			whiteSet.removePiece(pieces[x][y]);
			
		}else{
			blackSet.removePiece(pieces[x][y]);
		}
		
	}
	
	public void tempMovePiece(int x1,int y1,int x2,int y2){
		tempPiece = pieces[x2][y2];
		pieces[x1][y1].setX(x2);
		pieces[x1][y1].setY(y2);
		pieces[x2][y2] = pieces[x1][y1];
		pieces[x1][y1] = null;
	}
	
	public void undoTempMovePiece(int x1, int y1,int x2,int y2){
		pieces[x2][y2].setX(x1);
		pieces[x2][y2].setY(y1);
		pieces[x1][y1]=pieces[x2][y2];
		pieces[x2][y2]=tempPiece;
		tempPiece = null;
	}
	
	
	public Piece findPiece(int x, int y){
		if(isFilled(x,y)){
			return pieces[x][y];
		}else{
			return null;
		}
		
		
	}
	
	public boolean isFilled(int x, int y){
		// Don't allow moves outside of board
		if(x < 0 || x > 7){
			return true;
		}
		if(y < 0 || y > 7){
			return true;
		}
		
		
		// test to see if filled
		if(pieces[x][y] == null){
			return false;
		}
		return true;
	}
	
	public Color whatColor(int x, int y){
		if(isFilled(x,y)){
			return pieces[x][y].getColor();
		}
		return Color.WHITE;
		
	}
}
