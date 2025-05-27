package com.scoreit.scoreit.entity;

import com.scoreit.scoreit.dto.customList.CustomListUpdateData;
import jakarta.persistence.*;

@Entity
@Table(name = "custom_list")
public class CustomList {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    private Member member;
    @Column(name = "list_name")
    private String listName;
    private String list_description;

    public CustomList(){}

    public CustomList(String listName, Long id, Member member, String list_description) {
        this.listName = listName;
        this.id = id;
        this.member = member;
        this.list_description = list_description;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public Member getMember() {
        return member;
    }

    public void setMember(Member member) {
        this.member = member;
    }

    public void updateList(CustomListUpdateData data) {
        if(data.listName() != null){
            this.listName = data.listName();
        }
        if(data.list_description() != null)
            this.list_description = data.list_description();
    }
}
