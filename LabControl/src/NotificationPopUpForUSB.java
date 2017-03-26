import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Insets;
import java.awt.LinearGradientPaint;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.JLabel;
import java.awt.Font;

public class NotificationPopUpForUSB extends JDialog {

	private static final long serialVersionUID = 156L;
	LinearGradientPaint lgp;
	private String msg = "";

	public static void main(String[] args) {
		try {
			try {
				UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
			} catch (final Exception e1) {
				e1.printStackTrace();
			}
			new NotificationPopUpForUSB("xxxxxxxxxxxxxxxxxxxxx");
//			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
//			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public NotificationPopUpForUSB(String msg) {
		
		this.msg = msg;
		setUndecorated(true);
		setBounds(100, 100, 312, 115);

		////////////////////////////////////
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		Insets scnMax = Toolkit.getDefaultToolkit().getScreenInsets(getGraphicsConfiguration());
		int taskBarSize = scnMax.bottom;
		setLocation(screenSize.width - getWidth(), screenSize.height - taskBarSize - getHeight());
		/////////////////////////////////////

		lgp = new LinearGradientPaint(0, 0, 0, getHeight() / 2, new float[] { 0f, 0.3f, 1f },
				new Color[] { new Color(0.8f, 0.8f, 1f), new Color(0.7f, 0.7f, 1f), new Color(0.6f, 0.6f, 1f) });

		setContentPane(new BackgroundPanel());
		Container c = getContentPane();
		c.setLayout(null);

		JButton btnNewButton = new JButton("X");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				dispose();
			}
		});

		btnNewButton.setBounds(257, 11, 45, 23);
		c.add(btnNewButton);

		JButton okButton = new JButton("Warning! ");
		okButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
			}
		});
		okButton.setBounds(44, 81, 89, 23);
		okButton.setActionCommand("OK");
		c.add(okButton);
		JButton cancelButton = new JButton("Shutdown!");
		cancelButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
			}
		});
		cancelButton.setBounds(152, 81, 99, 23);
		cancelButton.setActionCommand("Cancel");
		c.add(cancelButton);
		
		JLabel lblNewLabel = new JLabel("!!!USB-Alert Detected!!!");
		lblNewLabel.setForeground(new Color(204, 51, 102));
		lblNewLabel.setFont(new Font("Trebuchet MS", Font.BOLD, 14));
		lblNewLabel.setBounds(10, 11, 180, 23);
		getContentPane().add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel(this.msg);
		lblNewLabel_1.setFont(new Font("Microsoft YaHei", Font.PLAIN, 12));
		lblNewLabel_1.setBounds(10, 45, 292, 25);
		getContentPane().add(lblNewLabel_1);

		setVisible(true);
		// setLocationRelativeTo(null);
	}

	private class BackgroundPanel extends JPanel {
		private static final long serialVersionUID = 1L;

		public BackgroundPanel() {
			setOpaque(true);
		}

		protected void paintComponent(final Graphics g) {
			Graphics2D g2d = (Graphics2D) g;
			// background
			g2d.setPaint(lgp);
			g2d.fillRect(1, 1, getWidth() - 2, getHeight() - 2);
			g2d.setColor(Color.BLACK);

			// border
			g2d.drawRect(0, 0, getWidth() - 1, getHeight() - 1);
		}
	}
}