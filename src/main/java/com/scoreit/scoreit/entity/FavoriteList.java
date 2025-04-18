package com.scoreit.scoreit.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "favorite_list")
public class FavoriteList {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    private Member member;
    @Column(name = "list_name")
    private String listName;
    private String list_description;

    public FavoriteList(Long id, Member member, String listName, String list_description) {
        this.id = id;
        this.member = member;
        this.listName = listName;
        this.list_description = list_description;
    }

    public FavoriteList() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Member getMember() {
        return member;
    }

    public void setMember(Member member) {
        this.member = member;
    }

    public String getListName() {
        return listName;
    }

    public void setListName(String listName) {
        this.listName = listName;
    }

    public String getList_description() {
        return list_description;
    }

    public void setList_description(String list_description) {
        this.list_description = list_description;
    }
}
