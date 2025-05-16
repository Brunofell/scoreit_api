package com.scoreit.scoreit.service;

import com.scoreit.scoreit.entity.CustomList;
import com.scoreit.scoreit.entity.CustomListContent;
import com.scoreit.scoreit.entity.Member;
import com.scoreit.scoreit.repository.CustomListContentRepository;
import com.scoreit.scoreit.repository.CustomListRepository;
import com.scoreit.scoreit.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomListService {
    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private CustomListRepository customListRepository;
    @Autowired
    private CustomListContentRepository customListContentRepository;

    public CustomList createCustomList(Long memberId, String listName, String description){
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new RuntimeException("Member Not Found."));

        CustomList customList = new CustomList();
        customList.setMember(member);
        customList.setListName(listName);
        customList.setList_description(description);
        return customListRepository.save(customList);
    }

    public List<CustomList> getListByMemberId(Long memberId){
        return customListRepository.findByMemberId(memberId);
    }

    public void deleteCustomListById(Long id) {
        CustomList customList = customListRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Lista não encontrada."));

        // Remove todos os conteúdos associados à lista
        List<CustomListContent> contents = customListContentRepository.findByCustomList(customList);
        customListContentRepository.deleteAll(contents);

        // Remove a lista em si
        customListRepository.delete(customList);
    }



}
