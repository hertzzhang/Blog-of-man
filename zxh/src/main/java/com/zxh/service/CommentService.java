package com.zxh.service;

import com.zxh.pojo.Comment;

public interface CommentService {

    /*
    * 查看评论信息
    * */
    int commentSave(Comment comment);
    /*
    * 根据博客ID，删除评论
    * */
    int commentDelete(int id);
}
