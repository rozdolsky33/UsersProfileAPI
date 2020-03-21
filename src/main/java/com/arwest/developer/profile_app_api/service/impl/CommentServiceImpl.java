package com.arwest.developer.profile_app_api.service.impl;

import com.arwest.developer.profile_app_api.io.model.Comment;
import com.arwest.developer.profile_app_api.io.repository.CommentRepository;
import com.arwest.developer.profile_app_api.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
public class CommentServiceImpl implements CommentService {

    private CommentRepository commentRepository;

    @Autowired
    public CommentServiceImpl(CommentRepository commentRepository) {
        this.commentRepository = commentRepository;
    }

    @Override
    public void saveComment(Comment comment) {
        commentRepository.save(comment);
    }
}
