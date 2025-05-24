package com.scoreit.scoreit.service;

import com.scoreit.scoreit.entity.Member;
import com.scoreit.scoreit.entity.MemberFollower;
import com.scoreit.scoreit.entity.MemberFollowerId;
import com.scoreit.scoreit.repository.MemberFollowerRepository;
import com.scoreit.scoreit.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MemberFollowerService {

    @Autowired
    private MemberFollowerRepository memberFollowerRepository;
    @Autowired
    private MemberRepository memberRepository;


    //seguir
    public void follow(Long followerId, Long followedId){
        if (followerId.equals(followedId)) {
            throw new RuntimeException("Você não pode seguir a si mesmo.");
        }

        Member follower = memberRepository.findById(followerId)
                .orElseThrow(() -> new RuntimeException("Follower not found."));

        Member followed = memberRepository.findById(followedId)
                .orElseThrow(() -> new RuntimeException("Followed not found."));

        MemberFollowerId id = new MemberFollowerId(followerId, followedId);
        if (memberFollowerRepository.existsById(id)) {
            throw new RuntimeException("Você já segue este usuário.");
        }

        // Salvar relação de seguimento
        memberFollowerRepository.save(new MemberFollower(follower, followed));

        // Atualizar contadores
        follower.setFollowing_num(follower.getFollowing_num() + 1);
        followed.setFollowers(followed.getFollowers() + 1);

        // Salvar os dois membros com novos valores
        memberRepository.save(follower);
        memberRepository.save(followed);
    }

    //deixar de seguir
    public void unfollow(Long followerId, Long followedId) {
        Member follower = memberRepository.findById(followerId)
                .orElseThrow(() -> new RuntimeException("Follower not found."));
        Member followed = memberRepository.findById(followedId)
                .orElseThrow(() -> new RuntimeException("Followed not found."));

        MemberFollowerId id = new MemberFollowerId(followerId, followedId);
        if (!memberFollowerRepository.existsById(id)) {
            throw new RuntimeException("Você não segue este usuário.");
        }

        // Remover relação
        memberFollowerRepository.deleteById(id);

        // Atualizar contadores
        follower.setFollowing_num(Math.max(0, follower.getFollowing_num() - 1));
        followed.setFollowers(Math.max(0, followed.getFollowers() - 1));

        // Salvar membros atualizados
        memberRepository.save(follower);
        memberRepository.save(followed);
    }


    // Ver seus seguidores
    public List<Member> getFollowers(Long memberId){
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new RuntimeException("Membro não encontrado"));

        return memberFollowerRepository.findByFollowed(member)
                .stream()
                .map(MemberFollower::getFollower)
                .toList();
    }

    // Ver quem está seguindo
    public List<Member> getFollowing(Long memberId) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new RuntimeException("Membro não encontrado"));

        return memberFollowerRepository.findByFollower(member)
                .stream()
                .map(MemberFollower::getFollowed) // <- Aqui está a correção
                .toList();
    }

    // Verificar se o usuário segue outro
    public boolean isFollowing(Long followerId, Long followedId) {
        MemberFollowerId id = new MemberFollowerId(followerId, followedId);
        return memberFollowerRepository.existsById(id);
    }

    // Contar seguidores/seguidos
    public long countFollowers(Long memberId) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new RuntimeException("Membro não encontrado"));

        return memberFollowerRepository.countByFollowed(member);
    }

    public long countFollowing(Long memberId) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new RuntimeException("Membro não encontrado"));

        return memberFollowerRepository.countByFollower(member);
    }

}
