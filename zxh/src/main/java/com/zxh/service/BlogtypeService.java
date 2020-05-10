package com.zxh.service;

import com.zxh.pojo.BlogType;

import java.util.List;

public interface BlogtypeService  {

   /**
    * 查询博客分类和该分类下博客的数量
    * @return
    */
   String findBlogTypeNameandBlogCount();


}
