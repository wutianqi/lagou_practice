package com.lagou;

import com.custom.impot.MyBean;
import com.lagou.config.MyPropertiesConfig;
import com.lagou.controller.DemoController;
import com.lagou.pojo.Hero;
import com.lagou.pojo.Person;
import com.lagou.pojo.Student;
import com.lagou.pojo.Tom;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.annotation.Resource;

//@RunWith(SpringRunner.class) //测试启动器，加载springboot测试注解 junit4必须加
@SpringBootTest //标记为Springboot上下文，加载项目下的applicationContext上下文环境
@ExtendWith(SpringExtension.class) //junit5必须加
class SpringbootDemoApplicationTests {

    @Autowired
    private DemoController demoController;

    @Autowired
    private Person person;

    @Autowired
    private Student student;

    @Autowired
    private MyPropertiesConfig myPropertiesConfig;

    @Autowired
    private Tom tom;

    @Autowired
    private CustomBean customBean;

    /**
     * 整合springboot单元测试
     */
    @Test
   public void testTest(){
       String demo = demoController.demo();
       System.out.println(demo);
   }

    /**
     * @ConfigutationProperties 注入properties文件中的属性
     */
   @Test
    public void testDiPropertiesByConfigurationProperties(){
        System.out.println(person);
   }

    /**
     * @Value 注入
     */
   @Test
    public void testDiPropertiesByValue(){
       student.printName();
   }

    /**
     * 测试注入自定义配置文件中的属性
     */
   @Test
   public void testCustomProperties(){
        System.out.println(myPropertiesConfig);
   }


    /**
     * 测试注入随机数
     */
   @Test
    public void testRandom(){
       tom.printAge();
   }

    /**
     * 测试自定义starter
     */
   @Test
    public void testCustomStarter(){

       System.out.println(customBean);

    }

   @Resource(name = "myBean1")
   private MyBean myBean1;

   @Resource(name = "myBean2")
   private MyBean myBean2;

   @Resource(name = "myBean3")
   private MyBean myBean3;

    /**
     * 测试直接导入配置类
     */
    @Test
    public void testImportConfig(){
        System.out.println(myBean1 + "," + myBean2 + "," + myBean3);
    }

    @Autowired
    private Hero hero;

    /**
     * 测试在没有@ComponentScan注解时，在主启动类中配置Bean，是否可以获取到Bean
     */
    @Test
    public void testNoComponentScan(){
        System.out.println(hero);
    }

}
