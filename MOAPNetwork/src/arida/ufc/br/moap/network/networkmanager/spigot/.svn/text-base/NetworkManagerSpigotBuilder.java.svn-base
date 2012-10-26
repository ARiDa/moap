/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package arida.ufc.br.moap.network.networkmanager.spigot;

import org.gephi.io.importer.spi.SpigotImporter;
import org.gephi.io.importer.spi.SpigotImporterBuilder;
import org.openide.util.lookup.ServiceProvider;

/**
 *
 * @author igobrilhante
 */
@ServiceProvider(service = SpigotImporterBuilder.class)
public class NetworkManagerSpigotBuilder implements SpigotImporterBuilder {

    @Override
    public SpigotImporter buildImporter() {
        return new NetworkManagerSpigot();
    }

    @Override
    public String getName() {
        return "Network Manager Spigot";
    }
    
}
