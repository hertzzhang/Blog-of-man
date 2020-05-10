package com.zxh.service.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.zxh.dao.BloginformationMapper;
import com.zxh.pojo.BlogInformation;
import com.zxh.service.BloginformationService;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

@Service
@Transactional
public class BloginformationServiceImpl implements BloginformationService {

    @Resource
    private BloginformationMapper bloginformationMapper;

    @Resource
    private RedisTemplate<String,String> redisTemplate;

    @Override
    public String findDateandDateNum() {
        List<BlogInformation> blogInformations=null;
        String datecount=redisTemplate.opsForValue().get("datecount");
        if(datecount==null||datecount.equals(""))
        {
            blogInformations=bloginformationMapper.findDateandDateNum();
            datecount= JSON.toJSONString(blogInformations);
            redisTemplate.opsForValue().set("datecount",datecount);
        }


        return datecount;
    }

    @Override
    public IPage<BlogInformation> findBloginformation(IPage<BlogInformation> page, BlogInformation blogInformation) {

        return bloginformationMapper.findBloginformation(page,blogInformation);
    }

    @Override
    public BlogInformation findBlogId(int id) {

        //通过ID查找博客的基本信息和类型
        BlogInformation blogInformation= bloginformationMapper.findBlogId(id);

        //updateById，通过ID，修改博客的全部信息 ，此处只修改了点击数
        blogInformation.setClickhit(blogInformation.getClickhit()+1);
        bloginformationMapper.updateById(blogInformation);
        return blogInformation;
    }

    @Override
    public BlogInformation findBeforeID(int id) {
        return bloginformationMapper.findBeforeID(id);
    }

    @Override
    public BlogInformation findAfterID(int id) {
        return bloginformationMapper.findAfterID(id);
    }

    @Override
    public int addBloginformation(BlogInformation blogInformation) {
        blogInformation.setClickhit(0);
        blogInformation.setReplyhit(0);
        blogInformation.setReleasedate(new Date());
        System.out.println(blogInformation);
        //清空redis缓存
        redisTemplate.delete("datecount");
        redisTemplate.delete("blogtypename");
       int flag= bloginformationMapper.insert(blogInformation);
        return flag;
    }

    @Override
    public int bloginfoDelete(int id) {
       //清空缓存
        redisTemplate.delete("datecount");
        redisTemplate.delete("blogtypename");

      int flag=  bloginformationMapper.deleteById(id);
        return flag;
    }


}
