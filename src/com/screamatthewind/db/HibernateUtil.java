package com.screamatthewind.db;

import java.util.HashMap;
import java.util.Map;

import org.hibernate.SessionFactory;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

import com.screamatthewind.core.Contact;
import com.screamatthewind.core.Location;

public class HibernateUtil {
  private static StandardServiceRegistry registry;
  private static SessionFactory sessionFactory;

  public static SessionFactory getSessionFactory() {
    if (sessionFactory == null) {
      try {
        StandardServiceRegistryBuilder registryBuilder = 
            new StandardServiceRegistryBuilder();

        Map<String, String> settings = new HashMap<>();
        settings.put("hibernate.connection.driver_class", "com.mysql.cj.jdbc.Driver");
        settings.put("hibernate.connection.url", "jdbc:mysql://localhost:3306/revue_sac_db");
        settings.put("hibernate.connection.username", "revue_sac_user");
        settings.put("hibernate.connection.password", "password");
        settings.put("hibernate.show_sql", "false");
        // settings.put("hibernate.hbm2ddl.auto", "update");

        registryBuilder.applySettings(settings);

        registry = registryBuilder.build();

        MetadataSources sources = new MetadataSources(registry)
    		.addPackage("com.screamatthewind.core")
            .addAnnotatedClass(Location.class)
            .addAnnotatedClass(Contact.class);

        Metadata metadata = sources.getMetadataBuilder().build();

        sessionFactory = metadata.getSessionFactoryBuilder().build();
      } catch (Exception e) {
        System.out.println("SessionFactory creation failed");
        if (registry != null) {
          StandardServiceRegistryBuilder.destroy(registry);
        }
      }
    }
    return sessionFactory;
  }

  public static void shutdown() {
    if (registry != null) {
      StandardServiceRegistryBuilder.destroy(registry);
    }
  }
}