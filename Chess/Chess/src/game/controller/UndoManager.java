package game.controller;

import java.util.Stack;

import game.controller.AbstractUndoableEdit;

public class UndoManager {

	private Stack<AbstractUndoableEdit> undo;
	private Stack<AbstractUndoableEdit> redo;
	
	public UndoManager() {
		undo = new Stack<AbstractUndoableEdit>();
		redo = new Stack<AbstractUndoableEdit>();
	}
	
	public void reset(){
		while(undo.size() != 0){
			undo.pop();
		}
		while(redo.size() != 0){
			redo.pop();
		}
	}
	
	public void resetRedo(){
		while(redo.size() != 0){
			redo.pop();
		}
	}
	
	public void newOperation(AbstractUndoableEdit operation) {
		undo.push(operation);		
	}
	
	public void undo() {
		if(undo.empty()== true)
			return;
		AbstractUndoableEdit operation = undo.pop();
		operation.undo();
		redo.push(operation);
	}
	
	public void redo() {
		if(redo.empty() == true)
			return;
		AbstractUndoableEdit operation = redo.pop();
		operation.redo();
		undo.push(operation);
	}
	
}
