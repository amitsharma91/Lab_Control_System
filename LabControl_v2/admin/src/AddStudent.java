import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.border.BevelBorder;
import javax.swing.border.EmptyBorder;

import DBManager.DBManager;

public class AddStudent extends JFrame {

	private static final long serialVersionUID = 1L;

	BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

	int n;

	private JPanel contentPane;
	private JTextField textField;
	private JTextField textField_1;
	private JPasswordField passwordField;
	private JPasswordField passwordField_1;
	private JComboBox<String> comboBox;

	/**
	 * Create the frame.
	 */
	public AddStudent() throws Exception {
		super("Student Registration");
		setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		setBounds(100, 100, 483, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JLabel lblAddNewStudent = new JLabel("Register New Student");
		lblAddNewStudent.setForeground(Color.YELLOW);
		lblAddNewStudent.setFont(new Font("Times New Roman", Font.BOLD, 20));
		lblAddNewStudent.setBounds(135, 24, 208, 29);
		contentPane.add(lblAddNewStudent);

		JLabel lblStudentName = new JLabel("Student Name:");
		lblStudentName.setForeground(Color.WHITE);
		lblStudentName.setFont(new Font("Times New Roman", Font.BOLD, 14));
		lblStudentName.setBounds(80, 78, 142, 14);
		contentPane.add(lblStudentName);

		JLabel lblStudentsRollno = new JLabel("Student's Roll No:");
		lblStudentsRollno.setForeground(Color.WHITE);
		lblStudentsRollno.setFont(new Font("Times New Roman", Font.BOLD, 14));
		lblStudentsRollno.setBounds(81, 119, 142, 14);
		contentPane.add(lblStudentsRollno);

		JLabel lblPassword = new JLabel("Password:");
		lblPassword.setForeground(Color.WHITE);
		lblPassword.setFont(new Font("Times New Roman", Font.BOLD, 14));
		lblPassword.setBounds(81, 155, 142, 14);
		contentPane.add(lblPassword);

		JLabel lblConfirmPassword = new JLabel("Confirm Password:");
		lblConfirmPassword.setForeground(Color.WHITE);
		lblConfirmPassword.setFont(new Font("Times New Roman", Font.BOLD, 14));
		lblConfirmPassword.setBounds(81, 190, 151, 14);
		contentPane.add(lblConfirmPassword);

		textField = new JTextField();
		textField.setFont(new Font("Times New Roman", Font.BOLD, 14));
		textField.setBounds(237, 75, 96, 20);
		contentPane.add(textField);
		textField.setColumns(10);

		textField_1 = new JTextField();
		textField_1.setFont(new Font("Times New Roman", Font.BOLD, 14));
		textField_1.setBounds(237, 116, 96, 20);
		contentPane.add(textField_1);
		textField_1.setColumns(10);

		passwordField = new JPasswordField();
		passwordField.setFont(new Font("Times New Roman", Font.BOLD, 14));
		passwordField.setBounds(237, 152, 96, 20);
		contentPane.add(passwordField);

		passwordField_1 = new JPasswordField();
		passwordField_1.setFont(new Font("Times New Roman", Font.BOLD, 14));
		passwordField_1.setBounds(237, 187, 96, 20);
		contentPane.add(passwordField_1);

		JButton btnRegister = new JButton("Register Student");
		btnRegister.setMnemonic('l');
		Image imgLogin = new ImageIcon(this.getClass().getResource("img/register.png")).getImage();
		Image newimgLogin = imgLogin.getScaledInstance(20, 20, java.awt.Image.SCALE_SMOOTH);
		btnRegister.setIcon(new ImageIcon(newimgLogin));
		btnRegister.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
		btnRegister.setForeground(new Color(102, 102, 204));
		btnRegister.setBackground(new Color(255, 153, 0));
		btnRegister.setToolTipText("Click here to Register");
		btnRegister.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(textField.getText().trim().equals("") || textField_1.getText().trim().equals("") || String.valueOf(passwordField.getPassword()).equals("") || String.valueOf(passwordField_1.getPassword()).equals("")){
					JOptionPane.showMessageDialog(null, "Please fill in all the Credentials","Error", JOptionPane.ERROR_MESSAGE);
				}
				else{
					if(comboBox.getSelectedItem().equals("--Select Class--")){
						JOptionPane.showMessageDialog(null, "Please Select Class","Error", JOptionPane.ERROR_MESSAGE);
						comboBox.requestFocusInWindow();
					}else{
						try {
							Connection con = null;
							con = DBManager.getConnection();
							String name, password, confrim;
							name = textField.getText();
							password = String.valueOf(passwordField.getPassword());
							confrim = String.valueOf(passwordField_1.getPassword());
							String roll = comboBox.getSelectedItem()+textField_1.getText();

							ResultSet check = DBManager.getResultSet("SELECT roll FROM student WHERE roll='" + roll+"'");
							if (check.next()) {
								JOptionPane.showMessageDialog(null, "Sorry Student Already Exists, with same Roll Number.",
										"Error", JOptionPane.ERROR_MESSAGE);
								textField.setText("");
								textField_1.setText("");
								passwordField.setText("");
								passwordField_1.setText("");
								textField.requestFocusInWindow();
							} else {
								if (password.equals(confrim)) {
									PreparedStatement pstmt = (PreparedStatement) con
											.prepareStatement("INSERT INTO student(roll,name,password) VALUES(?,?,?)");

									pstmt.setString(1, roll);
									pstmt.setString(2, name);
									pstmt.setString(3, password);

									pstmt.executeUpdate();
									JOptionPane.showMessageDialog(null, "Student Sucessfully Added\nUsername: "+roll);	
									System.out.println("Record Saved successfully");
									dispose();
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
						}
					}					
				}				
			}
		});
		btnRegister.setBounds(176, 226, 138, 35);
		contentPane.add(btnRegister);
		contentPane.setBackground(new Color(204, 0, 102));
		
		comboBox = new JComboBox<String>();
		comboBox.addItem("--Select Class--");
		comboBox.addItem("fybca");
		comboBox.addItem("sybca");
		comboBox.addItem("tybca");
		comboBox.addItem("fybcs");
		comboBox.addItem("sybcs");
		comboBox.addItem("tybcs");
		comboBox.setBounds(343, 117, 118, 20);
		contentPane.add(comboBox);
		
		setIconImage(new ImageIcon(AddStudent.class.getResource("/img/lab.png")).getImage());
		setVisible(true);
		setResizable(false);
		setLocationRelativeTo(null);
	}
}