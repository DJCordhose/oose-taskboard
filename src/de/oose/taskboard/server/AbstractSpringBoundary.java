package de.oose.taskboard.server;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;

import javax.servlet.ServletContext;

import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

/**
 * The server side implementation of the RPC service.
 */
@SuppressWarnings("serial")
public abstract class AbstractSpringBoundary extends RemoteServiceServlet {

	@Override
	protected void onBeforeRequestDeserialized(String serializedRequest) {
		super.onBeforeRequestDeserialized(serializedRequest);
		inject();
	}
	
	protected <TBean> TBean getBean(Class<TBean> pClass) {
		WebApplicationContext applicationContext = getApplicationContext();
		return applicationContext.getBean(pClass);
	}

	protected WebApplicationContext getApplicationContext() {
		ServletContext servletContext = this.getServletContext();
		WebApplicationContext applicationContext = WebApplicationContextUtils
				.getRequiredWebApplicationContext(servletContext);
		return applicationContext;
	}

	protected void inject() {
		WebApplicationContext applicationContext = getApplicationContext();

		applicationContext.getAutowireCapableBeanFactory().autowireBeanProperties(this,
				AutowireCapableBeanFactory.AUTOWIRE_BY_TYPE, false);
	}

	protected String toString(Throwable throwable) {
		final Writer result = new StringWriter();
		final PrintWriter printWriter = new PrintWriter(result);
		throwable.printStackTrace(printWriter);
		return result.toString().replace("\n", "<br>");
	}

}
