package com.lagou.controller;

import com.lagou.pojo.Article;
import com.lagou.pojo.ArticlePageVO;
import com.lagou.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

/**
 * 文章管理
 * @author wuqi
 * @date 2020-06-22 19:57
 */
@Controller
public class ArticleController {

    @Autowired
    private ArticleService articleService;

    /**
     * 首页展示
     * @param model
     * @return
     */
    @RequestMapping("/")
    private String indexPage(Integer pageNum,Integer pageSize, Model model){
        if(pageNum == null){
            pageNum = 1;
        }
        if(pageSize == null){
            pageSize = 3;
        }

        ArticlePageVO articlePageVO = articleService.selectArticleByPage(pageNum, pageSize);

        model.addAttribute("pageInfo", articlePageVO);

        return "client/index";
    }

}
