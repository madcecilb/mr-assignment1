import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import org.jsoup.Jsoup;

public class Text {
	private String path;
	private String topic;
	private String text;
	
	public Text(String iPath) throws IOException{
		path = iPath;
		String fileText = readFileAsString(iPath);
		if(!fileText.contains("HTML")){
			topic = "Plain Text";
			text = fileText;
		}
		else{
			File in = new File(iPath);
			org.jsoup.nodes.Document jSoupDocument = org.jsoup.Jsoup.parse(in, null);
			
			topic = jSoupDocument.title();
			text = jSoupDocument.body()!=null ? jSoupDocument.body().text() : "";
		}
	}
	
	public static String readFileAsString(String filePath) throws java.io.IOException
	{
	    BufferedReader reader = new BufferedReader(new FileReader(filePath));
	    String line, results = "";
	    while( ( line = reader.readLine() ) != null)
	    {
	        results += line;
	    }
	    reader.close();
	    return results;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public String getTopic() {
		return topic;
	}

	public void setTopic(String topic) {
		this.topic = topic;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}
	
}
