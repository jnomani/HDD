import java.io.*;
import java.util.ArrayList;

import javax.swing.*;


public class Control {

	
	
	
	public static void main(String[] args) {
		
		
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Fatal Error Occurred!");
			System.exit(1);
		}
		
		/*View.SplashWindow s = new View.SplashWindow();
		
		s.setVisible(true);*/
		
		
		
			
		
		
		String fPath = args.length == 0 ? JOptionPane.showInputDialog("Enter source path") : args[0];
		File f = new File(fPath);
		if(!f.exists()){
			System.out.println("Path does not exist!");
			System.exit(1);
		}
		
		String fDrive = fPath.substring(0, 3);
		File dFile = new File(fDrive);
		
		
		File[] dirs = Drive.getAllDrives();
		ArrayList<Drive> drives = new ArrayList<Drive>();
		
		for(int i = 0; i < dirs.length; i++){
			
			if(!dirs[i].getPath().equals(dFile.getPath())){
			System.out.println("Loading Drive " + dirs[i].getPath() + "...");
			drives.add(new Drive(dirs[i]));
			}
		}
		System.out.println("Done! Loading Interface...");
		//s.setVisible(false);
		
		Drive[] dr = new Drive[drives.size()];
		
		for(int i = 0; i < dr.length; i++){
			dr[i] = drives.get(i);
		}
		new View(dr, f);
	}
	
	public static double getFolderSize(File directory){
		
		
		
		
		double length = 0;
		
		for(File f: directory.listFiles()){
			if(!f.isDirectory())
			length += f.length();
			else length += getFolderSize(f);
		}
		
		double size = (length  / 1024.0) / 1024.0;
		
		return size;
	}

	public static void copyFolder(File src, File dest, JLabel updatelbl, JLabel updatelbl2) throws IOException{
		
		if(src.isDirectory()){
			 
    		
    		if(!dest.exists()){
    		   dest.mkdir();
    		   updatelbl.setText("Directory copied from " 
                              + src + "  to " + dest);
    		}
 
    		
    		String files[] = src.list();
 
    		for (String file : files) {
    		   
    		   File srcFile = new File(src, file);
    		   File destFile = new File(dest, file);
    		   //recursive copy
    		   copyFolder(srcFile,destFile, updatelbl, updatelbl2);
    		}
 
    	}else{
    		
    		
    		InputStream in = new FileInputStream(src);
    	        OutputStream out = new FileOutputStream(dest); 
 
    	        byte[] buffer = new byte[1024];
 
    	        int length;
    	         
    	        while ((length = in.read(buffer)) > 0){
    	    	   out.write(buffer, 0, length);
    	        }
 
    	        in.close();
    	        out.close();
    	        updatelbl.setText("File copied from " + src);
    	        updatelbl2.setText(" to " + dest);

		
		
    	}
	}
}
