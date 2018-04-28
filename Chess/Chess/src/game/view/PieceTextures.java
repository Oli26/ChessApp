package game.view;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;

import javax.imageio.ImageIO;

public class PieceTextures {
	private static HashMap<String,BufferedImage> textures;
	static String pathToResources = "Pictures\\";
	
	
	static {
		textures = new HashMap<String,BufferedImage>();
		BufferedImage texture = null;
		try {
			System.out.println("Working Directory = " +
		              System.getProperty("user.dir") + pathToResources + "KingBlack.png");
			System.out.println("C:\\Users\\Oli26\\Desktop\\Coding\\Code\\Java\\Chess\\Chess\\src\\Resources");
            File imgFile = new File(pathToResources+"KingBlack.png");
            texture = ImageIO.read(imgFile);
        } catch (Exception e) {
        	System.err.println(e.getMessage());
            System.err.println("Could not load"); 
        }
        textures.put("BKing", texture);
        
        
        try {
            File imgFile = new File(pathToResources+"KingWhite.png");
            texture = ImageIO.read(imgFile);
        } catch (IOException ioe) {
            System.err.println("Could not load"); 
        }
        textures.put("WKing", texture);
        
        
        try {
            File imgFile = new File(pathToResources+"BishopBlack.png");
            texture = ImageIO.read(imgFile);
        } catch (IOException ioe) {
            System.err.println("Could not load"); 
        }
        textures.put("BBishop", texture);
		
        try {
            File imgFile = new File(pathToResources+"BishopWhite.png");
            texture = ImageIO.read(imgFile);
        } catch (IOException ioe) {
            System.err.println("Could not load"); 
        }
        textures.put("WBishop", texture);
		
        try {
            File imgFile = new File(pathToResources+"PawnBlack.png");
            texture = ImageIO.read(imgFile);
        } catch (IOException ioe) {
            System.err.println("Could not load"); 
        }
        textures.put("BPawn", texture);
		
        try {
            File imgFile = new File(pathToResources+"PawnWhite.png");
            texture = ImageIO.read(imgFile);
        } catch (IOException ioe) {
            System.err.println("Could not load"); 
        }
        textures.put("WPawn", texture);
		
        
        try {
            File imgFile = new File(pathToResources+"QueenBlack.png");
            texture = ImageIO.read(imgFile);
        } catch (IOException ioe) {
            System.err.println("Could not load"); 
        }
        textures.put("BQueen", texture);
		
        try {
            File imgFile = new File(pathToResources+"QueenWhite.png");
            texture = ImageIO.read(imgFile);
        } catch (IOException ioe) {
            System.err.println("Could not load"); 
        }
        textures.put("WQueen", texture);
		
        
        try {
            File imgFile = new File(pathToResources+"HorseBlack.png");
            texture = ImageIO.read(imgFile);
        } catch (IOException ioe) {
            System.err.println("Could not load"); 
        }
        textures.put("BHorse", texture);
        
        try {
            File imgFile = new File(pathToResources+"HorseWhite.png");
            texture = ImageIO.read(imgFile);
        } catch (IOException ioe) {
            System.err.println("Could not load"); 
        }
        textures.put("WHorse", texture);
		
        try {
            File imgFile = new File(pathToResources+"RookBlack.png");
            texture = ImageIO.read(imgFile);
        } catch (IOException ioe) {
            System.err.println("Could not load"); 
        }
        textures.put("BRook", texture);
		
        try {
            File imgFile = new File(pathToResources+"RookWhite.png");
            texture = ImageIO.read(imgFile);
        } catch (IOException ioe) {
            System.err.println("Could not load"); 
            ioe.printStackTrace();
        }
        textures.put("WRook", texture);
		
		
        
		
		
		
								
	}
	
	
	public static BufferedImage getTexture(String piece) {
        return textures.get(piece);
    }
	
}
