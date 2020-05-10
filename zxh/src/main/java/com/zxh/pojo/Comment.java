package com.zxh.pojo;

import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.util.Date;

@TableName("blogcomment")
public class Comment implements Serializable {

    private Integer id;
    private String userip;
    private Integer blogid;
    private String content;
    private Date commentdate;
    private  Integer state;

    @Override
    public String toString() {
        return "Comment{" +
                "id=" + id +
                ", userip='" + userip + '\'' +
                ", blogid=" + blogid +
                ", content='" + content + '\'' +
                ", commentdate=" + commentdate +
                ", state=" + state +
                '}';
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUserip() {
        return userip;
    }

    public void setUserip(String userip) {
        this.userip = userip;
    }

    public Integer getBlogid() {
        return blogid;
    }

    public void setBlogid(Integer blogid) {
        this.blogid = blogid;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getCommentdate() {
        return commentdate;
    }

    public void setCommentdate(Date commentdate) {
        this.commentdate = commentdate;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }
}
