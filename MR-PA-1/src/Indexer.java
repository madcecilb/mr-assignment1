import java.io.IOException;
import java.util.ArrayList;


import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.RAMDirectory;
import org.apache.lucene.util.Version;


public class Indexer {
	private IndexWriter writer;
	private Directory indexDirectory;
	private StandardAnalyzer analyzer;
	private IndexWriterConfig config;
	private Version version;

	public Indexer(ArrayList<Text> texts) throws IOException {
		//  Specify the analyzer for tokenizing text.
	    //  The same analyzer should be used for indexing and searching
		version = Version.LUCENE_36;
		analyzer = new StandardAnalyzer(version);
		indexDirectory = new RAMDirectory();

		config = new IndexWriterConfig(version, 
				analyzer);

		writer = new IndexWriter(indexDirectory, config);
		for (Text article :texts) {
			writer.addDocument(getDocument(article));
		}
		writer.commit();
	}
	
	public void close() throws IOException {
		writer.close();
	}
	
	protected static Document getDocument(Text text){
		Document doc = new Document();
		
		doc.add(new Field("path", text.getPath(),
				Field.Store.YES, Field.Index.NO));
		doc.add(new Field("topic", text.getTopic(),
				Field.Store.YES, Field.Index.ANALYZED));
		doc.add(new Field("text", text.getText(),
				Field.Store.YES, Field.Index.ANALYZED));
		
		return doc;
	}
	
	public IndexWriter getWriter() {
		return writer;
	}

	public void setWriter(IndexWriter writer) {
		this.writer = writer;
	}

	public Directory getIndexDirectory() {
		return indexDirectory;
	}

	public void setIndexDirectory(Directory indexDirectory) {
		this.indexDirectory = indexDirectory;
	}

	public StandardAnalyzer getAnalyzer() {
		return analyzer;
	}

	public void setAnalyzer(StandardAnalyzer analyzer) {
		this.analyzer = analyzer;
	}

	public IndexWriterConfig getConfig() {
		return config;
	}

	public void setConfig(IndexWriterConfig config) {
		this.config = config;
	}

	public Version getVersion() {
		return version;
	}

	public void setVersion(Version version) {
		this.version = version;
	}
	
	
}

