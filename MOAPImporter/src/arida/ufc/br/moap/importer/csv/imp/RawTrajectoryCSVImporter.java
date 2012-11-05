/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package arida.ufc.br.moap.importer.csv.imp;

import arida.ufc.br.moap.core.beans.AnnotationType;
import arida.ufc.br.moap.core.beans.LatLonPoint;
import arida.ufc.br.moap.core.beans.MovingObject;
import arida.ufc.br.moap.core.beans.Trajectory;
import arida.ufc.br.moap.core.imp.Parameters;
import arida.ufc.br.moap.core.imp.Reporter;
import arida.ufc.br.moap.datamodelapi.spi.ITrajectoryModel;
import arida.ufc.br.moap.importer.spi.ITrajectoryImporter;
import java.io.File;
import java.io.FileFilter;
import java.io.FileReader;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import org.joda.time.DateTime;
import org.supercsv.cellprocessor.Optional;
import org.supercsv.cellprocessor.ParseDate;
import org.supercsv.cellprocessor.ParseDouble;
import org.supercsv.cellprocessor.constraint.NotNull;
import org.supercsv.cellprocessor.ift.CellProcessor;
import org.supercsv.io.CsvListReader;
import org.supercsv.io.ICsvListReader;
import org.supercsv.prefs.CsvPreference;

/**
 *
 * @author igobrilhante
 * 
 * <p>Class to import csv file with raw trajectory into a {@link ITrajectoryDataModel}
 * </P>
 * <p> @param {@link ITrajectoryModel} for inserting the data</p>
 * <p> @param {@link Parameters} to set the file path, for loading a single file, or a directory to load a list of csv files</p>
 */
public class RawTrajectoryCSVImporter implements ITrajectoryImporter {

    /*
     * Necessary Parameters
     */
    public static final String PARAMETER_FILE = "file";
    
    /*
     * Standard Variables
     */
    private final String LATITUDE = "lat";
    private final String LONGITUDE = "lon";
    private final String TIME = "time";
    private final String USERID = "userid";
    private final String TIME_FORMAT = "yyyy-MM-dd'T'HH:mm:ss'Z'";
            
    private String filepath;
    private Map<String, Integer> mandatoryIdx;
    private Map<String, Integer> annotationIdx;
    private ITrajectoryModel trajectoryDataModel;
    private Reporter reporter = new Reporter(RawTrajectoryCSVImporter.class);

    @Override
    public void buildImport(ITrajectoryModel trajectoryDataModel, Parameters parameters) {
//        this.reporter.setReport("Importing Raw Trajectory CSV File");
        this.filepath = (String) parameters.getParam(PARAMETER_FILE);
        File file = new File(this.filepath);
        this.trajectoryDataModel = trajectoryDataModel;
        try {
            if (file.isFile()) {
                
//                this.reporter.setReport("Importing a CSV File");
                readWithCsvListReader(file);
            } else if (file.isDirectory()) {
                System.out.println("dir");
//                this.reporter.setReport("Importing a list of CSV File");
                Queue<File> files = new LinkedList<File>();
                FileFilter filter = new FileFilter() {
                    @Override
                    public boolean accept(File file) {
                        if (!file.getName().endsWith(".csv")) {
                            return false;
                        }
                        return true;
                    }
                };
                inspectDirectory(file, filter, files);
                while(!files.isEmpty()){
                    File selectedFile = files.poll();
                    if(selectedFile.isDirectory()){
                        inspectDirectory(selectedFile, filter, files);
                    }
                    else{
                        readWithCsvListReader(selectedFile);
                    }
                }
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }

    /*
     * Inspect other files inside the directory
     */
    private void inspectDirectory(File file, FileFilter filter, Queue<File> fileSet) {
        System.out.println("F: "+file.getName());
        File[] fileList = file.listFiles(filter);
        for (File f : fileList) {
            System.out.println("f:" + f.getName());
            fileSet.add(f);
        }
    }

    /*
     * Read the file
     */
    private void readWithCsvListReader(File file) throws Exception {

        ICsvListReader listReader = null;
        this.mandatoryIdx = new HashMap<String, Integer>();
        this.annotationIdx = new HashMap<String, Integer>();
        FileReader fileReader = new FileReader(file);
        try {
            listReader = new CsvListReader(fileReader, CsvPreference.STANDARD_PREFERENCE);
            String[] headers = listReader.getHeader(true);
            boolean hasUserId = checkUserId(headers);
            final CellProcessor[] processors = getProcessors(headers);

            if (!hasUserId) {
                readWithNoUserId(listReader, processors);
            } else {
                readWithUserId(listReader, processors);
            }


        } finally {
            if (listReader != null) {
                listReader.close();
            }
        }
    }

    /*
     * File with no user identier. Here it is considered only one trajectory
     */
    private void readWithNoUserId(ICsvListReader listReader, CellProcessor[] processors) throws IOException {
        Integer mo_id = this.trajectoryDataModel.getMovingObjectCount();
        MovingObject movingObject = this.trajectoryDataModel.factory().newMovingObject(mo_id.toString());
        Trajectory<LatLonPoint, DateTime> trajectory = this.trajectoryDataModel.factory().newTrajectory(mo_id+"_0", movingObject);
        List<Object> trajectoryList;
        while ((trajectoryList = listReader.read(processors)) != null) {

            Double latitude = (Double) trajectoryList.get(this.mandatoryIdx.get(LATITUDE));
            Double longitude = (Double) trajectoryList.get(this.mandatoryIdx.get(LONGITUDE));
            Date date = (Date) trajectoryList.get(this.mandatoryIdx.get(TIME));
            /*
             * Time
             */
            DateTime datetime = new DateTime(date);

            /*
             * LatLonPoint
             */
            LatLonPoint point = new LatLonPoint(longitude, latitude);

            /*
             * Add annotation to the point
             */
            for (String annotation : this.annotationIdx.keySet()) {
                point.getAnnotations().addAnnotation(annotation, AnnotationType.STRING, trajectoryList.get(this.annotationIdx.get(annotation)));
            }

            /*
             * Add point to the trajectory
             */
             trajectory.addPoint(point, datetime);



            System.out.println(String.format("lineNo=%s, rowNo=%s, trajectoryList=%s", listReader.getLineNumber(),
                    listReader.getRowNumber(), trajectoryList));
        }
        /*
         * Add trajectory into the TrajectoryModel
         */
        this.trajectoryDataModel.addTrajectory(trajectory);
    }

    /*
     * File with user identifier. This file can contain many users. It is assumed to have a orderered file by userid and time
     */
    private void readWithUserId(ICsvListReader listReader, CellProcessor[] processors) throws IOException {

        List<Object> trajectoryList;
        String previous_userid = "";
        String current_trajectory = "";
        while ((trajectoryList = listReader.read(processors)) != null) {

            String userid = (String) trajectoryList.get(this.mandatoryIdx.get(USERID));
            Double latitude = (Double) trajectoryList.get(this.mandatoryIdx.get(LATITUDE));
            Double longitude = (Double) trajectoryList.get(this.mandatoryIdx.get(LONGITUDE));
            Date date = (Date) trajectoryList.get(this.mandatoryIdx.get(TIME));
            /*
             * Time
             */
            DateTime datetime = new DateTime(date);

            /*
             * LatLonPoint
             */
            LatLonPoint point = new LatLonPoint(longitude, latitude);

            /*
             * Add annotation to the point
             */
            for (String annotation : this.annotationIdx.keySet()) {
                point.getAnnotations().addAnnotation(annotation, AnnotationType.STRING, trajectoryList.get(this.annotationIdx.get(annotation)));
            }

            /*
             * Add point to the trajectory
             */
            if (previous_userid.equalsIgnoreCase(userid)) {
                int i = this.trajectoryDataModel.getTrajectories(userid).size();
                this.trajectoryDataModel.getTrajectory(current_trajectory).addPoint(point, datetime);;
            } /*
             * Create a new moving object
             */ 
            else {
                MovingObject movingObject = this.trajectoryDataModel.factory().newMovingObject(userid);
                Trajectory<LatLonPoint, DateTime> trajectory = this.trajectoryDataModel.factory().newTrajectory(userid+"_0", movingObject);
                current_trajectory = userid+"_0";
                trajectory.addPoint(point, datetime);
                
                this.trajectoryDataModel.addTrajectory(trajectory);
            }
            previous_userid = userid;



            System.out.println(String.format("lineNo=%s, rowNo=%s, trajectoryList=%s", listReader.getLineNumber(),
                    listReader.getRowNumber(), trajectoryList));
        }

    }

    /*
     * Check if there is USERID column
     */
    private boolean checkUserId(String[] headers) {
        for (int i = 0; i < headers.length; i++) {
            if (headers[i].equalsIgnoreCase(USERID)) {
                return true;
            }
        }
        return false;
    }

    /*
     * Processor for the input data
     */
    private CellProcessor[] getProcessors(String[] header) {
        CellProcessor[] processors = new CellProcessor[header.length];

        for (int i = 0; i < header.length; i++) {
            String head = header[i];


            if (head.equalsIgnoreCase(USERID)) {
                this.mandatoryIdx.put(head, i);
                processors[i] = new NotNull();
            } else if (head.equalsIgnoreCase(TIME)) {
                this.mandatoryIdx.put(head, i);
                processors[i] = new NotNull(new ParseDate(TIME_FORMAT));
            } else if (head.equalsIgnoreCase(LATITUDE)) {
                this.mandatoryIdx.put(head, i);
                processors[i] = new NotNull(new ParseDouble());
            } else if (head.equalsIgnoreCase(LONGITUDE)) {
                this.mandatoryIdx.put(head, i);
                processors[i] = new NotNull(new ParseDouble());
            } else {
                this.annotationIdx.put(head, i);
                processors[i] = new Optional();
            }
        }

        return processors;
    }
}
