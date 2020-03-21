package com.arwest.developer.profile_app_api.io.repository;

import com.arwest.developer.profile_app_api.io.model.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {


    @Query("SELECT p FROM Post p order by p.postedDate DESC")
    List<Post> findAll();

    @Query("SELECT p FROM Post p WHERE p.username=:username order by p.postedDate DESC")
    List<Post> findPostByUsername(@Param("username") String username);

    @Query("SELECT p FROM Post p WHERE p.id=:id")
    Post findPostById(@Param("id") Long id);

    @Modifying // any query that not a select
    @Query("DELETE Post WHERE id=:id")
    void deletePostById(@Param("id") Long id);

}
