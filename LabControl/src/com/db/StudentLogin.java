package com.db;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

public class StudentLogin extends JFrame {

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
					StudentLogin frame = new StudentLogin();
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
	public StudentLogin() {
		setResizable(false);
		setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		setBounds(100, 100, 490, 255);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(240, 255, 255));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		textField = new JTextField();
		textField.setFont(new Font("Times New Roman", Font.BOLD, 16));
		textField.setBounds(220, 61, 123, 20);
		contentPane.add(textField);
		textField.setColumns(10);

		passwordField = new JPasswordField();
		passwordField.setFont(new Font("Times New Roman", Font.BOLD, 16));
		passwordField.setBounds(220, 103, 123, 20);
		contentPane.add(passwordField);

		JLabel lblUsername = new JLabel("Username");
		lblUsername.setFont(new Font("Times New Roman", Font.BOLD, 16));
		lblUsername.setBounds(123, 64, 87, 14);
		contentPane.add(lblUsername);

		JLabel lblPassword = new JLabel("Password");
		lblPassword.setFont(new Font("Times New Roman", Font.BOLD, 16));
		lblPassword.setBounds(122, 106, 63, 14);
		contentPane.add(lblPassword);

		JButton btnLogin = new JButton("Login");
		btnLogin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String user, pass;
				user = textField.getText();
				pass = String.valueOf(passwordField.getPassword());

				try {
					DBManager.DBManager.getConnection();
					String query = "SELECT * FROM student WHERE username'" + user + "'AND password='" + pass + "'";

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
		btnLogin.setBackground(new Color(30, 144, 255));
		btnLogin.setFont(new Font("Times New Roman", Font.BOLD, 16));
		btnLogin.setBounds(137, 153, 90, 23);
		contentPane.add(btnLogin);

		JButton btnNewButton = new JButton("Cancel");
		btnNewButton.setBackground(new Color(30, 144, 255));
		btnNewButton.setFont(new Font("Times New Roman", Font.BOLD, 16));
		btnNewButton.setBounds(237, 153, 86, 23);
		contentPane.add(btnNewButton);

		JLabel lblTeacherLogin = new JLabel("STUDENT LOGIN");
		lblTeacherLogin.setHorizontalAlignment(SwingConstants.CENTER);
		lblTeacherLogin.setFont(new Font("Times New Roman", Font.BOLD, 17));
		lblTeacherLogin.setBounds(150, 11, 155, 23);
		contentPane.add(lblTeacherLogin);
		setIconImage(new ImageIcon("src/img/lab.png").getImage());
	}

}
