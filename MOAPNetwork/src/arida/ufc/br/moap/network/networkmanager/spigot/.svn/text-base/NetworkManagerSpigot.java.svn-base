/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package arida.ufc.br.moap.network.networkmanager.spigot;

import org.gephi.io.importer.api.ContainerLoader;
import org.gephi.io.importer.api.Report;
import org.gephi.io.importer.spi.SpigotImporter;
import org.gephi.utils.longtask.spi.LongTask;
import org.gephi.utils.progress.ProgressTicket;

/**
 *
 * @author igobrilhante
 */
public class NetworkManagerSpigot implements SpigotImporter,LongTask {

    private ContainerLoader container;
    private Report report;
    private ProgressTicket progressTicket;
    private boolean cancel = false;
 
    @Override
    public boolean execute(ContainerLoader loader) {
        this.container = loader;
        this.report = new Report();
        //Import done here
        return !cancel;
    }
    @Override
    public ContainerLoader getContainer() {
        return container;
    }
    @Override
    public Report getReport() {
        return report;
    }
    @Override
    public boolean cancel() {
        cancel = true;
        return true;
    }
    @Override
    public void setProgressTicket(ProgressTicket progressTicket) {
        this.progressTicket = progressTicket;
    }
    
}
