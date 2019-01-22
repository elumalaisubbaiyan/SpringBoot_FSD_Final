package com.cts.fsd.mvc.configuration;

import java.util.logging.Level;

import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

public class ApplicationInitializer extends AbstractAnnotationConfigDispatcherServletInitializer {

	@Override
	protected Class<?>[] getRootConfigClasses() {
		return new Class[] { MVCApplicationConfiguration.class };
	}

	@Override
	protected Class<?>[] getServletConfigClasses() {
		return null;
	}

	@Override
	protected String[] getServletMappings() {
		java.util.logging.Logger.getLogger("org.hibernate").setLevel(Level.SEVERE);
		return new String[] { "/" };
	}

}