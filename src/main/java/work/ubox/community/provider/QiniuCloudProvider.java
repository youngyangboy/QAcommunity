package work.ubox.community.provider;

import com.alibaba.fastjson.JSON;
import com.qiniu.http.Response;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.Region;
import com.qiniu.storage.UploadManager;
import com.qiniu.storage.model.DefaultPutRet;
import com.qiniu.util.Auth;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import work.ubox.community.dto.FileDTO;
import work.ubox.community.exception.CustomizeErrorCode;
import work.ubox.community.exception.CustomizeException;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.UUID;


@Service
public class QiniuCloudProvider {

    public static final String url = "rajiouszu.hb-bkt.clouddn.com";

    //    @Value("${qiniu.accessKey}")
//    private String accessKey;
//
//    @Value("${qiniu.SecretKey}")
//    private String secretKey;
//    @Autowired
//    private FileDTO fileDTO;

    public FileDTO upload(MultipartFile file, String fileName) throws UnsupportedEncodingException {
        //构造一个带指定Region对象的配置类
        Configuration cfg = new Configuration(Region.huabei());
        //...其他参数参考类注释
        UploadManager uploadManager = new UploadManager(cfg);
        //...生成上传凭证，然后准备上传

        String bucket = "ubook";

        String generateFileName = "";
        FileDTO fileDTO = new FileDTO();



        String[] fileSplit = fileName.split("\\.");
        if (fileSplit.length > 1) {
            generateFileName = UUID.randomUUID().toString() + "." + fileSplit[fileSplit.length - 1];
            try {
                byte[] uploadBytes = file.getBytes();
                Auth auth = Auth.create("r0_DGhqoMxcpOW9mSdjRdBItS67KZUF0ypPWeIKt", "RmVssnm5-PCNxpi43L7SXW8Va11dtM4MOJK7nhXp");
                String uploadToken = auth.uploadToken(bucket);
                Response response = uploadManager.put(uploadBytes, generateFileName, uploadToken);
                DefaultPutRet puRet = JSON.parseObject(response.bodyString(), DefaultPutRet.class);

                String domainOfBucket = "rajiouszu.hb-bkt.clouddn.com";
                String encodedFileName = URLEncoder.encode(generateFileName, "utf-8").replace("+", "%20");
                String finalUrl = String.format("%s/%s", domainOfBucket, encodedFileName);


                fileDTO.setUrl(finalUrl);
                fileDTO.setSuccess(1);
                fileDTO.setMessage("存储成功");

            } catch (Exception e) {
                e.printStackTrace();
            }

        } else {
            throw new CustomizeException(CustomizeErrorCode.FILE_UPLOAD_FAIL);
        }
        return fileDTO;
    }


}
