package com.example.book.springboot.web;

import com.example.book.springboot.domain.posts.Posts;
import com.example.book.springboot.domain.posts.PostsRepository;
import com.example.book.springboot.web.dto.PostsSaveRequestDto;
import com.example.book.springboot.web.dto.PostsUpdateRequestDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.*;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class PostsApiControllerTest {


        @LocalServerPort
        private int port;

        @Autowired
        private TestRestTemplate restTemplate;

        @Autowired
        private PostsRepository postsRepository;

        @Autowired
        private WebApplicationContext context;

        private MockMvc mvc;

        @Before
        public void setup(){
            mvc = MockMvcBuilders.webAppContextSetup(context)
                    .apply(springSecurity())
                    .build();
        }

        @After
        public void tearDown() throws Exception{

            postsRepository.deleteAll();

        }

        @Test
        //인증된모의 사용자를 만들어서 사용
        //roles에 권한을 추가할수있음
        //즉, 이 어노테이션으로 인해ROLE_USER 권한을 가진 사용자가 API를 요청하는 것과 동일한 효과를 가짐
        @WithMockUser(roles = "USER")
        public void Posts_등록된다() throws Exception{
            //given
            String title = "title";
            String content = "content";
            PostsSaveRequestDto requestDto = PostsSaveRequestDto.builder()
                    .title("title")
                    .content("content")
                    .author("author")
                    .build();


            String url = "http://localhost:" + port +"api/v1/posts";

            //when
            //ResponseEntity<Long> responseEntity = restTemplate.postForEntity(url,requestDto,Long.class);

            //then
            //assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
            //assertThat(responseEntity.getBody()).isGreaterThan(0L);

            //when
            mvc.perform(post(url)
                    .contentType(MediaType.APPLICATION_JSON_UTF8)
                    .content(new ObjectMapper().writeValueAsString(requestDto)))
                    .andExpect(status().isOk());

            //then
            List<Posts> all = postsRepository.findAll();
            assertThat(all.get(0).getTitle()).isEqualTo(title);
            assertThat(all.get(0).getContent()).isEqualTo(content);

       }


       @Test
       @WithMockUser(roles = "USER")
    public void Posts_수정된다() throws Exception{
            //given
           Posts savedPosts = postsRepository.save(Posts.builder()
            .title("title")
            .content("content")
            .author("author").build());

           Long updateId = savedPosts.getId();
           String expectedTitle = "title2";
           String expectedContnet = "content2";

           PostsUpdateRequestDto requestDto = PostsUpdateRequestDto.builder()
                   .title(expectedTitle)
                   .content(expectedContnet)
                   .build();

           String url = "http://localhost:" + port + "/api/v1/posts/" + updateId;

          // HttpEntity<PostsUpdateRequestDto> requestEntity = new HttpEntity<>(requestDto);

          // ResponseEntity<Long> responseEntity = restTemplate.exchange(url, HttpMethod.PUT,requestEntity,Long.class);

           //then
           //assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
          //assertThat(responseEntity.getBody()).isGreaterThan(0L);

           //when
           mvc.perform(put(url).contentType(MediaType.APPLICATION_JSON_UTF8)
                   .content(new ObjectMapper().writeValueAsString(requestDto))).andExpect(status().isOk());

           List<Posts> all = postsRepository.findAll();
           assertThat(all.get(0).getTitle()).isEqualTo(expectedTitle);
           assertThat(all.get(0).getContent()).isEqualTo(expectedContnet);
       }

       @Test
       public void BaseTimeEntity_등록(){
            //given
           LocalDateTime now = LocalDateTime.of(2019,6,4,0,0,0);
           postsRepository.save(Posts.builder()
           .title("title")
           .content("content")
           .author("author").build());

           //when
           List<Posts> postsList = postsRepository.findAll();

           //then
           Posts posts = postsList.get(0);

           System.out.println(">>>>>>>>>>>> createDate="+posts.getCreatedDate()+", modifiedDate="+posts.getModifiedDate());

           assertThat(posts.getCreatedDate()).isAfter(now);
           assertThat(posts.getModifiedDate()).isAfter(now);


       }




}