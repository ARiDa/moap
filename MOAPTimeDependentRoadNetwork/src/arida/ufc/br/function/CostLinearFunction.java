package arida.ufc.br.function;

import ac.essex.graphing.plotting.ContinuousFunctionPlotter;
import ac.essex.graphing.plotting.Graph;
import ac.essex.graphing.swing.GraphApplication;
import arida.ufc.viewer.PlotCostLinearFunctionSetting;

public class CostLinearFunction implements Comparable<CostLinearFunction>{
	
	private double coeficienteAngular;
	
	private double coeficienteLinear;
	
	private double xInitial;
	
	private double yInitial;
	
	private double xFinal;
	
	private double yFinal;
	
	private String name;
	
	public CostLinearFunction(double xInitial, double yInitial, double xFinal,
			double yFinal, String name) {
		super();
		validate(xInitial, yInitial, xFinal,yFinal);
		this.name = name;
		this.xInitial = xInitial;
		this.yInitial = yInitial;
		this.xFinal = xFinal;
		this.yFinal = yFinal;
		coeficienteAngular = (double) (yFinal - yInitial) / (xFinal - xInitial);
		coeficienteLinear = - coeficienteAngular * xInitial + yInitial;	
	}

	private void validate(double xInitial, double yInitial, double xFinal, double yFinal) {
		if ((xInitial < 0) || (xFinal < 0) || (yInitial < 0) || (yFinal < 0))
			throw new RuntimeException("Values of X and Y must be positives.");
		if (xInitial >= xFinal) 
			throw new RuntimeException("X initial must be lower than x final.");
	}
	
	public double calculate(double input) {
		return coeficienteAngular * input + coeficienteLinear;
	}


	public double getxInitial() {
		return xInitial;
	}

	public double getyInitial() {
		return yInitial;
	}

	public double getxFinal() {
		return xFinal;
	}

	public double getyFinal() {
		return yFinal;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public double getCoeficienteAngular() {
		return coeficienteAngular;
	}

	public double getCoeficienteLinear() {
		return coeficienteLinear;
	}

	@Override
	public String toString() {
		return "Linear function: f(x) = " + coeficienteAngular + "* x + " + coeficienteLinear;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		long temp;
		temp = Double.doubleToLongBits(coeficienteAngular);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(coeficienteLinear);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(xFinal);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(xInitial);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(yFinal);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(yInitial);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		CostLinearFunction other = (CostLinearFunction) obj;
		if (Double.doubleToLongBits(coeficienteAngular) != Double
				.doubleToLongBits(other.coeficienteAngular))
			return false;
		if (Double.doubleToLongBits(coeficienteLinear) != Double
				.doubleToLongBits(other.coeficienteLinear))
			return false;
		if (Double.doubleToLongBits(xFinal) != Double
				.doubleToLongBits(other.xFinal))
			return false;
		if (Double.doubleToLongBits(xInitial) != Double
				.doubleToLongBits(other.xInitial))
			return false;
		if (Double.doubleToLongBits(yFinal) != Double
				.doubleToLongBits(other.yFinal))
			return false;
		if (Double.doubleToLongBits(yInitial) != Double
				.doubleToLongBits(other.yInitial))
			return false;
		return true;
	}

	public Graph getGraph(PlotCostLinearFunctionSetting p){
		Graph graph = new Graph(p); 
		ContinuousFunctionPlotter plotter = new ContinuousFunctionPlotter() {
			@Override
			public String getName() {
				return name;
			}
			
			@Override
			public double getY(double x) {
				return calculate(x);
			}
		};
		graph.functions.add(plotter);
		return graph;
	}
	
	public void plot(PlotCostLinearFunctionSetting p){
		new GraphApplication(getGraph(p));
	}

	@Override
	public int compareTo(CostLinearFunction o) {
		return (int) (this.xInitial - o.xInitial);
	}
}
