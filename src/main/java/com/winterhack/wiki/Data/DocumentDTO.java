package com.winterhack.wiki.Data;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class DocumentDTO {
  
  private Long id;
  private String title;
  private String content;
  private LocalDateTime datetime;
  private UserDTO user;
  private String addr;

}
