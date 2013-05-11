import java.io.File;
import java.io.IOException;
import java.util.ArrayList;


public class FolderParser {

	public static ArrayList<String> files;
	
	public static void initializeFileList(){
		files = new ArrayList<String>();
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
	
	public static ArrayList<Text> getTexts(String path) throws IOException {
		 initializeFileList();
		 
		 ArrayList<Text> texts = new ArrayList<Text>();
		 
		 findAllFiles(path);
		 
		 for (String file : files) {
			texts.add(new Text(file));
		 }
		 return texts;
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
