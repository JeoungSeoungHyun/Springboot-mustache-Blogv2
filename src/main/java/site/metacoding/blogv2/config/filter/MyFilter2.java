package site.metacoding.blogv2.config.filter;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.databind.ObjectMapper;

import site.metacoding.blogv2.web.api.dto.user.LoginDto;

public class MyFilter2 implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        System.out.println("MyFilter2");
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;

        LoginDto loginDto = null;

        if (req.getMethod().equals("POST")) { // 대소문자 구분해줘야함
            loginDto = new LoginDto();
            String contentType = req.getHeader("Content-Type");
            System.out.println("컨텐트 타입 : " + contentType);

            if (contentType.equals("application/x-www-form-urlencoded")) {
                String username = req.getParameter("username");
                String password = req.getParameter("password");

                loginDto.setUsername(username);
                loginDto.setPassword(password);
                System.out.println("파싱 잘됩니다.");
                System.out.println("로그인 디티오 : " + loginDto);

            } else if (contentType.equals("application/json")) {

                BufferedReader br = request.getReader();

                String body = "";
                while (true) {
                    String input = br.readLine();

                    if (input == null) {
                        break;
                    }
                    body = body + input;
                }
                System.out.println("바디 : " + body);

                ObjectMapper om = new ObjectMapper();
                loginDto = om.readValue(body, LoginDto.class); // gson.fromJson();

                System.out.println("loginDto : " + loginDto);

            }

            if (loginDto.getPassword().equals("")) {
                resp.setContentType("text/plain; charset=utf-8"); // 응답시 MIME타입 설정해주기
                PrintWriter out = resp.getWriter();
                out.println("비밀번호 없습니다.");
                out.flush();
            } else {
                chain.doFilter(request, response);
            }
        } else if (req.getMethod().equals("GET")) {
            chain.doFilter(request, response);

        } else {
            resp.setContentType("text/plain; charset=utf-8"); // 응답시 MIME타입 설정해주기
            PrintWriter out = resp.getWriter();
            out.println("Post/Get 요청이 아닙니다.");
            out.flush();
        }

    }

}
