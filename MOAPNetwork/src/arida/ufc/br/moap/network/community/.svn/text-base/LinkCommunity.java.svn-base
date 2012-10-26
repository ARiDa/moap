/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package arida.ufc.br.moap.network.community;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import org.apache.log4j.Logger;

/**
 *
 * @author igobrilhante
 */
public class LinkCommunity {
    private Logger logger = Logger.getLogger(LinkCommunity.class);
    public void execute(String network) throws IOException {
        logger.info("Link Community Execution");
        try {
            
            logger.info(System.getProperty("user.dir"));
            String[] commands = new String[]{"python",
                                             "./lib/python/link_clustering_weighted.py",
                                             "-n" + network};
            Process p = Runtime.getRuntime().exec(commands);


            BufferedReader stdInput = new BufferedReader(new InputStreamReader(p.getInputStream()));



            BufferedReader stdError = new BufferedReader(new InputStreamReader(p.getErrorStream()));



            // read the output
            String s;
            while ((s = stdInput.readLine()) != null) {

                logger.info(s);

            }



            // read any errors

            while ((s = stdError.readLine()) != null) {

                logger.info(s);

            }



//            System.exit(0);

        }
        catch (IOException e) {

            e.printStackTrace();

        }

    }
}
