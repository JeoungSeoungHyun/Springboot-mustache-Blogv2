package site.metacoding.blogv2.config;

import javax.servlet.http.HttpSession;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import lombok.RequiredArgsConstructor;
import site.metacoding.blogv2.config.filter.MyFilter1;
import site.metacoding.blogv2.config.filter.MyFilter2;
import site.metacoding.blogv2.config.filter.MyFilter3;

@RequiredArgsConstructor
// @Configuration
public class FilterConfig {

    private final HttpSession session;

    @Bean // IoC 컨테이너 필터 설정파일 등록
    public FilterRegistrationBean<?> filter1() {

        // 필터 설정 파일 // 설정 파일에 필터 등록
        FilterRegistrationBean<MyFilter1> bean = new FilterRegistrationBean<>(new MyFilter1());
        bean.addUrlPatterns("/*");
        bean.setOrder(2); // 낮은 번호의 필터가 가장 먼저 실행됨.
        return bean;
    }

    @Bean // IoC 컨테이너 필터 설정파일 등록
    public FilterRegistrationBean<?> filter2() {

        // 필터 설정 파일 // 설정 파일에 필터 등록
        FilterRegistrationBean<MyFilter2> bean = new FilterRegistrationBean<>(new MyFilter2());
        bean.addUrlPatterns("/*"); // 주소가 같아도 순서가 있기 때문에 상관 x
        bean.setOrder(3); // 낮은 번호의 필터가 가장 먼저 실행됨.
        return bean;
    }

    @Bean // IoC 컨테이너 필터 설정파일 등록
    public FilterRegistrationBean<?> filter3() {

        // 필터 설정 파일 // 설정 파일에 필터 등록
        FilterRegistrationBean<MyFilter3> bean = new FilterRegistrationBean<>(new MyFilter3(session));
        bean.addUrlPatterns("/*"); // 주소가 같아도 순서가 있기 때문에 상관 x
        bean.setOrder(1); // 낮은 번호의 필터가 가장 먼저 실행됨.
        return bean;
    }
}
