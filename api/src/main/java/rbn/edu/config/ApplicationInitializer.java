package rbn.edu.config;

import javax.servlet.Filter;

import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

public class ApplicationInitializer extends AbstractAnnotationConfigDispatcherServletInitializer {
    @Override
    protected Class<?>[] getRootConfigClasses() {
	return new Class<?>[] { ApplicationConfig.class };
    }

    @Override
    protected Class<?>[] getServletConfigClasses() {
	// when use this ? when apply different servlets in application?
	// return new Class<?>[] { WebConfig.class };
	return null;
    }

    @Override
    protected Filter[] getServletFilters() {
	return new Filter[] {};
    }

    @Override
    protected String[] getServletMappings() {
	return new String[] { "/" };
    }
}
