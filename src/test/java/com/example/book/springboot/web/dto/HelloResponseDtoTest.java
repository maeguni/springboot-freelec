package com.example.book.springboot.web.dto;


import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class HelloResponseDtoTest {

    @Test
    public void 롬복_기능_테스트(){
        //given
        String name = "test";
        int amount = 1000;

        //when
        HelloResponseDto dto =  new HelloResponseDto(name,amount);


        //then
        assertThat(dto.getName()).isEqualTo(name);
        assertThat(dto.getAmount()).isEqualTo(amount);


        //assertThat assertj라는 테스트 검증 라이브러리의 검증 메소드
        // 검증하고 싶은 대상을 모소드 인자로 받음
        //메소드 체이닝이 지원되어 isEqualTo와 같은 메소드를 이어서 사용할수 있음
        //isEqualTo
        //assertThat에 있는 값과 isEqualTo의 값을 비교해서 같을 떄만 성공


    }


}