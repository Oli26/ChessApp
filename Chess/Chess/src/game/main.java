package game;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import javax.swing.AbstractAction;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import game.controller.GameController;
import game.controller.ViewGameController;

public class main {
	private JFrame frame;
	private JPanel panel;
	private GridLayout layout;
	private JButton play;
	private JButton playAI;
	private JButton loadGame;
	public static void main(String[] args){
		main menu = new main();
	}
	
	
	public main(){
		frame = new JFrame();
		panel = new JPanel();

		
		JLabel titleLabel = new JLabel("Oli's Chess App");
		titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
		frame.add(titleLabel);
		
		
		////////// Play button ///////////////////////////
		AbstractAction playAction = new AbstractAction(){

			@Override
			public void actionPerformed(ActionEvent arg0) {
				GameController newGame = new GameController(1);
				
			}

			
		};
		
		play = new JButton(playAction);
		play.setText("Play");
		frame.add(play);

		///////// Play Computer button ///////////////////
		AbstractAction playAIAction = new AbstractAction(){

			@Override
			public void actionPerformed(ActionEvent arg0) {
				GameController newGame = new GameController(2);
				
			}

			
		};
		
		playAI = new JButton(playAIAction);
		playAI.setText("Play computer");
		frame.add(playAI);
				
		
		///////// Load game Button /////////////////////////
		AbstractAction loadGameAction = new AbstractAction(){

			@Override
			public void actionPerformed(ActionEvent arg0) {
				String loadedGame = "";
				String fileName;
				String dir = System.getProperty("user.dir") + "\\Saves";
				
				
				File folder = new File(dir);
				List<String> names = listFileNamesForFolder(folder);
				
				String[] options = new String[names.size()];
				for(int i=0; i<names.size();i++){
					options[i] = names.get(i);
				}
				
				
				JComboBox<String> box1 = new JComboBox<String>(options);
			    box1.setVisible(true);
				panel.add(new JLabel("Select Game"));
				panel.add(box1);
				
				JOptionPane.showConfirmDialog(null, panel, "Select a game", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
				String name = "Saves\\" + box1.getSelectedItem().toString();
				
				System.out.println(name);
				Charset cs = Charset.forName("UTF-8");;
				try{
					loadedGame = readFile(name,cs);
				}catch(IOException e){
					System.out.println("Error loading file");
					JOptionPane.showMessageDialog(frame,"Error loading file.");
				}catch(Exception e2){
					JOptionPane.showMessageDialog(frame,"Failed to load game from loaded string");
				}
				
				System.out.println(loadedGame);
				ViewGameController controller = new ViewGameController(loadedGame);
				
			}

			
		};
		
		loadGame = new JButton(loadGameAction);
		loadGame.setText("Load Game");
		frame.add(loadGame);
		
		
		
		// Finish loading GUI /////
		frame.setSize(200, 300);
		layout = new GridLayout(4,1);
		panel.setLayout(layout);
		frame.setLayout(layout);
		frame.setVisible(true);
	}
	
	static String readFile(String path, Charset encoding) 
			  throws IOException 
			{
			  byte[] encoded = Files.readAllBytes(Paths.get(path));
			  return new String(encoded, encoding);
			}
	
	public List<String> listFileNamesForFolder(File folder) {
		List<String> names = new ArrayList<String>();
	    for (final File fileEntry : folder.listFiles()) {
	        if (fileEntry.isDirectory()) {
	            names.addAll(listFileNamesForFolder(fileEntry));
	        } else {
	            names.add(fileEntry.getName());
	        }
	    }
	    return names;
	}
	
	
	
}
