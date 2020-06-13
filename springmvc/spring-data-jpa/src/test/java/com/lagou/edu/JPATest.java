package com.lagou.edu;

import com.lagou.edu.dao.ResumeDao;
import com.lagou.edu.pojo.Resume;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.persistence.criteria.*;
import java.util.List;
import java.util.Optional;

/**
 * @author wuqi
 * @date 2020-06-13 17:52
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath*:applicationContext.xml"})
public class JPATest {

    @Autowired
    private ResumeDao resumeDao;

    /**
     * 查询
     */
    @Test
    public void testFindById(){
        Optional<Resume> optional = resumeDao.findById(1);
        if(optional != null){
            System.out.println(optional.get());
        } else {
            System.out.println("空");
        }
    }

    /**
     * 根据Example进行查询
     * select resume0_.id as id1_0_, resume0_.address as address2_0_, resume0_.name as name3_0_, resume0_.phone as phone4_0_ from tb_resume resume0_ where resume0_.name=?
     */
    @Test
    public void testFindByExample(){
        Resume resume = new Resume();
        resume.setName("张三");
        Example<Resume> example = Example.of(resume);
        List<Resume> resumes = resumeDao.findAll(example);
        for(Resume s : resumes){
            System.out.println(s);
        }
    }

    /**
     * 增加和修改
     * 无id无增加，有Id为删除
     */
    @Test
    public void testSave(){
        //增加
        Resume resume = new Resume();
        resume.setName("张三丰");
        resume.setAddress("武当山");
        resume.setPhone("1700000000");

        //修改
        Resume updateResume = new Resume();
        updateResume.setId(4);
        updateResume.setPhone("17000001");
        updateResume.setName("张三丰");
        updateResume.setAddress("武当山");

        Resume save = resumeDao.save(resume);
        System.out.println(save);
    }

    /**
     * 删除
     */
    @Test
    public void testDelete(){
        resumeDao.deleteById(4);
    }

    /*
         =======================================使用JPA进行数据库操作的几种方式=============================================
         1.使用接口中继承的方法进行查询（上面的都是）
         2，使用jpql进行从查询
         3.使用原生sql进行查询
         4.使用自定义命名规则的方法进行查询
         5.使用Specification的方式 service层传入dao层的条件不确定，把service拿到条件封装成一个对象传递给Dao层，这个对象就叫做Specification（对条件的一个封装）

     */
    @Test
    public void testJpql(){
        List<Resume> resumes = resumeDao.findByName("张三");
        for(Resume s : resumes){
            System.out.println(s);
        }
    }

    /**
     * 使用原生sql进行查询
     */
    @Test
    public void testNativeSql(){
        List<Resume> resumes = resumeDao.findByName2("张三");
        for(Resume s : resumes){
            System.out.println(s);
        }
    }

    /**
     * 使用自定义命名规则
     */
    @Test
    public void testCustomMethod(){
        List<Resume> resumes = resumeDao.findByNameLike("张三");
        for(Resume s : resumes){
            System.out.println(s);
        }
    }

    /**
     * 使用Specification实现动态的复杂查询
     */
    @Test
    public void testBySpecification(){
        Specification<Resume> specification = new Specification<Resume>() {
            /**
             * Root 根对象，可以从中去除属性
             * CriteriaQuery 没用
             * CriteriaBuilder 动态sql构建器
             * CriteriaBuilder： 查询构造器，封装了很多的查询条件（like = 等）
             * @param root
             * @param query
             * @param criteriaBuilder
             * @return
             */
            @Override
            public Predicate toPredicate(Root<Resume> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                Path<Object> name = root.get("name");
                Path<Object> address = root.get("address");

                //构建精准查询条件
                Predicate predicate1 = criteriaBuilder.equal(name, "张三");
                //构建模糊查询条件
                Predicate predicate2 = criteriaBuilder.like(address.as(String.class), "北京");

                //组合两个条件的查询
                return criteriaBuilder.and(predicate1, predicate2);
            }
        };

        List<Resume> resumes = resumeDao.findAll(specification);
        for(Resume s : resumes){
            System.out.println(s);
        }
    }

    /**
     * 排序
     */
    @Test
    public void testSort(){
        Sort sort = new Sort(Sort.Direction.DESC,"id");
        List<Resume> resumes = resumeDao.findAll(sort);
        for(Resume r :resumes){
            System.out.println(r);
        }
    }

    /**
     * 测试分页
     */
    @Test
    public void testPage(){
        Pageable pageable = PageRequest.of(0,2);
        Page<Resume> page = resumeDao.findAll(pageable);
        int totalPages = page.getTotalPages();
        System.out.println(totalPages);
        List<Resume> resumes = page.getContent();
        for (Resume r : resumes){
            System.out.println(r);
        }

    }



}
