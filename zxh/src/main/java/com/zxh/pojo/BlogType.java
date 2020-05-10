package com.zxh.pojo;

import com.baomidou.mybatisplus.annotation.TableField;

import java.io.Serializable;

public class BlogType  implements Serializable {

    private Integer id;
    private String typename;
    private Integer orderno;
    //每个博客分类下的博客数量(数据库表中不存在)
    //@TableField(exist = false) 忽略该字段与数据库表匹配
  /*  @TableField(exist = false)*/
    private Integer blogcount;

    @Override
    public String toString() {
        return "BlogType{" +
                "id=" + id +
                ", typename='" + typename + '\'' +
                ", orderno=" + orderno +
                ", blogcount=" + blogcount +
                '}';
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTypename() {
        return typename;
    }

    public void setTypename(String typename) {
        this.typename = typename;
    }

    public Integer getOrderno() {
        return orderno;
    }

    public void setOrderno(Integer orderno) {
        this.orderno = orderno;
    }

    public Integer getBlogcount() {
        return blogcount;
    }

    public void setBlogcount(Integer blogcount) {
        this.blogcount = blogcount;
    }
}
