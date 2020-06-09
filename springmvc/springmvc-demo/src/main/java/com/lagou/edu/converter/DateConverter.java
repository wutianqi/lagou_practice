package com.lagou.edu.converter;

import org.springframework.core.convert.converter.Converter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 日期类型转换器
 * @author wuqi
 * @date 2020-06-09 6:59
 */
public class DateConverter implements Converter<String, Date> {  //String：源类型 Date：目标类型
    @Override
    public Date convert(String dateStr) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        try {
            return simpleDateFormat.parse(dateStr);
        } catch (ParseException e) {
            return  null;
        }
    }
}
