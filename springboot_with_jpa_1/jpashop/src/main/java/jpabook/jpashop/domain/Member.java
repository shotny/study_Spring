package jpabook.jpashop.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @Setter
public class Member {

    @Id @GeneratedValue
    @Column(name = "member_id")
    private Long id;

    private String userName;

    @Embedded //객체의 @Embedable 둘 중 하나만 붙여도 됨
    private Address address;

    @OneToMany(mappedBy = "member")
    //mappedBy:를 선언한 객체는 연관관계의 주인이 아님.
    // 어노테이션을 붙인 객체테이블에서 ""필드로 지정된 값의 거울일뿐이라는 표현
    private List<Order> orders = new ArrayList<>();
}
