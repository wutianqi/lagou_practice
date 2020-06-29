package com.lagou.repository;

import com.lagou.pojo.Article;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * JPA操作
 *
 * @author wuqi
 * @date 2020-06-20 15:11
 */
public interface ArticleRepository extends JpaRepository<Article, Integer> {
}
