import java.awt.Color;
import java.awt.EventQueue;
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
	Socket clientSocket,clientSocketApp;

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					new StudentMainConsole("Username");
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public StudentMainConsole(String username) {
		super("Student's Main Console");

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
		tabbedPane.add("      Home      ", new StudentHome());
		tabbedPane.add("      Chats     ", new StudentChatHome());
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
						if(ps.executeUpdate()>0){
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
		
		JLabel label = new JLabel(username+" is now LOGGED IN");
		label.setForeground(new Color(51, 102, 102));
		label.setBounds(209, 11, 182, 23);
		contentPane.add(label);

		setLocationRelativeTo(null);
		setIconImage(new ImageIcon("src/img/lab.png").getImage());
		setVisible(true);
		System.out.println(getWidth() + " ---- " + getHeight());
		// ****************************************************************//

		// &&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&//
		SwingWorker<String, String> worker = new SwingWorker<String, String>() {

			protected String doInBackground() throws Exception {
				BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
				try {
					clientSocket = new Socket("127.0.0.1", 9519);
				} catch (UnknownHostException e) {
					System.out.println("Some Problem in Connecting to Host>>>\n" + e);
				} catch (IOException e) {
					e.printStackTrace();
				}
				Thread forUSB = new Thread() {
					public void run() {
						
						int x = 0;
						while (true) {
							
							DataOutputStream dos;
							try {
								
								System.out.println(" { " + (++x) + " } " + DetectUSB.generateMsg(clientSocket,username));
								dos = new DataOutputStream(clientSocket.getOutputStream());
								System.out.print("ME>>> ");
								String outgoingMsg = br.readLine();
								dos.writeUTF(outgoingMsg);
								
							} catch (Exception e) {
								e.printStackTrace();
							}
						}
					}
				};
				forUSB.start();
				return null;
			}
		};
		 worker.execute();
		 /***************************************************************/
		 SwingWorker<String, String> workerApp = new SwingWorker<String, String>() {

				protected String doInBackground() throws Exception {
					BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
					try {
						clientSocketApp = new Socket("127.0.0.1", 9511);
					} catch (UnknownHostException e) {
						System.out.println("Some Problem in Connecting to Host>>>\n" + e);
					} catch (IOException e) {
						e.printStackTrace();
					}
					Thread forApp = new Thread() {
						public void run() {
							
							int x = 0;
							while (true) {
								
								DataOutputStream doss;
								try {
									
									System.out.println(" { " + (++x) + " } " + DetectUSB.generateAppMsg(clientSocketApp,username));
									doss = new DataOutputStream(clientSocketApp.getOutputStream());
									System.out.print("ME>>> ");
									String outgoingMsg = br.readLine();
									doss.writeUTF(outgoingMsg);
									
								} catch (Exception e) {
									e.printStackTrace();
								}
							}
						}
					};
					forApp.start();
					return null;
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