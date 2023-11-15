package com.korea.test.post;

import com.korea.test.category.Category;
import com.korea.test.category.CategoryService;
import com.korea.test.post.Post;
import com.korea.test.post.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class PostController {
    private final PostService postService;
    private final CategoryService categoryService;

    @RequestMapping("/")
    public String main(Model model) {
        //1. DB에서 데이터 꺼내오기
        List<Category> categoryList = categoryService.getCategoryList();

        if(categoryList.isEmpty()) {
            categoryService.saveDefaultCategory();
            return "redirect:/";
        }

        Category targetCategory = categoryList.get(0);

        List<Post> postList = postService.getPostListByCategory(targetCategory);

        if(postList.isEmpty()) {
            postService.saveDefaultPost(targetCategory);
            return "redirect:/";
        }

        //2. 꺼내온 데이터를 템플릿으로 보내기
        model.addAttribute("postList", postList);
        model.addAttribute("targetPost", postList.get(0));
        model.addAttribute("targetCategory", targetCategory);
        model.addAttribute("categoryList", categoryList);

        return "main";
    }


    @PostMapping("/createPost")
    public String createPost (Long categoryId) {
        Category category = categoryService.getCategoryById(categoryId);
        postService.saveDefaultPost(category);
        return "redirect:/";
    }




    @GetMapping("/postDetail/{id}")
    public String postDetail(Model model, @PathVariable Long id) {
        Post post = postService.getPostById(id);
        List<Post> postList = postService.getPostListByCategory(post.getCategory());
        List<Category> categoryList = categoryService.getCategoryList();

        model.addAttribute("targetPost", post);
        model.addAttribute("postList", postService.getPostList());
        model.addAttribute("postList", postList);
        model.addAttribute("targetCategory", post.getCategory());
        model.addAttribute("categoryList", categoryList);

        return "main";
    }
    @PostMapping("/update")
    public String update(Long id, String title, String content) {
        Post post = postService.getPostById(id);

        if(title.trim().length() == 0 ) {
            title = "제목없음";
        }

        post.setTitle(title);
        post.setContent(content);

        postService.save(post);
        return "redirect:/postDetail/" + id;
    }

    @PostMapping("/delete")
    public String delete(Long id) {
        this.postService.deleteById(id); ;
        return "redirect:/";
    }

}