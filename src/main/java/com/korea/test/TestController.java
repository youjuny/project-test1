package com.korea.test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.time.LocalDateTime;
import java.util.List;

@Controller
public class TestController {

    @Autowired
    private PostRepository postRepository;

    @RequestMapping("/test")
    @ResponseBody public String test() {
        return "test";
    }

    @RequestMapping("/")
    public String main(Model model) {
        //1. DB에서 데이터 꺼내오기
        List<Post> postList = postRepository.findAll();

        //2. 꺼내온 데이터를 템플릿으로 보내기
        model.addAttribute("postList", postList);

        return "main";
    }

    @PostMapping("/write")
    public String write() {
        Post post = new Post();
        post.setTitle("new title..");
        post.setContent("");
        post.setCreateDate(LocalDateTime.now());

        postRepository.save(post);

        return "redirect:/";
    }
}
