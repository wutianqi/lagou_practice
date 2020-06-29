package com.lagou.dao;

import com.lagou.pojo.Article;

import java.util.List;

/**
 * 文章
 * @author wuqi
 * @date 2020-06-22 20:11
 */
public interface ArticleMapper {
    /**
     * 分页查新
     * @return
     */
    List<Article> selectByPage();
}
