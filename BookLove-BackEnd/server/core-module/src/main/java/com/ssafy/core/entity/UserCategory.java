package com.ssafy.core.entity;


import lombok.*;
import javax.persistence.*;


@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "user_category"

)
public class UserCategory extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long userCategoryId;

    //외래키
    @ManyToOne(targetEntity = User.class)
    @JoinColumn(name = "user_id")
    private User user;

    // 유저 선호 카테고리 뎁스
    @Column(nullable = false, length = 64)
    private String categoryName;

    public void updateCategoryName(String categoryName){

        this.categoryName = categoryName;
    }

}
