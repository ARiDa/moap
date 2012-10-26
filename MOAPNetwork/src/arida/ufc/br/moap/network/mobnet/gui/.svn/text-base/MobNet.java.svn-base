package arida.ufc.br.moap.network.mobnet.gui;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.UIManager;

import org.jdesktop.swingx.JXMapKit;
import java.awt.BorderLayout;
import org.jdesktop.swingx.JXMapKit.DefaultProviders;

public class MobNet {

	private JFrame frame;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MobNet window = new MobNet();
	                System.setProperty("apple.laf.useScreenMenuBar", "true");
	                System.setProperty("com.apple.mrj.application.apple.menu.about.name", "Test");
	                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
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
	public MobNet() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JMenuBar menuBar = new JMenuBar();
		frame.setJMenuBar(menuBar);
		
		JMenu mnMobnet = new JMenu("MobNet");
		menuBar.add(mnMobnet);
		
		JMenuItem mntmAbout = new JMenuItem("About");
		mnMobnet.add(mntmAbout);
		
		JMenu mnTrajectoryNetwork = new JMenu("Trajectory Network");
		menuBar.add(mnTrajectoryNetwork);
		
		JMenuItem mntmBuildNetwork = new JMenuItem("Build Network");
		mnTrajectoryNetwork.add(mntmBuildNetwork);
		
		JMenuItem mntmView = new JMenuItem("View");
		mnTrajectoryNetwork.add(mntmView);
		
		JMenu mnPoiNetwork = new JMenu("Poi Network");
		menuBar.add(mnPoiNetwork);
		
		JMenuItem mntmBuildNetwork_1 = new JMenuItem("Build Network");
		mnPoiNetwork.add(mntmBuildNetwork_1);
		
		JMenuItem mntmCommunityDetection = new JMenuItem("Community Detection");
		mnPoiNetwork.add(mntmCommunityDetection);
		
		JMenu mnView = new JMenu("View");
		mnPoiNetwork.add(mnView);
		
		JMenuItem mntmNetwork = new JMenuItem("Network");
		mnView.add(mntmNetwork);
		
		JMenuItem mntmCommunity = new JMenuItem("Community");
		mnView.add(mntmCommunity);
		
		JXMapKit mapKit = new JXMapKit();
		mapKit.setDefaultProvider(DefaultProviders.OpenStreetMaps);
		frame.getContentPane().add(mapKit, BorderLayout.CENTER);
	}

}
