package com.zxh.service;

import com.zxh.pojo.BlogUser;

public interface BloguserService {

    /**
     *  根据博主名称，查询博主信息。
     * @param username
     * @return
     */
    BlogUser  findBlogUser(String username);
}
