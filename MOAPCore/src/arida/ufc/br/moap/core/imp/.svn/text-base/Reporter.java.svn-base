package arida.ufc.br.moap.core.imp;

import arida.ufc.br.moap.core.imp.Reporter.ReporterLevel;
import java.util.ArrayList;
import java.util.List;
import org.apache.log4j.Logger;

/**
 * A Reporter represents the algorithm state and to check the pseudocode.
 * @author franzejr
 */
public class Reporter {

    enum ReporterLevel {

        INFO, ERROR
    }
    private List<String> reports;
    private final Logger logger;

    public Reporter(Class c) {
        this.logger = Logger.getLogger(c);
        this.reports = new ArrayList<String>();
    }

    public String getAllReports() {
        return this.toString();
    }

    /**
     * @return the report
     */
    public String getReport() {
        return reports.get(reports.size() - 1);
    }

    /**
     * @param report the report to set
     */
    public void setReport(String report) {
        logger.info(report);
        reports.add(report);

    }

    public void setReport(String report, ReporterLevel level) {
        if (level.equals(ReporterLevel.INFO)) {
            logger.info(report);
        }
        if (level.equals(ReporterLevel.ERROR)) {
            logger.error(report);
        }

        reports.add(report);

    }

    @Override
    public String toString() {
        //TODO
        return super.toString();
    }
}
