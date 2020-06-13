package com.lagou.edu.controller;

import com.lagou.edu.dao.ResumeDao;
import com.lagou.edu.pojo.Resume;
import com.lagou.edu.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Optional;

/**
 * @author wuqi
 * @date 2020-06-14 0:07
 */
@Controller
@RequestMapping("/resume")
public class ResumeController {
    @Autowired
    private ResumeDao resumeDao;

    /**
     * 获取简历列表
     * @param request
     * @return
     */
    @RequestMapping("/list")
    public String resume(HttpServletRequest request){
        List<Resume> resumes = resumeDao.findAll();
        request.setAttribute("resumes", resumes);
        return "list";
    }

    /**
     * 跳转到save页面
     * @param request
     * @return
     */
    @RequestMapping("/save")
    public String save(HttpServletRequest request){
        return "save";
    }

    /**
     * 保存简历
     * @param resume 简历
     * @return
     */
    @RequestMapping("/saveResume")
    public String saveResume(Resume resume){
        if(resume.getId() ==  null){
            resumeDao.save(resume);
        } else {
            resumeDao.save(resume);
        }

        return "forward:/resume/list";
    }

    /**
     * 跳转到更新页面
     * @param id
     * @param request
     * @return
     */
    @RequestMapping("/update")
    public String update(Integer id, HttpServletRequest request){
        Optional<Resume> optional = resumeDao.findById(id);
        Resume resume = optional.get();
        request.setAttribute("resume", resume);

        return "update";
    }

    /**
     * 删除
     * @param id
     * @param request
     * @return
     */
    @RequestMapping("/delete")
    public String delete(Integer id, HttpServletRequest request){
        resumeDao.deleteById(id);

        return "forward:/resume/list";
    }


}

