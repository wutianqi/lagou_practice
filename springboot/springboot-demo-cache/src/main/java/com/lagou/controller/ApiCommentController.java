package com.lagou.controller;

import com.lagou.pojo.Comment;
import com.lagou.service.ApiCommentService;
import com.lagou.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author wuqi
 * @date 2020-06-21 9:15
 */
@RestController
@RequestMapping("/api")
public class ApiCommentController {

    @Autowired
    private ApiCommentService commentService;

    @RequestMapping("/getCommentById")
    public Comment getCommentById(Integer id){
        return commentService.getCommentById(id);
    }

    @RequestMapping("/updateById")
    public Comment updateById(Comment comment){
        Comment oldComment = commentService.getCommentById(comment.getId());

        oldComment.setAuthor(comment.getAuthor());

        Comment newComment = commentService.updateById(oldComment);

        return newComment;
    }

    @RequestMapping("/deleteById")
    public void deleteById(Integer id){
        commentService.deleteById(id);
    }
}
