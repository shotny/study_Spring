package jpabook.jpashop.service;

import jpabook.jpashop.domain.Member;
import jpabook.jpashop.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true) //읽기전용엔 true!! 기본값은 false
@RequiredArgsConstructor
public class MemberService {

//    @Autowired
//    private MemberRepository memberRepository;
//    테스트 등의 이유로 값을 변경해야할 때가 있는데 private 이라 값을 변경할 수 없다.
//    -> 아래의 <세터인젝션> 사용

//    private MemberRepository memberRepository;
//
//    @Autowired
//    public void setMemberRepository(MemberRepository memberRepository) {
//        this.memberRepository = memberRepository;
//    }
//    public 으로 외부에서 언제든 수정이 가능하다
//    -> 아래의 <생성자 인젝션> 사용

//  스프링이 뜰 때 생성자에서 인젝션을 하고 이후에 누군가 바꿀 수 없다.
    private final MemberRepository memberRepository;

//    @Autowired
//    public MemberService(MemberRepository memberRepository) {
//        this.memberRepository = memberRepository;
//    }
    // 생성자가 딱 하나만 있는경우 오토와이어드 어노테이션이 없어도 스프링이 알아서 생성자 주입을 해준다.


//    @AllArgsConstructor 를 클래스에 붙이면 필트를 가진 생성자를 만들어준다.
//    @RequiredArgsConstructor는 final이 있는 필드로만 생성자를 만들어준다.


    @Transactional
    public Long join(Member member) {
        validateDuplicateMember(member);
        memberRepository.save(member);
        return member.getId();
    }

    // 멀티쓰레드 환경 등 저장이 동시에 될 경우 이렇게 해두어도 같은 회원명으로 중북 저장될 수 있다.
    // 그렇기 때문에 실무에서는 한 번 더 점검해야한다. (DB에 유니크 제약 조건 등)
    public void validateDuplicateMember(Member member) {
        List<Member> findMembers = memberRepository.findByMember(member.getName());
        if (!findMembers.isEmpty()) {
            throw new IllegalStateException("이미 존재하는 회원입니다.");
        }
    }

    public List<Member> findMembers() {
        return memberRepository.findAll();
    }

    public Member findOne(Long memberId) {
        return memberRepository.find(memberId);
    }
}
