package kr.co.ddapp.config;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.filter.HiddenHttpMethodFilter;
import org.springframework.web.servlet.DispatcherServlet;

import javax.servlet.*;
import java.util.EnumSet;

public class AppInitializer implements WebApplicationInitializer {

    private static Logger logger = LogManager.getLogger(AppInitializer.class);


    public void onStartup(ServletContext servletContext) throws ServletException {

        //파라미터 설정 (기본값 : true)

        //리스너 등록 #1
        AnnotationConfigWebApplicationContext context = new AnnotationConfigWebApplicationContext();
        context.setConfigLocation("kr.co.ddapp.config");
        servletContext.addListener(new ContextLoaderListener(context));

        //리스너 등록 #2
        //servletContext.addListener(new SessionCounter());


        //서블릿 매핑
        context.register(AppConfig.class);
        context.setServletContext(servletContext);

        ServletRegistration.Dynamic dispatcher = servletContext.addServlet("dispatcher", new DispatcherServlet(context));
        dispatcher.setLoadOnStartup(1);
        dispatcher.addMapping("/");
        dispatcher.setInitParameter("throwExceptionIfNoHandlerFound", "true");  //404처리를 하도록 한다.


        //필터추가.
        // #1. character encoding filter
        FilterRegistration.Dynamic characterEncodingFilter = servletContext.addFilter("characterEncodingFilter", new CharacterEncodingFilter());
        characterEncodingFilter.addMappingForUrlPatterns(EnumSet.allOf(DispatcherType.class), true, "/*");
        characterEncodingFilter.setInitParameter("encoding", "UTF-8");
        characterEncodingFilter.setInitParameter("forceEncoding", "true");


//        // 필터 #2. xss
//        FilterRegistration.Dynamic lucyXSSFilter = servletContext.addFilter("lucyXSSFilter", new XssEscapeServletFilter());
//        lucyXSSFilter.addMappingForUrlPatterns(EnumSet.allOf(DispatcherType.class), true, "/*");


        // #2. hidden-method 필터 (RESTful 하게 사용위함
        servletContext.addFilter("hiddenHttpMethodFilter", new HiddenHttpMethodFilter()).addMappingForUrlPatterns(null, true, "/*");


    }
}
