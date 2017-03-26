package com.db;

import java.io.DataOutputStream;
import java.io.File;
import java.net.InetAddress;
import java.net.Socket;

import javax.swing.SwingUtilities;
import javax.swing.UIManager;

public class DetectUSB {
	static File[] oldListRoot = File.listRoots();
	static File driveDectected = null;
	static String msg = "NA";
	static int c = 0;
	public static void main(String[] args) {
		DetectUSB.waitForNotifying();
	}

	public static String generateMsg(Socket clentSocket) {
		Thread t = new Thread(new Runnable() {
			public void run() {
				while (true) {
					System.out.print("-"+(++c)+"-");
					try {
						Thread.sleep(100);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					if (File.listRoots().length > oldListRoot.length) {
						oldListRoot = File.listRoots();

						SwingUtilities.invokeLater(new Runnable() {
							public void run() {
								try {
									msg = "USB Attached at " + InetAddress.getLocalHost();

									DataOutputStream dos = new DataOutputStream(clentSocket.getOutputStream());
									dos.writeUTF(msg);

								} catch (Exception e) {
									e.printStackTrace();
								}
							}
						});
					} else if (File.listRoots().length < oldListRoot.length) {

						SwingUtilities.invokeLater(new Runnable() {
							public void run() {
								try {
									msg = "USB Removed at " + InetAddress.getLocalHost();
									DataOutputStream dos = new DataOutputStream(clentSocket.getOutputStream());
									dos.writeUTF(msg);
								} catch (Exception e) {
									e.printStackTrace();
								}
							}
						});
						oldListRoot = File.listRoots();
					}
				}
			}
		});
		t.start();
		return msg;
	}

	public static void waitForNotifying() {
		Thread t = new Thread(new Runnable() {
			public void run() {
				while (true) {
					try {
						Thread.sleep(100);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					if (File.listRoots().length > oldListRoot.length) {
						// System.out.println("**********new drive
						// detected***********");
						oldListRoot = File.listRoots();
						// System.out.println("drive" +
						// oldListRoot[oldListRoot.length - 1] + " detected");

						SwingUtilities.invokeLater(new Runnable() {
							@Override
							public void run() {
								try {
									UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
								} catch (final Exception e1) {
									e1.printStackTrace();
								}
								for (File f : oldListRoot) {
									System.out.println(f);
								}
								System.out.println("Size is: " + oldListRoot.length);
								String msg = "Drive " + oldListRoot[oldListRoot.length - 1] + " is Detected";
								driveDectected = oldListRoot[oldListRoot.length - 1];
								System.out.println("Into main Class: " + msg);
								new NotificationPopup(msg);

							}
						});

						// String msg = "Drive
						// "+oldListRoot[oldListRoot.length-1]+" is Detected";
						// NotificationPopup np = new NotificationPopup(msg);
						// JOptionPane.showMessageDialog(null,"Drive " +
						// oldListRoot[oldListRoot.length-1] + " is
						// Detected","Alert", JOptionPane.INFORMATION_MESSAGE);

					} else if (File.listRoots().length < oldListRoot.length) {
						// System.out.println(oldListRoot[oldListRoot.length -
						// 1] + " drive removed");

						SwingUtilities.invokeLater(new Runnable() {
							@Override
							public void run() {
								try {
									UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
								} catch (final Exception e1) {
									e1.printStackTrace();
								}
								for (File f : oldListRoot) {
									System.out.println(f);
								}
								System.out.println("Size is: " + oldListRoot.length);
								String msg = "Drive " + driveDectected + " is Removed";
								System.out.println("Into Main Class: " + msg);
								new NotificationPopup(msg);

							}
						});
						oldListRoot = File.listRoots();
					}
				}
			}
		});
		t.start();
	}
}