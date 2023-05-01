package com.sparta.project.Service;

import com.sparta.project.Entity.Comment;
import com.sparta.project.Entity.Like;
import com.sparta.project.Entity.Post;
import com.sparta.project.Entity.User;
import com.sparta.project.Repository.CommentRepository;
import com.sparta.project.Repository.LikeRepository;
import com.sparta.project.Repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class LikeService {
    private final PostRepository postRepository;
    private final CommentRepository commentRepository;
    private final LikeRepository likeRepository;

    // Post 좋아요
    @Transactional
    public String postLike(Long postId, User user) {

        // 1. 게시글 조회
        Post post = postRepository.findById(postId).orElseThrow(
                () -> new IllegalArgumentException("게시글을 찾을 수 없습니다.")
        );

        // 2. LikeRepository 에서 postId, userId 로 조회
        Like like = likeRepository.findByPostIdAndUser_Id(postId, user.getId());

        // 좋아요 없으면 DB에 추가
        if (like == null) {
            likeRepository.save(new Like(user, postId, null));
            post.like();
            return "게시글 좋아요 완료";
        } else { // 좋아요 있으면 DB에서 제거
            likeRepository.deleteById(like.getId());
            post.unlike();
            return "게시글 좋아요 취소완료";
        }
    }

    // Reply 좋아요
    @Transactional
    public String commentLike(Long commentId, User user) {

        // 1. 댓글 조회
        Comment comment = commentRepository.findById(commentId).orElseThrow(
                () -> new IllegalArgumentException("댓글을 찾을 수 없습니다.")
        );

        // 2. LikeRepository 에서 postId, userId 로 조회
        Like like = likeRepository.findByCommentIdAndUser_Id(commentId, user.getId());

        // 좋아요 없으면 DB에 추가
        if (like == null) {
            likeRepository.save(new Like(user, null, commentId));
            comment.like();
            return "댓글 좋아요 완료";
        } else { // 좋아요 있으면 DB에서 제거
            likeRepository.deleteById(like.getId());
            comment.unlike();
            return "댓글 좋아요 취소완료";
        }
    }
}
