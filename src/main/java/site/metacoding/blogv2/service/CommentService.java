package site.metacoding.blogv2.service;

import java.util.Optional;

import javax.management.RuntimeErrorException;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import site.metacoding.blogv2.domain.comment.Comment;
import site.metacoding.blogv2.domain.comment.CommentRepository;
import site.metacoding.blogv2.domain.post.Post;
import site.metacoding.blogv2.domain.post.PostRepository;

@RequiredArgsConstructor
@Service
public class CommentService {

    private final CommentRepository commentRepository;
    private final PostRepository postRepository;

    @Transactional
    public void 댓글쓰기(Comment comment, Integer postId) {

        // 이렇게 해도 된다. 하지만 이 경우 잘못된 postId를 주는 공격이 가능해지기 때문에
        // 확인후 넣도록 하자.
        // Post post = new Post();
        // post.setId(postId);
        // comment.setPost(post);

        Optional<Post> postOp = postRepository.findById(postId);

        if (postOp.isPresent()) {

            Post postEntity = postOp.get();

            comment.setPost(postEntity);
        } else {
            throw new RuntimeException("없는 게시글에 댓글을 작성할 수 없습니다.");
        }

        commentRepository.save(comment);
    }
}
