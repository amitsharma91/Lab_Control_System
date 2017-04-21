import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.net.UnknownHostException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Calendar;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.SwingWorker;
import javax.swing.border.EmptyBorder;

public class StudentMainConsole extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	Socket clientSocket, clientSocketApp;
	private static String serverIP = "";

	public static String getServerIp() {
		return serverIP;
	}

	public boolean checkIP(String ip) {
		System.out.println("Received-IP: " + ip.trim());
		String[] parts = ip.trim().split("\\.");
		if (parts.length != 4) {
			System.out.println("length invalid: " + parts.length);
			return false;
		}

		for (String s : parts) {
			int i = Integer.parseInt(s);
			if ((i < 0) || (i > 255)) {
				System.out.println("Invalid Unit: " + i);
				return false;
			}
		}
		if (ip.trim().endsWith(".") || ip.trim().startsWith(".")) {
			System.out.println("dot at end or begining..");
			return false;
		}
		return true;
	}

	public StudentMainConsole(String username) {
		super("Student's Main Console");
		System.out.println("getting");
		serverIP = JOptionPane.showInputDialog(this, "Enter Server IP:", "");

		while (serverIP == null) {
			String opt[] = { "Exit", "Try Again" };
			int val = JOptionPane.showOptionDialog(null, "This will CLOSE you Application", "Warning!",
					JOptionPane.YES_NO_OPTION, JOptionPane.ERROR_MESSAGE, null, opt, opt[0]);
			if (val == JOptionPane.YES_OPTION) {
				System.exit(0);
			}
			if (val == JOptionPane.NO_OPTION)
				serverIP = JOptionPane.showInputDialog(this, "Enter Server IP:", "");
			// check for empty
			while (serverIP.trim() != null && serverIP.trim().equals("")) {
				JOptionPane.showMessageDialog(null, "IP Bannot be Empty", "ERROR!", JOptionPane.ERROR_MESSAGE);
				serverIP = JOptionPane.showInputDialog(this, "Enter Server IP:");
				if (serverIP == null)
					break;
			}
		}

		while (serverIP.trim() != null && serverIP.trim().equals("")) {
			JOptionPane.showMessageDialog(null, "IP Bannot be Empty", "ERROR!", JOptionPane.ERROR_MESSAGE);
			serverIP = JOptionPane.showInputDialog(this, "Enter Server IP:");
			// check for null
			while (serverIP == null) {
				String opt[] = { "Exit", "Try Again" };
				int val = JOptionPane.showOptionDialog(null, "This will CLOSE you Application", "Warning!",
						JOptionPane.YES_NO_OPTION, JOptionPane.ERROR_MESSAGE, null, opt, opt[0]);
				if (val == JOptionPane.YES_OPTION) {
					System.exit(0);
				}
				if (val == JOptionPane.NO_OPTION)
					serverIP = JOptionPane.showInputDialog(this, "Enter Server IP:", "");
			}
		}

		if (!checkIP(serverIP)) {
			JOptionPane.showMessageDialog(null, "Sorry Invalid Server-IP! Re-Login");
			System.exit(0);
		}

		// ****************************************************************//
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		setResizable(false);
		setBounds(100, 100, 634, 495);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.setBounds(10, 38, 607, 408);
		System.out.println("***********************this is fake: " + serverIP);
		tabbedPane.add("      Home      ", new StudentHome());
		contentPane.add(tabbedPane);

		JButton btnNewButton = new JButton("Logout");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

				int selectedOption = JOptionPane.showConfirmDialog(null, "Are you sure you want to LOGOUT?", "Choose",
						JOptionPane.YES_NO_OPTION);
				if (selectedOption == JOptionPane.YES_OPTION) {
					PreparedStatement ps;
					try {
						Connection con = DBManager.DBManager.getConnection();
						ps = con.prepareStatement("INSERT INTO student_history(t_stamp,process,sid) VALUES(?,?,?)");
						Timestamp ts = new Timestamp(Calendar.getInstance().getTime().getTime());
						ps.setTimestamp(1, ts);
						ps.setString(2, "Logged OUT");
						ps.setString(3, username);
						if (ps.executeUpdate() > 0) {
							System.exit(0);
						}
					} catch (SQLException e) {
						e.printStackTrace();
					}
				}

			}
		});
		btnNewButton.setBounds(519, 4, 89, 23);
		contentPane.add(btnNewButton);

		JLabel label = new JLabel(username + " is now LOGGED IN");
		label.setForeground(new Color(51, 102, 102));
		label.setBounds(209, 11, 182, 23);
		contentPane.add(label);

		setLocationRelativeTo(null);
		setIconImage(new ImageIcon(StudentMainConsole.class.getResource("/img/lab.png")).getImage());
		setVisible(true);
		System.out.println(getWidth() + " ---- " + getHeight());
		// ****************************************************************//

		// %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%//
		// FOR SCREENSHOT

		final Thread t1 = new Thread() {
			public void run() {
				try {
					ClientScreenshot.getscreen(serverIP);
				} catch (Exception e) {
					e.printStackTrace();
					System.out.println("Something went wrong at Client-Side while Execution FOR SCREENSHOT");
				}
			}
		};
		t1.start();

		Thread t2 = new Thread() {
			public void run() {
				try {
					t1.join();
					ClientScreenshot.sendFile(serverIP);
				} catch (Exception e) {
					e.printStackTrace();
					System.out.println("Something went wrong at Client-Side while Execution FOR SENDING FILE");
				}
			}
		};
		t2.start();

		// %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%//

		// &&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&//
		SwingWorker<String, String> worker = new SwingWorker<String, String>() {

			protected String doInBackground() throws Exception {
				BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
				try {
					clientSocket = new Socket(serverIP, 9519);
				} catch (UnknownHostException e) {
					System.out.println("Some Problem in Connecting to Host>>>\n" + e);
					JOptionPane.showMessageDialog(null, "Error in USB Thread");
				} catch (IOException e) {
					e.printStackTrace();
				}
				int x = 0;
				while (true) {

					DataOutputStream dos;
					try {

						System.out.println(" { " + (++x) + " } " + DetectUSB.generateMsg(clientSocket, username));
						dos = new DataOutputStream(clientSocket.getOutputStream());
						System.out.print("ME>>> ");
						String outgoingMsg = br.readLine();
						dos.writeUTF(outgoingMsg);

					} catch (Exception e) {
						e.printStackTrace();
					}
					return null;
				}

			}
		};
		worker.execute();
		/***************************************************************/
		SwingWorker<String, String> workerApp = new SwingWorker<String, String>() {

			protected String doInBackground() throws Exception {
				BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
				try {
					clientSocketApp = new Socket(serverIP, 9511);
				} catch (UnknownHostException e) {
					System.out.println("Some Problem in Connecting to Host>>>\n" + e);
					JOptionPane.showMessageDialog(null, "Error in App Thread");
				} catch (IOException e) {
					e.printStackTrace();
				}

				int x = 0;
				while (true) {
					DataOutputStream doss;
					try {

						System.out.println(" { " + (++x) + " } " + DetectUSB.generateAppMsg(clientSocketApp, username));
						doss = new DataOutputStream(clientSocketApp.getOutputStream());
						System.out.print("ME>>> ");
						String outgoingMsg = br.readLine();
						doss.writeUTF(outgoingMsg);

					} catch (Exception e) {
						e.printStackTrace();
					}
					return null;
				}
			}
		};
		workerApp.execute();
		/***************************************************************/
		// &&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&//

		// #############################//
		/*
		 * BufferedReader br = new BufferedReader(new
		 * InputStreamReader(System.in)); try { clentSocket = new
		 * Socket("127.0.0.1", 56489); } catch (UnknownHostException e) {
		 * System.out.println("Some Problem in Connecting to Host>>>\n" + e); }
		 * catch (IOException e) { e.printStackTrace(); }
		 * 
		 * Thread forUSB = new Thread() { public void run() { int x = 0; while
		 * (true) { DataOutputStream dos; try {
		 * System.out.println(" { "+(++x)+" } "+DetectUSB.generateMsg(
		 * clentSocket));
		 * 
		 * dos = new DataOutputStream(clentSocket.getOutputStream());
		 * System.out.print("ME>>> "); String outgoingMsg = br.readLine();
		 * dos.writeUTF(outgoingMsg); } catch (Exception e) {
		 * e.printStackTrace(); } } // while end } }; forUSB.start();
		 */
		// #############################//
	}
}