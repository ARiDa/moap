package arida.ufc.br.moap.optics;

import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;

import arida.ufc.br.moap.clustering.api.IClusteringAlgorithm;
import arida.ufc.br.moap.core.imp.Parameters;
import arida.ufc.br.moap.core.imp.Reporter;
import arida.ufc.br.moap.core.spi.IDataModel;
import arida.ufc.br.moap.datamodelapi.imp.ListModelImpl;

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
public class Optics<T>
    extends IClusteringAlgorithm<T> {

    public static int UNDEFINED = Integer.MAX_VALUE;
    public static int NOISE_ID = -1;
    
    private List<OpticsObject<T>> setOfObjects;
    
    // Parameters
    private float epsilon;
    private int minPts;
    private Parameters params;
    
    private PriorityQueue<OpticsObject<T>> orderSeed;
    
    // Ordered List
    private List<OpticsObject<T>> ClusterOrdered;
    // Result
    private List<OpticsCluster<T>> clusters;

    /**
     * @param dataset
     * @param epsilon
     * @param minPts
     * @param distanceFunction
     */
    public Optics() {
        
        // Parameters for the algorithm
        this.params = new Parameters();
        params.addClass("minPts", Integer.class);
        params.addClass("eps", Float.class);
        
        report = new Reporter(this.getClass());
     
    }
    

    /**
     * Name of the algorithm
     * @return 
     */
    @Override
    public String getName() {
        // TODO Auto-generated method stub
        return "Optics Algorithm";
    }


    /**
     * 
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
     * The priority queue is updated with the epslon-neighborhood of p and q, respectively
     * @param object
     */
    private void update(OpticsObject<T> object) {
        
        double coredist = object.getCoreDistance();
        // Loop over the neighborhood
        for (OpticsObject<T> p : object.getNeighbors()) {
            if (!p.isProcessed()) {
                double dist = distanceFunction.evaluate(object.getObject(), p.getObject());
                double newReachDist = Math.max(coredist,dist);

                // p is not in orderSeed
                if (p.getReachabilityDistance() == UNDEFINED) {
                    p.setReachabilityDistance(newReachDist);
                    orderSeed.add(p);
                } 
                // p is already in orderSeed
                else {
                    if (newReachDist < p.getReachabilityDistance()) {
                        // Update it in the queue by assigning a new reachability distance
                        orderSeed.remove(p);
                        p.setReachabilityDistance(newReachDist);
                        orderSeed.add(p);
                        
                    }
                }
            }
        }
        
    }

    /**
     * Set the clusters for each object
     * Extract DBSCAN Clustering <p> It extracts the clusters based on the
     * Optics execution that creates Ordered Clusters</p>
     */
    private void extractDBSCANClustering() {
        report.setReport("Extract DBScan Clustering");
        // cluster noise id
        int noise = NOISE_ID;
        // initial cluster id
        int clusterId = -1;
        OpticsCluster<T> noiseCluster = new OpticsCluster<T>(noise);
        OpticsCluster<T> cluster = null;
        int size = ClusterOrdered.size();
        
        /*
         * Loop over the points
         */
        for (int i = 0; i < size; i++) {
            OpticsObject<T> object = ClusterOrdered.get(i);
//            System.out.println(object.getObject()+" "+object.getReachabilityDistance());
            if (object.getReachabilityDistance() > this.epsilon) {
                if (object.getCoreDistance() <= epsilon) {
                    // Insert cluster to result clusters
//                    if (cluster != null) { 
//                        this.clusters.add(cluster);
//                    }
                    
                    // New Optics Cluster
//                    System.out.println("New optics cluster");
                    clusterId = clusterId + 1;
                    object.setClusterID(clusterId); 
                    cluster = new OpticsCluster<T>(clusterId);
                    cluster.getObjects().add(object.getObject());
                    
                    this.clusters.add(cluster);
                } else {
                    // Noise
                    // Get the Noise Cluster and insert a new object
//                    System.out.println("Noise");
                    object.setClusterID(noise); 
                    noiseCluster.getObjects().add(object.getObject());
                }
            } else {
                // Get a Optics Cluster with clusterId and insert a new object
//                System.out.println("Add to cluster");
                object.setClusterID(clusterId); 
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
                if (distanceFunction.evaluate((T) p.getObject(), (T) object.getObject()) <= this.epsilon) {
                    neighbors.add(p);
                }
            }
        }
        object.setNeighbors(neighbors);
    }

    /**
     * Set core-distance of an object. This is obtained as follows.
     * 
     * core-distance(p) = UNDEFINED, if |N(p,eps)| < MinPts
     * core-distance(p) = distance to the MinPts-th point, otherwise
     * 
     * @param object
     */
    private void setCoreDistance(OpticsObject<T> object) {
        // If its neighbor size is smaller than minPts: |N(p,eps)| < MinPts
        if (object.getNeighbors().size() < this.minPts) {
            object.setCoreDistance(UNDEFINED);
        } // Otherwise, its core-distance is minPtsDistance
        else {
            // distance to the MinPts-th point
            double d = minPtsDistance(object);
            object.setCoreDistance(d);
        }
    }

    /**
     * Help to find core-distance by getting the smallest distance between an object and its neighbors
     * @param object
     * @return
     */
    private double minPtsDistance(OpticsObject<T> object) {
        double minDistance = Double.MAX_VALUE;
        for (OpticsObject<T> q : object.getNeighbors()) {
            double distance = distanceFunction.evaluate(q.getObject(), object.getObject());
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

    /**
     * 
     * @param data
     * @param parameters
     * @return 
     */
    @Override
    public ListModelImpl<OpticsCluster<T>> execute(IDataModel<T> data, Parameters parameters) {
        
        /**
         * Setting parameters
         */
        setParameters(parameters);
        
        Optics.UNDEFINED = (int)this.epsilon+5;
        
        this.setOfObjects = new ArrayList<OpticsObject<T>>();
        this.orderSeed = new PriorityQueue<OpticsObject<T>>();
        
        
        /**
         * Creating OpticsObjects
         */
        for (T t : data.getInstances()) {
            OpticsObject<T> o = new OpticsObject<T>(t);
            this.setOfObjects.add(o);
        }
        
        /**
         * Set neighbors of each point p
         */
        for (OpticsObject<T> p : setOfObjects) {
            setNeighbors(p);
        }

        /**
         * Execution
         */
        report.setReport("Optics Execution");
        int size = setOfObjects.size();
        this.clusters = new ArrayList<OpticsCluster<T>>();
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

        /**
         * Extract the cluster
         */
        extractDBSCANClustering(); 
        
        report.setReport("Optics Execution End");
        
        /**
         * Setting the result
         */
        ListModelImpl<OpticsCluster<T>> res = new ListModelImpl<OpticsCluster<T>>(this.clusters);
        
        this.result = res;
        
        return res;
    }
    

    
    private void setParameters(Parameters parameters){
        report.setReport("Setting parameters");
        
        if(this.params.validate(parameters)){
            this.epsilon = (Float)parameters.getParamValue("eps");
            this.minPts = (Integer)parameters.getParamValue("minPts");
            
            report.setReport("Parameters "+parameters);
            report.setReport("Distance function: "+this.distanceFunction.getName());
            
        }
        
        
    }

    
}
