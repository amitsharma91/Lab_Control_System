package com.db;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.WindowConstants;
class SwingDemo extends JFrame
{
	SwingDemo(){
		
		setIconImage(new ImageIcon("src/img/lab.png").getImage());
		setVisible(true);
		setSize(500,500);
		setDefaultCloseOperation(WindowConstants.HIDE_ON_CLOSE);
	}
	public static void main(String[] args){
		new SwingDemo();
	}
}