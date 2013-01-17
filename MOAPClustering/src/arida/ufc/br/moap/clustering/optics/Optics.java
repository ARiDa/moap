package arida.ufc.br.moap.clustering.optics;

import arida.ufc.br.moap.clustering.api.ICluster;
import arida.ufc.br.moap.clustering.api.IClusteringAlgorithm;
import arida.ufc.br.moap.core.imp.Parameters;
import arida.ufc.br.moap.core.spi.IDataModel;
import arida.ufc.br.moap.distance.spi.IDistanceFunction;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.PriorityQueue;
import org.apache.log4j.Logger;

/**
 * @author igobrilhante
 *
 * @param <T> is the object type to be clustered
 *
 * Optics is a density-based algorithm similar to DBSCAN. <p>It is based on
 * ordering points to identify cluster structures.</p> <p>This implementation is
 * based on the paper Ankerst, M., Breunig, M., & Kriegel, H. (1999). OPTICS:
 * ordering points to identify the clustering structure. ACM SIGMOD Record.
 * Retrieved from <a
 * href='http://dl.acm.org/citation.cfm?id=304187'>http://dl.acm.org/citation.cfm?id=304187</a>
 */
public class Optics<T> extends IClusteringAlgorithm<T> {

    public static final int UNDEFINED = Integer.MAX_VALUE;
    private Logger logger = Logger.getLogger(Optics.class);
    private List<OpticsObject<T>> setOfObjects;
    private double epsilon;
    private double minPts;
    private PriorityQueue<OpticsObject<T>> orderSeed;
    private IDistanceFunction<T> distance_function;
    private List<OpticsObject<T>> ClusterOrdered;
    private Collection<ICluster<T>> clusters;

    /**
     * @param dataset
     * @param epsilon
     * @param minPts
     * @param distanceFunction
     */
    public Optics(List<T> dataset, double epsilon, double minPts, IDistanceFunction<T> distanceFunction) {
        this.setOfObjects = new ArrayList<OpticsObject<T>>();
        this.orderSeed = new PriorityQueue<OpticsObject<T>>();
        for (T t : dataset) {
            OpticsObject<T> o = new OpticsObject<T>(t);
            this.setOfObjects.add(o);
        }
        this.distance_function = distanceFunction;

        // Parameters for the algorithm
        this.epsilon = epsilon;
        this.minPts = minPts;

        // Set neighbors the p
        for (OpticsObject<T> p : setOfObjects) {
            setNeighbors(p);
        }
    }



    /*
     * (non-Javadoc) @see
     * mf.algorithm.clustering.spi.IClusteringAlgorithm#getName()
     */
    @Override
    public String getName() {
        // TODO Auto-generated method stub
        return "Optics Algorithm";
    }

    /*
     * (non-Javadoc) @see
     * mf.algorithm.clustering.spi.IClusteringAlgorithm#getClusters()
     */
//    @Override
//    public Collection<ICluster<T>> getClusters() {
//        // TODO Auto-generated method stub
//        return this.clusters;
//    }

    /**
     * @param object
     */
    private void expandClusterOrder(OpticsObject<T> object) {
//        logger.info("Expand Cluster Order");
        // Object is set processed
        object.setProcessed(true);
        // Update its reachability-distance
        // Compute its core-distance
        setCoreDistance(object);
        // Add the object to the result
        ClusterOrdered.add(object);

        // if its core-distance is not UNDEFINED
        if (object.getCoreDistance() != UNDEFINED) {
            // Update object
            update(object);
            // While the orderSeed is not empty
            while (!orderSeed.isEmpty()) {
                OpticsObject<T> current = orderSeed.poll();
                current.setProcessed(true);
                setCoreDistance(current);
                ClusterOrdered.add(current);
                if (current.getCoreDistance() != UNDEFINED) {
                    update(current);
                }
            }
        }
    }

    /**
     * @param object
     */
    private void update(OpticsObject<T> object) {
//        logger.info("Update");
        double coredist = object.getCoreDistance();
        for (OpticsObject<T> p : object.getNeighbors()) {
            if (!p.isProcessed()) {
                double new_r_t = Math.max(coredist,
                        this.distance_function.evaluate(object.getObject(), p.getObject()));

                // p is not in orderSeed
                if (p.getReachabilityDistance() == UNDEFINED) {
                    p.setReachabilityDistance(new_r_t);
                    orderSeed.add(p);
                } // p is already in orderSeed
                else {
                    if (new_r_t < p.getReachabilityDistance()) {
                        orderSeed.remove(p);
                        p.setReachabilityDistance(new_r_t);
                        orderSeed.add(p);
                    }
                }
            }
        }
        
    }

    // Set the clusters for each object
    /**
     * Extract DBSCAN Clustering <p> It extracts the clusters based on the
     * Optics execution that creates Ordered Clusters</p>
     */
    private void extractDBSCANClustering() {
        logger.info("Extract DBScan Clustering");
        int noise = -1;
        int clusterId = -1;
        OpticsCluster<T> noiseCluster = new OpticsCluster<T>(noise);
        OpticsCluster<T> cluster = null;
        int size = ClusterOrdered.size();
        for (int i = 0; i < size; i++) {
            OpticsObject<T> object = ClusterOrdered.get(i);
            if (object.getReachabilityDistance() > this.epsilon) {
                if (object.getCoreDistance() <= epsilon) {
                    if (cluster != null) { // Insert cluster to result clusters
                        this.clusters.add(cluster);
                    }

                    clusterId++;
                    object.setClusterID(clusterId); // New Optics Cluster
                    cluster = new OpticsCluster<T>(clusterId);
                    cluster.getObjects().add(object.getObject());
                } else {
                    object.setClusterID(noise); // Get the Noise Cluster and insert a new object
                    noiseCluster.getObjects().add(object.getObject());
                }
            } else {
                object.setClusterID(clusterId); // Get a Optics Cluster with clusterId and insert a new object
                cluster.getObjects().add(object.getObject());
            }
        }
        this.clusters.add(noiseCluster); // Insert noise to result clusters
    }

    // Set neighbors of an object given a epsilon
    /**
     * @param object
     */
    private void setNeighbors(OpticsObject<T> object) {
        List<OpticsObject<T>> neighbors = new ArrayList<OpticsObject<T>>();

        for (OpticsObject<T> p : setOfObjects) {
            if (!p.equals(object)) {
                if (distance_function.evaluate((T) p.getObject(), (T) object.getObject()) <= this.epsilon) {
                    neighbors.add(p);
                }
            }
        }
        object.setNeighbors(neighbors);
    }

    // Set core-distance of an object
    /**
     * @param object
     */
    private void setCoreDistance(OpticsObject<T> object) {
        // If its neighbor size is smaller than minPts
        if (object.getNeighbors().size() < this.minPts) {
            object.setCoreDistance(UNDEFINED);
        } // Otherwise, its core-distance is minPtsDistance
        else {
            double d = minPtsDistance(object);
            object.setCoreDistance(d);
        }
    }

    // Help to find core-distance by getting the smallest distance between an object and its neighbors
    /**
     * @param object
     * @return
     */
    private double minPtsDistance(OpticsObject<T> object) {
        double minDistance = Double.MAX_VALUE;
        for (OpticsObject<T> q : object.getNeighbors()) {
            double distance = distance_function.evaluate(q.getObject(), object.getObject());
            if (distance < minDistance) {
                minDistance = distance;
            }
        }
        return minDistance;
    }

    /**
     * @return
     */
    public List<OpticsObject<T>> getClusterOrdered() {
        return ClusterOrdered;
    }

    @Override
    public Collection<ICluster<T>> getClusters() {
        return this.clusters;
    }

    /**
     * 
     * @param data
     * @param parameters
     * @return 
     */
    @Override
    public IDataModel execute(IDataModel data, Parameters parameters) {
               logger.info("Optics Execution");
        int size = setOfObjects.size();
        this.clusters = new ArrayList<ICluster<T>>();
        this.ClusterOrdered = new ArrayList<OpticsObject<T>>();
        int i = 0;
        while (i < size) {
            OpticsObject<T> object = setOfObjects.get(i);
            // if the object is not processed
            if (!object.isProcessed()) {
                expandClusterOrder(object);
            }
            i++;
        }

        extractDBSCANClustering(); // Extract the cluster
        logger.info("Optics Execution End");
        
        return null;
    }

    
}
