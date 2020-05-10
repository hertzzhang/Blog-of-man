package com.zxh.controller.foregin;

import com.zxh.pojo.BlogUser;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/bloguser")
public class BlogUserController {


    @RequestMapping("/login")
    public  String login(BlogUser blogUser){
        try {
            //获取当前登录用户主体
            Subject subject = SecurityUtils.getSubject();
            //创建登录令牌
            UsernamePasswordToken usernamePasswordToken = new UsernamePasswordToken(blogUser.getUsername(),blogUser.getPassword());
            //登录
            subject.login(usernamePasswordToken);
            //保存当前登录用户
            subject.getSession().setAttribute("loginuser",blogUser);
        }
        catch (Exception e){
            e.printStackTrace();
            //登录失败回到当前登录页面,采用重定向方式
            return "redirect:/login.html";
        }
        //登录成功，跳转到后台首页
         return "redirect:/admin/login";
    }

}
