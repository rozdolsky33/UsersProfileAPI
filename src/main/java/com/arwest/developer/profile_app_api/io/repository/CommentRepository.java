package com.arwest.developer.profile_app_api.io.repository;

import com.arwest.developer.profile_app_api.io.model.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long> {
}
