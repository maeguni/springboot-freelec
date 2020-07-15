package com.example.book.springboot;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

//@EnableJpaAuditing // JPA Auditing 활성화
@SpringBootApplication
public class Application {

    //SpringBootAppication 자동설정,Bean 읽기와 생성  > 프로젝트 최상단에 위치해야됨
    public static void main(String[] args){
        SpringApplication.run(Application.class,args);
    }

}
