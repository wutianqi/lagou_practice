package com.lagou.service;

import com.lagou.pojo.Comment;
import com.lagou.repository.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.concurrent.TimeUnit;

/**
 * 基于Api的缓存实现
 * @author wuqi
 * @date 2020-06-21 9:13
 */
@Service
public class ApiCommentService {

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private RedisTemplate redisTemplate;

    /**
     * 查询评论
     * @param id
     * @return
     */
    public Comment getCommentById(Integer id){
        //从缓存中获取
        Object o = redisTemplate.opsForValue().get("comment_" + id);

        if(o != null){
            return (Comment) o;
        }

        //缓存中没有，则查询出存入缓存中
        Optional<Comment> byId = commentRepository.findById(id);
        if(byId.isPresent()){
            redisTemplate.opsForValue().set("comment_" + id, byId.get(), 1, TimeUnit.DAYS);
        }

        return byId.get();
    }

    /**
     * 更新数据并将最新的数据存入缓存
     * @param comment
     */
    public Comment updateById(Comment comment){
        commentRepository.updateByCommentId(comment.getAuthor(), comment.getId());

        //更新缓存
        redisTemplate.opsForValue().set("comment_" + comment.getId(), comment, 1, TimeUnit.DAYS);

        return comment;
    }

    /**
     * 删除数据然后删除缓存
     * @param id
     */
    public void deleteById(Integer id){
        commentRepository.deleteById(id);

        //删除缓存
        redisTemplate.delete("comment_" + id);
    }
}
