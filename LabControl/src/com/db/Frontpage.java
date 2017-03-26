package com.db;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JProgressBar;
import java.awt.Color;

public class Frontpage extends JFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	JProgressBar pro;
	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Frontpage frame = new Frontpage();
					//frame.pb();
					frame.setLocation(460,250);
			//		frame.pb();
					frame.setVisible(true);
					frame.pb();
					
					
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			
		});
	}

	/**
	 * Create the frame.
	 */
	public Frontpage() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBackground(Color.BLACK);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		setUndecorated(true);
		
		JProgressBar pro = new JProgressBar(0,100);
		pro.setStringPainted(true);
		pro.setBounds(0, 251, 450, 14);
		contentPane.add(pro);
	}
	
	public void pb(){
		int i=0;
	while(i<=100){
		pro.setValue(i);
		i=i+20;
		
	}
		//pro.setValue(50);
		System.out.println("call to the progress method");
		
	}
}
