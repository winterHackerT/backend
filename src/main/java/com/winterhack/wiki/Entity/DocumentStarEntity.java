package com.winterhack.wiki.Entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class DocumentStarEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private long id;
  
  @Column(nullable = false, length = 500)
  private String documentTitle;

  @ManyToOne
  private UserEntity user;

}
