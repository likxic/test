package dev.e23.dashstar.listener;

import dev.e23.dashstar.util.HibernateUtil;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;
import jakarta.persistence.PersistenceException;

@WebListener
public class HibernateListener implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        try {
            HibernateUtil.init();
        } catch (PersistenceException ex) {
            System.err.println("Database connection failed. Server will shut down.");
            shutdownServer();
        }
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        if (HibernateUtil.getEntityManagerFactory() != null && HibernateUtil.getEntityManagerFactory().isOpen()) {
            HibernateUtil.getEntityManagerFactory().close();
        }
    }

    private void shutdownServer() {
        System.exit(1);
    }
}
