package com.zxh.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.zxh.dao.CommentMapper;
import com.zxh.pojo.Comment;
import com.zxh.service.CommentService;
import com.zxh.utils.ExamineUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Date;

@Service
public class CommentServiceImpl implements CommentService {

    @Resource
    private CommentMapper commentMapper;

    @Override
    public int commentSave(Comment comment) {

        try {
            //添加评论的时间
            comment.setCommentdate(new Date());
            //设置评论审核的状态
            comment.setState(ExamineUtil.COMMENT_Eaxmine_No);
            //添加评论用户的IP
            comment.setUserip(InetAddress.getLocalHost().getHostAddress());
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }


        return commentMapper.commentSave(comment);
    }

    @Override
    public int commentDelete(int id) {
        QueryWrapper<Comment> queryWrapper = new QueryWrapper<Comment>();
        queryWrapper.eq("blogid",id);
        int flag = commentMapper.delete(queryWrapper);
        return flag;
    }
}
