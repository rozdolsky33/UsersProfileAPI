package com.arwest.developer.profile_app_api.service;

import com.arwest.developer.profile_app_api.io.model.Post;

public interface CommentService {

    void saveComment(Post post, String username, String content);
}
