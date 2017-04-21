
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Vector;

import javax.swing.SwingUtilities;
import javax.swing.UIManager;

public class DetectUSB {
	static File[] oldListRoot = File.listRoots();
	static File driveDectected = null;
	static String msg = "NA";
	static int c = 0;

	static Vector<String> appList = null;
	static int lengthOld;

	public static String generateMsg(Socket clentSocket, String Username) {
		Thread t = new Thread(new Runnable() {
			public void run() {
				while (true) {
					// System.out.print("-" + (++c) + "-");

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
									msg = "USB Attached by: \"" + Username + "\" @ "+InetAddress.getLocalHost().getHostAddress();// InetAddress.getLocalHost();
									System.out.println("< LOCAL > " + msg);
									DataOutputStream dos = new DataOutputStream(clentSocket.getOutputStream());
									System.out.println(" < Trying to Sent Msg(ATTACHED) to Server > ");
									dos.writeUTF(msg);
									System.out.println(" < SEND to SERVER: " + msg + " > ");
									/*System.out.println("WARNING MSG: "
											+ new BufferedReader(new InputStreamReader(clentSocket.getInputStream()))
													.readLine());*/
								} catch (Exception e) {
									e.printStackTrace();
								}
							}
						});
					} else if (File.listRoots().length < oldListRoot.length) {

						SwingUtilities.invokeLater(new Runnable() {
							public void run() {
								try {
									msg = "USB Removed by: \"" + Username + "\" @ "+InetAddress.getLocalHost().getHostAddress();// InetAddress.getLocalHost();
									System.out.println("LOCAL > " + msg);
									DataOutputStream dos = new DataOutputStream(clentSocket.getOutputStream());
									System.out.println(" < Trying to Sent Msg(REMOVED) to Server > ");
									dos.writeUTF(msg);
									System.out.println(" < SEND to SERVER: " + msg + " > ");
									/*System.out.println("WARNING MSG: "
											+ new BufferedReader(new InputStreamReader(clentSocket.getInputStream()))
													.readLine());*/
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

	public static String generateAppMsg(Socket clentSocket, String Username) throws IOException {
		Vector<String> list = new Vector<String>();
		list.add("Firefox.exe");
		list.add("uTorrent.exe");
		list.add("vlc.exe");
		list.add("chrome.exe");
		list.add("AcroRD32.exe");
		list.add("NeroStartSmart.exe");
		list.add("WinRAR.exe");
		list.add("PhotoshopCS6Portable.exe");
		list.add("PotPlayerMini.exe");
		list.add("AIMP3.exe");
		list.add("Button Shop.exe");
		list.add("Backupper.exe");
		list.add("Calculator.exe");
		list.add("Music.UI.exe");
		// list.add("eclipse.exe");
		list.add("notepad++.exe");
		// Vector<String> appList = null;
		appList = new Vector<String>();

		Process p = Runtime.getRuntime().exec(System.getenv("windir") + "\\system32\\" + "tasklist.exe");
		BufferedReader br = new BufferedReader(new InputStreamReader(p.getInputStream()));
		appList = new Vector<String>();
		String nn;
		while ((nn = br.readLine()) != null) {
			appList.add(nn);
		}

		lengthOld = appList.size();
		System.out.println("LENGTH OUSIDE LOOP: " + lengthOld);

		/*
		 * for (String s : appList) { // System.out.println(s); if
		 * (s.indexOf("Console") > 0) { System.out.println(s); } }
		 */

		System.out.println("PROCESSSSSSSSEDDDDD...\nAPPS:");

		Thread t = new Thread(new Runnable() {
			Vector<String> buffer = new Vector<String>();

			boolean checkIt(String key) {
				boolean flag = false;
				for (String s : buffer) {
					if (s == key)
						flag = true;
				}
				System.out.println("returning " + flag);
				return flag;
			}

			public void run() {
				while (true) {

					System.out.print("-" + (++c) + "-");
					try {
						Thread.sleep(600);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					String n;
					try {

						Process p = Runtime.getRuntime()
								.exec(System.getenv("windir") + "\\system32\\" + "tasklist.exe");
						BufferedReader br = new BufferedReader(new InputStreamReader(p.getInputStream()));
						appList = new Vector<String>();
						while ((n = br.readLine()) != null) {
							appList.add(n);
						}
						int lengthNew = appList.size();
						System.out.println("LENGTH INSIDE LOOP: " + lengthNew);

						if (lengthNew > lengthOld) {
							for (String key : list) {
								for (String content : appList) {
									if (content.contains(key) && content.contains("Console") && !checkIt(key)) {
										buffer.add(key);
										msg = "'" + Username + "'" + " has opened: \"" + key + "\" app @ "+InetAddress.getLocalHost().getHostAddress();
										System.out.println(msg);
										DataOutputStream dos = new DataOutputStream(clentSocket.getOutputStream());
										dos.writeUTF(msg);
									}
								}
							}
						}

					} catch (IOException e) {
						e.printStackTrace();
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