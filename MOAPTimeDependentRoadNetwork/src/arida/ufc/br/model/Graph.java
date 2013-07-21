package arida.ufc.br.model;

import java.util.HashSet;
import java.util.Set;

import arida.ufc.br.function.CostLinearFunction;

public class Graph {
	
	public Set<Vertice> getVertices() {
		return vertices;
	}

	public Set<Edge> getEdges() {
		return edges;
	}

	protected final Set<Vertice> vertices;
	
	protected final Set<Edge> edges;
	
	protected final Set<CostLinearFunction> functions;
	
	public Graph() {
		this.vertices = new HashSet<Vertice>();
		this.edges = new HashSet<Edge>();
		this.functions = new HashSet<CostLinearFunction>();
	}
	
	public void addFunction(CostLinearFunction ... function) {
		for(CostLinearFunction f: function)
			functions.add(f);
	
	}
	
	public void addVertice(Vertice ... vertice) {
		for(Vertice v: vertice)
			vertices.add(v);
	}
	
	public void addEdge(Edge ... edge) {
		for(Edge e: edge)
			edges.add(e);
	}
}
