package com.db;

import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

public class Secondform extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Secondform frame = new Secondform();
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
	public Secondform() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JLabel lblChooseYourLogin = new JLabel("CHOOSE YOUR LOGIN");
		lblChooseYourLogin.setFont(new Font("Times New Roman", Font.BOLD, 22));
		lblChooseYourLogin.setBounds(63, 11, 260, 27);
		contentPane.add(lblChooseYourLogin);

		JButton btnAdmin = new JButton("ADMIN");
		btnAdmin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				AdministratorLogin ad = new AdministratorLogin();
				ad.setVisible(true);
			}
		});
		btnAdmin.setFont(new Font("Times New Roman", Font.BOLD, 14));
		btnAdmin.setBounds(21, 111, 95, 23);
		contentPane.add(btnAdmin);

		JButton btnTeacher = new JButton("TEACHER");
		btnTeacher.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				TeacherLogin tl = new TeacherLogin();
				tl.setVisible(true);
			}
		});
		btnTeacher.setFont(new Font("Times New Roman", Font.BOLD, 14));
		btnTeacher.setBounds(162, 111, 110, 23);
		contentPane.add(btnTeacher);

		JButton btnStudent = new JButton("STUDENT");
		btnStudent.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				StudentLogin sl = new StudentLogin();
				sl.setVisible(true);
			}
		});
		btnStudent.setFont(new Font("Times New Roman", Font.BOLD, 14));
		btnStudent.setBounds(316, 111, 95, 23);
		contentPane.add(btnStudent);
		setIconImage(new ImageIcon("src/img/lab.png").getImage());
	}
}
