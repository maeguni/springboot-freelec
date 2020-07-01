package com.example.book.springboot.domain;


import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;

@Getter
@MappedSuperclass
//jpa entity 클래스들이 basetimeEntity을 상속할 경우 필드들도 칼럼으로 인식
@EntityListeners(AuditingEntityListener.class)
// baeTimeEntity 클래스에 Auditing 기능을 포함
public class BaseTimeEntity {

    @CreatedDate
    // Entity가 생성되어 저장될때 시간이 자동 저장
    private LocalDateTime createdDate;

    @LastModifiedDate
    //조회한 Entity의 값을 변경할 때 시간이 자동 저장
    private LocalDateTime modifiedDate;

}
