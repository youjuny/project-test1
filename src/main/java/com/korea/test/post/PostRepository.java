package com.korea.test.post;

import com.korea.test.category.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long>  {
    List<Post> findByCategory(Category targetcategory);
}
