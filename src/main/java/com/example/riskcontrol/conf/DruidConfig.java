package com.example.riskcontrol.conf;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.support.http.StatViewServlet;
import com.alibaba.druid.support.http.WebStatFilter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.SQLException;

@ConfigurationProperties(prefix = "spring.datasource")
@Component
public class DruidConfig {

    //驱动名称
    private String driverClassName;
    //数据库 url
    private String url;
    //数据库用户名
    private String username;
    //数据库登陆密码
    private String password;
    //最大的连接数量
    private int maxActive;
    //初始化大小
    private int initialSize;
    // 设置 超时的等待时间
    private int maxWait;
    //最小的连接数量
    private int minIdle;
    //监控统计拦截的filters，如果去掉后监控界面sql将无法统计
    private String filters;

    public String getFilters() {
        return filters;
    }

    public void setFilters(String filters) {
        this.filters = filters;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getMaxActive() {
        return maxActive;
    }

    public void setMaxActive(int maxActive) {
        this.maxActive = maxActive;
    }

    public int getInitialSize() {
        return initialSize;
    }

    public void setInitialSize(int initialSize) {
        this.initialSize = initialSize;
    }

    public int getMaxWait() {
        return maxWait;
    }

    public void setMaxWait(int maxWait) {
        this.maxWait = maxWait;
    }

    public int getMinIdle() {
        return minIdle;
    }

    public void setMinIdle(int minIdle) {
        this.minIdle = minIdle;
    }

    //注册 Servlet 组件
    @Bean
    public ServletRegistrationBean druidServlet() {
        return new ServletRegistrationBean(new StatViewServlet(), "/druid/*");
    }


    // 注册 Filter 组件
    @Bean
    public FilterRegistrationBean statFilter(){
        FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean(new WebStatFilter());
        //验证所有请求
        filterRegistrationBean.addUrlPatterns("/*");
        //对 *.js,*.gif,*.jpg,*.png,*.css,*.ico,/druid/* 不进行验证
        filterRegistrationBean.addInitParameter("exclusions", "*.js,*.gif,*.jpg,*.png,*.css,*.ico,/druid/*");
        return filterRegistrationBean;
    }

    //配置数据源
    @Bean
    public DataSource dataSource() {
        DruidDataSource dataSource = new DruidDataSource();
        dataSource.setDriverClassName("com.mysql.cj.jdbc.Driver");
        dataSource.setUrl(url);
        dataSource.setUsername(username);
        dataSource.setPassword(password);
        dataSource.setMaxActive(maxActive);
        dataSource.setInitialSize(initialSize);
        try {
            dataSource.setFilters(filters);
            dataSource.init();
        } catch (SQLException e) {
            //do nothing
            e.printStackTrace();
        }
        dataSource.setMaxWait(maxWait);
        dataSource.setMinIdle(minIdle);
        return dataSource;
    }
}