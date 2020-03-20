package com.arwest.developer.profile_app_api.service;

import com.arwest.developer.profile_app_api.io.model.AppUser;
import com.arwest.developer.profile_app_api.io.model.Post;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.List;

public interface PostService {

    Post savePost(AppUser user, HashMap<String, String> request, String postImageName);
    List<Post> postList();
    Post getPostById(Long id);
    List<Post> findPostByUsername(String username);
    Post deletePost(Post post);
    String savePostImage(MultipartFile multipartFile, String fileName);

}
