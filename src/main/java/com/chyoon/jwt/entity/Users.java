package com.chyoon.jwt.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Comment;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "USERS")
public class Users implements UserDetails {

    @Id
    @Column(name = "U_ID")
    @Comment("UID")
    private Long uId;

    @Column(name = "USER_ID", unique = true, nullable = false, length = 30)
    @Comment("유저 아이디")
    private String userId;

    @Column(name = "USER_PW", nullable = false, length = 512)
    @Comment("유저 비밀번호")
    private String userPw;

    @Column(name = "USER_NAME", nullable = false, length = 30)
    @Comment("유저 이름")
    private String userName;

    public String getUserName() {// 유저명 반환(밑에 시큐리티 username과는 다름)
        return this.userName;
    }

    public void updatePassword(String encodedPassword) {
        this.setUserPw(encodedPassword);
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public String getPassword() {
        return this.userPw;
    }

    @Override
    public String getUsername() {// 식별값 반환
        return this.userId;
    }

    @Override
    public boolean isAccountNonExpired() {
        return false;
    }

    @Override
    public boolean isAccountNonLocked() {
        return false;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return false;
    }

    @Override
    public boolean isEnabled() {
        return false;
    }
}