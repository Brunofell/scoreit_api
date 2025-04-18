package com.scoreit.scoreit.service;

import com.scoreit.scoreit.entity.FavoriteList;
import com.scoreit.scoreit.entity.FavoriteListContent;
import com.scoreit.scoreit.entity.Member;
import com.scoreit.scoreit.repository.FavoriteListContentRepository;
import com.scoreit.scoreit.repository.FavoriteListRepository;
import com.scoreit.scoreit.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class FavoriteListContentService {
    @Autowired
    private FavoriteListContentRepository favoriteListContentRepository;
    @Autowired
    private FavoriteListRepository favoriteListRepository;
    @Autowired
    private MemberRepository memberRepository;

    public void addContentInFavorites(Long memberId, String mediaId, String mediaType){
        FavoriteList favoriteList = favoriteListRepository.findByMemberIdAndListName(memberId, "Favorites");
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new RuntimeException("Member Not Found."));

        if (favoriteList == null) {

            favoriteList = new FavoriteList();
            favoriteList.setMember(memberRepository.findById(memberId).orElseThrow(() -> new RuntimeException("Membro não encontrado")));
            favoriteList.setListName("Favorites");
            favoriteList.setList_description("List of "+ member.getName() +"'s favorite media!");
            favoriteListRepository.save(favoriteList);
        }

        Optional<FavoriteListContent> existingContent = favoriteListContentRepository.findByFavoriteListAndMediaIdAndMediaType(favoriteList, mediaId, mediaType);
        if (existingContent.isPresent()) {
            throw new RuntimeException("This content is already in your list.");
        }

        // Cria um novo registro de conteúdo na lista de favoritos
        FavoriteListContent content = new FavoriteListContent();
        content.setCustomList(favoriteList);
        content.setMediaId(mediaId);
        content.setMediaType(mediaType);

        favoriteListContentRepository.save(content);

    }

    public List<FavoriteListContent> getFavoriteListContent(Long memberId){
        FavoriteList favoriteList = favoriteListRepository.findByMemberIdAndListName(memberId, "Favorites");

        if (favoriteList == null) {
            throw new RuntimeException("Lista de favoritos não encontrada.");
        }

        return favoriteListContentRepository.findByFavoriteList(favoriteList);
    }

    public void removeContent(Long memberId, String mediaId, String mediaType){
        FavoriteList favoriteList = favoriteListRepository.findByMemberIdAndListName(memberId, "Favorites");

        if (favoriteList == null) {
            throw new RuntimeException("Lista de favoritos não encontrada.");
        }

        Optional<FavoriteListContent> content = favoriteListContentRepository.findByFavoriteListAndMediaIdAndMediaType(favoriteList, mediaId, mediaType);

        if (content.isEmpty()) {
            throw new RuntimeException("Conteúdo não encontrado na lista de favoritos.");
        }

        favoriteListContentRepository.delete(content.get());
    }

}
