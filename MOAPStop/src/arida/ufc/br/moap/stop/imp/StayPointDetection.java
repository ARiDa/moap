package arida.ufc.br.moap.stop.imp;

import arida.ufc.br.moap.stop.spi.IStop;
import arida.ufc.br.moap.core.beans.LatLonPoint;
import arida.ufc.br.moap.core.beans.MovingObject;
import arida.ufc.br.moap.core.beans.Trajectory;
import arida.ufc.br.moap.core.imp.Parameters;
import arida.ufc.br.moap.datamodelapi.spi.ITrajectoryModel;
import arida.ufc.br.moap.datamodelapi.imp.TrajectoryModelImpl;
import arida.ufc.br.moap.datamodelapi.spi.ITrajectoryModel;
import arida.ufc.br.moap.distance.imp.SphericalLawofCosines;
import arida.ufc.br.moap.distance.spi.IDistanceFunction;
import arida.ufc.br.moap.stop.spi.IStopAlgorithm;
import java.util.List;
import org.joda.time.DateTime;
import org.joda.time.Interval;

/**
 *
 * @author franzejr
 * @author rafaelelias
 * 
 * <p>
 * Reference: Li, Q., Zheng, Y., Xie, X., Chen, Y., Liu, W., & Ma, W.-Y. (2008).
 * Mining user similarity based on location history. Proceedings of the 16th ACM
 * SIGSPATIAL international conference on Advances in geographic information
 * systems - GIS ’08, (c), 1. doi:10.1145/1463434.1463477
 * </p>
 * <p>
 * Given a trajectory as sequence of points in space and time, it is transformed into a sequence of <b>stops</b> ({@link StayPoint) in space and time.
 * </p>
 * <p>
 * Example:
 * Input
 * t1: (x1,y1,t1) -> (x2,y2,t2) -> (x3,y3,t3) -> (x4,y4,t4) -> (x5,y5,t5)
 * 
 * Output
 * t1: s((x1,y1,t1)) -> s((x3,y3,t3)) -> s((x5,y5,t5))
 * </p>
 */
public class StayPointDetection extends IStopAlgorithm {
   
    public static final String PARAMETER_TIME_THRESHOLD = "timeThreshold";
    public static final String PARAMETER_SPATIAL_THRESHOLD = "spatialThreshold";
    
//    private  List<IStop> stayPoints = new ArrayList<StayPoint>();
    
    
    

    @Override
    public List<IStop> getStops() {
        return null;
    }

    @Override
    public ITrajectoryModel execute(ITrajectoryModel data, Parameters parameters) {
        //report.setReport("Starting the algorithm, it works for a single trajectory");
        
        //parameters.addParam(PARAMETER_TIME_THRESHOLD, (double)0.01);
        //parameters.addParam(PARAMETER_SPATIAL_THRESHOLD, (double)0.01);
        
        //Debug
        System.out.println("aq1 StayPointDetection");
        
        this.result = new TrajectoryModelImpl();

        System.out.println("aq2 StayPointDetection");
            
        /*
         * Time Threshold in seconds
         * Spatial Threhold in meters
         */
        
        
        //Debug
        
        
        double timeThreshold = (Double) parameters.getParam(PARAMETER_TIME_THRESHOLD);
        
        //Debug
        System.out.println("tempo limite = " + timeThreshold);
        
        double distanceThreshold = (Double) parameters.getParam(PARAMETER_SPATIAL_THRESHOLD);
        
        //Debug
        System.out.println("distancia limite = " + distanceThreshold);
        
        
        /*
         * Compute distance between LatLon points
         */
        //report.setReport("Using the Spherical Law of Cosines for compute the distance between two points");
        SphericalLawofCosines distanceFunction = new SphericalLawofCosines();
        /*
         * For each trajectory
         */
        //report.setReport("Starting the algorithm, it works for a single trajectory");
        for (MovingObject mo : data.getMovingObjects()) {
            this.result.addMovingObject(mo);
            List<Trajectory> trajs = mo.getTrajectories();
            for(Trajectory trajectory : trajs){
                 System.out.println("iterando nas trajetorias");
                stayingPointDetection(trajectory, distanceFunction, distanceThreshold, timeThreshold);
            }
            
        }

        return result;
    }

    /*
     * Algorithm to detect stops for a given trajectory. This algorithm is for a single trajectory.
     */
    private void stayingPointDetection(Trajectory trajectory, IDistanceFunction distanceFunction, double spatialThreshold, double temporalThreshold) {
        //report.setReport("Starting the stayingPointDetection method");    
        int pointNum = trajectory.getPointCount();
        String movingObjectId = trajectory.getMovingObject();
        Trajectory new_trajectory = this.result.factory().newTrajectory(trajectory.getId());
        
        this.result.getMovingObject(movingObjectId).addTrajectory(new_trajectory);
        LatLonPoint point_i, point_j;
        DateTime time_i, time_j;
        int i = 0, j = 0;
        int stopCount = 0;
        int size = this.result.getMovingObject(movingObjectId).getTrajectoryCount();
        
        System.out.println("point num: "+pointNum);

        while (i < pointNum) {
            System.out.println("i: "+i);
            j = i + 1;
            point_i = (LatLonPoint) trajectory.getPoint(i);
            point_j = (LatLonPoint) trajectory.getPoint(j);
            //System.out.println(" ehuaheauhea");
            

            
            while (j < pointNum) {
                System.out.println("j: "+j);
                /*
                 * LatLong distance
                 */
                double dist = distanceFunction.distance(point_i, point_j);
                //System.out.println("point_i" +point_i + ",point_j" +point_j); 
                System.out.println("stayingPointDetection dist: " + dist + "     spatialThreshold" + spatialThreshold);
                if (dist > spatialThreshold) {
                    
                    time_i = (DateTime) trajectory.getTime(i);
                    time_j = (DateTime) trajectory.getTime(j);
                    double timeDifference;
                    timeDifference = time_j.getMillis() - time_i.getMillis();
                    System.out.println("timeDifference: " +timeDifference);
                    
                    if(timeDifference > temporalThreshold){
                     //Mean Lat and Long
                     
                        LatLonPoint centroid = computeCentroid(null);
                        /*
                         * Getting the arrive and leave time
                         */
                        Interval interval = new Interval(time_i, time_j);
                        IStop stop = new StayPoint(centroid);
                        System.out.println("Interval: "+interval);
                        
                        /*
                         * First Stop
                         */
//                        if(stopCount == 0){
                            /*
                             * Set the first point as a stop
                             */
//                            if(i > 0){
//                                IStop firstStop = new StayPoint((LatLonPoint) trajectory.getPoint(i));
//                                
//                                this.result.getMovingObject(movingObjectId).getTrajectory(size-1).addPoint(stop, interval);
//                            }
//                        }
                        
                        stopCount++;
                        /*
                         * Adding to the Stay Point to the result
                         */
                        //report.setReport("Adding a StayPoint in a StayPoint List"); 
                        
                        
//                        this.result.getMovingObject(movingObjectId).getTrajectory(size-1).addPoint(stop, interval);
                        System.out.println("SIZE:" + size);
                    }
                    i = j; break;
                }
                j++;
            }
            // End Loop
            i = j;
            

        }
        //report.setReport("Method stayingPointDetection returns a list");    
        
    }
    /*
     * Calculates the Centroid for a set of points
     * @return Centroid 
     */
    private LatLonPoint computeCentroid(List<LatLonPoint> list) {
        LatLonPoint centroid = null;
        return centroid;
    }
}