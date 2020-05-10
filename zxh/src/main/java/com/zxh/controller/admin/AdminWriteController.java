package com.zxh.controller.admin;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zxh.Lucenen.BloginformationIndex;
import com.zxh.pojo.BlogInformation;
import com.zxh.service.BloginformationService;
import com.zxh.service.CommentService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/adminwrite")
public class AdminWriteController {

    @Resource
    private BloginformationService bloginformationService;

    @Resource
    BloginformationIndex bloginformationIndex;

    @Resource
    private CommentService commentService;

    /**
     *
     * 跳转到写博客页面
     * @return
     */
    @RequestMapping("/writerblog")
    public String writerBlog(){

        return "admin/writeBlog";
    }

    /**
     *  添加新的博客
     * @return
     */
    private static Map<String,Object> map = new HashMap<String, Object>();
    @RequestMapping("/addBloginformation")
    @ResponseBody
    public String addBloginformation(BlogInformation blogInformation) throws IOException {
        map.clear();
        int flag=bloginformationService.addBloginformation(blogInformation);
        //发布成功后，将相关信息存入索引库中
        bloginformationIndex.addIndex(blogInformation);
        if(flag!=0)
        {
            map.put("success",true);
        }
        else {
            map.put("success",false);
        }

        return JSON.toJSONString(map);
    }

    @RequestMapping("/blogManage")
    public  String toblogManage(){


        return "admin/blogManage";
    }

    /**
     *
     * @param blogInformation
     * @param page 当前页码
     * @param rows  一页有有多少记录
     * @return
     */
    @RequestMapping("/blogManagelist")
    @ResponseBody
    public String searchBloglist(BlogInformation blogInformation,Integer page,Integer rows){
        Map<String,Object> map = new HashMap<String, Object>();
        Page<BlogInformation> page1 = new Page<BlogInformation>(page,rows);
        IPage<BlogInformation> blogInformationIPage = bloginformationService.findBloginformation(page1,blogInformation);
        map.put("bloglist",blogInformationIPage.getTotal());
        map.put("rows",blogInformationIPage.getRecords());
        return JSON.toJSONString(map);
    }

    /**
     * 根据多组博客ID，删除博客
     * @param blogids
     * @return
     */
    @RequestMapping("/deletblog")
    @ResponseBody
    public  String deletBlogInfo(@RequestParam("ids") String blogids) throws IOException {
        Map<String,Object> map = new HashMap<String, Object>();
        String[] ids = blogids.split(",");
        for(int i=0;i<ids.length;i++)
        {
            commentService.commentDelete(Integer.parseInt(ids[i]));
            int count = bloginformationService.bloginfoDelete(Integer.parseInt(ids[i]));
            if(count>0)
            {
                map.put("success",true);
                bloginformationIndex.indexDelete(ids[i]);
            }
        }
        return JSON.toJSONString(map);
    }
    @RequestMapping("/upbloginfo")
    public String upblogInfo(int blogId,Model model)
    {
        model.addAttribute("upblogid",blogId);
        return "admin/modifyBlog";
    }

    /**
     * 根据博客ID，查找博客信息
     * @param blogId
     * @return
     */
    @RequestMapping("findblogID")
    @ResponseBody
    public Object findBlogbyid(int blogId){
        BlogInformation blogInformation = bloginformationService.findBlogId(blogId);
        return  blogInformation;
    }

}
