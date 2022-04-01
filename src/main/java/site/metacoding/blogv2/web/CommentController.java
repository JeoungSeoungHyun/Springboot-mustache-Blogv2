package site.metacoding.blogv2.web;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import lombok.RequiredArgsConstructor;
import site.metacoding.blogv2.domain.comment.Comment;
import site.metacoding.blogv2.domain.user.User;
import site.metacoding.blogv2.service.CommentService;

@RequiredArgsConstructor
@Controller
public class CommentController {

    private final CommentService commentService;
    private final HttpSession session;

    @PostMapping("/s/post/{postId}/comment")
    public String write(@PathVariable Integer postId, Comment comment) { // x-www-form-urlencoded

        User principal = (User) session.getAttribute("principal");

        comment.setUser(principal);

        // 레파지토리 연결 불가능해서 postId도 함께 넘겨줌
        commentService.댓글쓰기(comment, postId);
        return "redirect:/post/" + postId;
    }

}
