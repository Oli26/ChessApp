package game.controller;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

public class UpdateAction extends AbstractAction {
	private GameController controller;
	public UpdateAction(GameController c){
		super("Update");
		controller = c;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		controller.updateCustom();

	}

}
