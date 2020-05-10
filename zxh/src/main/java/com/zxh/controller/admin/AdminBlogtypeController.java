package com.zxh.controller.admin;

import com.zxh.service.BlogtypeService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

@Controller
@RequestMapping("/admintype")
public class AdminBlogtypeController {


    @Resource
    private BlogtypeService blogtypeService;

    /*
    * 获取博客分类名称
    *
    * */
    @RequestMapping("/blogtype")
    @ResponseBody
    public  String blogtypelist(){

       String typeandcount = blogtypeService.findBlogTypeNameandBlogCount();

       return  typeandcount;
    }

}
