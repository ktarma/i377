import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import dao.SetupDao;

public class MyServletContextListener implements ServletContextListener {
	ServletContext context;

	public void contextInitialized(ServletContextEvent contextEvent) {
		System.out.println("Context Created");
		context = contextEvent.getServletContext();
		// set variable to servlet context
		// context.setAttribute("TEST", "TEST_VALUE");

		try {
			SetupDao setupDao = new SetupDao();
			setupDao.createSchema();
		} finally {

		}
	}

	public void contextDestroyed(ServletContextEvent contextEvent) {
		// context = contextEvent.getServletContext();
		System.out.println("Context Destroyed");
	}
}