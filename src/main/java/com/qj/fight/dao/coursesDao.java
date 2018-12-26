package com.qj.fight.dao;

import com.qj.fight.entity.buyCourses;
import com.qj.fight.entity.courses;
import org.beetl.sql.core.annotatoin.Param;
import org.beetl.sql.core.annotatoin.SqlResource;
import org.beetl.sql.core.mapper.BaseMapper;

import java.util.List;
import java.util.Map;

@SqlResource("fight.course")
public interface coursesDao extends BaseMapper<courses> {
    List <courses> select();
    Map<String ,Integer >selectCoursesByName(@Param("coursename")String coursename);
    int insertBuyCourses(buyCourses buyCourses);
}
