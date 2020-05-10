package com.zxh.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.zxh.pojo.BlogInformation;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface BloginformationMapper extends BaseMapper<BlogInformation> {

    //按照日期给博客分类，存入到list对象中，返回一个List对象。
    List<BlogInformation> findDateandDateNum();

    //分页查询博客信息，存入到分页对象中，返回一个分页对象
    //@Param和 xml里面 ParamType作用一样。
    IPage<BlogInformation> findBloginformation(@Param("IPage") IPage<BlogInformation> page,@Param("blog")BlogInformation blogInformation);

    /**
     * 根据id查看博客的内容。
     * @param id
     * @return
     */
    BlogInformation findBlogId(int id);

    BlogInformation findBeforeID(int id);
    BlogInformation findAfterID(int id);



}
