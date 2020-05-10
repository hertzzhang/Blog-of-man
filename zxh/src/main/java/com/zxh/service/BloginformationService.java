package com.zxh.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.zxh.pojo.BlogInformation;
import org.apache.ibatis.annotations.Param;

public interface BloginformationService {

    String findDateandDateNum();

    IPage<BlogInformation> findBloginformation(IPage<BlogInformation> page,BlogInformation blogInformation);
    BlogInformation findBlogId(int id);

    BlogInformation findBeforeID(int id);
    BlogInformation findAfterID(int id);
    int addBloginformation(BlogInformation blogInformation);
    int bloginfoDelete(int id);
}
