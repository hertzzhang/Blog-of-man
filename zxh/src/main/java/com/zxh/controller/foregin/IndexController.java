package com.zxh.controller.foregin;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zxh.pojo.BlogInformation;
import com.zxh.service.BloginformationService;
import com.zxh.utils.PageUtil;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.List;

@Controller
public class IndexController {

    @Resource
    private BloginformationService bloginformationService;

    @RequestMapping(value = {"/","/index","index.html"})
    public String blogindex(BlogInformation blogInformation, @RequestParam(value = "page",defaultValue = "1") Long current, Model model){
        //创建分页对象，current表示当前页码，size表示一页现实5条数据
        int size=3;
        Page<BlogInformation> page = new Page<BlogInformation>(current,size);

        StringBuffer  str =  new StringBuffer();
        //判断查询条件是否为空,用于处理在分页中的url中的信息
        if(blogInformation!=null)
        {
            if(blogInformation.getTypeid()!=null)
            {
                str.append("typeid="+blogInformation.getTypeid());
            }
            if(blogInformation.getReleasedate()!=null&& !blogInformation.getReleasedate().equals(""))
            {
                str.append("releasedate="+blogInformation.getReleasedate());
            }
        }

        //调用数据库查询，并把结果分页
        IPage<BlogInformation> blogPage= bloginformationService.findBloginformation(page,blogInformation);
        //获取博客数据列表
        List<BlogInformation> records = blogPage.getRecords();
        //将对象放在model中,存放在requeset域中。在main页面中使用
        model.addAttribute("blogpagelist",records);
        //将index页面需要跳转的页面地址存入到requeset中，在index页面中使用
        model.addAttribute("pagecontent","foreign/main");
        //将分页信息存放在requeset中
        model.addAttribute("pagecode",
                PageUtil.genPagination("/index",blogPage.getTotal(),current.intValue(),size,str.toString()));
        return "index";
    }


}
