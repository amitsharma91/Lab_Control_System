import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.OutputStream;
import java.net.ConnectException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;

import javax.imageio.ImageIO;

class ClientScreenshot {

	// public static Socket client;
	public static String img_name;
	private static int MAX_CONNECTION = 20;
	private static int reconnections = 0;

	/*
	 * public static void getscreen(String ip) throws Exception {
	 * 
	 * System.out.println("Connectin to cleint..."); Socket client = new
	 * Socket(ip, 5566);
	 * System.out.println("Connection Established with Server @ IP: \"" + ip +
	 * "\""); ObjectInputStream in = new
	 * ObjectInputStream(client.getInputStream());
	 * 
	 * Rectangle size = (Rectangle) in.readObject(); int[] rgbData = new
	 * int[(int) (size.getWidth() * size.getHeight())];
	 * 
	 * for (int x = 0; x < rgbData.length; x++) { rgbData[x] = in.readInt(); }
	 * 
	 * BufferedImage screen = new BufferedImage((int) size.getWidth(), (int)
	 * size.getHeight(), BufferedImage.TYPE_INT_ARGB); screen.setRGB(0, 0, (int)
	 * size.getWidth(), (int) size.getHeight(), rgbData, 0, (int)
	 * size.getWidth());
	 * 
	 * // <%=new SimpleDateFormat("MMMM dd, YYYY").format(rs.getDate("doe")) %>
	 * 
	 * System.out.println( new
	 * SimpleDateFormat("MMMM_dd_YYYY_HH_MM_SS").format(new Timestamp(new
	 * java.util.Date().getTime())));
	 * 
	 * img_name = (ip + "_screen" + new
	 * SimpleDateFormat("MMMM_dd_YYYY_HH_MM_SS") .format(new Timestamp(new
	 * java.util.Date().getTime())).toString().replace('-', '_') + ".png")
	 * .replace(':', '_');
	 * 
	 * System.out.println("Image_Name: " + img_name); ImageIO.write(screen,
	 * "png", new File(img_name)); System.out.println("File Created....");
	 * 
	 * try { Thread.sleep(500); } catch (Exception e) {
	 * System.out.println("Thread Isseu...\n" + e); }
	 * 
	 * new
	 * DataOutputStream(client.getOutputStream()).writeBytes(img_name.trim());
	 * System.out.println("File Name is sent to Server: " + img_name);
	 * 
	 * in.close(); client.close(); }
	 * 
	 * public static void sendFile(String ip) { String fileName =
	 * img_name.trim();
	 * 
	 * try { Thread.sleep(3000); File file = new File(fileName.trim()); Socket
	 * client = new Socket(ip, 5567);
	 * System.out.println("Connected to Server for Sending File");
	 * 
	 * byte[] mybytearray = new byte[(int) file.length()]; BufferedInputStream
	 * bis = new BufferedInputStream(new FileInputStream(file));
	 * bis.read(mybytearray, 0, mybytearray.length); OutputStream os =
	 * client.getOutputStream(); os.write(mybytearray, 0, mybytearray.length);
	 * 
	 * os.flush(); os.close(); System.out.println("File sent to server"); }
	 * catch (Exception e) { e.printStackTrace(); } }
	 */
	
	
	
	
	///////////////// ************************************************/////////////////////////////////
	//////////////////////////
	//////////////////////////
	//////////////////////////
	//////////////////////////
	//////////////////////////
	//////////////////////////
	
	public static void getscreen(String ip) throws Exception {
		try {

			Socket client = new Socket();
			InetSocketAddress sa = new InetSocketAddress(ip, 5566);
			client.connect(sa, 500);// timeout 500ms

			System.out.println("Connection Established with Server @ IP: \"" + ip + "\"");
			ObjectInputStream in = new ObjectInputStream(client.getInputStream());

			Rectangle size = (Rectangle) in.readObject();
			int[] rgbData = new int[(int) (size.getWidth() * size.getHeight())];

			for (int x = 0; x < rgbData.length; x++) {
				rgbData[x] = in.readInt();
			}

			BufferedImage screen = new BufferedImage((int) size.getWidth(), (int) size.getHeight(),
					BufferedImage.TYPE_INT_ARGB);
			screen.setRGB(0, 0, (int) size.getWidth(), (int) size.getHeight(), rgbData, 0, (int) size.getWidth());

			System.out.println(new SimpleDateFormat("MMMM_dd_YYYY_HH_MM_SS")
					.format(new Timestamp(new java.util.Date().getTime())));

			img_name = (ip + "_screen"
					+ new SimpleDateFormat("MMMM_dd_YYYY_HH_MM_SS")
							.format(new Timestamp(new java.util.Date().getTime())).toString().replace('-', '_')
					+ ".png").replace(':', '_');

			System.out.println("Image_Name: " + img_name);
			ImageIO.write(screen, "png", new File(img_name));
			System.out.println("File Created....");

			try {
				Thread.sleep(500);
			} catch (Exception e) {
				System.out.println("Thread Isseu...\n" + e);
			}

			new DataOutputStream(client.getOutputStream()).writeBytes(img_name.trim());
			System.out.println("File Name is sent to Server: " + img_name);

			in.close();
			client.close();

		} catch (ConnectException e) {
			System.out.println("Error while connecting. " + e.getMessage());
			/******************************/
			System.out.println("getscreen() will try to reconnect in 1 seconds...(" + reconnections + "/10");
			try {
				Thread.sleep(1000);
			} catch (InterruptedException ie) {
			}
			if (reconnections < MAX_CONNECTION) {
				reconnections++;
				getscreen(ip);
			} else {
				System.out.println("Reconnections Failed,exeed max reconnection tries,shutting doen");
				System.exit(0);
				return;
			}
			/******************************/
		} catch (SocketTimeoutException e) {
			System.out.println("Connection: " + e.getMessage());
			/******************************/
			System.out.println("getscreen() will try to reconnect in 1 seconds...(" + reconnections + "/10");
			try {
				Thread.sleep(1000);
			} catch (InterruptedException ie) {
			}
			if (reconnections < MAX_CONNECTION) {
				reconnections++;
				getscreen(ip);
			} else {
				System.out.println("Reconnections Failed,exeed max reconnection tries,shutting doen");
				System.exit(0);
				return;
			}
			/******************************/
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
	////////////////////////////////////////////////////////////////////////////////////////////////////////

	public static void sendFile(String ip) {
		try {
			Thread.sleep(1000);

			Socket client = new Socket();
			InetSocketAddress sa = new InetSocketAddress(ip, 5567);
			client.connect(sa, 500);// timeout 500ms

			String fileName = img_name.trim();
			File file = new File(fileName.trim());
			System.out.println("\nConnected to Server for Sending File");

			byte[] mybytearray = new byte[(int) file.length()];System.out.println("mybytearray formed");
			BufferedInputStream bis = new BufferedInputStream(new FileInputStream(file));
			bis.read(mybytearray, 0, mybytearray.length);System.out.println("BufferedInputStream read");
			OutputStream os = client.getOutputStream();
			os.write(mybytearray, 0, mybytearray.length);System.out.println("OutputStream read");

			os.flush();System.out.println("OutputStream flushed...");
			os.close();System.out.println("OutputStream closed...");
			System.out.println("File sent to server");
		} catch (ConnectException e) {
			System.out.println("Error while connecting. " + e.getMessage());
			/******************************/
			System.out.println("sendFile() will try to reconnect in 1 seconds...(" + reconnections + "/10");
			try {
				Thread.sleep(1000);
			} catch (InterruptedException ie) {
			}
			if (reconnections < MAX_CONNECTION) {
				reconnections++;
				sendFile(ip);
			} else {
				System.out.println("Reconnections Failed,exeed max reconnection tries,shutting doen");
				System.exit(0);
				return;
			}
			/******************************/
		} catch (SocketTimeoutException e) {
			System.out.println("Connection: " + e.getMessage());
			/******************************/
			System.out.println("sendFile() will try to reconnect in 1 seconds...(" + reconnections + "/10");
			try {
				Thread.sleep(1000);
			} catch (InterruptedException ie) {
			}
			if (reconnections < MAX_CONNECTION) {
				reconnections++;
				sendFile(ip);
			} else {
				System.out.println("Reconnections Failed,exeed max reconnection tries,shutting doen");
				System.exit(0);
				return;
			}
			/******************************/
		} catch (IOException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	//////////////////////////
	//////////////////////////
	//////////////////////////
	//////////////////////////
	//////////////////////////
	//////////////////////////
	//////////////////////////
	//////////////////////////
	///////////////// ************************************************/////////////////////////////////

	public static void main(String[] args) {
		final Thread t1 = new Thread() {
			public void run() {
				try {
					getscreen("127.0.0.1");
				} catch (Exception e) {
					e.printStackTrace();
					System.out.println("Something went wrong at Client-Side while Execution FOR SCREENSHOT");
				}
			}
		};
		t1.start();

		Thread t2 = new Thread() {
			public void run() {
				try {
					t1.join();
					sendFile("127.0.0.1");
				} catch (Exception e) {
					e.printStackTrace();
					System.out.println("Something went wrong at Client-Side while Execution FOR SENDING FILE");
				}
			}
		};
		t2.start();
	}
}