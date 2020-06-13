package com.lagou.edu.dao;

import com.lagou.edu.pojo.Resume;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * 一个符合spring data jpa要求的Dao需要实现两个接口JpaRepository<Resume, Integer>，JpaSpecificationExecutor<Resume>
 *     JpaRepository<Resume, Integer>
 *         进行基本的CRUD操作
 *     JpaSpecificationExecutor<Resume>
 *         进行复杂的操作（分页，排序）
 * 
 * @author wuqi
 * @date 2020-06-13 17:48
 */
public interface ResumeDao extends JpaRepository<Resume, Integer>, JpaSpecificationExecutor<Resume> {
    /**
     * 使用jpql进行查询
     * Jpql是JPA的查询语言，使用的是查询实体对象，查询使用的条件是实体类中的字段
     * @param name
     * @return
     */
    @Query("from Resume where name=?1")
    public List<Resume> findByName(String name);

    /**
     * 使用原生sql进行查询，需要指定使用原生的sql
     * @param name
     * @return
     */
    @Query(value = "select * from tb_resume where name=?1", nativeQuery = true)
    public List<Resume> findByName2(String name);

    /**
     * 使用自定义命名规则进行查询
     * @param name
     * @return
     */
    public List<Resume> findByNameLike(String name);
}
