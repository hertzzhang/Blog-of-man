package com.zxh.controller.foregin;

import com.zxh.Lucenen.BloginformationIndex;
import com.zxh.pojo.BlogInformation;
import com.zxh.service.BloginformationService;
import org.slf4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.List;
import java.util.Scanner;

@Controller
@RequestMapping(value = "/bloginformation")
public class BloginformationController {

    @Resource
    private BloginformationService bloginformationService;
    @Resource
    private BloginformationIndex bloginformationIndex;

    /*
    * 根据日期查询博客和分类。
    * */
    @RequestMapping(value = "/datecount")
    @ResponseBody
    public String blogdatecount(){


        String datecount =null;
        datecount=bloginformationService.findDateandDateNum();

        return  datecount;
    }


    /**
     *  根据博客ID，查看该博客具体内容。
     * @param id
     * @return
     */
    @RequestMapping("/view/{bid}")
    public String blogContent(@PathVariable(name = "bid") int id, Model model){

        //存入博客的信息到model中
        BlogInformation blogInformation= bloginformationService.findBlogId(id);
        model.addAttribute("bloginformation",blogInformation);
        // 对关键字进行处理，然后存入model中。
        if(blogInformation.getKeyword()!=null)
        {
                //用空格拆分关键字，前提是数据库的关键字使用空格隔离。
                String[] keyword = blogInformation.getKeyword().split(" ");
                model.addAttribute("keyword",keyword);
        }
        else
        {
            model.addAttribute("keyword",null);
        }

        //存入博客的上一篇和下一篇的信息到model中
        model.addAttribute("pagenumber",getPagenumer(bloginformationService.findBeforeID(id),bloginformationService.findAfterID(id)));

        //将index页面需要跳转的页面地址存入到requeset中，在index页面中使用
        model.addAttribute("pagecontent","foreign/blog/view");
        return "index";
    }



    /**
     * 处理 上一篇 和下一篇 的代码
     * @param before 上一篇的博客信息
     * @param after 下一篇的博客信息
     * @return
     */
     static StringBuffer sb = new StringBuffer();
    private  String getPagenumer(BlogInformation before ,BlogInformation after){
            //每次调用此方法时，都把 StringBuffer对象变成空。
        // Stringbuffer sb =  new StringBuffer() 如果在方法体中调用会生成很多无用的对象，会导致垃圾回收。
            sb.setLength(0);

            if(before==null||before.getId()==null)
            {
                sb.append("<p>上一篇：没有上一篇</p>");
            }
            else
            {
                sb.append("<p>上一篇：<a href='/bloginformation/view/"+before.getId()+"'>"+before.getTitle()+"</a></p>");
            }
        if(after==null||after.getId()==null)
        {
            sb.append("<p>下一篇：没有下一篇</p>");
        }
        else
        {
            sb.append("<p>下一篇：<a href='/bloginformation/view/"+after.getId()+"'>"+after.getTitle()+"</a></p>");
        }

        return  sb.toString();
    }

    /**
     *  将索引文件的数据返回给index页面。
     * @param indexword
     * @param page
     * @param model
     * @return
     * @throws Exception
     */
    @RequestMapping("/indexkeyword")
    public String indexKeyword(String indexword, @RequestParam(value = "page",required = false,defaultValue = "1") Integer page,Model model) throws Exception {
        //设置每页显示的条数
        int pageSize = 3;
        System.out.println("11111");
        //获取从索引文件中得到的结果
        List<BlogInformation> blogindexlist =bloginformationIndex.searchBlogInfo(indexword);

       //设置当前页显示多少数据。
        Integer indexnum= blogindexlist.size()>=page*pageSize ? page*pageSize : blogindexlist.size();
        //将当前页的博客信息和数量放入model中
        model.addAttribute("blogindexlist",blogindexlist.subList((page-1)*pageSize,indexnum));
        model.addAttribute("num",blogindexlist.size());
        model.addAttribute("indexword",indexword);
        model.addAttribute("pagecontent","foreign/blog/result");
        //添加分页的菜单。
        model.addAttribute("pageindexcode",getPageindexcode(page,pageSize,blogindexlist.size(),indexword));
        return "index";
    }

    /**
     *  处理 索引关键字的分页信息。
     * @param page
     * @param pagesize
     * @param blogsize
     * @param indexword
     * @return
     */
    private  String getPageindexcode(int page,int pagesize,int blogsize,String indexword){
        StringBuffer pageindexcode = new StringBuffer();
        //计算总页数
        int pagenum = blogsize % pagesize ==0 ? blogsize/pagesize : blogsize/pagesize+1;
         if(pagenum==0)
         {
             return "";
         }
         else
         {
             pageindexcode.append("<nav>");
             pageindexcode.append("<ul class='pager'>");
             //判断是否可以点击上一页
             if (page>1)
             {
                    pageindexcode.append("<li><a href='/bloginformation/indexkeyword?page="+(page-1)+"&indexword="+indexword+"'>上一页</a></li>");
             }
             else
             {
                 pageindexcode.append("<li class='disabled'><a>上一页</a></li>");
             }
             //判断是否可以点击下一页
             if (page<pagenum)
             {
                 pageindexcode.append("<li><a href='/bloginformation/indexkeyword?page="+(page+1)+"&indexword="+indexword+"'>下一页</a></li>");

             }
             else
             {
                 pageindexcode.append("<li class='disabled'><a>下一页</a></li>");
             }
             pageindexcode.append("</ul>");
             pageindexcode.append("</nav>");
         }
         return  pageindexcode.toString();
    }
}
