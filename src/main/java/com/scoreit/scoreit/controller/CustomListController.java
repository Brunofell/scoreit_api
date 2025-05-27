package com.scoreit.scoreit.controller;

import com.scoreit.scoreit.dto.customList.CustomListContentData;
import com.scoreit.scoreit.dto.customList.CustomListRegisterData;
import com.scoreit.scoreit.dto.customList.CustomListUpdateData;
import com.scoreit.scoreit.entity.CustomListContent;
import com.scoreit.scoreit.service.CustomListContentService;
import com.scoreit.scoreit.service.CustomListService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/customList")
public class CustomListController {

    @Autowired
    private CustomListService customListService;
    @Autowired
    private CustomListContentService customListContentService;

    @PostMapping("/register")
    public ResponseEntity<String> customListRegister(@RequestBody @Valid CustomListRegisterData data) {
        customListService.createCustomList(data.memberId(), data.listName(), data.description());
        return ResponseEntity.ok(data.listName() + " successfully created.");
    }


    @PostMapping("/addContent")
    public ResponseEntity<String> addContentToList(@RequestBody @Valid CustomListContentData data) {
        try {
            customListContentService.addContentInCustom(data.memberId(), data.mediaId(), data.mediaType(), data.listName());
            return ResponseEntity.ok("Conteúdo adicionado a " + data.listName());
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

    @DeleteMapping("/deleteContent")
    public ResponseEntity<String> removeContentFromList(@RequestBody @Valid CustomListContentData data) {
        try {
            customListContentService.removeContent(data.memberId(), data.mediaId(), data.mediaType(), data.listName());
            return ResponseEntity.ok("Content removed from " + data.listName());
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }


    @PutMapping("/update")
    public ResponseEntity<String> updateListData(@RequestBody @Valid CustomListUpdateData data){
        customListService.updateListData(data);
        return ResponseEntity.ok( data.listName() + " has been updated.");
    }

}
