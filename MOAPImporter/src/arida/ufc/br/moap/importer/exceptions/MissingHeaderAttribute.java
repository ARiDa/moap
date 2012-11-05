/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package arida.ufc.br.moap.importer.exceptions;

/**
 *
 * @author igobrilhante
 */
public class MissingHeaderAttribute extends RuntimeException {
    
    public MissingHeaderAttribute(){
        super();
    }
    
    public MissingHeaderAttribute(String message){
        super(message);
    }
    
    public MissingHeaderAttribute(String message,Throwable cause){
        super(message,cause);
    }
    
    public MissingHeaderAttribute(Throwable cause){
        super(cause);
    }
    
}
