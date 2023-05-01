package com.sparta.project.Entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sparta.project.Dto.CommentRequestDto;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Entity(name = "Comments")
@NoArgsConstructor
public class Comment extends Timestamped{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String contents;

    @Column(nullable = false)
    private String username;

    @Column(nullable = false)
    private int commentLike;

    @JsonIgnore
    @ManyToOne
    private User user;

    @JsonIgnore
    @ManyToOne
    private Post post;

    public Comment(CommentRequestDto commentRequestDto, User user, Post post) {
        this.contents = commentRequestDto.getContents();
        this.username = user.getUsername();
        this.user = user;
        this.post = post;

    }

    public void updateComment(CommentRequestDto commentRequestDto, User user) {
        this.user = user;
        this.contents = commentRequestDto.getContents();
    }

    public void like() {
        this.commentLike += 1;
    }

    public void unlike() {
        this.commentLike -= 1;
    }

}
