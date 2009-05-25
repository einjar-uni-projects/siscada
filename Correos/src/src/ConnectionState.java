/*
 * 
 * 
 */
 /* Universidad Carlos III
 * Campus de Leganes
 * Ingenieria Informatica
 * ASIGNATURA: SS.II.
*/
/**
 *
 * @author Miguel Degayón Cortés, 100033447
 */
public class ConnectionState {

    private boolean _state;

	public ConnectionState(boolean estado){
		_state = estado;
	}

	public synchronized void  setState(boolean state){
		_state = state;
	}

	public synchronized boolean  getState(){
 		return _state;
	}

}
