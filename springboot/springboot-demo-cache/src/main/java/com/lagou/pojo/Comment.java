package com.lagou.pojo;

import javax.persistence.*;
import java.io.Serializable;

/**
 * @author wuqi
 * @date 2020-06-20 14:40
 */
@Entity
@Table(name = "t_comment")
public class Comment implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String content;
    private String author;
    @Column(name = "a_id")
    private Integer aId;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public Integer getaId() {
        return aId;
    }

    public void setaId(Integer aId) {
        this.aId = aId;
    }

    @Override
    public String toString() {
        return "Comment{" +
                "id=" + id +
                ", content='" + content + '\'' +
                ", author='" + author + '\'' +
                ", aId=" + aId +
                '}';
    }
}
