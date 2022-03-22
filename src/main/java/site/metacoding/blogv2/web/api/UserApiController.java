package site.metacoding.blogv2.web.api;

import java.util.Optional;

import javax.servlet.http.HttpSession;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import site.metacoding.blogv2.domain.user.User;
import site.metacoding.blogv2.service.UserService;
import site.metacoding.blogv2.web.api.dto.ResponseDto;
import site.metacoding.blogv2.web.api.dto.user.JoinDto;
import site.metacoding.blogv2.web.api.dto.user.LoginDto;

// 웹이 아닌 프로그램에는 뷰가 아닌 JSON데이터를 돌려줘야 하기 때문에 ApiController가 필요
@RequiredArgsConstructor
@RestController
public class UserApiController {

    private final UserService userService;
    private final HttpSession session;

    @PostMapping("/api/join")
    public ResponseDto<String> join(@RequestBody JoinDto joinDto) {

        userService.회원가입(joinDto);

        return new ResponseDto<String>(1, "회원가입 성공", null);
    }

    @PostMapping("/api/login")
    public ResponseDto<String> login(@RequestBody LoginDto loginDto) {

        User userEntity = userService.로그인(loginDto);

        if (userEntity != null) {
            session.setAttribute("principal", userEntity);
            return new ResponseDto<String>(1, "로그인 성공", null);
        } else {
            return new ResponseDto<String>(-1, "로그인 실패", null);
        }

    }
}
