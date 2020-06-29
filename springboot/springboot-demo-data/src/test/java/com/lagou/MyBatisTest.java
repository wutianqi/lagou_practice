package com.lagou;

import com.lagou.dao.CommentMapper;
import com.lagou.pojo.Address;
import com.lagou.pojo.Article;
import com.lagou.pojo.Comment;
import com.lagou.pojo.Person;
import com.lagou.repository.ArticleRepository;
import com.lagou.repository.PersonRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;
import java.util.Optional;

/**
 * @author wuqi
 * @date 2020-06-20 15:05
 */
@SpringBootTest
@ExtendWith(SpringExtension.class) //junit5必须加
public class MyBatisTest {

    @Autowired
    private CommentMapper commentMapper;

    /**
     * 测试mybatis
     */
    @Test
    public void MybatisTest() {
        Comment comment = commentMapper.getCommentById(1);
        System.out.println(comment);
    }


    @Autowired
    private ArticleRepository articleRepository;

    /**
     * jpa测试
     */
    @Test
    public void jpaTest() {
        Optional<Article> optionalArticle = articleRepository.findById(1);
        if (optionalArticle.isPresent()) {
            System.out.println(optionalArticle.get());
        }
    }

    @Autowired
    private PersonRepository personRepository;

    /**
     * 往redis中插入数据
     */
    @Test
    public void testRedisSave() {
        Address address = new Address();
        address.setCity("北京");
        address.setCountry("中国");

        Person person = new Person();
        person.setAddress(address);
        person.setFirstname("张");
        person.setLastname("三");

        Person save = personRepository.save(person);
        System.out.println(save);
    }

    /**
     * 从redis中取值
     */
    @Test
    public void testRedisGet() {
        List<Person> personList = personRepository.findByAddress_City("北京");
        System.out.println(personList);

    }


}
