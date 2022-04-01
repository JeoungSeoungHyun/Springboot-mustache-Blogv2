package site.metacoding.blogv2.web;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;

import lombok.RequiredArgsConstructor;
import site.metacoding.blogv2.domain.comment.Comment;
import site.metacoding.blogv2.domain.post.Post;
import site.metacoding.blogv2.domain.user.User;
import site.metacoding.blogv2.service.PostService;
import site.metacoding.blogv2.web.api.dto.ResponseDto;
import site.metacoding.blogv2.web.api.dto.comment.CommentResponseDto;

@RequiredArgsConstructor
@Controller
public class PostController {

    private final PostService postService;
    private final HttpSession session;

    @GetMapping({ "/", "/post" })
    public String home() {
        return "post/list";
    }

    // 페이지 요청
    // /s 붙었으니까 인터셉터가 인증 처리를 한다.
    @GetMapping("/s/post/writeForm")
    public String writeForm() {
        return "post/writeForm";
    }

    @GetMapping("/post/{id}")
    public String detail(@PathVariable Integer id, Model model) {

        User principal = (User) session.getAttribute("principal");

        Post postEntity = postService.글상세보기(id);

        // 무한으로 로딩되서 터지는듯
        // System.out.println("comments" + postEntity);

        List<CommentResponseDto> comments = new ArrayList<>();
        for (Comment comment : postEntity.getComments()) {
            CommentResponseDto dto = new CommentResponseDto();
            dto.setComment(comment);

            // System.out.println("comment" + comment);

            System.out.println("principal : " + principal);

            // 로그인하지 않은 경우 삭제 버튼 모두 나타나지 않도록 해주기
            if (principal == null) {
                dto.setAuth(false);
                // 로그인 한 경우 권한 확인
            } else {
                if (comment.getUser().getId() == principal.getId()) {
                    dto.setAuth(true);
                } else {
                    dto.setAuth(false);
                }
            }
            comments.add(dto);
        }

        model.addAttribute("comments", comments);
        model.addAttribute("postId", id);
        return "post/detail";
    }

}
