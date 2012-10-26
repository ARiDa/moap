package vis;

import java.awt.EventQueue;

import javax.swing.JFrame;

import org.jdesktop.swingx.JXMapKit;
import org.jdesktop.swingx.JXMapKit.DefaultProviders;

/**
 * 
 * @author igobrilhante
 */
public class Vis {

	private JFrame frame;

	/**
	 * Launch the application.
         * 
         * @param args 
         */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Vis window = new Vis();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public Vis() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		JXMapKit map = new JXMapKit();
		map.setDefaultProvider(DefaultProviders.OpenStreetMaps);
		frame.add(map);
	}

}
