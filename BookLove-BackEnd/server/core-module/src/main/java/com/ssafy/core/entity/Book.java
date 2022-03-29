package com.ssafy.core.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import static javax.persistence.FetchType.LAZY;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "book")
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long bookId;

    @Column(nullable = false, length = 2000)
    private String title;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "category_id")
    private Category category;

    @Column(nullable = false, length = 1000)
    private String description;

    @Column(nullable = false, length = 1000)
    private String author;

    @Column(nullable = false, length = 1000)
    private String pub_date;

    @Column(nullable = false, length = 200)
    private String isbn;

    @Column(nullable = false)
    private int priceSales;

    @Column(nullable = false)
    private int priceStandard;

    @Column(nullable = false, length = 2000)
    private String cover;

    @Column(nullable = false, length = 2000)
    private String link;

    @Column(nullable = false, length = 2000)
    private String category_name;

    @Column(nullable = false, length = 1000)
    private String publisher;

    @Column(nullable = false)
    private int salesPoint;

    @Column(nullable = false)
    private int customerReviewRank;
}
