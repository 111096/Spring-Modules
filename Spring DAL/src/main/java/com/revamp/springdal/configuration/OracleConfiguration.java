package com.revamp.springdal.configuration;

import com.revamp.springdal.model.CustomerEntity;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;

import javax.sql.DataSource;
import java.util.Properties;

import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EnableJpaRepositories
public class OracleConfiguration {

    @Value("${oracle.driver}")
    public String driver;

    @Value("${oracle.url}")
    public String url;

    @Value("${oracle.username}")
    public String username;

    @Value("${oracle.password}")
    public String password;

    @Value("${jpa.show-sql}")
    public String showSql;

    @Value("${jpa.properties.hibernate.format_sql}")
    public String formatSql;

    @Value("${jpa.database-platform}")
    public String platform;

    @Value("${jpa.hibernate.use-new-id-generator-mappings}")
    public String mapping;

    @Value("${jpa.hibernate.ddl-auto}")
    public String ddl;

    @Bean
    public DataSource dataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName(driver);
        dataSource.setUrl(url);
        dataSource.setUsername(username);
        dataSource.setPassword(password);
        return dataSource;
    }

    @Bean(name = "entityManagerFactory")
    public LocalSessionFactoryBean sessionFactory() {
        LocalSessionFactoryBean sessionFactory = new LocalSessionFactoryBean();
        sessionFactory.setDataSource(dataSource());
        Properties hibernateProperties = new Properties();
        hibernateProperties.setProperty("spring.jpa.show-sql", showSql);
        hibernateProperties.setProperty("spring.jpa.properties.hibernate.format_sql", formatSql);
        hibernateProperties.setProperty("spring.jpa.database-platform", platform);
        hibernateProperties.setProperty("spring.jpa.hibernate.use-new-id-generator-mappings", mapping);
        hibernateProperties.setProperty("spring.jpa.hibernate.ddl-auto", ddl);
        sessionFactory.setHibernateProperties(hibernateProperties);
        sessionFactory.setAnnotatedClasses(CustomerEntity.class);
        return sessionFactory;
    }

    @Bean
    HibernateTransactionManager hibernateTransactionManager() {
        HibernateTransactionManager hibernateTransactionManager = new HibernateTransactionManager();
        hibernateTransactionManager.setSessionFactory(sessionFactory().getObject());
        return hibernateTransactionManager;
    }
}