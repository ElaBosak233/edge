package dev.e23.edge.config;

import dev.e23.edge.interceptor.JwtInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.resource.PathResourceResolver;

import java.io.IOException;

@Configuration // 告诉 Spring 这是一个配置类
public class MvcConfig implements WebMvcConfigurer {
    /*
    配置静态资源映射，用于前端页面访问（前端构建出来的内容放到 ./dist 目录下）
    Spring 在 /api 之外的路径下会去 dist 目录下查找资源
    优先查找存在的静态资源，如果找不到则返回 index.html
    因为 Vite + React 构建的是单页应用，入口只有 index.html，其他的静态资源通常都是 JS 和 CSS
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/**")
                .addResourceLocations("file:dist/")
                .resourceChain(true)
                .addResolver(new PathResourceResolver() {
                    @Override
                    protected Resource getResource(String resourcePath, Resource location) throws IOException {
                        Resource requestedResource = location.createRelative(resourcePath);
                        return requestedResource.exists() && requestedResource.isReadable() ? requestedResource : new FileSystemResource("dist/index.html");
                    }
                });
    }

    /*
    配置拦截器，用于拦截请求，若需要鉴权的请求则检查 JWT Token 是否合法
    excludePathPatterns 方法用于排除不需要拦截的请求（比如用户的登录注册肯定不能拦截）
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new JwtInterceptor())
                .addPathPatterns("/api/**")
                .excludePathPatterns(
                        "/api/users/login",
                        "/api/users/register",
                        "/api/channels/"
                );
    }
}
