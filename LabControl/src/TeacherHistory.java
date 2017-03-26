import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.sql.ResultSet;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import DBManager.DBManager;

public class TeacherHistory extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTable table;
	private Object[][] records;
	private int rows_no = 0;
	private JScrollPane scrollPane;

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					TeacherHistory frame = new TeacherHistory();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public TeacherHistory() {
		super("Teacher's History Panel");
		setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		setResizable(false);
		setBounds(100, 100, 539, 386);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(204, 0, 102));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		scrollPane = new JScrollPane();
		scrollPane.setBounds(12, 40, 501, 307);
		contentPane.add(scrollPane);

		records = getData();
		table = new JTable();
		table.getTableHeader().setBackground(new Color(153, 153, 204));
		table.getTableHeader().setForeground(Color.WHITE);
		table.setModel(new DefaultTableModel(records, new String[] { "Record id", "Date & Time", "Process", "Teacher Name" }));
		table.enable(false);
		scrollPane.setViewportView(table);

		JLabel lblDeleteTeacherRecord = new JLabel("Teacher's History");
		lblDeleteTeacherRecord.setForeground(new Color(255, 204, 51));
		lblDeleteTeacherRecord.setFont(new Font("Times New Roman", Font.BOLD, 15));
		lblDeleteTeacherRecord.setBounds(179, 11, 157, 24);
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
			ResultSet rs1 = DBManager.getResultSet("SELECT count(*) FROM teacher_history");
			rs1.next();
			rows_no = rs1.getInt(1);

			record = new Object[rows_no][4];
			// System.out.println("Rows : "+rows_no);
			ResultSet rs2 = DBManager.getResultSet("SELECT * FROM teacher_history");
			int i = 0;
			while (rs2.next()) {
				for (int j = 0; j < 4; j++) {
					if (j == 3) {
						ResultSet r = DBManager
								.getResultSet("Select tname from teacher WHERE tid=" + rs2.getObject(j + 1).toString());
						r.next();
						record[i][j] = (Object) r.getString("tname");
					} else
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
}