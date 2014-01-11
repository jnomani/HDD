import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
//import java.awt.image.BufferedImage;

import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import javax.swing.*;

public class View {

	private File srcFile;
	private Drive[] dr;

	public View(Drive[] drives, File f) {
		this.srcFile = f;
		this.dr = drives;
		new DriveSelectView();
	}

	/*
	 * public static class SplashWindow extends JFrame{
	 * 
	 * public SplashWindow(){
	 * 
	 * Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
	 * 
	 * this.setLocation( dim.width /2 - 300, dim.height / 2 - 300);
	 * 
	 * setBackground(Color.BLACK); setSize(300,300); setUndecorated(true); } }
	 */

	private class DriveSelectView extends JFrame {
		private JButton[] driveIcons;
		private JLabel[] driveLetters;
		private JLabel[] driveSerials;
		private JLabel[] driveSpace;

		private JLabel dlbl2 = new JLabel(srcFile.getPath());
		private JLabel dlbl3 = new JLabel("Size:");

		double s = (int) (Control.getFolderSize(srcFile) * 100) / 100.0;
		private JLabel filler2 = new JLabel(String.valueOf(s) + " Megabyte(s)");

		private ImageIcon HDD = new ImageIcon(getClass().getResource("HDD.png"));
		private ImageIcon USB = new ImageIcon(getClass().getResource("USB.png"));
		private ImageIcon CD = new ImageIcon(getClass().getResource("CD.png"));
		private ImageIcon NET = new ImageIcon(getClass().getResource("NET.png"));
		private ImageIcon RAM = new ImageIcon(getClass().getResource("RAM.png"));
		private ImageIcon Unknown = new ImageIcon(getClass().getResource("Unknown.png"));

		/*
		 * private ImageIcon resizeIcon(ImageIcon imgi){ Image img =
		 * imgi.getImage(); BufferedImage bi = new
		 * BufferedImage(img.getWidth(null), img.getHeight(null),
		 * BufferedImage.TYPE_INT_ARGB); Graphics g = bi.createGraphics();
		 * g.drawImage(img, 0, 0, 75, 75, null); ImageIcon newIcon = new
		 * ImageIcon(bi);
		 * 
		 * return newIcon; }
		 */

		/*
		 * private void initTopBar() { dlbl2.setText(srcFile.getPath());
		 * dlbl3.setText("Size:");
		 * 
		 * double s = (int) (Control.getFolderSize(srcFile) * 100) / 100.0;
		 * 
		 * filler2.setText(String.valueOf(s) + " Megabyte(s)");
		 * 
		 * dlbl2.setHorizontalAlignment(JLabel.CENTER);
		 * dlbl3.setHorizontalAlignment(JLabel.CENTER);
		 * filler2.setHorizontalAlignment(JLabel.CENTER); }
		 */

		public DriveSelectView() {

			int width = 500;
			int height = dr.length * 90;

			/*
			 * HDD = resizeIcon(HDD); USB = resizeIcon(USB); CD =
			 * resizeIcon(CD); RAM = resizeIcon(RAM); NET = resizeIcon(NET);
			 * Unknown = resizeIcon(Unknown);
			 */

			initComponents();

			setDefaultCloseOperation(EXIT_ON_CLOSE);

			setSize(width, height);
			setTitle("Select a Drive");

			JLabel welcomeLbl = new JLabel(
					"Welcome! To begin, select the drive you wish to make changes to.");

			JPanel drSelectPanel = new JPanel();

			drSelectPanel.setLayout(new GridLayout(dr.length + 2, 4));

			JButton dlbl1 = new JButton("Source Folder: ");
			dlbl1.setHorizontalAlignment(JLabel.CENTER);
			dlbl1.addActionListener(new newSource());
			dlbl1.setBorder(BorderFactory.createEmptyBorder());
			dlbl1.setContentAreaFilled(false);

			// initTopBar();

			dlbl2.setHorizontalAlignment(JLabel.CENTER);
			dlbl3.setHorizontalAlignment(JLabel.CENTER);
			filler2.setHorizontalAlignment(JLabel.CENTER);

			drSelectPanel.add(dlbl1);
			drSelectPanel.add(dlbl2);
			drSelectPanel.add(dlbl3);
			drSelectPanel.add(filler2);

			JLabel lbl1 = new JLabel("Drive Path:");
			JLabel lbl2 = new JLabel("Serial ID:");
			JLabel lbl3 = new JLabel("Available Space:");
			JLabel lbl4 = new JLabel("Select: ");

			lbl1.setHorizontalAlignment(JLabel.CENTER);
			lbl2.setHorizontalAlignment(JLabel.CENTER);
			lbl3.setHorizontalAlignment(JLabel.CENTER);
			lbl4.setHorizontalAlignment(JLabel.CENTER);

			drSelectPanel.add(lbl1);
			drSelectPanel.add(lbl2);
			drSelectPanel.add(lbl3);
			drSelectPanel.add(lbl4);

			for (int i = 0; i < dr.length; i++) {
				drSelectPanel.add(driveLetters[i]);
				drSelectPanel.add(driveSerials[i]);
				drSelectPanel.add(driveSpace[i]);
				drSelectPanel.add(driveIcons[i]);
			}

			this.add(drSelectPanel);
			setVisible(true);
		}

		private void initComponents() {
			driveIcons = new JButton[dr.length];
			driveLetters = new JLabel[dr.length];
			driveSerials = new JLabel[dr.length];
			driveSpace = new JLabel[dr.length];

			for (int i = 0; i < dr.length; i++) {
				driveLetters[i] = new JLabel(dr[i].getDir().getPath());
				driveLetters[i].setHorizontalAlignment(JLabel.CENTER);
				driveSerials[i] = new JLabel(dr[i].getSerial());
				driveSerials[i].setHorizontalAlignment(JLabel.CENTER);
				driveSpace[i] = new JLabel(
						String.valueOf(dr[i].getAvailspace()) + " Gigabyte(s)");

				driveSpace[i].setHorizontalAlignment(JLabel.CENTER);

				if (dr[i].getDriveType().equals("Fixed")) {

					driveIcons[i] = new JButton(HDD);

				} else if (dr[i].getDriveType().equals("Removable")) {

					driveIcons[i] = new JButton(USB);

				} else if (dr[i].getDriveType().equals("Network")) {

					driveIcons[i] = new JButton(NET);

				} else if (dr[i].getDriveType().equals("CD-ROM")) {
					driveIcons[i] = new JButton(CD);
				} else if (dr[i].getDriveType().equals("RAM Disk")) {
					driveIcons[i] = new JButton(RAM);
				} else {
					driveIcons[i] = new JButton(Unknown);
				}
				driveIcons[i].setBorder(BorderFactory.createEmptyBorder());
				driveIcons[i].setContentAreaFilled(false);
				driveIcons[i].setName(String.valueOf(i));
				driveIcons[i].addActionListener(new driveAction());
			}

		}

		private class driveAction implements ActionListener {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				Drive d = dr[Integer.parseInt(((JButton) arg0.getSource())
						.getName())];

				new CopyFileView(d);

			}

		}

		private class newSource implements ActionListener {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				String newPath = JOptionPane
						.showInputDialog("Enter new source folder:");

				if (newPath == null) {
				} else {
					File f = new File(newPath);
					if (f.exists()) {
						String[] s = { newPath };
						setVisible(false);
						Control.main(s);
					} else {
						JOptionPane.showMessageDialog(null, "Invalid path!");
					}

				}

			}

		}
	}

	private class DriveView extends JFrame {

	}

	private class CopyFileView extends JFrame {

		Thread t = new Thread(new Copy());

		Drive d;

		JLabel j = new JLabel("");
		JLabel j2 = new JLabel("");
		JButton jb = new JButton("Start"), jbb = new JButton("Cancel");

		public CopyFileView(Drive d) {
			this.d = d;
			setSize(200, 50);
			setTitle("Copying...");

			jb.addActionListener(new Act());
			jbb.addActionListener(new NoAct());

			this.setLayout(new GridLayout(1,3));
			JPanel bp = new JPanel();
			bp.setLayout(new GridLayout(1, 2));

			
			bp.add(jb);
			bp.add(jbb);
			

			add(bp);
			add(j);
			add(j2);

			setVisible(true);

		}

		private class Act implements ActionListener {

			@Override
			public void actionPerformed(ActionEvent arg0) {

				if (t.getState() == Thread.State.RUNNABLE)
					return;
				t.start();

			}

		}

		private class NoAct implements ActionListener {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				t.stop();
				setVisible(false);
			}

		}

		private class Copy implements Runnable {

			@Override
			public void run() {
				
				String name = srcFile.getName().equals("") ? srcFile.getPath()
						.substring(0, 1) + "Drive ": srcFile.getName();

				DateFormat dateFormat = new SimpleDateFormat(
						"yyyy-MM-dd HH-mm-ss");
				File dest = new File(d.getDir().getPath() + "Back Up of "
						+ name + " "
						+ dateFormat.format((Calendar.getInstance().getTime())));

				try {
					Control.copyFolder(srcFile, dest, j, j2);
				} catch (IOException e) {
					JOptionPane.showMessageDialog(null, e.getMessage());
					setVisible(false);

				}

				JOptionPane.showMessageDialog(null, "Done!");
				setVisible(false);

			}

		}
	}

}
