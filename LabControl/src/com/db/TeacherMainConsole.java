package com.db;

import java.awt.BorderLayout;
import java.io.DataInputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.border.EmptyBorder;

public class TeacherMainConsole extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private static Thread main;
	ServerSocket serverSoc = null;
	Socket clientSocket;

	public static void main(String[] args) {
		TeacherMainConsole.main = Thread.currentThread();
		 new TeacherMainConsole();
		/*EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					TeacherMainConsole frame = new TeacherMainConsole();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});*/
	}

	public TeacherMainConsole() {
		super("Teacher's Main Console");
		
		//****************************************************************//
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 634, 470);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);

		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.add("      Home      ", new TecherHome());
		tabbedPane.add("   ScreenShots  ", new TeacherScreenShot());
		tabbedPane.add("      Chats     ", new TeacherChatHome());
		tabbedPane.add(" Get Client IP's", new TeacherGetStudentsList());
		contentPane.add(tabbedPane, BorderLayout.CENTER);
		
		setLocationRelativeTo(null);
		setIconImage(new ImageIcon("src/img/lab.png").getImage());
		setVisible(true);
		System.out.println(getWidth() + " ---- " + getHeight());
		//****************************************************************//
		
		//#############################//
		try {
			// for USB-Detection on Client
			serverSoc = new ServerSocket(56489);
			System.out.println("Waiting for Client...");
			clientSocket = serverSoc.accept();
			System.out.println("Connected to client");
		} catch (IOException e) {
			System.out.println("Some Problem while creating Server Socket....\n" + e);
		}

		/* Thread for detecting message from client USB-Attach-Detach */
		Thread forUSB = new Thread() {
			public void run() {
				try {
					main.join();
				} catch (InterruptedException e1) {
					e1.printStackTrace();
				}
				while (true) {

					try {
						DataInputStream dis = new DataInputStream(clientSocket.getInputStream());
						String incommingMsg = dis.readUTF();
						if (incommingMsg.contains("USB")) {
							System.out.println("Client Msg>>> " + incommingMsg);
							new NotificationPopup(incommingMsg);
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
				}
			}
		};
		forUSB.start();
		//#############################//
	}
}