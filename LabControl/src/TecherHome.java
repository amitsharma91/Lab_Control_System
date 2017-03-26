import java.awt.Color;
import java.awt.ComponentOrientation;
import java.awt.Font;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.DataOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingWorker;

public class TecherHome extends JPanel {

	private static final long serialVersionUID = 1L;
	private ServerSocket serverBoradcast;
	private Socket conn;

	public TecherHome() {
		setLayout(null);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(12, 29, 579, 354);
		add(scrollPane);

		JTextArea textArea = new JTextArea();
		textArea.setLineWrap(true);
		textArea.setCaretColor(Color.WHITE);
		textArea.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
		textArea.setWrapStyleWord(true);
		textArea.setTabSize(2);
		textArea.setForeground(new Color(255, 204, 0));
		textArea.setBackground(new Color(0, 0, 102));
		textArea.setFont(new Font("Microsoft YaHei", Font.BOLD, 15));
		scrollPane.setViewportView(textArea);

		JLabel lblStartTypingHere = new JLabel("Start Typing Below:");
		lblStartTypingHere.setBounds(12, 12, 197, 15);
		add(lblStartTypingHere);

		SwingWorker<String, String> worker = new SwingWorker<String, String>() {

			protected String doInBackground() throws Exception {
				try {
					serverBoradcast = new ServerSocket(7537);
					conn = serverBoradcast.accept();
				} catch (Exception e) {
					e.printStackTrace();
				}

				textArea.addKeyListener(new KeyAdapter() {
					public void keyReleased(KeyEvent e) {
						switch (e.getKeyCode()) {
						default:
							try {
								DataOutputStream dos = new DataOutputStream(conn.getOutputStream());
								dos.writeUTF(textArea.getText());
							} catch (Exception e1) {
								try {
									Thread.sleep(3000);
									System.exit(0);
								} catch (InterruptedException e2) {
									e2.printStackTrace();
								}
							}
						}
					}
				});
				return null;
			}
		};
		worker.execute();
	}
}