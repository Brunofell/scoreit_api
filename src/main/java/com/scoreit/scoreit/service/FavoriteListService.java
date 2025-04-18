package com.scoreit.scoreit.service;

import com.scoreit.scoreit.entity.FavoriteList;
import com.scoreit.scoreit.entity.Member;
import com.scoreit.scoreit.repository.FavoriteListRepository;
import com.scoreit.scoreit.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class FavoriteListService {
    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private FavoriteListRepository favoriteListRepository;

    public FavoriteList createFavoriteList(Long memberId, String listName, String description){
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new RuntimeException("Member Not Found."));

        FavoriteList favoriteList = new FavoriteList();
        favoriteList.setMember(member);
        favoriteList.setListName(listName);
        favoriteList.setList_description(description);
        return favoriteListRepository.save(favoriteList);
    }

    public Optional<FavoriteList> getListByMemberId(Long memberId){
        return favoriteListRepository.findByMemberId(memberId);
    }

}
