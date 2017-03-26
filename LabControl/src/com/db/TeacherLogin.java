package com.db;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.ResultSet;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

public class TeacherLogin extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField textField;
	private JPasswordField passwordField;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					TeacherLogin frame = new TeacherLogin();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public TeacherLogin() {
		setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		setBounds(100, 100, 490, 256);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(240, 255, 255));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JLabel lblTecherLogin = new JLabel("TEACHER LOGIN");
		lblTecherLogin.setFont(new Font("Times New Roman", Font.BOLD, 17));
		lblTecherLogin.setBounds(156, 11, 162, 27);
		contentPane.add(lblTecherLogin);

		JLabel lblUsername = new JLabel("Username");
		lblUsername.setFont(new Font("Times New Roman", Font.BOLD, 16));
		lblUsername.setBounds(122, 69, 87, 14);
		contentPane.add(lblUsername);

		JLabel lblPassword = new JLabel("Password");
		lblPassword.setFont(new Font("Times New Roman", Font.BOLD, 16));
		lblPassword.setBounds(122, 100, 87, 14);
		contentPane.add(lblPassword);

		textField = new JTextField();
		textField.setFont(new Font("Times New Roman", Font.BOLD, 16));
		textField.setBounds(217, 66, 114, 20);
		contentPane.add(textField);
		textField.setColumns(10);

		passwordField = new JPasswordField();
		passwordField.setFont(new Font("Times New Roman", Font.BOLD, 16));
		passwordField.setBounds(219, 97, 114, 20);
		contentPane.add(passwordField);

		JButton btnLogin = new JButton("Login");
		btnLogin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String user, pass;
				user = textField.getText();
				pass = passwordField.getText();

				Connection con = null;
				try {
					con = DBManager.DBManager.getConnection();
					String query = "SELECT * FROM Login WHERE username='user',password='pass'";

					ResultSet rs = DBManager.DBManager.getResultSet(query);
					if (rs.next()) {
						System.out.println("Login Successfull");
					} else
						System.out.println("Failed to login");
				} catch (Exception e) {

				} finally {
					DBManager.DBManager.closeConnection();
				}
			}
		});
		btnLogin.setBackground(new Color(135, 206, 250));
		btnLogin.setFont(new Font("Times New Roman", Font.BOLD, 16));
		btnLogin.setBounds(141, 140, 81, 23);
		contentPane.add(btnLogin);

		JButton btnCancel = new JButton("Cancel");
		btnCancel.setBackground(new Color(135, 206, 250));
		btnCancel.setFont(new Font("Times New Roman", Font.BOLD, 16));
		btnCancel.setBounds(232, 140, 86, 23);
		contentPane.add(btnCancel);
		setIconImage(new ImageIcon("src/img/lab.png").getImage());
	}

}
