package arida.ufc.br.moap.stop.imp;

import arida.ufc.br.moap.core.beans.LatLonPoint;
import arida.ufc.br.moap.core.beans.MovingObject;
import arida.ufc.br.moap.core.beans.Trajectory;
import arida.ufc.br.moap.core.imp.Parameters;
import arida.ufc.br.moap.core.imp.Reporter;
import arida.ufc.br.moap.datamodelapi.imp.TrajectoryModelImpl;
import arida.ufc.br.moap.datamodelapi.spi.ITrajectoryModel;
import arida.ufc.br.moap.distance.imp.SphericalLawofCosines;
import arida.ufc.br.moap.distance.spi.IDistanceFunction;
import arida.ufc.br.moap.stop.spi.IStop;
import arida.ufc.br.moap.stop.spi.IStopAlgorithm;
import java.util.List;
import org.joda.time.DateTime;
import org.joda.time.Interval;

/**
 *
 * @author franzejr
 * @author rafaelelias
 * @author igobrilhante
 *
 * <p> Reference: Li, Q., Zheng, Y., Xie, X., Chen, Y., Liu, W., & Ma, W.-Y.
 * (2008). Mining user similarity based on location history. Proceedings of the
 * 16th ACM SIGSPATIAL international conference on Advances in geographic
 * information systems - GIS ’08, (c), 1. doi:10.1145/1463434.1463477 </p> <p>
 * Given a trajectory as sequence of points in space and time, it is transformed
 * into a sequence of <b>stops</b> ({@link StayPoint) in space and time.
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
    public ITrajectoryModel<StayPoint,Interval> execute(ITrajectoryModel data, Parameters parameters) {
        report = new Reporter(StayPointDetection.class);
        report.setReport("Starting the algorithm, it works for a single trajectory");

        this.result = new TrajectoryModelImpl<StayPoint,Interval>();


        /*
         * Time Threshold in seconds
         * Spatial Threhold in meters
         */



        double timeThreshold = (Double) parameters.getParamValue(PARAMETER_TIME_THRESHOLD);

        report.setReport("TimeThreshold: "+timeThreshold);

        double distanceThreshold = (Double) parameters.getParamValue(PARAMETER_SPATIAL_THRESHOLD);

        report.setReport("DistanceThreshold: "+distanceThreshold);


        /*
         * Compute distance between LatLon points
         */
        report.setReport("Using the Spherical Law of Cosines for compute the distance between two points");
        SphericalLawofCosines distanceFunction = new SphericalLawofCosines();
        /*
         * For each trajectory
         */
        report.setReport("Starting the algorithm, it works for a single trajectory");
        
        for (Trajectory traj : data.getTrajectories()) {
            System.out.println("iterando nas trajetorias");
            stayingPointDetection(traj, distanceFunction, distanceThreshold, timeThreshold);

        }

        return result;
    }

    /*
     * Algorithm to detect stops for a given trajectory. This algorithm is for a single trajectory.
     */
    private void stayingPointDetection(Trajectory trajectory, IDistanceFunction distanceFunction, double spatialThreshold, double temporalThreshold) {
        //report.setReport("Starting the stayingPointDetection method");    
        int pointNum = trajectory.getPointCount();
        MovingObject movingObjectId = trajectory.getMovingObject();

        Trajectory new_trajectory = this.result.factory().newTrajectory(trajectory.getId(), movingObjectId);

       
        this.result.addTrajectory(new_trajectory);
      

        LatLonPoint point_i, point_j;
        DateTime time_i, time_j;
        int i = 0, j = 0;
        int stopCount = 0;

        int size = this.result.getTrajectoryCount();
        
        while (i < pointNum) {
            
            j = i + 1;
            point_i = (LatLonPoint) trajectory.getPoint(i);
           

            while (j < pointNum) {
                
            
                point_j = (LatLonPoint) trajectory.getPoint(j);
             
               
                /*
                 * LatLong distance
                 */
                double dist = distanceFunction.distance(point_i, point_j);
            
                if (dist > spatialThreshold) {

                    time_i = (DateTime) trajectory.getTime(i);
                    time_j = (DateTime) trajectory.getTime(j);
                    double timeDifference;
                    timeDifference = time_j.getMillis() - time_i.getMillis();
                    

                    /*
                     * If this condition is TRUE, a stay point is found
                     */
                    if (timeDifference > temporalThreshold) {
                        //Mean Lat and Long
                        
                        //If the first point and last point are not stay points
                        if(i!=0 && j!=pointNum){
                        
                            /*Compute centroid - Disabled */
                            //LatLonPoint centroid = computeCentroid(null);
                            
                           
                            Interval interval = new Interval(time_i, time_j);
                            //Adding Point J as a Stay Point
                            StayPoint stop = new StayPoint(point_j);
                            
                            new_trajectory.addPoint(stop, interval);
                            /*
                             * Adding to the Stay Point to the result
                             */
                            report.setReport("Adding a StayPoint in a StayPoint List"); 
                            this.result.addTrajectory(new_trajectory);
    
                            stopCount++;      

                        }
                    }
                    i = j;
                    break;
                }
                j++;
            }
            /*
             * Force end loop since j has reached the end of the trajectory
             */
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
