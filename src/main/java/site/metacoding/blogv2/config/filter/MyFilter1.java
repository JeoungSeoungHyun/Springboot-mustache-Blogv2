package site.metacoding.blogv2.config.filter;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class MyFilter1 implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        System.out.println("MyFilter1");

        // 다운캐스팅해서 사용하자 req, res

        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;

        // System.out.println(req.getRequestURL());
        // System.out.println(req.getRequestURI());

        String path = req.getRequestURI();
        if (path.contains("fuck")) {
            resp.setContentType("text/plain; charset=utf-8"); // 응답시 MIME타입 설정해주기
            PrintWriter out = resp.getWriter();
            out.println("욕하지마!!!!!");
            out.flush();
        } else {
            chain.doFilter(request, response); // 다른 필터가 있다면 다음필터로 이동 없다면 DS로 이동
        }
    }

}