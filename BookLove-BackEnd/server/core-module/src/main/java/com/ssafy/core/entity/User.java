package com.ssafy.core.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.ssafy.core.converter.JoinCodeConverter;
import com.ssafy.core.converter.MFCodeConverter;
import com.ssafy.core.converter.YNCodeConverter;
import com.ssafy.core.code.JoinCode;
import com.ssafy.core.code.MFCode;
import com.ssafy.core.code.YNCode;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.*;
import java.util.stream.Collectors;


@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "user"

)
// 회원 테이블
public class User extends BaseEntity implements UserDetails {

    // User 테이블의 키값 = 회원의 고유 키값
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long userId;

    // 회원이 가입한 타입 (none:일반, sns:소셜회원가입)
    @Convert(converter = JoinCodeConverter.class)
    @Column(nullable = false, length = 5)
    private JoinCode type;

    // 회원아이디(일반:아이디, 소셜회원가입:발급번호)
    @Column(nullable = false,length = 120)
    private String id;

    // 회원 닉네임
    @Column(nullable = false, length = 64)
    private String nickname;

    // 회원 성별
    @Column(length = 64)
    private String gender;

    // 회원 나이
    @Column(length = 64)
    private int age;

    // 비밀번호
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @Column(length = 255)
    private String password;

    // 카테고리
    @ManyToMany
    @JoinTable(name = "category_user",
            joinColumns = @JoinColumn(name = "category_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id"))
    private List<Category> categories;

    // 회원 선호정보 입력여부
    @Column(nullable = false, length = 64)
    private boolean isChecked;

    // 접근용 토큰
    @Column(length = 255)
    private String accessToken;

    // 재발급용 토큰
    @Column(length = 255)
    private String refreshToken;

    // =================================================================================================
    // JWT
    // =================================================================================================
    @Column(length = 100)
    private String provider;

    @ElementCollection(fetch = FetchType.EAGER)
    @Builder.Default
    private List<String> roles = new ArrayList<>();

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.roles.stream().map(SimpleGrantedAuthority::new).collect(Collectors.toList());
    }

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @Override
    public String getUsername() {
        return this.id;
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    // =================================================================================================



    public void updateNickname(String nickname){

        this.nickname = nickname;
    }

    public void updateGender(String gender){

        this.gender=gender;
    }
    public void updateAge(int age){

        this.age=age;
    }

    public void updatePwd(String password){

        this.password=password;
    }

    public void updateAccessToken(String token){
        this.accessToken = token;
    }

    public void updateRefreshToken(String token){
        this.refreshToken = token;
    }

    public void updateIsChecked(boolean isChecked){
        this.isChecked = isChecked;
    }

    public void updateCategory(List<Category> categories){
        this.categories.clear();
        this.categories.addAll(categories);
    }

}
