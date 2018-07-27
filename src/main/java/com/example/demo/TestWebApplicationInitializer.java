//package com.example.demo;
//import javax.servlet.ServletContext;
//import javax.servlet.ServletException;
//import javax.servlet.ServletRegistration;
//
//import org.springframework.web.WebApplicationInitializer;
//import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
//import org.springframework.web.servlet.DispatcherServlet;
//
//public class TestWebApplicationInitializer implements WebApplicationInitializer {
//
//	public void onStartup(ServletContext servletContext) throws ServletException {
//
//		// load spring web application conf.
//		AnnotationConfigWebApplicationContext ac = new AnnotationConfigWebApplicationContext();
//		ac.register(Config.class);
//		ac.setServletContext(servletContext);
//
//		// creater and register rhe dispatcher servlet
//		DispatcherServlet servlet = new DispatcherServlet(ac);
//		ServletRegistration.Dynamic registration = servletContext.addServlet("app", servlet);
//		registration.setLoadOnStartup(1);
//		registration.addMapping("/");
//
//	}
//
//}
