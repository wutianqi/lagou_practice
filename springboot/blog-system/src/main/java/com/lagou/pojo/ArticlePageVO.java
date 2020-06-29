package com.lagou.pojo;

import java.util.List;

/**
 * 分页查询返回结果
 * @author wuqi
 * @date 2020-06-22 20:32
 */
public class ArticlePageVO {
    /**
     * 文章列表
     */
    private List<Article> articles;
    /**
     * 总条数
     */
    private Integer totalCount;
    /**
     * 总页数
     */
    private Integer totalPageNum;
    /**
     * 当前页数
     */
    private Integer currentPageNum;


    public List<Article> getArticles() {
        return articles;
    }

    public void setArticles(List<Article> articles) {
        this.articles = articles;
    }

    public Integer getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(Integer totalCount) {
        this.totalCount = totalCount;
    }

    public Integer getTotalPageNum() {
        return totalPageNum;
    }

    public void setTotalPageNum(Integer totalPageNum) {
        this.totalPageNum = totalPageNum;
    }

    public Integer getCurrentPageNum() {
        return currentPageNum;
    }

    public void setCurrentPageNum(Integer currentPageNum) {
        this.currentPageNum = currentPageNum;
    }
}
