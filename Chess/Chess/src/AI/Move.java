package AI;

public class Move {
	public int x1,x2,y1,y2;
	public Move(int xOne, int yOne, int xTwo, int yTwo){
		x1 = xOne;
		x2 = xTwo;
		y1 = yOne;
		y2 = yTwo;
	}

	public void printMove(){
		System.out.printf("Move: (%d,%d) -> (%d, %d) \n", x1,y1,x2,y2);
	}
}
