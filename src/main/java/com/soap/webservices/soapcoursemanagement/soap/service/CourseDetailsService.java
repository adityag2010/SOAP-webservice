package com.soap.webservices.soapcoursemanagement.soap.service;

import com.soap.webservices.soapcoursemanagement.soap.bean.CourseBean;
import com.soap.webservices.soapcoursemanagement.soap.bean.StatusBean;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Service
public class CourseDetailsService {
    private static List<CourseBean> courses;

    static {
        courses = new ArrayList<>();
        courses.add(new CourseBean(1, "Spring", "Spring in 10 Steps"));
        courses.add(new CourseBean(2, "Spring MVC", "Spring MVC with 10 Examples"));
        courses.add(new CourseBean(3, "Spring Boot", "Spring Boot has 6k students"));
        courses.add(new CourseBean(4, "Maven", "Most popular maven course"));
    }

    public CourseBean findCourseById(int id) {
        for (CourseBean courseBean : courses) {
            if (courseBean.getId() == id) {
                return courseBean;
            }
        }
        return null;
    }

    public List<CourseBean> findAllCourses() {
        return courses;
    }

    public StatusBean deleteCourseById(int id) {
        Iterator<CourseBean> courseIterator = courses.iterator();
        while(courseIterator.hasNext()) {
            CourseBean courseBean = courseIterator.next();
            if (courseBean.getId() == id) {
                courseIterator.remove();
                return StatusBean.SUCCESS;
            }
        }
        return StatusBean.FAILURE;
    }
}
