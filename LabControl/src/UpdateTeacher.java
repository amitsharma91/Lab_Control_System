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

public class UpdateTeacher extends JFrame implements ActionListener {

	String n, u;
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTable table;
	private Object[][] records;
	private int rows_no = 0;
	private JTextField textField;
	private JScrollPane scrollPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					new UpdateTeacher();
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
	public UpdateTeacher() {
		super("Teacher - Update Record");
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
		table.setModel(new DefaultTableModel(records, new String[] { "Teacher id", "Teacher Name", "Username" }));
		table.enable(false);
		scrollPane.setViewportView(table);

		JLabel lblEnterTeacherId = new JLabel("Enter Teacher ID  to Update Record:");
		lblEnterTeacherId.setForeground(Color.WHITE);
		lblEnterTeacherId.setBounds(41, 269, 249, 24);
		contentPane.add(lblEnterTeacherId);

		textField = new JTextField();
		textField.setBorder(BorderFactory.createLineBorder(Color.WHITE));
		textField.setBounds(308, 270, 59, 24);
		contentPane.add(textField);
		textField.requestFocusInWindow();
		textField.setColumns(10);

		JButton btnDeleteRecord = new JButton("Update Record");
		Image imgLoginbtnbtnupdateRecord = new ImageIcon(this.getClass().getResource("img/update_in.png")).getImage();
		Image newimgLoginbtnupdateRecord = imgLoginbtnbtnupdateRecord.getScaledInstance(20, 20, java.awt.Image.SCALE_SMOOTH);
		btnDeleteRecord.setIcon(new ImageIcon(newimgLoginbtnupdateRecord));
		btnDeleteRecord.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
		btnDeleteRecord.setFont(new Font("Arial Rounded MT Bold", Font.PLAIN, 13));
		btnDeleteRecord.setForeground(new Color(102, 102, 204));
		btnDeleteRecord.setBackground(new Color(255, 153, 0));
		btnDeleteRecord.setBounds(379, 265, 132, 32);
		btnDeleteRecord.addActionListener(this);
		contentPane.add(btnDeleteRecord);

		JLabel lblDeleteTeacherRecord = new JLabel("Update Teacher Record");
		lblDeleteTeacherRecord.setForeground(new Color(255, 204, 51));
		lblDeleteTeacherRecord.setFont(new Font("Times New Roman", Font.BOLD, 15));
		lblDeleteTeacherRecord.setBounds(147, 12, 211, 24);
		contentPane.add(lblDeleteTeacherRecord);
		setVisible(true);
		setResizable(false);
		setLocationRelativeTo(null);
		setIconImage(new ImageIcon("src/img/lab.png").getImage());
	}

	public Object[][] getData() {
		Object[][] record = null;
		try {
			DBManager.getConnection();
			ResultSet rs1 = DBManager.getResultSet("SELECT count(*) FROM teacher");
			rs1.next();
			rows_no = rs1.getInt(1);

			record = new Object[rows_no][3];
			// System.out.println("Rows : "+rows_no);
			ResultSet rs2 = DBManager.getResultSet("SELECT * FROM teacher");
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
				// String stud_roll = textField.getText().trim();
				int tid = Integer.parseInt(textField.getText().trim());
				try {
					ResultSet res = DBManager.getConnection().createStatement()
							.executeQuery("SELECT * FROM teacher WHERE tid =" + tid);
					if (res.next()) {
						EditTeacher es = null;
						try {
							es = new EditTeacher(res.getInt("tid"));
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