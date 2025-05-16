package com.scoreit.scoreit.service;

import com.scoreit.scoreit.entity.*;
import com.scoreit.scoreit.repository.CustomListContentRepository;
import com.scoreit.scoreit.repository.CustomListRepository;
import com.scoreit.scoreit.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CustomListContentService {
    @Autowired
    private CustomListContentRepository customListContentRepository;
    @Autowired
    private CustomListRepository customListRepository;
    @Autowired
    private MemberRepository memberRepository;


    public void addContentInCustom(Long memberId, String mediaId, String mediaType, String listName){
        CustomList customList = customListRepository.findByMemberIdAndListName(memberId, listName);
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new RuntimeException("Member Not Found."));

        Optional<CustomListContent> existingContent = customListContentRepository.findByCustomListAndMediaIdAndMediaType(customList, mediaId, mediaType);
        if (existingContent.isPresent()) {
            throw new RuntimeException("This content is already in your list.");
        }

        // Cria um novo registro de conteúdo na lista de favoritos
        CustomListContent content = new CustomListContent();
        content.setCustomList(customList);
        content.setMediaId(mediaId);
        content.setMediaType(mediaType);

        customListContentRepository.save(content);
    }

    public List<CustomListContent> getCustomListContent(Long memberId, String listName){
        CustomList customList = customListRepository.findByMemberIdAndListName(memberId, listName);

        if (customList == null) {
            throw new RuntimeException("Lista não encontrada.");
        }

        return customListContentRepository.findByCustomList(customList);
    }

    public void removeContent(Long memberId, String mediaId, String mediaType, String listName){
        CustomList customList = customListRepository.findByMemberIdAndListName(memberId, listName);

        if (customList == null) {
            throw new RuntimeException("Lista não encontrada.");
        }

        Optional<CustomListContent> content = customListContentRepository.findByCustomListAndMediaIdAndMediaType(customList, mediaId, mediaType);

        if (content.isEmpty()) {
            throw new RuntimeException("Conteúdo não encontrado na lista.");
        }

        customListContentRepository.delete(content.get());
    }



}
