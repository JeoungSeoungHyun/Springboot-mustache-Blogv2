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
import javax.servlet.http.HttpSession;

import lombok.RequiredArgsConstructor;
import site.metacoding.blogv2.domain.user.User;

@RequiredArgsConstructor
public class MyFilter3 implements Filter {

    private final HttpSession session;

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        System.out.println("MyFilter3");
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;

        String path = req.getRequestURI();

        System.out.println("패스 : " + path);

        if (path.contains("/s/")) {
            User principal = (User) session.getAttribute("principal");

            if (principal == null) {
                resp.setContentType("text/plain; charset=utf-8");
                PrintWriter out = resp.getWriter();
                out.println("이런 사기꾼이 나가라");
                out.flush();
            } else {
                System.out.println("인증확인 통과");
                chain.doFilter(request, response);
            }
        } else {
            chain.doFilter(request, response);
        }
    }

}
