package com.arwest.developer.profile_app_api.service.impl;

import com.arwest.developer.profile_app_api.io.model.AppUser;
import com.arwest.developer.profile_app_api.io.model.Post;
import com.arwest.developer.profile_app_api.io.repository.PostRepository;
import com.arwest.developer.profile_app_api.service.PostService;
import com.arwest.developer.profile_app_api.unitility.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

@Service
@Transactional
public class PostServiceImpl implements PostService {

    private PostRepository postRepository;

    @Autowired
    public PostServiceImpl(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    @Override
    public Post savePost(AppUser user, HashMap<String, String> request, String postImageName) {
        String caption = request.get("caption");
        String location = request.get("location");
        Post post = new Post();
        post.setCaption(caption);
        post.setLocation(location);
        post.setUsername(user.getUsername());
        post.setPostedDate(new Date());
        post.setUserImageId(user.getId());
        user.setPost(post);
        postRepository.save(post);
        return post;
    }

    @Override
    public List<Post> postList() {
        return postRepository.findAll();
    }

    @Override
    public Post getPostById(Long id) {
        return postRepository.findPostById(id);
    }

    @Override
    public List<Post> findPostByUsername(String username) {
        return postRepository.findPostByUsername(username);
    }

    @Override
    public Post deletePost(Post post) {
        try{
            Files.deleteIfExists(Paths.get(Constants.POST_FOLDER + "/" + post.getName()+ ".png"));
            postRepository.deletePostById(post.getId());
            return post;
        }catch (Exception ex){
            ex.getStackTrace();
        }
        return null;
    }

    @Override
    public String savePostImage(MultipartFile multipartFile, String fileName) {

        /*
         * MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) // convert to multipart
         * request; Iterator<String> it = multipartRequest.getFileNames(); MultipartFile
         * multipartFile = multipartRequest.getFile(it.next());
         */

        try {
            byte[] bytes = multipartFile.getBytes();
            Path path = Paths.get(Constants.POST_FOLDER + fileName + ".png");
            Files.write(path, bytes, StandardOpenOption.CREATE);
        } catch (IOException e) {
            System.out.println("Error occured. Photo not saved!");
            return "Error occured. Photo not saved!";
        }
        System.out.println("Photo saved successfully!");
        return "Photo saved successfully!";
    }
}
