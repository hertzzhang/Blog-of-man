package com.zxh.Lucenen;

import com.zxh.pojo.BlogInformation;
import com.zxh.utils.DateUtil;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.StringField;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.*;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.*;
import org.apache.lucene.search.highlight.*;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.wltea.analyzer.lucene.IKAnalyzer;

import java.io.IOException;
import java.io.StringReader;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/*
*
* 博客索引库，在搜索关键字时使用。
* */
@Component
public class BloginformationIndex {

   //声明创建索引文件存放的目录对象
    private Directory dir;

    /**
     * 生成并返回IndexWriter对象，用于写入索引文件。
     * @return
     */
    public IndexWriter getIndexWriter() throws IOException {

        //1.读取索引文件的地址,生成对象
        dir= FSDirectory.open(Paths.get("D:\\112"));
        //2.指定写入器的分词类型（这里是IK分词）
        IndexWriterConfig indexWriterConfig = new IndexWriterConfig(new IKAnalyzer());
        //3.创建inderwriter对象，把地址跟分词类型存入到对象中。
        IndexWriter indexWriter =  new IndexWriter(dir,indexWriterConfig);
        return indexWriter;
    }

    /**把博客写入到索引文件中
     *
     * @param blogInformation 需要写入索引文件的数据。
     */
    public void addIndex(BlogInformation blogInformation) throws IOException {
            //获取indexwriter对象
            IndexWriter indexWriter = getIndexWriter();
            //创建文档对象
            Document document = new Document();
            //在索引文件中存储四个内容， 博客ID ，博客发布时间，博客标题，博客内容，
            //方法参数解释 ： 参数1：域名字段名称，参数2：字段值(只能是String类型)，参数3：是否存储到文档中
            //StringField不进行分词，TextFiled进行分词

            document.add(new StringField("id",String.valueOf(blogInformation.getId()), Field.Store.YES));
            document.add(new StringField("releasedate", DateUtil.formatDate(new Date(),"yyyy-MM-dd"), Field.Store.YES));
            document.add(new TextField("title",blogInformation.getTitle(), Field.Store.YES));
            document.add(new TextField("content",blogInformation.getContentnohtml(), Field.Store.YES));
            //将document文档对象，通过indexwriter对象写入到索引文件中。
            indexWriter.addDocument(document);
            //关闭索引写入流
            indexWriter.close();

    }

    /**
     *  根据索引关键字查询博客信息
     * @param indexword
     * @return
     */
    public List<BlogInformation> searchBlogInfo(String indexword) throws Exception {

        //创建集合，保存根据搜索的关键字查询出来的结果
        List<BlogInformation> list = new ArrayList<BlogInformation>();
        //1.读取索引文件的地址，生成对象
        dir= FSDirectory.open(Paths.get("D:\\112"));
        //2.把dir对象加入到输出流中，读取到indexReader对象中
        IndexReader indexReader = DirectoryReader.open(dir);
        //3.创建indexsearcher对象，把inderReader读取到indexsearcher中
        IndexSearcher indexSearcher = new IndexSearcher(indexReader);
        //4.创建分词对象
        IKAnalyzer ikAnalyzer = new IKAnalyzer();

        //5.创建QureyParser对象，传入需要查找的范围和分词器类型
        //参数1：与创建索引时一致，参数2：分词器类型
        QueryParser queryTitle = new QueryParser("title",ikAnalyzer);

        //使用关键字的域对索引关键字indexword进行分词，把结果保存到query中
        Query queryTitleindex = queryTitle.parse(indexword);
        QueryParser queryContent = new QueryParser("content",ikAnalyzer);
        //使用关键字的域对索引关键字indexword进行分词，把结果保存到query中
        Query queryContentindex = queryContent.parse(indexword);

        BooleanQuery.Builder builder =new BooleanQuery.Builder();
        //6.把query添加到builder之中，并指定权限
        builder.add(queryTitleindex, BooleanClause.Occur.SHOULD);
        builder.add(queryContentindex, BooleanClause.Occur.SHOULD);
        //从indexsearch中查找出含有indexword关键字的100条信息
        TopDocs topDocs = indexSearcher.search(builder.build(),100);

        //让含有索引关键字的indexword分词高亮显示
        QueryScorer queryScorer =new QueryScorer(queryTitleindex);
        Fragmenter fragmenter = new SimpleSpanFragmenter(queryScorer);
        SimpleHTMLFormatter simpleHTMLFormatter = new SimpleHTMLFormatter("<b><font color='red'>","</font></b>");
        Highlighter highlighter = new Highlighter(simpleHTMLFormatter,queryScorer);
        highlighter.setTextFragmenter(fragmenter);



        for(ScoreDoc scoreDoc: topDocs.scoreDocs)
        {
            //获取每一个文本对象
            Document document = indexSearcher.doc(scoreDoc.doc);
            //将结果存入博客对象中

            BlogInformation blogInformation =  new BlogInformation();

            blogInformation.setId(Integer.parseInt(document.get("id")));
            blogInformation.setReleasedate(DateUtil.formatString(document.get("releasedate"),"yyyy-MM-dd"));
            String title = document.get("title");
            String content =document.get("content");
            if(title!=null)
            {
                //让之前设置的indexword分词结果与title分词结果进行比较，获取最佳匹配的title分词结果，让title分词结果高亮显示
                TokenStream tokenStream = ikAnalyzer.tokenStream("title",new StringReader(title));
                String hightitle = highlighter.getBestFragment(tokenStream,title);
                if(StringUtils.isEmpty(highlighter))
                {
                    blogInformation.setTitle(title);
                }
                else
                    {
                    blogInformation.setTitle(hightitle);
                }
            }
            if(content!=null)
            {
                //获取最佳匹配的content，让content高亮显示
                TokenStream tokenStream = ikAnalyzer.tokenStream("content",new StringReader(content));
                String highcontent = highlighter.getBestFragment(tokenStream,content);
                if(StringUtils.isEmpty(highcontent))
                {
                    if(content.length()<=200)
                    {
                        blogInformation.setContent(content);
                    }
                    else {
                        blogInformation.setContent(content.substring(0,200));
                    }
                }
                else
                 {
                    blogInformation.setContent(highcontent);
                }
            }
            list.add(blogInformation);
        }

        return list;

    }

    /**
     * 根据ID删除，lucenen文件中的博客
     * @param id
     */
    public  void indexDelete(String id) throws IOException {
        //获取indexwriter对象
        IndexWriter indexWriter = getIndexWriter();
        //根据id删除 lucenen文件的信息
        Long i=indexWriter.deleteDocuments(new Term("id",id));
        System.out.println(i+"+++++23456789");
        indexWriter.forceMergeDeletes();
        indexWriter.commit();
        indexWriter.close();
    }

}
