package com.lagou.repository;

import com.lagou.pojo.Person;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * redis操作
 *
 * @author wuqi
 * @date 2020-06-20 15:24
 */
public interface PersonRepository extends CrudRepository<Person, Integer> {
    List<Person> findByAddress_City(String city);
}
