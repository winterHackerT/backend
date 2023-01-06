package com.winterhack.wiki.Entity;

import javax.persistence.*;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter

public class FileEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    @Column(nullable = false, columnDefinition = "TEXT")
    private String fileSelect;

    @Column (nullable = false, length = 200)
    private String fileName;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String content;

    @Column(nullable = true, length = 200)
    private String licence;

    @Column(nullable = false, length = 200)
    private String category;

    @Column(columnDefinition="TEXT")
    private String summary;
}
