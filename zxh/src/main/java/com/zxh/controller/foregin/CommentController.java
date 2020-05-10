package com.zxh.controller.foregin;

import com.alibaba.fastjson.JSON;
import com.zxh.pojo.Comment;
import com.zxh.service.CommentService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/comment")
public class CommentController {

    @Resource
    private CommentService commentService;


    static Map<String,Object> map = new HashMap<String,Object>();
    @PostMapping("/addcomment")
    @ResponseBody
    public String addComment(Comment comment) throws UnknownHostException {

        map.clear();

       int flag=  commentService.commentSave(comment);
       if (flag==1)
       {
           map.put("success",true);
       }
       if(flag==0)
       {
           map.put("success",false);
       }
       return JSON.toJSONString(map);
    }
}
