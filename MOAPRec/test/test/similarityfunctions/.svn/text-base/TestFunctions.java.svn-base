package test.similarityfunctions;

import function.similarity.CosineSimilarity;
import function.similarity.PearsonSimilarity;
import function.similarity.TanimotoSimilarity;

public class TestFunctions {
	public static void main(String[] args){
		double[][] matrix = new double[5][4];
		
		matrix[0][0] = 4;
		matrix[0][1] = 0;
		matrix[0][2] = 5;
		matrix[0][3] = 5;
		
		matrix[1][0] = 4;
		matrix[1][1] = 2;
		matrix[1][2] = 1;
		matrix[1][3] = 0;
		
		matrix[2][0] = 3;
		matrix[2][1] = 0;
		matrix[2][2] = 2;
		matrix[2][3] = 4;
		
		matrix[3][0] = 4;
		matrix[3][1] = 4;
		matrix[3][2] = 0;
		matrix[3][3] = 0;
		
		matrix[4][0] = 2;
		matrix[4][1] = 1;
		matrix[4][2] = 3;
		matrix[4][3] = 5;
		
		PearsonSimilarity pearson = new PearsonSimilarity(matrix);
		pearson.execute();
		
		CosineSimilarity cosine = new CosineSimilarity(matrix);
		cosine.execute();
		
		TanimotoSimilarity tanimoto = new TanimotoSimilarity(matrix);
		tanimoto.execute();
		
		for(int i = 0;i<5;i++){
			for(int j = i+1; j <5;j++){
				System.out.println("Pearson "+ i +" " + j + " : "+pearson.getSimilarity(i, j));
				System.out.println("Cosine "+ i +" " + j + " : "+cosine.getSimilarity(i, j));
				System.out.println("Tanimoto "+ i +" " + j + " : "+tanimoto.getSimilarity(i, j));
			}
		}
	}
}
