package com.db;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

public class AdminsTab extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					new AdminsTab("Administrator Home");
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public AdminsTab(String title) {
		super(title);
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		setBounds(100, 100, 450, 300);

		JMenuBar menuBar = new JMenuBar();
		menuBar.setForeground(new Color(255, 255, 255));
		menuBar.setBackground(new Color(204, 102, 0));
		setJMenuBar(menuBar);

		JMenu mnAdd = new JMenu("Add");
		mnAdd.setForeground(new Color(255, 255, 255));
		mnAdd.setBackground(new Color(204, 153, 153));
		// mnAdd.setFont(new Font("Times New Roman", Font.BOLD, 17));
		menuBar.add(mnAdd);

		JMenuItem mntmStudent_1 = new JMenuItem("Student");
		mntmStudent_1.setBackground(new Color(51, 153, 204));
		// mntmStudent_1.setFont(new Font("Times New Roman", Font.BOLD, 16));
		mntmStudent_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					EventQueue.invokeLater(new Runnable() {
						public void run() {
							try {
								AddStudent frame = new AddStudent();
								frame.setVisible(true);
							} catch (Exception e) {
								e.printStackTrace();
							}
						}
					});
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		mnAdd.add(mntmStudent_1);

		JMenuItem mntmTeacher = new JMenuItem("Teacher");
		mntmTeacher.setBackground(new Color(51, 153, 204));
		// mntmTeacher.setFont(new Font("Times New Roman", Font.BOLD, 16));
		mntmTeacher.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				AddTecher ad = new AddTecher("Teacher Registration");
				ad.setVisible(true);
			}
		});
		mnAdd.add(mntmTeacher);

		JMenu mnRemove = new JMenu("Remove");
		mnRemove.setForeground(new Color(255, 255, 255));
		mnRemove.setBackground(new Color(204, 153, 153));
		menuBar.add(mnRemove);

		JMenuItem mntmStudent = new JMenuItem("Student");
		mntmStudent.setBackground(new Color(51, 153, 204));
		mnRemove.add(mntmStudent);
		mntmStudent.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				EventQueue.invokeLater(new Runnable() {
					public void run() {
						try {
							RemoveStudent frame = new RemoveStudent();
							frame.setVisible(true);
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				});
			}
		});

		JMenuItem mntmTecher = new JMenuItem("Teacher");
		mntmTecher.setBackground(new Color(51, 153, 204));
		mnRemove.add(mntmTecher);
		mntmTecher.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					DeleteTeacher frame = new DeleteTeacher();
					frame.setVisible(true);
				} catch (Exception ex) {
					ex.printStackTrace();
				}
			}
		});

		JMenu mnUpdate = new JMenu("Update");
		mnUpdate.setForeground(new Color(255, 255, 255));
		mnUpdate.setBackground(new Color(204, 153, 153));
		menuBar.add(mnUpdate);

		JMenuItem mntmStudent_up = new JMenuItem("Student");
		mntmStudent_up.setBackground(new Color(51, 153, 204));
		mnUpdate.add(mntmStudent_up);
		mntmStudent_up.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				EventQueue.invokeLater(new Runnable() {
					public void run() {
						try {
							UpdateStudent frame = new UpdateStudent();
							frame.setVisible(true);
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				});
			}
		});

		JMenuItem mntmTeacher_up = new JMenuItem("Teacher");
		mntmTeacher_up.setBackground(new Color(51, 153, 204));
		mnUpdate.add(mntmTeacher_up);
		mntmTeacher_up.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				EventQueue.invokeLater(new Runnable() {
					public void run() {
						try {
							UpdateTeacher frame = new UpdateTeacher();
							frame.setVisible(true);
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				});
			}
		});

		JMenu mnExit = new JMenu("Exit");
		mnExit.setForeground(new Color(255, 255, 255));
		mnExit.setBackground(new Color(204, 153, 153));
		menuBar.add(mnExit);

		JMenuItem mntmClose = new JMenuItem("Close");
		mntmClose.setBackground(new Color(51, 153, 204));
		mntmClose.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				int selectedOption = JOptionPane.showConfirmDialog(null, "Do you want to close the window?", "Choose",
						JOptionPane.YES_NO_OPTION);
				if (selectedOption == JOptionPane.YES_OPTION) {
					System.exit(0);
				}
			}
		});
		mnExit.add(mntmClose);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(204, 0, 102));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		setIconImage(new ImageIcon("src/img/lab.png").getImage());
		setLocationRelativeTo(null);
		setVisible(true);
		setResizable(false);
	}
}
