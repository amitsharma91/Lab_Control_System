import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

import javax.imageio.ImageIO;
import javax.swing.JOptionPane;

class ServerScreenshot {
	// public static ServerSocket server;
	// public static Socket client;
	public static String newFile;

	public static void screenShot() throws Exception {
		Robot robot = new Robot();
		BufferedImage screen;

		while (true) {
			// Socket for screenshot
			ServerSocket server = new ServerSocket(5566);
			System.out.println("Waiting for client");
			Socket client = server.accept();
			System.out.println("Connected to client @ IP: " + client.getInetAddress());

			Rectangle size = new Rectangle(Toolkit.getDefaultToolkit().getScreenSize());
			screen = robot.createScreenCapture(size);

			int[] rgbData = new int[(int) (size.getWidth() * size.getHeight())];
			screen.getRGB(0, 0, (int) size.getWidth(), (int) size.getHeight(), rgbData, 0, (int) size.getWidth());

			OutputStream baseOut = client.getOutputStream();
			ObjectOutputStream out = new ObjectOutputStream(baseOut);

			ImageIO.write(screen, "png", new File("orig_screen.png"));
			out.writeObject(size);

			for (int x = 0; x < rgbData.length; x++) {
				out.writeInt(rgbData[x]);
			}
			out.flush();

			try {
				Thread.sleep(500);
			} catch (Exception e) {
				System.out.println("Thread Isseu...\n" + e);
			}

			newFile = new BufferedReader(new InputStreamReader(client.getInputStream())).readLine();
			System.out.println("File_Name from Client is: " + newFile);

			out.close();
			baseOut.close();
			server.close();
			client.close();System.out.println("closed for screenshot");
		}
	}

	public static void saveFile() throws Exception {
		try {
			Thread.sleep(1000);
			ServerSocket server = new ServerSocket(5567);
			while (true) {
				Socket client = server.accept();
				System.out.println("Client Connected for getting File");

				String str1 = newFile.substring(0,newFile.indexOf(".png"))+"_copy.png";
				System.out.println("******** "+str1);
				
				InputStream ois = client.getInputStream();
				FileOutputStream fos = new FileOutputStream(str1.trim());

				byte[] mybytearray = new byte[10240];
				System.out.println("Reading file from server...");
				BufferedOutputStream bos = new BufferedOutputStream(fos);
				int bytesRead;
				while ((bytesRead = ois.read(mybytearray)) != -1) {
					bos.write(mybytearray);
				}

				bos.close();
				System.out.println("Writing file complete...");
				JOptionPane.showMessageDialog(null, "Screenshot taken sucessfully...");	
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		/*
		 * InputStream ois = client.getInputStream(); FileOutputStream fos = new
		 * FileOutputStream(newFile.trim());
		 * 
		 * byte[] mybytearray = new byte[1024]; System.out.println(
		 * "Reading file from server..."); BufferedOutputStream bos = new
		 * BufferedOutputStream(fos); int bytesRead; while ((bytesRead =
		 * ois.read(mybytearray)) != -1) { bos.write(mybytearray); }
		 * bos.close(); System.out.println("Writing file complete...");
		 */
	}

	public static void main(String[] args) {
		final Thread t1 = new Thread() {
			public void run() {
				try {
					screenShot();
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
					saveFile();
				} catch (Exception e) {
					System.out.println("Some Problem in Execution at Server Side for COPYING FILE");
					e.printStackTrace();
				}
			}
		};
		t2.start();
	}
}
