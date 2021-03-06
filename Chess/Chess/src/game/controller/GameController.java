package game.controller;

import java.awt.event.ActionEvent;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Observable;
import java.util.Observer;

import javax.swing.AbstractAction;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.KeyStroke;

import game.model.GameModel;
import game.view.GamePanel;

public class GameController extends Observable implements Observer{
	private JFrame frame;
	private GamePanel panel;
	private GameModel model;
	private MouseInput mouse;
	private UndoManager manager;
	private int gameType;
	public GameController(int gameT){
		gameType = gameT;
		manager = new UndoManager();
		model = new GameModel(manager, gameT);
		frame = new JFrame();
		
		panel = new GamePanel(this,model);
		mouse = new MouseInput(this,model,panel);
		
		frame.getContentPane().add(panel);
        frame.pack();
        
        JMenuBar menuBar = new JMenuBar();
        frame.setJMenuBar(menuBar);
        
        
        JMenu gameMenu = new JMenu("game");
        AbstractAction undoAction = new AbstractAction("Undo"){
			@Override
			public void actionPerformed(ActionEvent arg0) {
				undo();
				
			}
        };
        JMenuItem undoItem = new JMenuItem(undoAction);
        undoItem.setIcon(null);
 	   	undoItem.setAccelerator(KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_Z, java.awt.Event.CTRL_MASK));
 	   
        gameMenu.add(undoItem);
        
        AbstractAction redoAction = new AbstractAction("Redo"){
    			@Override
    			public void actionPerformed(ActionEvent arg0) {
    				redo();
    				
    			}
        };
        JMenuItem redoItem = new JMenuItem(redoAction);
        undoItem.setIcon(null);
        redoItem.setAccelerator(KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_X, java.awt.Event.CTRL_MASK));
        gameMenu.add(redoItem);
                  
        
        AbstractAction getMovesAction = new AbstractAction("Save Game"){
			@Override
			public void actionPerformed(ActionEvent arg0) {
				saveMoves();
			}
	    };
	    JMenuItem getMovesItem = new JMenuItem(getMovesAction);
	    undoItem.setIcon(null);
	    redoItem.setAccelerator(KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_X, java.awt.Event.CTRL_MASK));
	    gameMenu.add(getMovesItem);
	              
	        
        
        menuBar.add(gameMenu);
		
		
		
		frame.setSize(535, 580);
		frame.setVisible(true);
		
		
		setChanged();
        notifyObservers();
	}
	
	
	public void saveMoves(){
		String path = JOptionPane.showInputDialog("Enter a path");
		
		String output = manager.getMoves();
		System.out.print(output);
		
		String fileName;
		if(path.contains(".txt")){
			fileName = path;
		}else{
			fileName = path+".txt";
		}
		
		BufferedWriter bw = null;
		FileWriter fw = null;

		try {

			

			fw = new FileWriter("Saves\\" + fileName);
			bw = new BufferedWriter(fw);
			bw.write(output);


		} catch (IOException e) {

			e.printStackTrace();

		} finally {

			try {

				if (bw != null)
					bw.close();

				if (fw != null)
					fw.close();

			} catch (IOException ex) {

				ex.printStackTrace();

			}

		}
	}
	
	
	
	public void undo(){
		manager.undo();
		if(gameType == 1){
			model.flipMove();
		}
		
		
		updateCustom();
		
	}
	public void redo(){
		manager.redo();
		if(gameType == 1){
			model.flipMove();
		}
		updateCustom();
	}
	
	public void updateCustom(){
		setChanged();
		notifyObservers();
	}
	
	@Override
	public void update(Observable o, Object arg) {
		// TODO Auto-generated method stub

	}
	
}
