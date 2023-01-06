package com.winterhack.wiki.Entity;

import java.time.LocalDateTime;

import javax.persistence.*;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class DocumentEntity {
  
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;
  
  @Column(nullable = false, length = 500)
  private String title;
  
  @Column(nullable = false, columnDefinition = "TEXT")
  private String content;
  
  @Column(nullable = false)
  private LocalDateTime datetime;
  
  @ManyToOne
  private UserEntity user;
  
  @Column(nullable = true)
  private String addr;

  @Column(nullable = false, length = 1000)
  private String working;

  @Column(nullable = false, length = 1000)
  private String message;

}
