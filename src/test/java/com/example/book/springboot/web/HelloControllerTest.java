package com.example.book.springboot.web;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@RunWith(SpringRunner.class)
//테스트를 진행할 때 jUnit에 내장된 실행자 외에 다른 실행자를 실행
//springRunner라는 스프링 실행자를 사용
//스프링 부트 테스트와 jUnit 사이에 연결자 역활
@WebMvcTest
public class HelloControllerTest {

    @Autowired
    //웹API 테스트 할 떄 사용
    // 스프링 MVC 테스트의 시작점
    // http GET,POST 등에 대한 API 테스트 가능
    private MockMvc mvc;


    @Test
    public void heello가_리턴된다() throws Exception{
            String hello = "hello";

            mvc.perform(get("/hello"))
                    //mvc.perform의 결과를 검증
                    //header의 status 검증
                    //OK 는 200인지 검지
                    .andExpect(status().isOk())
                    //응답 본문의 내용 검증
                    //Contr
                    .andExpect(content().string(hello));

    }

    @Test
    public void helloDto가_리턴된다() throws Exception{
            String name = "hello";
            int amount = 1000;


            mvc.perform(
                    get("/hello/dto")
                    .param("name", name)
                    .param("amount",String.valueOf(amount)))

                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.name",is(name)))
                    .andExpect(jsonPath("$.amount",is(amount)));

                // param string만 허용
                // 요청 param 설정
                // jsonPath
                // JSON 응답값을 필드별로 검증 할수 있는 메소드
                // $를 준으로 필드명을 명시
    }


}