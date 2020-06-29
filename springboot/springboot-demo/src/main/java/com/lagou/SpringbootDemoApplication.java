package com.lagou;

import com.custom.impot.NotAutoConfig;
import com.lagou.import_useage.MyImportBeanDefinitionRegistrar;
import com.lagou.import_useage.MyImportSelector;
import com.lagou.pojo.Hero;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.autoconfigure.AutoConfigurationPackages;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;

import java.util.List;


@Import({NotAutoConfig.class, MyImportSelector.class, MyImportBeanDefinitionRegistrar.class}) //@Import导入配置类
@SpringBootApplication  //可以被下面三个注解所替代
//@SpringBootConfiguration
//@EnableAutoConfiguration
//@ComponentScan //这个注解才是真正进行包扫描的注解 @AutoConfigurationPackage并不是！！！！它只是存储了自动配置的包
public class SpringbootDemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringbootDemoApplication.class, args);
    }

    @Bean
    public Hero hero(){
        Hero hero = new Hero();
        hero.setName("zhangsan");
        hero.setAge(10);
        return hero;
    }
}
