package com.zxh.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zxh.pojo.BlogType;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

public interface BlogtyperMapper extends BaseMapper<BlogType> {


    /*查询博客分类名称和该分类下文章的数量*/
    List<BlogType> findBlogTypeNameandBlogCount();

}
