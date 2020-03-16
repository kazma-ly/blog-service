package com.kazma233.blog.config;

import com.kazma233.blog.config.filter.AuthFilter;
import com.kazma233.blog.config.filter.CORSFilter;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.format.FormatterRegistry;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;
import java.util.TimeZone;

@AllArgsConstructor
@Configuration
public class MyWebConfigurer implements WebMvcConfigurer {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {

    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.
                addInterceptor(new CORSFilter()).
                order(0).
                addPathPatterns("/**");

        registry.
                addInterceptor(new AuthFilter()).
                order(Integer.MAX_VALUE).
                addPathPatterns("/**").
                excludePathPatterns(
                        "/users/login",
                        "/comments/**",
                        "/articles/**"
                );
    }

    @Override
    public void addFormatters(FormatterRegistry registry) {
    }

    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
    }

    @Bean
    @Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)
    public Jackson2ObjectMapperBuilderCustomizer jackson2ObjectMapperBuilderCustomizer() {
        return jacksonObjectMapperBuilder -> {
            jacksonObjectMapperBuilder.timeZone(TimeZone.getTimeZone("Asia/Shanghai"));
            jacksonObjectMapperBuilder.indentOutput(true);
        };
    }

//    // 跨域请求
//    @Bean
//    CorsConfiguration buildConfig() {
//        CorsConfiguration corsConfiguration = new CorsConfiguration();
//        // 放行哪些原始域
//        corsConfiguration.addAllowedOrigin("*");
//        // 放行哪些原始域(头部信息)
//        corsConfiguration.addAllowedHeader("*");
//        // 放行哪些原始域(请求方式)
//        corsConfiguration.addAllowedMethod("*");
//        // 是否发送Cookie信息
//        corsConfiguration.setAllowCredentials(true);
//        // 暴露哪些头部信息（因为跨域访问默认不能获取全部头部信息）
//         corsConfiguration.addExposedHeader("Content-Type, X-Requested-With, Connection, Host, Accept, Accept-Encoding, Accept-Language, Cache-Control");
//        return corsConfiguration;
//    }
//
//    @Bean
//    public CorsFilter corsFilter(CorsConfiguration corsConfiguration) {
//        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
//        source.registerCorsConfiguration("/**", corsConfiguration);
//        return new CorsFilter(source);
//    }

}
