package com.winterhack.wiki.Service;

import java.util.Optional;
import java.util.Locale.Category;

import org.apache.tomcat.util.http.fileupload.FileUpload;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.winterhack.wiki.Data.UserDTO;
import com.winterhack.wiki.Entity.FileEntity;
import com.winterhack.wiki.Entity.UserEntity;
import com.winterhack.wiki.Entity.FileEntity;
import com.winterhack.wiki.Repository.FileRepository;
import com.winterhack.wiki.Repository.UserRepository;

import antlr.collections.List;
import net.bytebuddy.build.Plugin.Engine.Summary;

@Service

public class FileService {
    @Autowired
    private FileRepository fileRepository;

    @Autowired
    private UserRepository userRepository;

    public void addNewFile(String fileSelect, String fileName, String content, UserDTO userDTO, String licence, String category, String summary){
        FileEntity fileEntity = new FileEntity();

        fileEntity.setFileSelect(fileSelect);
        fileEntity.setFileName(fileName);
        fileEntity.setContent(content);
        fileEntity.setLicence(licence);
        fileEntity.setCategory(category);
        fileEntity.setSummary(summary);


    fileRepository.save(fileEntity);
    
    }

    public Optional<FileEntity> getFile(String fileName){
        List<FileEntity> list = fileRepository.findByUsername(fileName); 
  
    }
    public void deleteFile(String fileName, UserDTO user, String addr){
        addNewFile(addr, fileName, addr, user, addr, fileName, addr);
    }

}
