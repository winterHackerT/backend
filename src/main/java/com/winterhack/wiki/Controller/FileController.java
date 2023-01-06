import java.io.File;
import java.nio.file.Files;
import java.security.Principal;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.hibernate.annotations.common.util.impl.LoggerFactory;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import com.winterhack.wiki.Data.ResultDTO;
import com.winterhack.wiki.Data.File.FileDTO;
import com.winterhack.wiki.Entity.UserEntity;
import com.winterhack.wiki.Exception.User.ReadUserException;
import com.winterhack.wiki.Service.FileService;
import com.winterhack.wiki.Service.UserService;

import io.jsonwebtoken.io.IOException;

@RestController
public class FileController{
    @Autowired
    private FileService fileService;

    @Autowired
    private UserService userService;
    /*
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Resource(name = "fileService")

    @PostMapping("/Registor/action")
    public void boardRegisterAction(MultipartHttpServletRequest multiRequest){
        try {
            fileService.uploadFile=(multiRequest);
        } catch(Exception e){
            if(logger.isErrorEnabled()){
                logger.error("#Exception Message : {}", e.getMessage());
            }
        }
    }
}
    */
    @RequestMapping(method = RequestMethod.POST, path = "/files")
    public ResultDTO post(HttpServletRequest request, Principal principal, @RequestBody @Valid FileDTO file){
        UserEntity user = null;

        if(principal !=null){
            try{
                user = userService.readUser(principal.getName());
            }
            catch (ReadUserException error){
                return new ResultDTO("유저인증 오?류"+ error.getMessage(), false);
            }
        }
        fileService.postFile(
            file.getTitle(),
            user,
            request.getRemoteAddr(),
            file.getMessage();
            
        );
    }
   @GetMapping(value = "fileupload")
   public ModelAndView uploadView(){
    ModelAndView mav = new ModelAndView("fileupload");
    return mav;
   }
   public String saveFile(@RequestParam String itemName, @RequestParam MultipartFile file) throws IOException{
    if(!file.isEmpty()){
        file.transferTo(new File(file.getOriginalFilename(), itemName));
    }
   }*/
}