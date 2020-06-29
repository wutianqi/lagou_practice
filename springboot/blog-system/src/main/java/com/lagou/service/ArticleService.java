package com.lagou.service;

import com.lagou.pojo.ArticlePageVO;

/**
 * 文章
 * @author wuqi
 * @date 2020-06-22 20:25
 */
public interface ArticleService {
    /**
     * 分页查询文章列表
     * @param pageNum 页码
     * @param pageSize 每页多少条
     * @return
     */
    ArticlePageVO selectArticleByPage(int pageNum, int pageSize);
}
