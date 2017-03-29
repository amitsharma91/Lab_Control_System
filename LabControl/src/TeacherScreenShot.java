
import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.JLabel;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class TeacherScreenShot extends JPanel {

	private static final long serialVersionUID = 1L;
	private JTextField textField;

	public TeacherScreenShot() {
		setLayout(null);
		
		JButton btnNewButton = new JButton("Get Screenshot");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%//
				//FOR SCREENSHOT
				
				final Thread t1 = new Thread() {
					public void run() {
						try {
							ServerScreenshot.screenShot();
						} catch (Exception e) {
							System.out.println("Some Problem in Execution at Server Side for SCREENSHOT");
							e.printStackTrace();
						}
					}
				};
				t1.start();

				Thread t2 = new Thread() {
					public void run() {
						try {
							ServerScreenshot.saveFile();
						} catch (Exception e) {
							System.out.println("Some Problem in Execution at Server Side for COPYING FILE");
							e.printStackTrace();
						}
					}
				};
				t2.start();			
				
				//%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%//
			}
		});
		btnNewButton.setBounds(274, 104, 130, 36);
		add(btnNewButton);
		
		textField = new JTextField();
		textField.setBounds(121, 104, 143, 36);
		add(textField);
		textField.setColumns(10);
		
		JLabel lblNewLabel = new JLabel("Client IP:");
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblNewLabel.setBounds(40, 104, 71, 36);
		add(lblNewLabel);

	}
}