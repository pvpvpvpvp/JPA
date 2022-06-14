package jpabook.jpashop.service;

import jpabook.jpashop.domain.Member;
import jpabook.jpashop.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

// spring 최신버전은 하나있는 기본 생성자에 @Autowired 자동으로 붙혀준다
// 그래서 final로 선언한 필드를 기준으로 기본 생성자를 만들어준다.!
//
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;

    @Transactional
    public Long join(Member member){
        validateDuplicateMember(member);
        memberRepository.save(member);
        return member.getId();
    }
    //was에서 동시에 들어온다면 같이질 수 있으니 아이디를 유니크설정함으로 최후의 방어를 해준다.
    private void validateDuplicateMember(Member member) {
        List<Member> findMember = memberRepository.findByName(member.getName());
        if (!findMember.isEmpty()){
            throw new IllegalStateException("이미 존재하는 회원입니다.");
        }
    }
    public List<Member> findMembers(){
        return  memberRepository.findAll();
    }
    public Member findOne(Long memeberId) {
        return memberRepository.findOne(memeberId);
    }
}
