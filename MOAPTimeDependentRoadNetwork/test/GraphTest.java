import arida.ufc.br.function.CostLinearFunction;
import arida.ufc.br.model.Edge;
import arida.ufc.br.model.Graph;
import arida.ufc.br.model.Vertice;
import arida.ufc.viewer.GenerateGraphML;


public class GraphTest {

	
	public static void main(String[] args) {
		Vertice vA, vB, vC;
		vA = new Vertice(1,"A",0,0);
		vB = new Vertice(2,"B",0,0);
		vC = new Vertice(3,"C",0,0);
		
		Edge eAB, eBA, eBC, eAC, eCA, eCB;
		eAB = new Edge(vA, vB);
		eBA = new Edge(vB, vA);
		eBC = new Edge(vB, vC);
		eAC = new Edge(vA, vC);
		eCA = new Edge(vC, vA);
		eCB = new Edge(vC, vB);
		
		vA.addEdge(eAB);
		vA.addEdge(eAC);
		vB.addEdge(eBA);
		vB.addEdge(eBC);
		vC.addEdge(eCA);
		vC.addEdge(eCB);
		
		CostLinearFunction c1, c2, c3, c4, c5;
		c1 = new CostLinearFunction(0, 20,10,20 , "(A,B) = (B,A)");
		c2 = new CostLinearFunction(10, 20,16,15, "(A,B) = (B,A)");
		c3 = new CostLinearFunction(16, 15,18,15, "(A,B) = (B,A)");
		c4 = new CostLinearFunction(18, 15,24,20, "(A,B) = (B,A)");
		
		eAB.addFunction(c1,c2,c3,c4);
		eBA.addFunction(c1,c2,c3,c4);
		
		c1 = new CostLinearFunction(0, 30,10,30 ,"(B,C)");
		c2 = new CostLinearFunction(10, 30,14,40,"(B,C)");
		c3 = new CostLinearFunction(14, 40,16,50,"(B,C)");
		c4 = new CostLinearFunction(16, 50,20,50,"(B,C)");
		c5 = new CostLinearFunction(20, 50,24,30,"(B,C)");
		
		eBC.addFunction(c1,c2,c3,c4,c5);
		
		c1 = new CostLinearFunction(0, 25,12,25 , "(A,C) = (C,A)");
		c2 = new CostLinearFunction(12, 25,16,15, "(A,C) = (C,A)");
		c3 = new CostLinearFunction(16, 15,18,15, "(A,C) = (C,A)");
		c4 = new CostLinearFunction(18, 15,24,25, "(A,C) = (C,A)");
		
		eAC.addFunction(c1,c2,c3,c4);
		eCA.addFunction(c1,c2,c3,c4);
		
		c1 = new CostLinearFunction(0, 25,12,15 , "(C,B)");
		c2 = new CostLinearFunction(12, 15,16,20, "(C,B)");
		c3 = new CostLinearFunction(16, 20,24,25, "(C,B)");
		
		eCB.addFunction(c1,c2,c3);
		
		
		Graph g = new Graph();
		g.addVertice(vA, vB, vC);
		g.addEdge(eAB,eAC,eBA,eBC,eCA,eCB);
		g.addFunction(c1,c2,c3,c4,c5);
		GenerateGraphML.generateGraphML(g, "c://tdg.graphml");
		
//		PlotCostLinearFunctionSetting p = new PlotCostLinearFunctionSetting(0, 24, 14, 50, Color.red, "Função Temporal", 8, 5);
//		
//		eAB.plotFunctions(p);
//		eAC.plotFunctions(p);
//		eBA.plotFunctions(p);
//		eBC.plotFunctions(p);
//		eCA.plotFunctions(p);
//		eCB.plotFunctions(p);
		
		
		
		
		
		
		
	}
}
