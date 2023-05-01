package com.sparta.project.Service;

import com.sparta.project.Dto.PostRequestDto;
import com.sparta.project.Dto.PostResponseDto;
import com.sparta.project.Entity.Post;
import com.sparta.project.Entity.User;
import com.sparta.project.Entity.UserRoleEnum;
import com.sparta.project.Repository.PostRepository;
import com.sparta.project.Repository.UserRepository;
import com.sparta.project.jwt.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;

    //게시글 작성
    @Transactional
    public PostResponseDto createPost(PostRequestDto requestDto, User user) {
        Post post = new Post(requestDto, user);
        postRepository.save(post);
        PostResponseDto postResponseDto = new PostResponseDto(post);
        return postResponseDto;
    }

    //전체 게시글 조회
    @Transactional(readOnly = true)
    public List<PostResponseDto> getPosts() {
        List<PostResponseDto> postResponseDtos = new ArrayList<>();
        List<Post> allByOrderByCreatedAtDesc = postRepository.findAllByOrderByCreatedAtDesc();
        for (Post post : allByOrderByCreatedAtDesc) {
            postResponseDtos.add(new PostResponseDto(post));
        }
        return postResponseDtos;

    }
    //return postRepository.findAllByOrderByCreatedAtDesc().stream().map(PostResponseDto::new).collect(Collectors.toList());

    //선택한 게시글 조회
    @Transactional
    public PostResponseDto getPost(Long postId) {
        Post post = postRepository.findById(postId).orElseThrow(
                () -> new IllegalArgumentException("게시글이 존재하지 않습니다.")
        );
        PostResponseDto postResponseDto = new PostResponseDto(post);
        return postResponseDto;
    }

    //게시글 수정
    public PostResponseDto updatePost(Long postId, PostRequestDto requestDto, User user) {

        Post post = postRepository.findById(postId).orElseThrow(
                () -> new IllegalArgumentException("게시글이 존재하지 않습니다.")
        );

        //관리자 권한 및 유저 일치여부 확인
        if (user.getRole() == UserRoleEnum.ADMIN || post.getUser().getId().equals(user.getId())) {
            post.update(requestDto);
            return new PostResponseDto(post);
        }
        else {
            throw new IllegalArgumentException("권한이 없습니다." + HttpStatus.BAD_REQUEST);
        }
    }

    //게시글 삭제
    public String deletePost(Long postId, User user) {

        Post post = postRepository.findById(postId).orElseThrow(
                () -> new IllegalArgumentException("아이디가 존재하지 않습니다.")
        );

        //관리자 권한 및 유저 일치여부 확인
        if (user.getRole() == UserRoleEnum.ADMIN || post.getUser().getId().equals(user.getId())) {
                postRepository.deleteById(postId);
                return "삭제 완료";
        }
        else {
            throw new IllegalArgumentException("권한이 없습니다." + HttpStatus.BAD_REQUEST);
        }
    }

}