package com.example.book.springboot.domain.posts;


import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor
//기본생성자 자동추가
@Entity
// 테이블 링크 될 클래스임을 나타냄
// 기본값으로 클래스의 카멜케이스 이름을 언더스코어 네이밍으로 테이블 이름을 매칭함(_)
public class Posts {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        //PK 생성규칙
        // 스프링 부트 2.0 에서는 GeneratedValue를 추가해줘야 auto_increment가 됨
        private Long id;

        @Column(length = 500, nullable = false)
        //테이블의 칼럼을 나타내며 선언 안해도 해당 클래스의 필드는 모두 칼럼이됨
        // 사용이유: 기본값 외에 추가로 변경이 필요한 옵션이 있으면 사용
        //문자열의 경우 varchar(255)가 기본값/ 변경 원하면 사용
        private String title;

        @Column(columnDefinition = "TEXT", nullable = false )
        private String content;

        private String author;

        @Builder
        //해당 클래스의 빌더 패턴 클래스를 생성
        // 생성자 상단에 선언 시 생성자에 포함된 필드만 빌더에 포함
        public Posts(String title, String content, String author) {
            this.title = title;
            this.content = content;
            this.author =  author;
        }

        public void update(String title , String content){
            this.title = title;
            this.content = content;


        }

}
