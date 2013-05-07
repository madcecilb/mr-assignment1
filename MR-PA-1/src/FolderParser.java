import java.io.File;


public class FolderParser {

	/**
	 * @param args
	 */
	public static void main(String[] args) 
	{
	 
		// Directory path here
		String path = "D:/eclipse-jee-juno-SR1-win32"; 
		String[] listOfFiles = getAllFilesAndDirectories(path);
		for (String string : listOfFiles) {
			System.out.println(string);
		}
	}
	
	public static File[] getAllFiles(String path){
		
		File folder = new File(path);
		File[] listOfFiles = folder.listFiles(); 		
		
		return listOfFiles;
	}
	
	public static String[] getAllFilesAndDirectories(String path){
		File folder = new File(path);
		String[] listOfFiles = folder.list(); 		
		
		return listOfFiles;
	}
}
