package com.lagou.repository;

import com.lagou.pojo.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author wuqi
 * @date 2020-06-21 9:12
 */
public interface CommentRepository extends JpaRepository<Comment, Integer> {

    @Transactional
    @Modifying
    @Query(value = "update t_comment set author=?1 where id=?2", nativeQuery = true)
    public void updateByCommentId(String author, Integer id);
}
