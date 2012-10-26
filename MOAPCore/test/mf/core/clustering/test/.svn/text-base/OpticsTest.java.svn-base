package mf.core.clustering.test;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Iterator;

import mf.algorithm.clustering.imp.Optics;
import mf.algorithm.clustering.imp.OpticsCluster;
import mf.algorithm.clustering.imp.OpticsObject;
import mf.algorithm.clustering.spi.ICluster;
import mf.core.algorithm.distance.imp.GeoDistance;
import mf.core.beans.LatLonPoint;

public class OpticsTest {
	public static void main(String[] args){
		String filename = "/Users/igobrilhante/points_of_interest.csv";
		try {
			DataInputStream d = new DataInputStream(new FileInputStream(new File(filename)));
			BufferedReader buffer = new BufferedReader(new InputStreamReader(d));
			String line;
			ArrayList<LatLonPoint> points = new ArrayList<LatLonPoint>();
			int i = 1000;
			try {
				while((line = buffer.readLine())!=null ){
					String[] cols = line.split(",");
					LatLonPoint p = new LatLonPoint();
					p.setLatitude(Double.parseDouble(cols[1]));
					p.setLongitude(Double.parseDouble(cols[0]));
					points.add(p);
//					i--;
				}
				GeoDistance distance = new GeoDistance();
				Optics<LatLonPoint> clustering = new Optics<LatLonPoint>(points, 1, 1, distance);
				clustering.execute();
				
				System.out.println(clustering.getClusterOrdered().size());
				System.out.println("\n#########################\n");
				DataOutputStream output = new DataOutputStream(new FileOutputStream(new File("/Users/igobrilhante/optics.csv")));
				BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(output));
				writer.append("x,y,cluster\n");
				for(OpticsObject<LatLonPoint> p: clustering.getClusterOrdered()){
//					System.out.println(p.getObject().getX()+","+p.getObject().getY()+","+p.getClusterID());
					writer.append(p.getObject().getLongitude()+","+p.getObject().getLatitude()+","+p.getClusterID()+"\n");
				}
				writer.close();
				output.close();
				
//				for(Iterator iter = clustering.getClusters().iterator();iter.hasNext();iter.next()){
//					ICluster cluster = (ICluster) iter.next();
//					System.out.println(cluster);
//				}
				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
