package com.kazma233.blog.config.data;

import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.TransactionManagementConfigurer;

// TransactionManagementConfigurer -> 相当于
// <tx:annotation-driven transaction-manager="dataSourceTransactionManager"/>

@Configuration
@EnableTransactionManagement
public class DataTransactionConfig implements TransactionManagementConfigurer {

//    @Autowired
//    private DruidDataSource dataSource;

    @Autowired
    private HikariDataSource dataSource;

    // 事务管理
//    @Bean(name = "transactionManager")
//    public DataSourceTransactionManager transactionManager(DruidDataSource druidDataSource) {
//        return new DataSourceTransactionManager(druidDataSource);
//    }


    @Override
    public PlatformTransactionManager annotationDrivenTransactionManager() {
        DataSourceTransactionManager dataSourceTransactionManager = new DataSourceTransactionManager();
        dataSourceTransactionManager.setDataSource(dataSource);
        dataSourceTransactionManager.afterPropertiesSet();
        return dataSourceTransactionManager;
    }
}
