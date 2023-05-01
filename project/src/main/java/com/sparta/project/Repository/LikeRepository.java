package com.sparta.project.Repository;

import com.sparta.project.Entity.Like;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LikeRepository extends JpaRepository<Like, Long> {
    Like findByPostIdAndUser_Id(Long postId, Long userId);
    Like findByCommentIdAndUser_Id(Long commentId, Long userId);
    void deleteAllByPostId(Long postId);
    void deleteAllByCommentId(Long commentId);
}
