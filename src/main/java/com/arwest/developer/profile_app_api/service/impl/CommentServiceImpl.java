package com.arwest.developer.profile_app_api.service.impl;

import com.arwest.developer.profile_app_api.io.model.Comment;
import com.arwest.developer.profile_app_api.io.model.Post;
import com.arwest.developer.profile_app_api.io.repository.CommentRepository;
import com.arwest.developer.profile_app_api.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Date;

@Service
@Transactional
public class CommentServiceImpl implements CommentService {

    private CommentRepository commentRepository;

    @Autowired
    public CommentServiceImpl(CommentRepository commentRepository) {
        this.commentRepository = commentRepository;
    }

    @Override
    public void saveComment(Post post, String username, String content) {
        Comment comment = new Comment();
        comment.setContent(content);
        comment.setUsername(username);
        comment.setPostedDate(new Date());
        post.setComments(comment);
        commentRepository.save(comment);
    }
}
