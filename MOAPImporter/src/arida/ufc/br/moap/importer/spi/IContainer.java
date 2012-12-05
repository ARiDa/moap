
package arida.ufc.br.moap.importer.spi;

import arida.ufc.br.moap.core.imp.Reporter;

/**
 * A container is created each time data are imported by <b>importers</b>. Its role is to host all data
 * collected by importers during import process. After pushing data in the container, its content can be
 * analysed to verify its validity and then be processed by <b>processors</b>. Thus containers are
 * <b>loaded</b> by importers and <b>unloaded</b> by processors.
 * <p>
 * 
 * 
 * @author franzejr
 * @see IDataImporter
 * 
 */
public interface IContainer {

    /**
     * Set the source of the data put in the container. Could be a file name.
     *
     * @param source the original source of data.
     * @throws NullPointerException if <code>source</code> is <code>null</code>
     */
    public void setSource(String source);

    /**
     * If exists, returns the source of the data.
     *
     * @return the source of the data, or <code>null</code> if source is not
     * defined.
     */
    public String getSource();

    /**
     * Get containers loading interface. The <b>loader</b> is used by modules
     * which put data in the container, whereas the <b>unloader</b> interface is
     * used by modules which read containers content.
     *
     * @return the containers loading interface
     */
    public IContainerLoader getLoader();

    /**
     * Get containers unloading interface. The <b>unloader</b> interface is used
     * by modules which read containers content, whereas the <b>loader</b> is
     * used for pushing data in the container.
     *
     * @return the container unloading interface
     */
    public IContainerUnloader getUnloader();

    /**
     * Set a report this container can use to report issues detected when
     * loading the container. Report are used to log info and issues during
     * import process. Only one report can be associated to a container.
     *
     * @param report set <code>report</code> as the default report for this
     * container
     * @throws NullPointerException if <code>report</code> is <code>null</code>
     */
    public void setReport(Reporter report);

    /**
     * Returns the report associated to this container, if exists.
     *
     * @return the report set for this container or <code>null</code> if no
     * report is defined
     */
    public Reporter getReport();

    /**
     * This method must be called after the loading is complete and before
     * unloading. Its aim is to verify data consistency as a whole.
     *
     * @return <code>true</code> if container data is
     * consistent, <code>false</code> otherwise
     */
    public boolean verify();

    /**
     * Close the current loading and clean content before unloading.
     */
    public void closeLoader();
}
