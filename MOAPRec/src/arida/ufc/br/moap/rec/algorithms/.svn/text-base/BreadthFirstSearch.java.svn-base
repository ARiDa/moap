package arida.ufc.br.moap.rec.algorithms;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import org.gephi.graph.api.DirectedGraph;
import org.gephi.graph.api.Edge;
import org.gephi.graph.api.EdgeIterable;
import org.gephi.graph.api.Graph;
import org.gephi.graph.api.Node;
import org.gephi.graph.api.NodeIterable;

/**
 * 
 * @author igobrilhante
 */
public class BreadthFirstSearch {
	private Graph graph;
	private Map<Node, Map<Node,Integer>> distances;
	Map<Node, Integer> indices;
	
        /**
         * 
         * @param graph
         */
        public BreadthFirstSearch(Graph graph){
		this.graph = graph;
		
	}
	
	/**
	 * @return
	 */
	public Map<Node, Map<Node, Integer>> getDistances() {
		return distances;
	}

	/**
	 * 
	 */
	public void execute(){
		indices = new HashMap<Node,Integer>();
		distances = new HashMap<Node, Map<Node,Integer>>();

		NodeIterable nodeIter = this.graph.getNodes();
		int index = 0;
		
		for(Node node : nodeIter){
			indices.put(node,index);
			index++;
		}
		nodeIter = this.graph.getNodes();
		
		for(Node node : nodeIter){
			Map<Node,Integer> node_distances = bfs(node);
			distances.put(node, node_distances);
		}
	}
	
	// Breadth-First Search from a given node
	/**
	 * @param node
	 * @return Distances to the other nodes
	 */
	private Map<Node,Integer> bfs(Node node){
		int node_count = this.graph.getNodeCount();
		Map<Node,Integer> node_distance = new HashMap<Node, Integer>();
		
		int[] d = new int[node_count];
		for(int i=0;i<node_count;i++){
			d[i] = -1;
		}
		LinkedList<Node> queue = new LinkedList<Node>();
		queue.addLast(node);
		int node_index = indices.get(node);
		d[node_index] = 0;
		
		while(!queue.isEmpty()){
			Node v = queue.poll();
			int v_index = indices.get(v);
			EdgeIterable edgeIter = ((DirectedGraph)this.graph).getOutEdges(v);
			
			for(Edge edge : edgeIter){
				Node r = graph.getOpposite(v, edge);
				int r_index = indices.get(r);
				if(d[r_index] < 0 ){
					d[r_index] = d[v_index] + 1;
					queue.addLast(r);
					node_distance.put(r, d[r_index]);
				}
			}
		}
		
		return node_distance;
		
	}
	
}
