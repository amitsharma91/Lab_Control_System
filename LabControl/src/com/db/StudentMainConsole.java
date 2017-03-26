package com.db;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.net.UnknownHostException;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.border.EmptyBorder;

public class StudentMainConsole extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	Socket clentSocket;

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					new StudentMainConsole();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public StudentMainConsole() {
		super("Student's Main Console");
		
		//****************************************************************//
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 633, 474);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);

		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.add("      Home      ", new StudentHome());
		tabbedPane.add("      Chats     ", new StudentChatHome());
		contentPane.add(tabbedPane, BorderLayout.CENTER);
		
		setLocationRelativeTo(null);
		setIconImage(new ImageIcon("src/img/lab.png").getImage());
		setVisible(true);
		System.out.println(getWidth() + " ---- " + getHeight());
		//****************************************************************//
		
		//#############################//
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		try {
			clentSocket = new Socket("127.0.0.1", 56489);
		} catch (UnknownHostException e) {
			System.out.println("Some Problem in Connecting to Host>>>\n" + e);
		} catch (IOException e) {
			e.printStackTrace();
		}

		Thread forUSB = new Thread() {
			public void run() {
				while (true) {
					DataOutputStream dos;
					try {
						System.out.println(DetectUSB.generateMsg(clentSocket));
						
						dos = new DataOutputStream(clentSocket.getOutputStream());
						System.out.print("ME>>> ");
						String outgoingMsg = br.readLine();
						dos.writeUTF(outgoingMsg);
					} catch (Exception e) {
						e.printStackTrace();
					}
				} // while end
			}
		};
		forUSB.start();
		//#############################//
	}
}