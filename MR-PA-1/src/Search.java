import java.io.IOException;
import java.util.ArrayList;

import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.queryParser.MultiFieldQueryParser;
import org.apache.lucene.queryParser.ParseException;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopScoreDocCollector;
import org.apache.lucene.search.highlight.Highlighter;
import org.apache.lucene.search.highlight.InvalidTokenOffsetsException;
import org.apache.lucene.search.highlight.QueryScorer;
import org.apache.lucene.search.highlight.SimpleSpanFragmenter;
import org.apache.lucene.search.highlight.TokenSources;


public class Search {

	
	/**
	 * @param args
	 * @throws IOException 
	 * @throws ParseException 
	 * @throws InvalidTokenOffsetsException 
	 */
	public static void main(String[] args) throws IOException, ParseException, InvalidTokenOffsetsException {
		
		if(args.length != 0) {
			String path = args.length > 0 ? args[0] : "data";
		    // query
		    String querystr = args.length > 1 ? args[1] : "function";
		    	    
			ArrayList<Text> texts = FolderParser.getTexts(path);
			System.out.println(texts.size() + " files will be indexed");
		    Indexer indexer = new Indexer(texts);
		    
		    //Searches in all fields
		    Query textQuery = new MultiFieldQueryParser(
		    		indexer.getVersion(),
	                new String[] {"text", "topic"},
	                indexer.getAnalyzer()).parse(querystr);
		    
		    // search
		    int hitsPerPage = 10;
		    IndexReader reader = IndexReader.open(indexer.getIndexDirectory());
		    IndexSearcher searcher = new IndexSearcher(reader);
		    TopScoreDocCollector collector = TopScoreDocCollector.create(hitsPerPage, true);
		    searcher.search(textQuery, collector);
		    ScoreDoc[] hits = collector.topDocs().scoreDocs;
		    
		    QueryScorer scorer = new QueryScorer(textQuery, "text");
			Highlighter highlighter = new Highlighter(scorer);
			highlighter.setTextFragmenter(new SimpleSpanFragmenter(scorer, 100));
			  
		    //  display results
		    System.out.println("Found " + hits.length + " hits.");
		    for(int i=0;i<hits.length;++i) {
		      int docId = hits[i].doc;
		      Document d = searcher.doc(docId);
		      String text = d.get("text");
		      TokenStream stream =
		    		  TokenSources.getAnyTokenStream(searcher.getIndexReader(),
		    				  hits[i].doc,
		    				  "text",
		    				  d,
		    				  indexer.getAnalyzer());
		      
		      //get best excerpts
		      String[] fragment =
		    		  highlighter.getBestFragments(stream, text, 2);
		      
		      System.out.println((i + 1) + ".  Path - " + d.get("path") +  "\n" + " Score-" + hits[i].score + "\t" + "topic " +d.get("topic"));
		      for (String string : fragment) {
			      System.out.println(string);
		      }
		    }

		    // reader can only be closed when there
		    // is no need to access the documents any more.
		    reader.close();
		    searcher.close();
		}
		else {
			System.out.println("Please indicate folder to be indexed.");
		}

	}
}
