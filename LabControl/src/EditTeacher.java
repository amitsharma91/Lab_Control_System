

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.ResultSet;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import DBManager.DBManager;

public class EditTeacher extends JFrame {

	private static final long serialVersionUID = 1L;

	BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

	int n;
	private JPanel contentPane;
	private JTextField textField;
	private JTextField textField_1;
	private JTextField passwordField;
	private String name;
	private String password;
	private int tid;

	private String usename;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					new EditTeacher(1);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public EditTeacher(int id) {
		super("Teacher - Edit Info");

		try {

			DBManager.getConnection();
			String query = "SELECT * FROM teacher WHERE tid=" + id;
			ResultSet rs = DBManager.getResultSet(query);
			System.out.println("Query Executed...:\t" + query);
			if (rs.next()) {
				System.out.println("into if");
				name = rs.getString("tname");
				password = rs.getString("password");
				usename = rs.getString("username");
				tid = rs.getInt("tid");
				System.out.println("Name: " + name + "\nTid: " + tid + "\nPassword: " + password);
			}
		} catch (Exception e) {
			System.out.println("ERROR: Something wentwrong in DB communication\n" + e);
		}

		setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JLabel lblAddNewFaculty = new JLabel("Edit Teacher Details");
		lblAddNewFaculty.setForeground(new Color(255, 255, 51));
		lblAddNewFaculty.setFont(new Font("Times New Roman", Font.BOLD, 20));
		lblAddNewFaculty.setBounds(115, 11, 201, 24);
		contentPane.add(lblAddNewFaculty);

		JLabel lblFacultyName = new JLabel("Faculty Name:");
		lblFacultyName.setForeground(new Color(255, 255, 255));
		lblFacultyName.setFont(new Font("Times New Roman", Font.BOLD, 14));
		lblFacultyName.setBounds(62, 63, 126, 14);
		contentPane.add(lblFacultyName);

		JLabel lblUsername = new JLabel("Username:");
		lblUsername.setForeground(new Color(255, 255, 255));
		lblUsername.setFont(new Font("Times New Roman", Font.BOLD, 14));
		lblUsername.setBounds(62, 101, 126, 14);
		contentPane.add(lblUsername);

		JLabel lblPassword = new JLabel("Password:");
		lblPassword.setForeground(new Color(255, 255, 255));
		lblPassword.setFont(new Font("Times New Roman", Font.BOLD, 14));
		lblPassword.setBounds(62, 143, 126, 14);
		contentPane.add(lblPassword);

		textField = new JTextField(name);
		textField.setFont(new Font("Times New Roman", Font.BOLD, 14));
		textField.setBounds(228, 60, 143, 20);
		contentPane.add(textField);
		textField.setColumns(10);

		textField_1 = new JTextField(usename);
		textField_1.setEditable(false);
		textField_1.setFont(new Font("Times New Roman", Font.BOLD, 14));
		textField_1.setBounds(228, 98, 143, 20);
		contentPane.add(textField_1);
		textField_1.setColumns(10);

		passwordField = new JTextField(password);
		passwordField.setFont(new Font("Times New Roman", Font.BOLD, 14));
		passwordField.setBounds(228, 140, 143, 20);
		contentPane.add(passwordField);

		JButton btnAdd = new JButton("Update Record");
		btnAdd.addActionListener(new ActionListener() {
			private Connection con;

			public void actionPerformed(ActionEvent ae) {

				try {
					con = DBManager.getConnection();
					String query = "UPDATE teacher SET tname='" + textField.getText() + "',password='"
							+ passwordField.getText() + "' WHERE tid = " + tid;
					if (con.createStatement().executeUpdate(query) > 0) {
						JOptionPane.showMessageDialog(null, "Record Updated Sucessfully..");
						dispose();
						EventQueue.invokeLater(new Runnable() {
							public void run() {
								try {
									UpdateTeacher frame = new UpdateTeacher();
									frame.setVisible(true);
								} catch (Exception e) {
									e.printStackTrace();
								}
							}
						});
					}
				} catch (Exception e) {
					System.out.println("ERROR in Database Communication: " + e);
				} finally {
				}

			}
		});

		btnAdd.setBounds(126, 193, 167, 30);
		contentPane.add(btnAdd);
		contentPane.setBackground(new Color(204, 0, 102));
		
		setIconImage(new ImageIcon("src/img/lab.png").getImage());
		setVisible(true);
		setResizable(false);
		setLocationRelativeTo(null);
	}
}
