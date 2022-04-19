package work.ubox.community.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import work.ubox.community.dto.FileDTO;
import work.ubox.community.provider.QiniuCloudProvider;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;


@Controller
public class FileController {

    @Autowired
    private QiniuCloudProvider qiniuCloudProvider;

    @ResponseBody
    @PostMapping("/file/upload")
    public FileDTO upload(HttpServletRequest request) throws UnsupportedEncodingException {

        MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
        MultipartFile file = multipartRequest.getFile("editormd-image-file");

//        FileDTO fileDTO1 = qiniuCloudProvider.upload(file, file.getOriginalFilename());

//        FileDTO fileDTO = new FileDTO();
//        fileDTO.setSuccess(1);
////        fileDTO.setUrl("/images/logos/vi.png");
//        fileDTO.setUrl(QiniuCloudProvider.url+);

        FileDTO fileDTO = qiniuCloudProvider.upload(file, file.getOriginalFilename());

        return fileDTO;
    }
}
