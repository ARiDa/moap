package test.bfs;

import static org.junit.Assert.*;

import org.gephi.graph.api.DirectedGraph;
import org.gephi.graph.api.Edge;
import org.gephi.graph.api.GraphController;
import org.gephi.graph.api.GraphModel;
import org.gephi.graph.api.Node;
import org.gephi.project.api.ProjectController;
import org.gephi.project.api.Workspace;
import org.junit.Test;
import org.openide.util.Lookup;

import arida.ufc.br.locationrecommender.algorithms.BreadthFirstSearch;

public class BFSTest {

	@Test
	public void test() {
//		fail("Not yet implemented");
		ProjectController pc = Lookup.getDefault().lookup(ProjectController.class);
		pc.newProject();
		Workspace workspace = pc.getCurrentWorkspace();
		
		GraphModel graphModel = Lookup.getDefault().lookup(GraphController.class).getModel();
		
		Node n0 = graphModel.factory().newNode("n0");
		Node n1 = graphModel.factory().newNode("n1");
		Node n2 = graphModel.factory().newNode("n2");
		Node n3 = graphModel.factory().newNode("n3");
		
		Edge e1 = graphModel.factory().newEdge(n0, n1,1f,true);
		Edge e2 = graphModel.factory().newEdge(n0, n2,1f,true);
		Edge e3 = graphModel.factory().newEdge(n1, n2,1f,true);
		Edge e4 = graphModel.factory().newEdge(n2, n1,1f,true);
		Edge e5 = graphModel.factory().newEdge(n2, n3,1f,true);
		
		DirectedGraph digraph = graphModel.getDirectedGraph();
		digraph.addNode(n0);
		digraph.addNode(n1);
		digraph.addNode(n2);
		digraph.addNode(n3);
		
		digraph.addEdge(e1);
		digraph.addEdge(e2);
		digraph.addEdge(e3);
		digraph.addEdge(e4);
		digraph.addEdge(e5);
		
		BreadthFirstSearch search = new BreadthFirstSearch(digraph);
		search.execute();
		System.out.println(search.getDistances());
	}

}
