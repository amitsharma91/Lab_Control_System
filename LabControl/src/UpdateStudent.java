
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.BevelBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import DBManager.DBManager;

public class UpdateStudent extends JFrame implements ActionListener {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTable table;
	private Object[][] records;
	private int rows_no = 0;
	private JTextField textField;
	private JScrollPane scrollPane;
	private JButton updateRecord;
	private JLabel lblDeleteTeacherRecord;
	private JLabel lblEnterTeacherId;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					new UpdateStudent();
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
	public UpdateStudent() {
		super("Student - Update Record");
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
		table.setModel(new DefaultTableModel(records, new String[] { "Roll no", "Student Name", "Username" }));
		table.enable(false);
		scrollPane.setViewportView(table);

		lblEnterTeacherId = new JLabel("Enter Student ID  to Update Record:");
		lblEnterTeacherId.setForeground(Color.WHITE);
		lblEnterTeacherId.setBounds(41, 269, 249, 24);
		contentPane.add(lblEnterTeacherId);

		textField = new JTextField();
		textField.setBorder(BorderFactory.createLineBorder(Color.WHITE));
		textField.setBounds(308, 270, 59, 24);
		contentPane.add(textField);
		textField.requestFocusInWindow();
		textField.setColumns(10);

		updateRecord = new JButton("Update Record");
		Image imgLoginbtnbtnupdateRecord = new ImageIcon(this.getClass().getResource("img/update_in.png")).getImage();
		Image newimgLoginbtnupdateRecord = imgLoginbtnbtnupdateRecord.getScaledInstance(20, 20, java.awt.Image.SCALE_SMOOTH);
		updateRecord.setIcon(new ImageIcon(newimgLoginbtnupdateRecord));
		updateRecord.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
		updateRecord.setFont(new Font("Arial Rounded MT Bold", Font.PLAIN, 13));
		updateRecord.setForeground(new Color(102, 102, 204));
		updateRecord.setBackground(new Color(255, 153, 0));
		updateRecord.setBounds(379, 265, 132, 32);
		updateRecord.addActionListener(this);
		contentPane.add(updateRecord);

		lblDeleteTeacherRecord = new JLabel("Update Student Record");
		lblDeleteTeacherRecord.setForeground(new Color(255, 204, 51));
		lblDeleteTeacherRecord.setFont(new Font("Times New Roman", Font.BOLD, 15));
		lblDeleteTeacherRecord.setBounds(147, 12, 211, 24);
		contentPane.add(lblDeleteTeacherRecord);
		setIconImage(new ImageIcon("src/img/lab.png").getImage());
		setVisible(true);
		setResizable(false);
		setLocationRelativeTo(null);
	}

	public Object[][] getData() {
		Object[][] record = null;
		try {
			DBManager.getConnection();
			ResultSet rs1 = DBManager.getResultSet("SELECT count(*) FROM student");
			rs1.next();
			rows_no = rs1.getInt(1);

			record = new Object[rows_no][3];
			// System.out.println("Rows : "+rows_no);
			ResultSet rs2 = DBManager.getResultSet("SELECT * FROM student");
			int i = 0;
			while (rs2.next()) {
				for (int j = 0; j < 3; j++) {
					record[i][j] = rs2.getObject(j + 1);
				}
				i++;
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DBManager.closeConnection();
		}

		return record;
	}

	public void actionPerformed(ActionEvent ae) {
		if (textField.getText().trim().equals("")) {
			JOptionPane.showMessageDialog(null, "Please enter some ID to Update", "Error", JOptionPane.ERROR_MESSAGE);
		} else {
			int selectedOption = JOptionPane.showConfirmDialog(null, "Do you wanna UPDATE specified Record?", "Choose",
					JOptionPane.YES_NO_OPTION);
			if (selectedOption == JOptionPane.YES_OPTION) {
				String stud_roll = textField.getText().trim();
				try {
					ResultSet res = DBManager.getConnection().createStatement()
							.executeQuery("SELECT * FROM student WHERE roll ='" + stud_roll + "'");
					if (res.next()) {
						EditStudent es = null;
						try {
							es = new EditStudent(res.getString("roll"));
							dispose();
						} catch (Exception e1) {
							e1.printStackTrace();
						}
						es.setVisible(true);
					} else {
						JOptionPane.showMessageDialog(null, "Sorry no such record exists");
						textField.setText("");
						textField.requestFocusInWindow();
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}
}