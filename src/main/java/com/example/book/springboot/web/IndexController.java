package com.example.book.springboot.web;

import com.example.book.springboot.config.auth.LoginUser;
import com.example.book.springboot.config.auth.dto.SessionUser;
import com.example.book.springboot.service.posts.PostsService;
import com.example.book.springboot.web.dto.PostsResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@RequiredArgsConstructor
@Controller
public class IndexController {

    private final PostsService postsService;
    //private final HttpSession httpSession;

    @GetMapping("/")
    public String index(Model model , @LoginUser SessionUser user){
        model.addAttribute("posts",postsService.findAllDesc());

        //앞서 작성된 CustomOAuth2UserService에서 로그인 성공 시 세션에 SessionUser를 저장하도록 구성
        //즉 로그인 성공 시 httpSession.getAttribute("user")에서 값을 가져올수 있다.
        //session 정보를 한번에 가져올수 있도록 interface로 만들어서 유지보수 향상
        //SessionUser user = (SessionUser) httpSession.getAttribute("user");

        if (user != null){
            model.addAttribute("userName",user.getName());
        }

        return "index";
    }

    @GetMapping("/posts/save")
    public String postsSave(){
        return "posts-save";
    }


    @GetMapping("/posts/update/{id}")
    public String postsUpdate(@PathVariable Long id,Model model){

        PostsResponseDto dto = postsService.findById(id);
        model.addAttribute("post",dto);

        return "posts-update";

    }





}
