import java.awt.Color;
import java.sql.ResultSet;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import DBManager.DBManager;

public class TeacherViewsStudentsLogs extends JPanel {

	private static final long serialVersionUID = 1L;
	private JTable table;
	private Object[][] records;
	private int rows_no = 0;

	public TeacherViewsStudentsLogs() {
		setLayout(null);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 11, 573, 372);
		
		records = getData();
		table = new JTable();
		table.getTableHeader().setBackground(new Color(153, 153, 204));
		table.getTableHeader().setForeground(Color.WHITE);
		table.setModel(new DefaultTableModel(records, new String[] { "Record id", "Date & Time", "Process", "Students Name" }));
		table.setEnabled(false);
		scrollPane.setViewportView(table);
		
		add(scrollPane);

	}
	
	public Object[][] getData() {
		Object[][] record = null;
		try {
			DBManager.getConnection();
			ResultSet rs1 = DBManager.getResultSet("SELECT count(*) FROM student_history");
			rs1.next();
			rows_no = rs1.getInt(1);

			record = new Object[rows_no][4];
			// System.out.println("Rows : "+rows_no);
			ResultSet rs2 = DBManager.getResultSet("SELECT * FROM student_history");
			int i = 0;
			while (rs2.next()) {
				for (int j = 0; j < 4; j++) {
					if (j == 3) {
						ResultSet r = DBManager
								.getResultSet("Select name from student WHERE roll='" + rs2.getObject(j + 1).toString()+"'");
						r.next();
						record[i][j] = (Object) r.getString("name");
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