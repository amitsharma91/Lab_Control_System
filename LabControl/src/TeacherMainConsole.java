import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.DataInputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
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

public class TeacherMainConsole extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTabbedPane tabbedPane;
	private JButton btnLogout;
	// private static Thread main;
	ServerSocket serverSoc = null, serverSocApp = null;
	Socket clientSocket, clientSocketApp;

	public static void main(String[] args) {
		// TeacherMainConsole.main = Thread.currentThread();
		new TeacherMainConsole("username");
		/*
		 * EventQueue.invokeLater(new Runnable() { public void run() { try {
		 * TeacherMainConsole frame = new TeacherMainConsole();
		 * frame.setVisible(true); } catch (Exception e) { e.printStackTrace();
		 * } } });
		 */
	}

	public TeacherMainConsole(String username) {
		super("Teacher's Main Console");

		// ****************************************************************//
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		setBounds(100, 100, 634, 507);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.setBounds(10, 36, 608, 422);
		tabbedPane.add("      Home      ", new TecherHome());
		tabbedPane.add("   ScreenShots  ", new TeacherScreenShot());
		tabbedPane.add("      Chats     ", new TeacherChatHome());
		tabbedPane.add(" Get Client IP's", new TeacherGetStudentsList());
		contentPane.add(tabbedPane);

		btnLogout = new JButton("Logout");
		btnLogout.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				int selectedOption = JOptionPane.showConfirmDialog(null, "Are you sure you want to LOGOUT?", "Choose",
						JOptionPane.YES_NO_OPTION);
				if (selectedOption == JOptionPane.YES_OPTION) {
					PreparedStatement ps;
					try {
						Connection con = DBManager.DBManager.getConnection();
						ResultSet rs = DBManager.DBManager
								.getResultSet("SELECT * FROM teacher WHERE username='" + username + "'");
						rs.next();
						ps = con.prepareStatement("INSERT INTO teacher_history(t_stamp,process,t_id) VALUES(?,?,?)");
						Timestamp ts = new Timestamp(Calendar.getInstance().getTime().getTime());
						ps.setTimestamp(1, ts);
						ps.setString(2, "Logged OUT");
						ps.setInt(3, rs.getInt("tid"));
						if (ps.executeUpdate() > 0) {
							System.exit(0);
						}
					} catch (SQLException e) {
						e.printStackTrace();
					}
				}
			}
		});
		btnLogout.setBounds(519, 11, 89, 23);
		contentPane.add(btnLogout);

		JLabel lblUsersname = new JLabel(username + " is now LOGGED IN");
		lblUsersname.setForeground(new Color(51, 102, 102));
		lblUsersname.setBounds(199, 11, 182, 23);
		contentPane.add(lblUsersname);

		setLocationRelativeTo(null);
		setResizable(false);
		setIconImage(new ImageIcon("src/img/lab.png").getImage());
		setVisible(true);
		System.out.println(getWidth() + " ---- " + getHeight());
		// ****************************************************************//

		// &&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&//
		SwingWorker<String, String> workerUSB = new SwingWorker<String, String>() {

			protected String doInBackground() throws Exception {

				try {
					serverSoc = new ServerSocket(9519);// server Socket for
														// sending data
					System.out.println("Waiting for Client...");
				} catch (IOException e) {
					e.printStackTrace();
				}

				while (true) {
					try {
						clientSocket = serverSoc.accept();
						System.out.println("Connected to client");
						Thread forUSB = new Thread() {
							public void run() {
								while (true) {

									System.out.println(" < INTO INNER WHILE > ");

									DataInputStream dis = null;
									//DataOutputStream dos = null;
									try {
										dis = new DataInputStream(clientSocket.getInputStream());
										String incommingMsg = dis.readUTF();
										System.out.println(incommingMsg);
										if (incommingMsg.contains("USB")) {
											System.out.println("Client Msg>>> " + incommingMsg);
											new NotificationPopUpForUSB(incommingMsg);
											/*dos = new DataOutputStream(clientSocket.getOutputStream());
											System.out.println("SENDING WAAAAARRRRRNNNIINNGGGG..........");
											dos.writeUTF("WARNING to Remove Pendrive!!!");
											System.out.println("WAAAAARRRRRNNNIINNGGGG SENT..........");*/
										}
									} catch (Exception e) {
										System.out.println("Some problem in Communication with Client....\n" + e);
										try {
											Thread.sleep(3000);
											System.exit(0);
										} catch (InterruptedException ie) {
											ie.printStackTrace();
										}
									}
								} // end of while
							}// end of run()
						};// end of thread class
						forUSB.start();
					} catch (Exception e) {
						e.printStackTrace();
					} // end of outer catch
				} // end of outer-while
			}
		};
		workerUSB.execute();
		/***************************************************************/
		SwingWorker<String, String> workerApplication = new SwingWorker<String, String>() {

			protected String doInBackground() throws Exception {

				try {
					serverSocApp = new ServerSocket(9511);// server Socket for
															// sending data
					System.out.println("Waiting for Client...");
				} catch (IOException e) {
					e.printStackTrace();
				}
				while (true) {
					try {
						clientSocketApp = serverSocApp.accept();
						System.out.println("Connected to client");
						Thread forApp = new Thread() {
							public void run() {
								while (true) {

									System.out.println(" < INTO INNER WHILE - of APPCONTROL> ");

									DataInputStream diss = null;
									try {
										diss = new DataInputStream(clientSocketApp.getInputStream());
										String incommingMsg = diss.readUTF();
										System.out.println(incommingMsg);
										if (incommingMsg.contains("application")) {
											System.out.println("Client Msg>>> " + incommingMsg);
											System.out.println("---------INVOKING POPUP--------");
											new NotificationPopUpForApplications(incommingMsg);
											System.out.println("---------INVOKING POPUP DONE :) --------");
										}
									} catch (Exception e) {
										System.out.println("Some problem in Communication with Client....\n" + e);
										try {
											Thread.sleep(3000);
											System.exit(0);
										} catch (InterruptedException ie) {
											ie.printStackTrace();
										}
									}
								} // end of while
							}// end of run()
						};// end of thread class
						forApp.start();
					} catch (Exception e) {
						e.printStackTrace();
					} // end of outer catch
				} // end of outer-while
			}
		};
		workerApplication.execute();
		/***************************************************************/
		// &&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&//

		// #############################//
		/*
		 * try { // for USB-Detection on Client serverSoc = new
		 * ServerSocket(56489); System.out.println("Waiting for Client...");
		 * clientSocket = serverSoc.accept();
		 * System.out.println("Connected to client"); } catch (IOException e) {
		 * System.out.println("Some Problem while creating Server Socket....\n"
		 * + e); }
		 */

		/* Thread for detecting message from client USB-Attach-Detach */
		/*
		 * Thread forUSB = new Thread() { public void run() { try { main.join();
		 * } catch (InterruptedException e1) { e1.printStackTrace(); } while
		 * (true) {
		 * 
		 * try { DataInputStream dis = new
		 * DataInputStream(clientSocket.getInputStream()); String incommingMsg =
		 * dis.readUTF(); if (incommingMsg.contains("USB")) {
		 * System.out.println("Client Msg>>> " + incommingMsg); new
		 * NotificationPopup(incommingMsg); } } catch (Exception e) {
		 * System.out.println("Some problem in Communication with Client....\n"
		 * + e); try { Thread.sleep(3000); System.exit(0); } catch
		 * (InterruptedException ie) { ie.printStackTrace(); } } } } };
		 * forUSB.start();
		 */
		// #############################//
	}
}