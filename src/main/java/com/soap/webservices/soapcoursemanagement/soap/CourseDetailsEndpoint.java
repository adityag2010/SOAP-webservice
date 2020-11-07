package com.soap.webservices.soapcoursemanagement.soap;

import com.soap.webservices.soapcoursemanagement.soap.bean.CourseBean;
import com.soap.webservices.soapcoursemanagement.soap.bean.StatusBean;
import com.soap.webservices.soapcoursemanagement.soap.exception.CourseNotFoundException;
import com.soap.webservices.soapcoursemanagement.soap.service.CourseDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;
import soap_webservices.CourseDetails;
import soap_webservices.DeleteCourseDetailsRequest;
import soap_webservices.DeleteCourseDetailsResponse;
import soap_webservices.GetAllCourseDetailsRequest;
import soap_webservices.GetAllCourseDetailsResponse;
import soap_webservices.GetCourseDetailsRequest;
import soap_webservices.GetCourseDetailsResponse;
import soap_webservices.Status;

import java.util.List;

@Endpoint
public class CourseDetailsEndpoint {

    @Autowired
    CourseDetailsService courseDetailsService;

    @ResponsePayload
    @PayloadRoot(namespace = "http://soap-webservices", localPart = "GetCourseDetailsRequest")
    public GetCourseDetailsResponse processCourseDetailsRequest(@RequestPayload GetCourseDetailsRequest request) {

        CourseBean courseBean = courseDetailsService.findCourseById(request.getId());
        GetCourseDetailsResponse response = new GetCourseDetailsResponse();

        try {
            response.setCourseDetails(courseDetailsMapper(courseBean));
        } catch (RuntimeException e) {
            if (courseBean == null) {
                throw new CourseNotFoundException("Invalid course Id: " + request.getId());
            }
        }

        return response;
    }

    @ResponsePayload
    @PayloadRoot(namespace = "http://soap-webservices", localPart = "GetAllCourseDetailsRequest")
    public GetAllCourseDetailsResponse processAllCourseDetailsRequest(@RequestPayload GetAllCourseDetailsRequest request) {

        List<CourseBean> courses = courseDetailsService.findAllCourses();

        GetAllCourseDetailsResponse response = new GetAllCourseDetailsResponse();
        for (CourseBean courseBean : courses) {
            response.getCourseDetails().add(courseDetailsMapper(courseBean));
        }

        return response;
    }

    @ResponsePayload
    @PayloadRoot(namespace = "http://soap-webservices", localPart = "DeleteCourseDetailsRequest")
    public DeleteCourseDetailsResponse processDeleteCourseDetailsRequest(@RequestPayload DeleteCourseDetailsRequest request) {

        StatusBean status = courseDetailsService.deleteCourseById(request.getId());

        DeleteCourseDetailsResponse response = new DeleteCourseDetailsResponse();
        response.setStatus(statusMapper(status));

        return response;
    }

    private Status statusMapper(StatusBean status) {
        switch (status) {
            case SUCCESS:
                return Status.SUCCESS;
            case FAILURE:
                return Status.FAILURE;
            default:
                return null;
        }
    }

    private CourseDetails courseDetailsMapper(CourseBean courseBean) {
        CourseDetails courseDetails = new CourseDetails();
        courseDetails.setId(courseBean.getId());
        courseDetails.setName(courseBean.getName());
        courseDetails.setDescription(courseBean.getDescription());
        return courseDetails;
    }
}
