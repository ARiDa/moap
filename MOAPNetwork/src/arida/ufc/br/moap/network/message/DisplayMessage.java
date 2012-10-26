/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package arida.ufc.br.moap.network.message;

import javax.swing.JOptionPane;

/**
 *
 * @author igobrilhante
 */
public class DisplayMessage {
    public static synchronized void errorMessage(String message){
        JOptionPane.showMessageDialog(null, message,"Message",JOptionPane.ERROR_MESSAGE);
    }
}
