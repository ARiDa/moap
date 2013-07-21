package arida.ufc.viewer;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

import arida.ufc.br.model.Edge;
import arida.ufc.br.model.Graph;
import arida.ufc.br.model.Vertice;


public class GenerateGraphML {

	private StringBuffer arquivo = new StringBuffer(
			"<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n<graphml xmlns=\"").
			append("http://graphml.graphdrawing.org/xmlns\">\n\t<graph edgedefault=\"directed\">\n\n\t\t<!-- data schema -->\n\t\t<key id=\"label\" for=\"node\"").
			append(" attr.name=\"label\" attr.type=\"string\"/>\n\t\t<key id=\"label\" for=\"edge\" attr.name=\"label\" attr.type=\"string\"/>\n\n\t\t<!-- nodes -->  ");

	/**
	 * Método utilizado para gerar os nodos,
	 * será passado um id, para correlação nas arestas.
	 * Através do id, criaremos uma aresta de um nodo a outro.
	 * @param id
	 * @param label
	 * @return
	 */
	private void generateNodes(long id, String label){
		arquivo.append("\n\t\t<node id=\"").append(id).append("\">\n\t\t\t").append(
				"<data key=\"label\">").append(label).append("</data>\n\t\t</node>");
	}

	/**
	 * Nessa método serão construídos as arestas correspondes ao id de origem (idSource)
	 * ao id de destino (idTarget).
	 * @param idSource
	 * @param idTarget
	 * @return
	 */
	private void generateEdges(long idSource, long idTarget, String label){
		arquivo.append("\n\t\t<edge source=\"").append(idSource).append("\" target=\"").append(idTarget).append("\">\n\t\t\t").append(
				"<data key=\"label\">").append(label).append("</data>\n\t\t</edge>\n\t\t\t");
	}

	/**
	 * Esse método irá "fechar" o arquivo
	 *
	 */
	private void fecharArquivo(){
		arquivo.append("\n\n\t</graph>\n</graphml>");

	}

	/**
	 * Método utilizado para salvar o arquivo no disco
	 * @param path
	 */
	private void salvarXML(String path)
	{
		try {
			BufferedWriter out = new BufferedWriter(new FileWriter(path));
			out.write(arquivo.toString());
			out.flush();
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
			System.err.println("Erro ao salvar arquivo...Saindo");
			System.exit(0);
		}
	}
	
	 public static void generateGraphML (Graph g, String fullFileName){
         GenerateGraphML generator = new GenerateGraphML();
		 for(Vertice v: g.getVertices()){
			 generator.generateNodes(v.getId(),v.getLabel());
		 }
		 for(Edge e: g.getEdges()){
			 generator.generateEdges(e.getSource().getId(), e.getDestiny().getId(),e.getFunctions().toString());
		 }
		 generator.fecharArquivo();
		 generator.salvarXML(fullFileName);
	 }
}