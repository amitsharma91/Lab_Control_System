
import java.awt.Color;
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
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.border.BevelBorder;
import javax.swing.border.EmptyBorder;

public class AdministratorLogin extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JPasswordField passwordField;
	private JTextField textField;
	private JPasswordField passwordField_1;
	private JTextField textField_1;


	/**
	 * Create the frame.
	 */
	public AdministratorLogin() {
		super("Administrator Login");
		getContentPane().setLayout(null);

		JLabel label = new JLabel("Username: ");
		label.setFont(new Font("Times New Roman", Font.BOLD, 16));
		label.setBounds(118, 64, 87, 14);
		getContentPane().add(label);

		JLabel label_1 = new JLabel("Password: ");
		label_1.setFont(new Font("Times New Roman", Font.BOLD, 16));
		label_1.setBounds(117, 106, 63, 14);
		getContentPane().add(label_1);

		passwordField_1 = new JPasswordField();
		passwordField_1.setFont(new Font("Times New Roman", Font.BOLD, 16));
		passwordField_1.setBounds(215, 103, 123, 20);
		getContentPane().add(passwordField_1);

		textField_1 = new JTextField();
		textField_1.setFont(new Font("Times New Roman", Font.BOLD, 16));
		textField_1.setColumns(10);
		textField_1.setBounds(215, 61, 123, 20);
		getContentPane().add(textField_1);

		JButton button = new JButton("Cancel");
		button.setFont(new Font("Times New Roman", Font.BOLD, 16));
		button.setBackground(new Color(30, 144, 255));
		button.setBounds(232, 153, 86, 23);
		getContentPane().add(button);

		JButton button_1 = new JButton("Login");
		button_1.setFont(new Font("Times New Roman", Font.BOLD, 16));
		button_1.setBackground(new Color(30, 144, 255));
		button_1.setBounds(132, 153, 90, 23);
		getContentPane().add(button_1);

		JLabel lblAdminLogin = new JLabel("ADMIN LOGIN");
		lblAdminLogin.setFont(new Font("Times New Roman", Font.BOLD, 17));
		lblAdminLogin.setBounds(164, 11, 117, 14);
		getContentPane().add(lblAdminLogin);
		setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		setBounds(100, 100, 432, 199);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(204, 51, 102));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JLabel imageLabel = new JLabel("");
		imageLabel.setBounds(24, 11, 129, 130);
		contentPane.add(imageLabel);
		Image img = new ImageIcon(this.getClass().getResource("img/admin.png")).getImage();
		Image newimg = img.getScaledInstance(110, 110, java.awt.Image.SCALE_SMOOTH);
		imageLabel.setIcon(new ImageIcon(newimg));

		JButton btnLogin = new JButton("Login");
		btnLogin.setMnemonic('l');
		Image imgLogin = new ImageIcon(this.getClass().getResource("img/okk.png")).getImage();
		Image newimgLogin = imgLogin.getScaledInstance(20, 20, java.awt.Image.SCALE_SMOOTH);
		btnLogin.setIcon(new ImageIcon(newimgLogin));
		btnLogin.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
		btnLogin.setForeground(new Color(102, 102, 204));
		btnLogin.setBackground(new Color(255, 153, 0));
		btnLogin.setToolTipText("Click here to Login");
		btnLogin.setFont(new Font("Times New Roman", Font.BOLD, 15));
		btnLogin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String usern, passw;
				usern = textField.getText();
				passw = String.valueOf(passwordField.getPassword());

				if (usern.equals("") || passw.equals("")) {
					JOptionPane.showMessageDialog(null, "Please fill all the credentials", "Error",
							JOptionPane.ERROR_MESSAGE);
				} else {
					DBManager.DBManager.getConnection();
					try {
						String query = "SELECT * FROM admin WHERE user='" + usern + "' AND pass='" + passw + "'";
						ResultSet rs = DBManager.DBManager.getResultSet(query);
						if (rs.next()) {

							System.out.println("Login Successfull");
							dispose();
							AdminsTab at = new AdminsTab("Administrator Home");
							at.setVisible(true);
						} else {
							JOptionPane.showMessageDialog(null, "Access Denied!", "Access Denied",
									JOptionPane.ERROR_MESSAGE);
							textField.setText("");
							passwordField.setText("");
							textField.requestFocusInWindow();
						}
					} catch (Exception e) {

					} finally {
						DBManager.DBManager.closeConnection();
					}
				}
			}
		});
		btnLogin.setBounds(210, 118, 93, 30);
		contentPane.add(btnLogin);

		passwordField = new JPasswordField();
		passwordField.setFont(new Font("Times New Roman", Font.BOLD, 15));
		passwordField.setBounds(275, 77, 118, 20);
		contentPane.add(passwordField);

		textField = new JTextField();
		textField.setFont(new Font("Times New Roman", Font.BOLD, 15));
		textField.setBounds(275, 45, 118, 20);
		contentPane.add(textField);
		textField.setColumns(10);

		JLabel lblNewLabel = new JLabel("Username: ");
		lblNewLabel.setForeground(new Color(255, 255, 255));
		lblNewLabel.setFont(new Font("Times New Roman", Font.BOLD, 15));
		lblNewLabel.setBounds(163, 45, 102, 20);
		contentPane.add(lblNewLabel);

		JLabel lblNewLabel_1 = new JLabel("Password: ");
		lblNewLabel_1.setForeground(new Color(255, 255, 255));
		lblNewLabel_1.setFont(new Font("Times New Roman", Font.BOLD, 15));
		lblNewLabel_1.setBounds(163, 79, 102, 17);
		contentPane.add(lblNewLabel_1);

		setIconImage(new ImageIcon(AdministratorLogin.class.getResource("/img/lab.png")).getImage());
		setVisible(true);
		setResizable(false);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		System.out.println(getWidth()+" x "+getHeight());
	}
}
