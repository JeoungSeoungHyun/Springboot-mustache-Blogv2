package site.metacoding.blogv2;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class TestViewController {

    // @GetMapping("/post/list")
    // public String list() {
    // return "post/list";
    // }

    @GetMapping("/post/detail")
    public String detail() {
        return "post/detail";
    }

    @GetMapping("/post/updateForm")
    public String updateForm() {
        return "post/updateForm";
    }

    @GetMapping("/post/writeForm")
    public String writeForm() {
        return "post/writeForm";
    }

    @GetMapping("/user/detail")
    public String detail2() {
        return "user/detail";
    }

    @GetMapping("/user/joinForm")
    public String joinForm() {
        return "user/joinForm";
    }

    @GetMapping("/user/loginForm")
    public String loginForm() {
        return "user/loginForm";
    }

    @GetMapping("/user/updateForm")
    public String updateForm2() {
        return "user/updateForm";
    }

}
