package jpabook.jpashop.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jpabook.jpashop.domain.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class MemberRepository {

//    @PersistenceContext   // == 스프링 부트+데이터 JPA 에선 @AutoWired로도 인젝션할 수 있게 지원한다.
//    private EntityManager em;
//    -> 클래스 어노테이션을 이용해 파이널 필드로 선언할 수 있다.

    private final EntityManager em;

    public void save(Member member) {
        em.persist(member);
    }

//    public Long save(Member member) {
//        em.persist(member);
//        return member.getId();
//    }

    public Member find(Long id) {
        return em.find(Member.class, id);
    }

    public List<Member> findAll() {
        return em.createQuery("select m from Member m", Member.class).getResultList(); //JPQL 쿼리
    }

    public List<Member> findByMember(String name) {
        return em.createQuery("select m from Member m where m.name = :name", Member.class)
                .setParameter("name", name).
                getResultList();
    }

}
