package com.zxh.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.zxh.dao.BlogUserMapper;
import com.zxh.pojo.BlogUser;
import com.zxh.service.BloguserService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class BloguserServiceImpl implements BloguserService {

    @Resource
    private BlogUserMapper blogUserMapper;

    @Override
    public BlogUser findBlogUser(String username) {
        QueryWrapper<BlogUser> queryWrapper = new QueryWrapper<BlogUser>();
        //前一个属性column跟数据库字段名称保存一致，后一个为参数
        //相当于 key =  value
        queryWrapper.eq("username",username);
        BlogUser blogUser= blogUserMapper.selectOne(queryWrapper);
        return blogUser;
    }
}
