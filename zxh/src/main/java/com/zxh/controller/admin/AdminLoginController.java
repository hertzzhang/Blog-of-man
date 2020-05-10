package com.zxh.controller.admin;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/*
*博客管理员登陆
*
* */
@Controller
@RequestMapping("/admin")
public class AdminLoginController {


    /**
     *  跳转到home页面
     * @return
     */
    @RequestMapping("/login")
    public  String loginIndex(){

        return  "admin/home";
    }

}
