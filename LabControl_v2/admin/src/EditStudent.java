

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

public class EditStudent extends JFrame {

	private static final long serialVersionUID = 1L;

	BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

	int n;
	private JPanel contentPane;
	private JTextField textField;
	private JTextField textField_1;
	private JTextField passwordField;
	private Connection con = null;
	String name=null,password=null;
	String roll = "";


	/**
	 * Create the frame.
	 */
	public EditStudent(String id) throws Exception {
		
		super("Student - Edit Info");
		
		try{
			
			con = DBManager.getConnection();
			String query = "SELECT * FROM student WHERE roll='"+id+"'";
			ResultSet rs = DBManager.getResultSet(query);
			System.out.println("Query Executed...:\t"+query);
			if(rs.next()){
				System.out.println("into if");
				name = rs.getString("name");
				password = rs.getString("password");
				roll = rs.getString("roll");
				System.out.println("Name: "+name+"\nRoll.No: "+roll+"\nPassword: "+password);
			}
		}catch(Exception e){
			System.out.println("ERROR: Something wentwrong in DB communication\n"+e);
		}
		
		setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblAddNewStudent = new JLabel("Edit Student Details");
		lblAddNewStudent.setForeground(Color.YELLOW);
		lblAddNewStudent.setFont(new Font("Times New Roman", Font.BOLD, 20));
		lblAddNewStudent.setBounds(99, 23, 234, 29);
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
		
		textField = new JTextField(name);
		textField.setFont(new Font("Times New Roman", Font.BOLD, 14));
		textField.setBounds(237, 75, 96, 20);
		contentPane.add(textField);
		textField.setColumns(10);
		
		textField_1 = new JTextField(""+roll);
		textField_1.setEditable(false);
		textField_1.setFont(new Font("Times New Roman", Font.BOLD, 14));
		textField_1.setBounds(237, 116, 96, 20);
		contentPane.add(textField_1);
		textField_1.setColumns(10);
		
		passwordField = new JTextField(""+password);
		passwordField.setFont(new Font("Times New Roman", Font.BOLD, 14));
		passwordField.setBounds(237, 152, 96, 20);
		contentPane.add(passwordField);
		passwordField.requestFocusInWindow();
		
		JButton btnRegister = new JButton("Update");
		btnRegister.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try{
					con = DBManager.getConnection();
					String query="UPDATE student SET name='"+textField.getText()+"',password='"+passwordField.getText()+"' WHERE roll = '"+roll+"'";
					if(con.createStatement().executeUpdate(query)>0){
						JOptionPane.showMessageDialog(null, "Record Updated Sucessfully..");
						dispose();
						EventQueue.invokeLater(new Runnable() {
							public void run() {
								try {
									UpdateStudent frame = new UpdateStudent();
									frame.setVisible(true);
								} catch (Exception e) {
									e.printStackTrace();
								}
							}
						});
					}
				}catch(Exception e){
					System.out.println("ERROR in Database Communication: "+e);
				}finally{					
				}
			}
			
	});
		btnRegister.setBounds(156, 209, 118, 23);
		contentPane.add(btnRegister);
		contentPane.setBackground(new Color(204, 0, 102));
		setLocationRelativeTo(null);
		setIconImage(new ImageIcon(EditStudent.class.getResource("/img/lab.png")).getImage());
	}
}