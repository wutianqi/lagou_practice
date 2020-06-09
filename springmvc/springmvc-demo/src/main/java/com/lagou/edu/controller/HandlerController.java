package com.lagou.edu.controller;

import com.lagou.edu.pojo.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.UUID;

/**
 * Controller
 * @author wuqi
 * @date 2020-06-08 7:04
 */
@Controller
@RequestMapping("/demo")
public class HandlerController {

    //http://localhost:8080/demo/handle02
    @RequestMapping("/handle01")
    public ModelAndView handle01(@ModelAttribute("name") String name){
        Date date = new Date();

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("date", date);
        modelAndView.setViewName("demo");

        return modelAndView;
    }

    //http://localhost:8080/demo/handle02
    //如果不返回view，则RequestToViewNameTranslator将配合视图解析器起作用，将
    //相对路径 /demo/handle02转为视图名称
    @RequestMapping("/handle02")
    public void handle02(){
        System.out.println("hah");
    }

    //http://localhost:8080/demo/handle03
    /*
        可以返回String，返回值就是视图的名字
        接收的参数可以是 ModelMap,Map,Model,
        存入的数据都可以在页面上获取到

       实际被传入的参数是：org.springframework.validation.support.BindingAwareModelMap
       这个类是这三个类的顶层类，利用多态可以接收。
     */
    @RequestMapping("handle03")
    public String handle03 (Map map){
        map.put("date", new Date());
        System.out.println(map.getClass().getName());
        return "demo";
    }

    @RequestMapping("handle04")
    public String handle04 (ModelMap map){
        map.put("date", new Date());
        System.out.println(map.getClass().getName());
        return "demo";
    }

    @RequestMapping("handle05")
    public String handle05 (Model model){
        model.addAttribute("date", new Date());
        System.out.println(model.getClass().getName());
        return "demo";
    }

    /*
    接收日期格式的数据，需要我们自定义类型转换器
     */
    @RequestMapping("handle06")
    public String handle06(Date date){
        System.out.println(date);
        return "demo";
    }


    /*
        spring mvc对rest风格的支持。这里仅练习
        两种相同url的参数接收方式
        http://localhost:8080/demo/handle07/
     */
    @RequestMapping(value = "handle07/{id}", method = RequestMethod.PUT)
    public ModelAndView handle07(@PathVariable("id") Integer id){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("date", new Date());
        modelAndView.setViewName("demo");
        return modelAndView;
    }
    @RequestMapping(value = "handle07/{id}", method = RequestMethod.DELETE)
    public ModelAndView handle08(@PathVariable("id") String id){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("date", new Date());
        modelAndView.setViewName("demo");
        return modelAndView;
    }

    /*
        与ajax交互，接收json请求数据，返回json响应
     */
    @RequestMapping("handle08")
    @ResponseBody
    public User handle09(@RequestBody User user){
        user.setId(11);
        user.setName("张三丰");
        return user;
    }

    /*
        上传文件
     */
    @RequestMapping(value = "upload")
    public ModelAndView handle10(MultipartFile uploadFile, HttpSession httpSession) throws IOException {
        String originalFilename = uploadFile.getOriginalFilename();
        String suffix = originalFilename.substring(originalFilename.lastIndexOf("."), originalFilename.length());
        String uuid = UUID.randomUUID().toString();
        //文件名
        String fileName = uuid + suffix;

        //目录名
        String realPath = httpSession.getServletContext().getRealPath("/uploads");
        String datePath = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
        File folder = new File(realPath + "/" + datePath);
        if(!folder.exists()){
            folder.mkdirs();
        }

        uploadFile.transferTo(new File(folder, fileName));


        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("date", new Date());
        modelAndView.setViewName("demo");
        return modelAndView;
    }

    @RequestMapping("handle09")
    public String handle11(String name, RedirectAttributes redirectAttributes){
        //设置重定向参数传递属性，该属性值会暂存在session中，等重定向并赋值之后，会
        //立马被清空。
        redirectAttributes.addFlashAttribute("name", name);

        return "redirect:handle01";
    }


}


