///*
// * To change this template, choose Tools | Templates
// * and open the template in the editor.
// */
//package arida.ufc.br.moap.importer.csv.imp;
//
//import arida.ufc.br.moap.core.beans.AnnotationType;
//import arida.ufc.br.moap.core.beans.LatLonPoint;
//import arida.ufc.br.moap.core.beans.Trajectory;
//import arida.ufc.br.moap.core.imp.Parameters;
//import arida.ufc.br.moap.datamodelapi.spi.ITrajectoryModel;
//import arida.ufc.br.moap.importer.spi.ITrajectoryImporter;
//import java.io.File;
//import java.util.List;
//import org.casaca.gpx4j.core.data.GpxDocument;
//import org.casaca.gpx4j.core.data.Track;
//import org.casaca.gpx4j.core.data.TrackSegment;
//import org.casaca.gpx4j.core.data.Waypoint;
//import org.casaca.gpx4j.core.driver.GpxDriver;
//import org.casaca.gpx4j.core.driver.IGpxReader;
//import org.casaca.gpx4j.core.exception.GpxFileNotFoundException;
//import org.casaca.gpx4j.core.exception.GpxIOException;
//import org.casaca.gpx4j.core.exception.GpxPropertiesException;
//import org.casaca.gpx4j.core.exception.GpxReaderException;
//import org.casaca.gpx4j.core.exception.GpxValidationException;
//import org.casaca.gpx4j.core.logging.Logger;
//import org.casaca.gpx4j.core.util.Constants;
//import org.joda.time.DateTime;
//
///**
// *
// * @author igobrilhante
// */
//public class RawTrajecotyGPXImporter implements ITrajectoryImporter{
//
//    public static final String PARAMETER_FILE = "file";
//    private String file;
//    private ITrajectoryModel trajectoryModel;
//    private GpxDriver gpxDriver;
//   
//    
//    @Override
//    public void buildImport(ITrajectoryModel dataModel, Parameters parameters) {
//        this.file = (String) parameters.getParam(PARAMETER_FILE);
//        this.trajectoryModel = dataModel;
//        importGPXFile();
//    }
//    
//    private void importGPXFile(){
//        try {
//            gpxDriver = GpxDriver.getGpxDriver();
//            loadProperties();
//            IGpxReader gpxReader = gpxDriver.createReader();
//            try {
//                GpxDocument gpxDocument = gpxReader.readGpxDocument(new File(file));
//                String userid = gpxDocument.getMetadata().getName();
//                
//                
//                List<Track> tracks = gpxDocument.getTracks();
//                for(Track track : tracks){
//                    Trajectory<LatLonPoint,DateTime> trajectory = new Trajectory<LatLonPoint, DateTime>(userid+"_"+track.getNumber());
//                    trajectory.getAnnotations().addAnnotation("description", AnnotationType.STRING, track.getDesc());
//                    trajectory.getAnnotations().addAnnotation("comment", AnnotationType.STRING, track.getCmt());
//                    trajectory.getAnnotations().addAnnotation("source", AnnotationType.STRING, track.getSrc());
//                    trajectory.getAnnotations().addAnnotation("type", AnnotationType.STRING, track.getType());
//                    
//                    List<TrackSegment> trackSegments = track.getTrackSegments();
//                    for(TrackSegment ts : trackSegments){
//                        List<Waypoint> waypoints = ts.getWaypoints();
//                        for(Waypoint wp : waypoints){
//                            LatLonPoint point = new LatLonPoint(wp.getLongitude().doubleValue(), wp.getLatitude().doubleValue());
//                            point.getAnnotations().addAnnotation("ele", AnnotationType.DOUBLE, wp.getElevation());
//                            point.getAnnotations().addAnnotation("comment", AnnotationType.STRING, wp.getCmt());
//                            point.getAnnotations().addAnnotation("description", AnnotationType.STRING, wp.getDescription());
//                            point.getAnnotations().addAnnotation("name", AnnotationType.STRING, wp.getName());
//                            point.getAnnotations().addAnnotation("satelities", AnnotationType.INT, wp.getSat());
//                            point.getAnnotations().addAnnotation("source", AnnotationType.STRING, wp.getSrc());
//                            
//                            DateTime datetime = new DateTime(wp.getTime());
//                            
//                            trajectory.addPoint(point, datetime);
//                            
//                        }
//                    }
//                    
//                    this.trajectoryModel.addTrajectory(trajectory);
//                }
//                
//            } catch (GpxValidationException ex) {
//                ex.printStackTrace();
//            } catch (GpxFileNotFoundException ex) {
//                ex.printStackTrace();
//            } catch (GpxIOException ex) {
//                ex.printStackTrace();
//            }
//        } catch (GpxFileNotFoundException ex) {
//            ex.printStackTrace();
//        } catch (GpxReaderException ex) {
//            ex.printStackTrace();
//        } catch (GpxPropertiesException ex) {
//            ex.printStackTrace();
//        }
//    }
//    
//    private void loadProperties(){
//        try {
//            
//            gpxDriver.loadDefaultDriverProperties();
//            
//            
//        } catch (GpxFileNotFoundException ex) {
//            ex.printStackTrace();
//        } catch (GpxIOException ex) {
//            ex.printStackTrace();
//        }
//    }
//    
//}
