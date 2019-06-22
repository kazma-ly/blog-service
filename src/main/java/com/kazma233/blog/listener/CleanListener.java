package com.kazma233.blog.listener;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

/**
 * 用于释放Tomcat不能释放的内存
 * Created by mac_zly on 2017/6/17.
 */

//@Component
public class CleanListener implements ServletContextListener {

    public static final String READ_NUM_MAP_NAME = "ReadNumMap";

//    @Autowired
//    private ServletContext context;

    // 初始化
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        //context.setAttribute(READ_NUM_MAP_NAME, new ConcurrentHashMap<String, Integer>());
    }

    // 销毁
    @Override
    public void contextDestroyed(ServletContextEvent sce) {

    }
}
