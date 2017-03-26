package com.db;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.border.BevelBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import DBManager.DBManager;

public class RemoveStudent extends JFrame implements ActionListener{

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTable table;
	private Object[][] records;
	private int rows_no= 0;
	private JTextField textField;
	private JScrollPane scrollPane;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					new RemoveStudent();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	@SuppressWarnings("deprecation")
	public RemoveStudent() {
		super("Student - Delete Record");
		setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		setResizable(false);
		setBounds(100, 100, 539, 386);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(204, 0, 102));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		scrollPane = new JScrollPane();
		scrollPane.setBounds(12, 40, 501, 201);
		contentPane.add(scrollPane);		
		
		records = getData();		
		table = new JTable();
		table.getTableHeader().setBackground(new Color(153, 153, 204));
		table.getTableHeader().setForeground(Color.WHITE);
		table.setModel(new DefaultTableModel(
				records,
			new String[] {
				"Roll no", "Student Name", "Username"
			}
		));
		table.enable(false);
		scrollPane.setViewportView(table);
		
		JLabel lblEnterTeacherId = new JLabel("Enter Student ID  to Delete Record:");
		lblEnterTeacherId.setForeground(Color.WHITE);
		lblEnterTeacherId.setBounds(41, 269, 249, 24);
		contentPane.add(lblEnterTeacherId);
		
		textField = new JTextField();
		textField.setBorder(BorderFactory.createLineBorder(Color.WHITE));
		textField.setBounds(308, 270, 59, 24);
		contentPane.add(textField);
		textField.requestFocusInWindow();
		textField.setColumns(10);
		
		JButton btnDeleteRecord = new JButton("Delete Record");
		//Image imgLogin = new ImageIcon(this.getClass().getResource("/delete.png")).getImage();
		//Image newimgLogin = imgLogin.getScaledInstance(20, 20,  java.awt.Image.SCALE_SMOOTH);
		//btnDeleteRecord.setIcon(new ImageIcon(newimgLogin));
		btnDeleteRecord.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
		btnDeleteRecord.setFont(new Font("Arial Rounded MT Bold", Font.PLAIN, 13));
		btnDeleteRecord.setForeground(new Color(102, 102, 204));
		btnDeleteRecord.setBackground(new Color(255, 153, 0));
		btnDeleteRecord.setBounds(379, 265, 132, 32);
		btnDeleteRecord.addActionListener(this);
		contentPane.add(btnDeleteRecord);
		
		JLabel lblDeleteTeacherRecord = new JLabel("Delete Student Record");
		lblDeleteTeacherRecord.setForeground(new Color(255, 204, 51));
		lblDeleteTeacherRecord.setFont(new Font("Times New Roman", Font.BOLD, 15));
		lblDeleteTeacherRecord.setBounds(147, 12, 211, 24);
		contentPane.add(lblDeleteTeacherRecord);
		setVisible(true);
		setResizable(false);
		setLocationRelativeTo(null);
	}

	public Object[][] getData(){
		Object[][] record = null;
		try{
			DBManager.getConnection();
			ResultSet rs1 = DBManager.getResultSet("SELECT count(*) FROM student");
			rs1.next();rows_no = rs1.getInt(1);		
			
			record = new Object[rows_no][3];
//			System.out.println("Rows : "+rows_no);
			ResultSet rs2 = DBManager.getResultSet("SELECT * FROM student");
			int i=0;
			while(rs2.next()){
				for(int j=0;j<3;j++){
					record[i][j] = rs2.getObject(j+1);
				}
				i++;
			}
		}catch(Exception e){e.printStackTrace();}
		finally{DBManager.closeConnection();}
		
		return record;
	}
	
	@Override
	public void actionPerformed(ActionEvent ae) {
		if(ae.getActionCommand().equalsIgnoreCase("Delete Record")){
					int selectedOption = JOptionPane.showConfirmDialog(null, 
		                    "Do you wanna delete specified Record?", 
		                    "Choose", 
		                    JOptionPane.YES_NO_OPTION); 
		if (selectedOption == JOptionPane.YES_OPTION) {
			System.out.println("Into Delete");
			int id = Integer.parseInt(textField.getText().trim());
			try{
				int res = DBManager.getConnection().createStatement().executeUpdate("DELETE FROM student WHERE roll = "+id); 
				if(res>0){
					JOptionPane.showMessageDialog(null, "student Deleted Sucessfully..");
					records = getData();		
					table.setModel(new DefaultTableModel(
							records,
						new String[] {
							"Roll no", "Student Name", "Username"
						}
					));
					scrollPane.setViewportView(table);
					table.addNotify();
					table.repaint();
					scrollPane.repaint();textField.setText("");textField.requestFocusInWindow();
					
					try {
	                    UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
	                } catch (Exception ex) {ex.printStackTrace();}
				}else{
					JOptionPane.showMessageDialog(null, "Sorry no such record exists");
					textField.setText("");textField.requestFocusInWindow();
				}
			}catch(Exception e){e.printStackTrace();}
			finally{DBManager.closeConnection();}
		}
		}
	}
}