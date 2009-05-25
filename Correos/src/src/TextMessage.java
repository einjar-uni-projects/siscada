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
public class TextMessage implements MessageInterface{

    String _text;

    public TextMessage(String msg){
        _text = new String(msg);
    }

    @Override
    public String toString(){
        return _text;
    }

}
