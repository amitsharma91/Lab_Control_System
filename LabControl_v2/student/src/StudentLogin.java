
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.Calendar;

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

public class StudentLogin extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField textField;
	private JPasswordField passwordField;


	public StudentLogin() {
		super("Student Login");
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 432, 200);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(204, 51, 102));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel imageLabel = new JLabel("");
		imageLabel.setBounds(27, 17, 129, 130);
		contentPane.add(imageLabel);
		Image img = new ImageIcon(this.getClass().getResource("img/login2.png")).getImage();
		Image newimg = img.getScaledInstance(140, 140, java.awt.Image.SCALE_SMOOTH);
		imageLabel.setIcon(new ImageIcon(newimg));

		textField = new JTextField();
		textField.setFont(new Font("Times New Roman", Font.BOLD, 16));
		textField.setBounds(280, 47, 123, 20);
		contentPane.add(textField);
		textField.setColumns(10);

		passwordField = new JPasswordField();
		passwordField.setFont(new Font("Times New Roman", Font.BOLD, 16));
		passwordField.setBounds(280, 82, 123, 20);
		contentPane.add(passwordField);

		JLabel lblUsername = new JLabel("Username: ");
		lblUsername.setForeground(Color.WHITE);
		lblUsername.setFont(new Font("Times New Roman", Font.BOLD, 16));
		lblUsername.setBounds(183, 50, 87, 14);
		contentPane.add(lblUsername);

		JLabel lblPassword = new JLabel("Password: ");
		lblPassword.setForeground(Color.WHITE);
		lblPassword.setFont(new Font("Times New Roman", Font.BOLD, 16));
		lblPassword.setBounds(183, 85, 87, 14);
		contentPane.add(lblPassword);

		JButton btnLogin = new JButton("Login");
		btnLogin.setMnemonic('l');
		Image imgLogin = new ImageIcon(this.getClass().getResource("img/okk.png")).getImage();
		Image newimgLogin = imgLogin.getScaledInstance(20, 20, java.awt.Image.SCALE_SMOOTH);
		btnLogin.setIcon(new ImageIcon(newimgLogin));
		btnLogin.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
		btnLogin.setForeground(new Color(102, 102, 204));
		btnLogin.setBackground(new Color(255, 153, 0));
		btnLogin.setToolTipText("Click here to Login");
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
		btnLogin.setFont(new Font("Times New Roman", Font.BOLD, 16));
		btnLogin.setBounds(218, 124, 99, 30);
		contentPane.add(btnLogin);
		setIconImage(new ImageIcon(StudentLogin.class.getResource("/img/lab.png")).getImage());
		setLocationRelativeTo(null);
	}
}