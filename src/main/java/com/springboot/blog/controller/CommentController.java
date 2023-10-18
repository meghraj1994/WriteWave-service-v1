package com.springboot.blog.controller;


import com.springboot.blog.entity.Comment;
import com.springboot.blog.payload.CommentDto;
import com.springboot.blog.service.CommentServie;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/")
public class CommentController {

    //autowire service
    private CommentServie commentServie;

    public CommentController(CommentServie commentServie) {
        this.commentServie = commentServie;
    }

    //create comment method;
    @PostMapping("/posts/{postId}/comments")
    public ResponseEntity<CommentDto> createComment(@PathVariable(name = "postId") long postId,
                                                    @Valid @RequestBody CommentDto commentDto) {
        return new ResponseEntity<>(commentServie.createComment(postId, commentDto), HttpStatus.CREATED);
    }

    //get comments by post id
    @GetMapping("/posts/{postId}/comments")
    public List<CommentDto> getAllCommentsByCommentId(@PathVariable(name = "postId") long postId) {
        return commentServie.getCommentsByPostId(postId);
    }

    //get comment from post by
    @GetMapping("/posts/{postId}/comments/{commentId}")
    public ResponseEntity<CommentDto> getCommentById(@PathVariable(name = "postId") long postId,
                                                     @PathVariable(name = "commentId") long commentID) {
        CommentDto commentDto = commentServie.getCommentById(postId, commentID);
        return new ResponseEntity<>(commentDto, HttpStatus.OK);

    }

    @PutMapping("/posts/{postId}/comments/{commentId}")
    public ResponseEntity<CommentDto> updateComment(@PathVariable(name = "postId") Long postId,
                                                    @PathVariable(name = "commentId") Long commentId,
                                                   @Valid @RequestBody CommentDto commentDto) {

        CommentDto updatedComment = commentServie.updateComment(postId, commentId, commentDto);

        return new ResponseEntity<>(updatedComment, HttpStatus.OK);

    }

    //delete comment
    @DeleteMapping("/posts/{postId}/comments/{commentId}")
    public ResponseEntity<String> deleteComment(@PathVariable(name = "postId") long postId,
                                                @PathVariable(name = "commentId") long commentId) {

        commentServie.deleteComment(postId,commentId);
        return new ResponseEntity<>("comment delete successfull",HttpStatus.OK);

    }
}
