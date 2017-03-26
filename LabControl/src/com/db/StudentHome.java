package com.db;

import java.awt.Color;
import java.awt.ComponentOrientation;
import java.awt.Font;
import java.io.DataInputStream;
import java.net.Socket;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingWorker;

public class StudentHome extends JPanel {

	private static final long serialVersionUID = 1L;

	private Socket conn;
	private String ips = "127.0.0.1";

	public StudentHome() {
		ips = JOptionPane.showInputDialog(this, "Enter Server IP:");
		setLayout(null);
		MyJLabel scrollText = new MyJLabel("Pendrives Not Allowed. You are being Monitered.");
		scrollText.setBounds(121, 12, 480, 15);

		add(scrollText);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(12, 39, 577, 354);
		add(scrollPane);

		JTextArea textArea = new JTextArea();
		textArea.setEditable(false);
		textArea.getCaret().setVisible(true);
		textArea.getCaret().setSelectionVisible(true);
		textArea.setWrapStyleWord(true);
		textArea.setTabSize(2);
		textArea.setLineWrap(true);
		textArea.setForeground(new Color(255, 204, 0));
		textArea.setFont(new Font("Dialog", Font.BOLD, 15));
		textArea.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
		textArea.setCaretColor(Color.WHITE);
		textArea.setBackground(new Color(0, 0, 102));
		scrollPane.setViewportView(textArea);

		JLabel lblNewLabel = new JLabel("Instructions: ");
		lblNewLabel.setBounds(22, 12, 100, 15);
		add(lblNewLabel);

		SwingWorker<String, String> worker = new SwingWorker<String, String>() {

			protected String doInBackground() throws Exception {
				try {
					conn = new Socket(ips, 9877);
				} catch (Exception e) {
					JOptionPane.showMessageDialog(null, "Sorry! Server Not Yet Started. Login After Server is Started.",
							"Error", JOptionPane.ERROR_MESSAGE);
					e.printStackTrace();
				}

				while (true) {
					try {
						DataInputStream dis = new DataInputStream(conn.getInputStream());
						String string = dis.readUTF();
						textArea.setText(string);
					} catch (Exception e1) {
						try {
							Thread.sleep(3000);
							System.exit(0);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					}
				}
			}
		};
		worker.execute();
	}
}