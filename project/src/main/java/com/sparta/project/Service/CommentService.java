package com.sparta.project.Service;

import com.sparta.project.Dto.CommentRequestDto;
import com.sparta.project.Dto.CommentResponseDto;
import com.sparta.project.Entity.Comment;
import com.sparta.project.Entity.Post;
import com.sparta.project.Entity.User;
import com.sparta.project.Entity.UserRoleEnum;
import com.sparta.project.Repository.CommentRepository;
import com.sparta.project.Repository.PostRepository;
import com.sparta.project.Repository.UserRepository;
import com.sparta.project.jwt.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;

    //댓글 작성
    @Transactional
    public CommentResponseDto createComment(Long postId, CommentRequestDto commentRequestDto, User user) {
        Post post = postRepository.findById(postId).orElseThrow(
                () -> new IllegalArgumentException("게시글이 존재하지 않습니다.")
        );

        Comment comment = new Comment(commentRequestDto, user, post);
        post.addComment(comment);
        comment.setUser(user);

        commentRepository.save(comment);
        CommentResponseDto commentResponseDto = new CommentResponseDto(comment);
        return commentResponseDto;
    }

    //댓글 수정
    @Transactional
    public CommentResponseDto updateComment(Long commentId, CommentRequestDto commentRequestDto, User user) {

        Comment comment = commentRepository.findById(commentId).orElseThrow(
                () -> new IllegalArgumentException("댓글이 존재하지 않습니다.")
        );

        //관리자 권한 및 유저 일치여부 확인
        if (user.getRole() == UserRoleEnum.ADMIN || comment.getUser().getId().equals(user.getId())) {
                comment.updateComment(commentRequestDto, user);
                return new CommentResponseDto(comment);
        }
        else {
            throw new IllegalArgumentException("권한이 없습니다." + HttpStatus.BAD_REQUEST);
        }
    }

    //댓글 삭제
    @Transactional
    public String deleteComment(Long commentId, User user) {
        Comment comment = commentRepository.findById(commentId).orElseThrow(
                () -> new IllegalArgumentException("아이디가 존재하지 않습니다.")
        );

        //관리자 권한 및 유저 일치여부 확인
        if (user.getRole() == UserRoleEnum.ADMIN || comment.getUser().getId().equals(user.getId())) {
            commentRepository.deleteById(commentId);
            return "삭제 완료";
        }
        else {
            throw new IllegalArgumentException("권한이 없습니다." + HttpStatus.BAD_REQUEST);
        }
    }

}
