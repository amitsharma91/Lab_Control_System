package com.db;

import java.awt.Color;
import java.awt.Container;
import java.awt.EventQueue;
import java.awt.HeadlessException;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JWindow;
import javax.swing.Timer;
import javax.swing.border.EtchedBorder;

public class SplashScreen extends JWindow {

	private static final long serialVersionUID = 1L;
	static boolean isRegistered;
	private static JProgressBar progressBar = new JProgressBar();
	private static SplashScreen execute;
	private static int count;
	private static Timer timer1;

	public SplashScreen() {

		Container container = getContentPane();
		container.setLayout(null);

		ImageIcon icon = new ImageIcon("src/img/back.jpg");
		JLabel thumb = new JLabel();
		thumb.setSize(630, 392);
		thumb.setLocation(0, 0);
		thumb.setIcon(icon);

		JPanel panel = new JPanel();
		panel.setBorder(new javax.swing.border.EtchedBorder());
		panel.setBackground(new Color(255, 255, 255));
		panel.setBounds(10, 10, 630, 392);
		panel.setLayout(null);
		panel.add(thumb);
		container.add(panel);
		progressBar.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.RAISED));
		progressBar.setBackground(new Color(102, 51, 102));

		progressBar.setMaximum(50);
		progressBar.setBounds(10, 413, 630, 15);
		progressBar.setForeground(new Color(255, 204, 51));
		progressBar.setStringPainted(true);
		container.add(progressBar);
		loadProgressBar();

		container.setBackground(new Color(0, 102, 153));
		setIconImage(new ImageIcon("src/img/lab.png").getImage());

		setIconImage(new ImageIcon("src/img/lab.png").getImage());
		setSize(650, 450);
		setLocationRelativeTo(null);
		setVisible(true);
	}

	private void loadProgressBar() {
		ActionListener al = new ActionListener() {

			public void actionPerformed(java.awt.event.ActionEvent evt) {
				count++;
				progressBar.setValue(count);
				// System.out.print(count + " ");

				if (count == 50) {
					createFrame();
					execute.setVisible(false);// swapped this around with
					timer1.stop();// timer1.stop()
				}
			}

			private void createFrame() throws HeadlessException {
				EventQueue.invokeLater(new Runnable() {
					public void run() {
						try {
							new AdministratorLogin();
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				});

			}
		};
		timer1 = new Timer(100, al);
		timer1.start();
	}

	public static void main(String[] args) {
		execute = new SplashScreen();
	}
};