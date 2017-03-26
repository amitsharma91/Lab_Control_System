
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.Calendar;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
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

	public StudentLogin() {
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
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
				user = textField.getText().trim();
				pass = String.valueOf(passwordField.getPassword()).trim();
				if (user.equals("") || pass.equals("")) {
					JOptionPane.showMessageDialog(null, "Please fill all the credentials", "Error",
							JOptionPane.ERROR_MESSAGE);
				} else {
					try {
						Connection con = DBManager.DBManager.getConnection();
						String query = "SELECT * FROM student WHERE roll='" + user + "' AND password='" + pass + "'";

						ResultSet rs = DBManager.DBManager.getResultSet(query);
						if (rs.next()) {
							PreparedStatement ps = con.prepareStatement("INSERT INTO student_history(t_stamp,process,sid) VALUES(?,?,?)");
							Timestamp ts = new Timestamp(Calendar.getInstance().getTime().getTime());
							ps.setTimestamp(1, ts);
							ps.setString(2, "Logged in");
							ps.setString(3, user);
							if(ps.executeUpdate()>0){
								System.out.println("Login Successfull");
								dispose();
								EventQueue.invokeLater(new Runnable() {
									public void run() {
										try {
											new StudentMainConsole(user);
										} catch (Exception e) {
											e.printStackTrace();
										}
									}
								});
							}
						} else {
							JOptionPane.showMessageDialog(null, "Failed to Login", "Error", JOptionPane.ERROR_MESSAGE);
							textField.setText("");
							passwordField.setText("");
							textField.requestFocusInWindow();
							System.out.println("Failed to login");
						}
					} catch (Exception e) {
					} finally {
						DBManager.DBManager.closeConnection();
					}
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
		setLocationRelativeTo(null);
	}
}