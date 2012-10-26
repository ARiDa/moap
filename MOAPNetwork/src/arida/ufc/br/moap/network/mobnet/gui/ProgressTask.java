/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package arida.ufc.br.moap.network.mobnet.gui;

import javax.swing.JButton;
import javax.swing.JProgressBar;
import javax.swing.SwingWorker;

/**
 *
 * @author igobrilhante
 */
public class ProgressTask extends SwingWorker<Object, Object> {
    private JProgressBar bar;
    private JButton button;
    
    public ProgressTask(JProgressBar bar,JButton button){
        this.bar = bar;
    }   
    @Override
    protected Object doInBackground() throws Exception {
        bar.setEnabled(true);
        bar.setIndeterminate(true);
        
        button.setEnabled(false);
        
        return null;
        
    }
    
    @Override
    protected void done(){
        bar.setEnabled(false);
        bar.setIndeterminate(false);
        
        button.setEnabled(true);
    }
    
}
