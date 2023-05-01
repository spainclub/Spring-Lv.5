package com.sparta.project.Entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity(name = "Likes")
@NoArgsConstructor
public class Like {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "userId")
    private User user;

    private Long postId;

    private Long commentId;

    public Like(User user, Long postId, Long commentId) {
        this.user = user;
        this.postId = postId;
        this.commentId = commentId;
    }
}
