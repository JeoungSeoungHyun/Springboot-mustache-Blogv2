package site.metacoding.blogv2.web;

import org.springframework.stereotype.Controller;

import lombok.RequiredArgsConstructor;
import site.metacoding.blogv2.service.UserService;

// 부가 로직(유효성 검사 같은)
@RequiredArgsConstructor
@Controller
public class UserController {

    private final UserService userService;
}
