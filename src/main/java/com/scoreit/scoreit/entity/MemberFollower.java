package com.scoreit.scoreit.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "member_followers")
public class MemberFollower {
    @EmbeddedId
    private MemberFollowerId id;

    @ManyToOne
    @MapsId("followerId")
    @JoinColumn(name = "follower_id")
    private Member follower;

    @ManyToOne
    @MapsId("followedId")
    @JoinColumn(name = "followed_id")
    private Member followed;


    public MemberFollower() {}

    public MemberFollower(Member follower, Member followed) {
        this.follower = follower;
        this.followed = followed;
        this.id = new MemberFollowerId(follower.getId(), followed.getId());
    }

    public MemberFollowerId getId() {
        return id;
    }

    public void setId(MemberFollowerId id) {
        this.id = id;
    }

    public Member getFollower() {
        return follower;
    }

    public void setFollower(Member follower) {
        this.follower = follower;
    }

    public Member getFollowed() {
        return followed;
    }

    public void setFollowed(Member followed) {
        this.followed = followed;
    }

}

