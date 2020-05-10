package com.zxh.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zxh.pojo.Comment;
import org.apache.ibatis.annotations.Param;

public interface CommentMapper extends BaseMapper<Comment> {

    //返回一个插入的数量
    int commentSave(@Param("comment") Comment comment);
}
