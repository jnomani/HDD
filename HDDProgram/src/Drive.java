import java.io.*;

public class Drive implements Comparable<Drive>{

	private File dir;
	private double availspace; // In Gbytes
	private String driveType;
	private String serial;

	public Drive(File dir) {
		this.dir = dir;
		loadSerial();
		loadSize();
		getFileSys();
	}

	public File getDir() {
		return dir;
	}

	public double getAvailspace() {
		return availspace;
	}

	public String getDriveType() {
		return driveType;
	}

	public String getSerial() {
		return serial;
	}

	private void loadSerial() {

		String result = "";
		try {
			File file = File.createTempFile("tempscript", ".vbs");
			file.deleteOnExit();
			FileWriter fw = new java.io.FileWriter(file);

			String vbs = "Set objFSO = CreateObject(\"Scripting.FileSystemObject\")\n"
					+ "Set colDrives = objFSO.Drives\n"
					+ "Set objDrive = colDrives.item(\""
					+ dir.getPath()
					+ "\")\n" + "Wscript.Echo objDrive.SerialNumber"; // see
																		// note
			fw.write(vbs);
			fw.close();
			Process p = Runtime.getRuntime().exec(
					"cscript //NoLogo " + file.getPath());
			BufferedReader input = new BufferedReader(new InputStreamReader(
					p.getInputStream()));
			String line;
			while ((line = input.readLine()) != null) {
				result += line;
			}
			input.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
		serial = result.trim();

		if(serial.equals("")) serial = "N/A";
		
	}
	
	private void getFileSys(){
		
		String result = "";
		try {
			File file = File.createTempFile("tempscript", ".vbs");
			file.deleteOnExit();
			FileWriter fw = new java.io.FileWriter(file);

			String vbs = "Set objFSO = CreateObject(\"Scripting.FileSystemObject\")\n"
					+ "Set colDrives = objFSO.Drives\n"
					+ "Set objDrive = colDrives.item(\""
					+ dir.getPath()
					+ "\")\n" + "Wscript.Echo objDrive.DriveType"; // see
																		// note
			fw.write(vbs);
			fw.close();
			Process p = Runtime.getRuntime().exec(
					"cscript //NoLogo " + file.getPath());
			BufferedReader input = new BufferedReader(new InputStreamReader(
					p.getInputStream()));
			String line;
			while ((line = input.readLine()) != null) {
				result += line;
			}
			input.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		int type = Integer.parseInt(result.trim());
		
		switch(type){
		case 1: driveType = "Removable"; break;
		case 2: driveType = "Fixed"; break;
		case 3: driveType = "Network"; break;
		case 4: driveType = "CD-ROM"; break;
		case 5: driveType = "RAM Disk"; break;
		default: driveType = "Unknown"; break;
		}
		
		
	}
	
	private void loadSize(){
		
		String result = "";
		try {
			File file = File.createTempFile("tempscript", ".vbs");
			file.deleteOnExit();
			FileWriter fw = new java.io.FileWriter(file);

			String vbs = "Set objFSO = CreateObject(\"Scripting.FileSystemObject\")\n"
					+ "Set colDrives = objFSO.Drives\n"
					+ "Set objDrive = colDrives.item(\""
					+ dir.getPath()
					+ "\")\n" + "Wscript.Echo objDrive.AvailableSpace"; // see
																		// note
			fw.write(vbs);
			fw.close();
			Process p = Runtime.getRuntime().exec(
					"cscript //NoLogo " + file.getPath());
			BufferedReader input = new BufferedReader(new InputStreamReader(
					p.getInputStream()));
			String line;
			while ((line = input.readLine()) != null) {
				result += line;
			}
			input.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		try{
		availspace =  (Long.parseLong(result.trim()) / 1024.0 /1024 / 1024) ;
		
		}catch(NumberFormatException e){
			availspace = 0;
		}
		availspace = (int)(availspace * 1000) / 1000.0;  
	}

	public String toString() {
		return serial;
		
		
	}
	
	public static File[] getAllDrives(){
		File[] roots = File.listRoots();
		
		return roots;
	}

	@Override
	public int compareTo(Drive arg0) {
		// TODO Auto-generated method stub
		return 0;
	}
}
