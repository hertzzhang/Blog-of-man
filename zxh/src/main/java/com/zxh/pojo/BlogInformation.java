package com.zxh.pojo;

import com.alibaba.fastjson.annotation.JSONField;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import org.springframework.boot.autoconfigure.domain.EntityScan;

import java.io.Serializable;
import java.util.Date;

//让mybatis plus方法按照bloginformation去查找这个表，而不用xxxx_xxxx。
@TableName("bloginformation")
public class BlogInformation implements Serializable {

    /*不使用mybatis plus提供的UUID自增。*/

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;


    private String title;
    private String summary;

    @JSONField(format = "yyyy-MM-dd")
    private Date releasedate;

    private Integer clickhit;
    private Integer replyhit;
    private String content;
    private Integer typeid;
    private String keyword;

    //数据库中无此字段,存储时间和数量。
    //在执行mybatis提供的方法时，忽略对此数据的操作。
    @TableField(exist = false)
    private String date;
    //在执行mybatis提供的方法时，忽略对此数据的操作。
    @TableField(exist = false)
    private Integer datecount;

    //一对一，存储博客类别信息
    //在执行mybatis提供的方法时，忽略对此数据的操作。
    @TableField(exist = false)
    private BlogType blogtype;

    //此对象用于存入索引文件时使用，是不带HTML标签的content
    @TableField(exist = false)
    private String contentnohtml;


    @Override
    public String toString() {
        return "BlogInformation{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", summary='" + summary + '\'' +
                ", releasedate=" + releasedate +
                ", clickhit=" + clickhit +
                ", replyhit=" + replyhit +
                ", content='" + content + '\'' +
                ", typeid=" + typeid +
                ", keyword='" + keyword + '\'' +
                ", date='" + date + '\'' +
                ", datecount=" + datecount +
                ", blogtype=" + blogtype +
                ", contentnohtml='" + contentnohtml + '\'' +
                '}';
    }

    public String getContentnohtml() {
        return contentnohtml;
    }

    public void setContentnohtml(String contentnohtml) {
        this.contentnohtml = contentnohtml;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public Date getReleasedate() {
        return releasedate;
    }

    public void setReleasedate(Date releasedate) {
        this.releasedate = releasedate;
    }

    public Integer getClickhit() {
        return clickhit;
    }

    public void setClickhit(Integer clickhit) {
        this.clickhit = clickhit;
    }

    public Integer getReplyhit() {
        return replyhit;
    }

    public void setReplyhit(Integer replyhit) {
        this.replyhit = replyhit;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Integer getTypeid() {
        return typeid;
    }

    public void setTypeid(Integer typeid) {
        this.typeid = typeid;
    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Integer getDatecount() {
        return datecount;
    }

    public void setDatecount(Integer datecount) {
        this.datecount = datecount;
    }

    public BlogType getBlogtype() {
        return blogtype;
    }

    public void setBlogtype(BlogType blogtype) {
        this.blogtype = blogtype;
    }
}
