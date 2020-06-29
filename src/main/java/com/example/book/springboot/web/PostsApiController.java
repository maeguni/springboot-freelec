package com.example.book.springboot.web;


import com.example.book.springboot.service.posts.PostsService;
import com.example.book.springboot.web.dto.PostsSaveRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
public class PostsApiController {


    private final PostsService postsService;

    @PostMapping("/api/v1/posts")
    public Long save(@RequestBody PostsSaveRequestDto requestDto){

        return postsService.save(requestDto);

    }

    @PutMapping("/api/v1/posts/{id}")
    public Long update(@PathVariable Long id,@RequestBody PostsSaveRequestDto requestDto){

        return postsService.update(id,requestDto);

    }

    @GetMapping("/api/v1/posts/{id}")
    public PostsSaveRequestDto findById(@PathVariable Long id){

        return postsService.findById(id);
    }

}
