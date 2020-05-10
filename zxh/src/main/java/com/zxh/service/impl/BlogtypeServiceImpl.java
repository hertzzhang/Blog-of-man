package com.zxh.service.impl;

import com.alibaba.fastjson.JSON;
import com.zxh.dao.BlogtyperMapper;
import com.zxh.pojo.BlogType;
import com.zxh.service.BlogtypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

@Service
@Transactional
public class BlogtypeServiceImpl implements BlogtypeService {

    //注入mapper对象
    @Resource
    private BlogtyperMapper blogtyperMapper;

    //注入redis对象
    @Resource
    private RedisTemplate<String,String> redisTemplate;

    @Override
    public String findBlogTypeNameandBlogCount() {

        List<BlogType> blogTypes=null;
        //从redis中读取博客分类信息,String类型。
        String blogTypeName=redisTemplate.opsForValue().get("blogtypename");

        //判断redis缓存中是否存在数据，如果没有就去数据库查询，查询后存入redis
        if(blogTypeName==null||blogTypeName.equals(""))
        {
         blogTypes =   blogtyperMapper.findBlogTypeNameandBlogCount();//从数据库获取信息
         blogTypeName= JSON.toJSONString(blogTypes);//把集合转换成json字符串
         redisTemplate.opsForValue().set("blogtypename",blogTypeName);//放入到redis缓存中
        }

        return blogTypeName;
    }
}
