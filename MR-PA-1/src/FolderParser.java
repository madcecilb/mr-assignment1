import java.io.File;
import java.util.ArrayList;


public class FolderParser {

	public static ArrayList<String> files;
	
	/**
	 * @param args
	 */
	public static void main(String[] args) 
	{	 
		// Directory path here
		String path = "data"; 
		//String[] listOfFiles = getAllFilesAndDirectories(path);
		//for (String string : listOfFiles) {
		//	System.out.println(string);
		//}
		files = new ArrayList<String>();
		
		findAllFiles(path);
		
		for (String string : files) {
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
	
	public static void findAllFiles(String path){
		
		String[] filesAndDirectories = getAllFilesAndDirectories(path);
		
		for (String string : filesAndDirectories) {
			if(!checkType(getWholePath(path, string))) {
				files.add(getWholePath(path, string));
			}
			else{
				findAllFiles(getWholePath(path, string));
			}
		}
	}
	
	public static String getWholePath(String path, String name){
		return path+"/"+name;
	}
	
	//returns true if directory and false if file
	public static Boolean checkType(String path){
		File fileOrFolder = new File(path);
		
		return fileOrFolder.isDirectory();
	}
}
