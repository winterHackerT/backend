package com.winterhack.wiki.Data;

import lombok.Data;

@Data
public class ResultDTO {
  
  private String message;
  private Boolean success;
  private Object data;

  public ResultDTO(String message, Boolean success) {
    this.message = message;
    this.success = success;
    this.data = null;
  }

  public ResultDTO(String message, Boolean success, Object data) {
    this.message = message;
    this.success = success;
    this.data = data;
  }

}
