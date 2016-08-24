package lucene;

import java.nio.file.Paths;

import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field.Store;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.index.Term;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TermQuery;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.FSDirectory;
import org.junit.Test;

public class luceneDemo {
	@Test
	public void index() throws Exception {
		// 创建一个文档Document
		Document doc = new Document();
		// 进行索引
		doc.add(new TextField("title", "三星 W999 黑色 电信3G手机 双卡双待双通", Store.YES));
		// 找到一个目录，将创建是索引文件放入这个目录，当前执行路径
		FSDirectory dir = FSDirectory.open(Paths.get("./index"));

		// 标准分词器，基于英文分词
		StandardAnalyzer analyzer = new StandardAnalyzer();

		IndexWriterConfig config = null;
		try {
			config = new IndexWriterConfig(analyzer);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// 定义索引对象
		IndexWriter indexWriter = new IndexWriter(dir, config);
		// 写入数据
		indexWriter.addDocument(doc);

		indexWriter.close();
		dir.close();
	}

	@Test
	public void Query() throws Exception {
		// 定义索引位置
		FSDirectory directory = FSDirectory.open(Paths.get("./index"));

		IndexSearcher indexSearcher = new IndexSearcher(DirectoryReader.open(directory));

		// 构造查询对象，词条搜索
		TermQuery query = new TermQuery(new Term("title", "星"));

		TopDocs topDocs = indexSearcher.search(query, 10);
		System.out.println("搜索结果总结：" + topDocs.totalHits);
		for (ScoreDoc scoreDoc : topDocs.scoreDocs) {
			System.out.println("得分：" + scoreDoc.score);

			// 通过文档id查询文档数据
			Document doc = indexSearcher.doc(scoreDoc.doc);
			System.out.println("商品标题" + doc.get("title"));
		}

	}
}
