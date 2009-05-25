/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
 /* Universidad Carlos III
 * Campus de Leganes
 * Ingenieria Informatica
 * ASIGNATURA: SSII
*/
/**
 *
 * @author Miguel Degayón Cortés, 100033447
 */
public class State {
    
    private boolean _state;

	public State(boolean estado){
		_state = estado;
	}

	public synchronized void  setState(boolean state){
		_state = state;
	}

	public synchronized boolean  getState(){
 		return _state;
	}
}


