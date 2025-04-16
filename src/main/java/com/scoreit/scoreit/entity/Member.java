package com.scoreit.scoreit.entity;

import com.scoreit.scoreit.dto.member.Gender;
import com.scoreit.scoreit.dto.member.MemberUpdate;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "members")
public class Member implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String email;
    private String password;
    private LocalDate birthDate;
    private Gender gender;
    private LocalDateTime createdAt;
    private boolean enabled;
    private String profileImageUrl;
    private String bio;

    public Member(Long id, String name, String email, String password, LocalDate birthDate, Gender gender, LocalDateTime createdAt, Boolean enabled, String profileImageUrl) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
        this.birthDate = birthDate;
        this.gender = gender;
        this.createdAt = createdAt;
        this.enabled = enabled;
        this.profileImageUrl = profileImageUrl;
    }

    public Member(String name, String email, LocalDate birthDate, Gender gender, String password) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.birthDate = birthDate;
        this.gender = gender;
        this.enabled = true;
    }

    public Member(){}

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public String getProfileImageUrl() {
        return profileImageUrl;
    }

    public void setProfileImageUrl(String profileImageUrl) {
        this.profileImageUrl = profileImageUrl;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public void updateMember(MemberUpdate memberUpdate){
        if(memberUpdate.name() != null){
            this.name = memberUpdate.name();
        }
//        if(memberUpdate.email() != null){
//            this.email = memberUpdate.email();
//        }
        if(memberUpdate.gender() != null){
            this.gender = memberUpdate.gender();
        }
        if(memberUpdate.birthDate() != null){
            this.birthDate = memberUpdate.birthDate();
        }
        if(memberUpdate.password() != null){
            this.password = memberUpdate.password();
        }
        if(memberUpdate.bio() != null){
            this.bio = memberUpdate.bio();
        }
    }

    public void deleteMember(){
        this.enabled = false;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of();
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return this.enabled;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }
}
