package com.lagou.pojo;

import org.springframework.data.redis.core.index.Indexed;

/**
 * @author wuqi
 * @date 2020-06-20 15:19
 */
public class Address {
    @Indexed
    private String city;
    private String country;

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    @Override
    public String toString() {
        return "Address{" +
                "city='" + city + '\'' +
                ", country='" + country + '\'' +
                '}';
    }
}
