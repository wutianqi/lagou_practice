package com.lagou.dao;

import com.lagou.pojo.Comment;

/**
 * @author wuqi
 * @date 2020-06-20 14:56
 */
public interface CommentMapper {

    Comment getCommentById(Integer id);
}
