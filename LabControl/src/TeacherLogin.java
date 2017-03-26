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
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
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
				user = textField.getText().trim();
				pass = String.valueOf(passwordField.getPassword()).trim();

				if (user.equals("") || pass.equals("")) {
					JOptionPane.showMessageDialog(null, "Please fill all the credentials", "Error",
							JOptionPane.ERROR_MESSAGE);
				} else {
					try {
						Connection con = DBManager.DBManager.getConnection();
						String query = "SELECT * FROM teacher WHERE username='" + user + "' AND password='" + pass
								+ "'";

						ResultSet rs = DBManager.DBManager.getResultSet(query);
						if (rs.next()) {
							PreparedStatement ps = con.prepareStatement(
									"INSERT INTO teacher_history(t_stamp,process,t_id) VALUES(?,?,?)");
							Timestamp ts = new Timestamp(Calendar.getInstance().getTime().getTime());
							ps.setTimestamp(1, ts);
							ps.setString(2, "Logged in");
							ps.setInt(3, rs.getInt("tid"));
							if (ps.executeUpdate() > 0) {
								System.out.println("Login Successfull "+rs.getString("username"));
								String paraStringUsername = rs.getString("username");
								dispose();
								// new TeacherMainConsole();
								EventQueue.invokeLater(new Runnable() {
									public void run() {
										try {
											TeacherMainConsole frame = new TeacherMainConsole(paraStringUsername);
											frame.setVisible(true);
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
							System.out.println("Failed to login**");
						}
					} catch (Exception e) {

					} finally {
						DBManager.DBManager.closeConnection();
					}
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
		setResizable(false);
		setIconImage(new ImageIcon("src/img/lab.png").getImage());
		setLocationRelativeTo(null);
	}
}