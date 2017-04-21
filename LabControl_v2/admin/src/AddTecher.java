
import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.border.BevelBorder;
import javax.swing.border.EmptyBorder;

import DBManager.DBManager;

public class AddTecher extends JFrame {

	private static final long serialVersionUID = 1L;

	BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

	int n;
	private JPanel contentPane;
	private JTextField textField;
	private JTextField textField_1;
	private JPasswordField passwordField;
	private JPasswordField passwordField_1;

	/**
	 * Launch the application.
	 */

	public AddTecher(String title) {
		super(title);
		setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JLabel lblAddNewFaculty = new JLabel("Register New Teacher");
		lblAddNewFaculty.setForeground(new Color(255, 255, 51));
		lblAddNewFaculty.setFont(new Font("Times New Roman", Font.BOLD, 20));
		lblAddNewFaculty.setBounds(130, 11, 201, 24);
		contentPane.add(lblAddNewFaculty);

		JLabel lblFacultyName = new JLabel("Faculty Name:");
		lblFacultyName.setForeground(new Color(255, 255, 255));
		lblFacultyName.setFont(new Font("Times New Roman", Font.BOLD, 14));
		lblFacultyName.setBounds(62, 63, 126, 14);
		contentPane.add(lblFacultyName);

		JLabel lblUsername = new JLabel("Username:");
		lblUsername.setForeground(new Color(255, 255, 255));
		lblUsername.setFont(new Font("Times New Roman", Font.BOLD, 14));
		lblUsername.setBounds(62, 94, 126, 14);
		contentPane.add(lblUsername);

		JLabel lblPassword = new JLabel("Password:");
		lblPassword.setForeground(new Color(255, 255, 255));
		lblPassword.setFont(new Font("Times New Roman", Font.BOLD, 14));
		lblPassword.setBounds(62, 126, 126, 14);
		contentPane.add(lblPassword);

		JLabel lblConfirmPassword = new JLabel("Confirm Password:");
		lblConfirmPassword.setForeground(new Color(255, 255, 255));
		lblConfirmPassword.setFont(new Font("Times New Roman", Font.BOLD, 14));
		lblConfirmPassword.setBounds(62, 162, 163, 14);
		contentPane.add(lblConfirmPassword);

		textField = new JTextField();
		textField.setFont(new Font("Times New Roman", Font.BOLD, 14));
		textField.setBounds(228, 60, 143, 20);
		contentPane.add(textField);
		textField.setColumns(10);

		textField_1 = new JTextField();
		textField_1.setFont(new Font("Times New Roman", Font.BOLD, 14));
		textField_1.setBounds(228, 91, 143, 20);
		contentPane.add(textField_1);
		textField_1.setColumns(10);

		passwordField = new JPasswordField();
		passwordField.setFont(new Font("Times New Roman", Font.BOLD, 14));
		passwordField.setBounds(228, 123, 143, 20);
		contentPane.add(passwordField);

		passwordField_1 = new JPasswordField();
		passwordField_1.setFont(new Font("Times New Roman", Font.BOLD, 14));
		passwordField_1.setBounds(228, 159, 143, 20);
		contentPane.add(passwordField_1);

		JButton btnAdd = new JButton("Register Teacher");
		btnAdd.setMnemonic('l');
		Image imgLogin = new ImageIcon(this.getClass().getResource("img/register.png")).getImage();
		Image newimgLogin = imgLogin.getScaledInstance(20, 20, java.awt.Image.SCALE_SMOOTH);
		btnAdd.setIcon(new ImageIcon(newimgLogin));
		btnAdd.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
		btnAdd.setForeground(new Color(102, 102, 204));
		btnAdd.setBackground(new Color(255, 153, 0));
		btnAdd.setToolTipText("Click here to Add Teacher");
		btnAdd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

				String name = textField.getText().trim();
				String username = textField_1.getText().trim();
				String password = String.valueOf(passwordField.getPassword()).trim();
				String confirmPassword = String.valueOf(passwordField_1.getPassword()).trim();

				if(name.equals("") || username.equals("") || password.equals("") || confirmPassword.equals("")){
					JOptionPane.showMessageDialog(null, "Please fill in all Credentials",
							"Error", JOptionPane.ERROR_MESSAGE);
				}
				else{
					Connection con = DBManager.getConnection();
					ResultSet check = DBManager
							.getResultSet("SELECT username FROM teacher WHERE username='" + username + "'");
					try {
						if (check.next()) {
							JOptionPane.showMessageDialog(null, "Sorry username Already Exists, Try something else.",
									"Error", JOptionPane.ERROR_MESSAGE);
							textField.setText("");
							textField_1.setText("");
							passwordField.setText("");
							passwordField_1.setText("");
							textField.requestFocusInWindow();
						} else {
							System.out.println("User not Present");
							if (password.equals(confirmPassword)) {

								String query = "INSERT INTO teacher(tname,username,password) VALUES('" + name + "','"
										+ username + "','" + password + "')";
								Statement st = con.createStatement();
								if (st.executeUpdate(query) > 0) {
									System.out.println("Sucessfully Registered...");
									JOptionPane.showMessageDialog(null, "Teacher Sucessfully Added");
									dispose();
								}
							} else {
								JOptionPane.showMessageDialog(null, "Sorry Password and Confirm Password Not Matches",
										"Error", JOptionPane.ERROR_MESSAGE);
								passwordField.setText("");
								passwordField_1.setText("");
								passwordField.requestFocusInWindow();
							}
						}
					} catch (Exception e) {
						e.printStackTrace();
					} finally {
						DBManager.closeConnection();
					}
				}
			}
		});
		btnAdd.setBounds(166, 201, 137, 31);
		contentPane.add(btnAdd);
		contentPane.setBackground(new Color(204, 0, 102));
		setIconImage(new ImageIcon(AddTecher.class.getResource("/img/lab.png")).getImage());
		setVisible(true);
		setResizable(false);
		setLocationRelativeTo(null);
	}
}