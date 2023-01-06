package com.winterhack.wiki.Data.Document;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class ReadDocumentDTO {
  
  private String title;
  private String content;
  private LocalDateTime datetime;
  private String username;
  private String addr;

}
