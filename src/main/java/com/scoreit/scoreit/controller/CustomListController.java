package com.scoreit.scoreit.controller;

import com.scoreit.scoreit.entity.CustomListContent;
import com.scoreit.scoreit.entity.FavoriteListContent;
import com.scoreit.scoreit.entity.Member;
import com.scoreit.scoreit.service.CustomListContentService;
import com.scoreit.scoreit.service.CustomListService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/customList")
public class CustomListController {

    @Autowired
    private CustomListService customListService;
    @Autowired
    private CustomListContentService customListContentService;

    @PostMapping("/register/{memberId}/{listName}/{description}")
    public ResponseEntity<String> customListRegister(
            @PathVariable Long memberId,
            @PathVariable String listName,
            @PathVariable String description) {
        customListService.createCustomList(memberId, listName, description);
        return ResponseEntity.ok(listName + " successfully created.");
    }

    @PostMapping("/addContent/{memberId}/{mediaId}/{mediaType}/{listName}")
    public ResponseEntity<String> addContentToList(@PathVariable Long memberId, @PathVariable String mediaId, @PathVariable String mediaType, @PathVariable String listName) {
        try {
            customListContentService.addContentInCustom(memberId, mediaId, mediaType, listName);
            return ResponseEntity.ok("Conteúdo adicionado a " + listName);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @GetMapping("/getList/{memberId}")
    public ResponseEntity getListByMemberId(@PathVariable Long memberId){
        return ResponseEntity.ok(customListService.getListByMemberId(memberId));
    }

    @GetMapping("/getContent/{memberId}/{listName}")
    public List<CustomListContent> getCustomListContent(@PathVariable Long memberId, @PathVariable String listName){
        return customListContentService.getCustomListContent(memberId, listName);
    }

    @DeleteMapping("/delete/{listId}")
    public ResponseEntity<String> deleteList(@PathVariable Long listId) {
        try {
            customListService.deleteCustomListById(listId);
            return ResponseEntity.ok("Lista deletada com sucesso.");
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @DeleteMapping("/deleteContent/{memberId}/{mediaId}/{mediaType}/{listName}")
    public ResponseEntity<String> removeContentFromList(@PathVariable Long memberId, @PathVariable String mediaId, @PathVariable String mediaType, @PathVariable String listName) {
        try {
            customListContentService.removeContent(memberId, mediaId, mediaType, listName);
            return ResponseEntity.ok("Content removed from " + listName);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

}
