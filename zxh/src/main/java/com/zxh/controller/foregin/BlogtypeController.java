package com.zxh.controller.foregin;


import com.zxh.service.BlogtypeService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

@Controller
@RequestMapping(value = "/blogtype")
public class BlogtypeController {

    @Resource
    private BlogtypeService blogtypeService;

    /*
    * 查询博客类别
    * */
    @RequestMapping(value = "/typelist")
    @ResponseBody
    public String bloglist(){
        //返回json字符串到前端
        String bloglist=null;
        bloglist=blogtypeService.findBlogTypeNameandBlogCount();

        return bloglist;
    }


}
