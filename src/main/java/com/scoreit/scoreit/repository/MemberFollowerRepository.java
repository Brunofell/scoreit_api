package com.scoreit.scoreit.repository;

import com.scoreit.scoreit.entity.Member;
import com.scoreit.scoreit.entity.MemberFollower;
import com.scoreit.scoreit.entity.MemberFollowerId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Arrays;
import java.util.List;

public interface MemberFollowerRepository extends JpaRepository<MemberFollower, MemberFollowerId> {
    List<MemberFollower> findByFollowed(Member followed);   // Para ver seguidores (quem segue `followed`)
    List<MemberFollower> findByFollower(Member follower);   // Para ver quem você está seguindo
    long countByFollowed(Member member);
    long countByFollower(Member member);
    @Query("SELECT mf.followed.id FROM MemberFollower mf WHERE mf.follower.id = :followerId")
    List<Long> findFollowedIdsByFollowerId(Long followerId);
}


