package com.lagou.service;

import com.lagou.pojo.Comment;
import com.lagou.repository.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * 基于注解的缓存实现
 * @author wuqi
 * @date 2020-06-21 9:13
 */
@Service
public class CommentService {

    @Autowired
    private CommentRepository commentRepository;

    /**
     * 查询评论
     * @param id
     * @return
     */
    //先从缓存查询，缓存不存在再去数据库查询，查询完成后存入缓存
    @Cacheable(cacheNames = "comments", key = "#id", unless = "#result == null") //值为空时不缓存
    public Comment getCommentById(Integer id){
        Optional<Comment> byId = commentRepository.findById(id);
        return byId.orElse(null);
    }

    /**
     * 更新数据并将最新的数据存入缓存
     * @param comment
     */
    //先更新，然后再把结果存入缓存中
    @CachePut(cacheNames = "comments", key = "#comment.id") //缓存的key为评论id
    public Comment updateById(Comment comment){
        commentRepository.updateByCommentId(comment.getAuthor(), comment.getId());

        return comment;
    }

    /**
     * 删除数据然后删除缓存
     * @param id
     */
    //先执行删除，最后再把缓存删除
    @CacheEvict(cacheNames = "comments", key = "#id") //缓存的key为id
    public void deleteById(Integer id){
        commentRepository.deleteById(id);
    }
}
