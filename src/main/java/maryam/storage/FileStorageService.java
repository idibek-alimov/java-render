package maryam.storage;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.PutObjectRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.FileSystemUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Random;
import java.util.stream.Stream;

@Service
@Slf4j
@RequiredArgsConstructor
public class FileStorageService implements FileStorageServiceInterface {

    @Value("${application.bucket.name}")
    private String bucketName;

    @Autowired
    private AmazonS3 s3Client;
    private final Path root = Paths.get("uploads");
//    private final Path user_pic_path = Paths.get("user_pics");

    @Override
    public void init() {
        try{
            Files.createDirectory(root);
        }catch (IOException e){
            throw new RuntimeException("Cloud not initialize folder for upload!");
        }
    }

    @Override
    public String save(MultipartFile file) {
        //this.init();
        Random random = new Random();
        log.info("bucket Name");
        log.info(bucketName);
        String extension = file.getOriginalFilename().split("\\.")[1];
        String new_name = String.format("%s%s",System.currentTimeMillis(),random.nextInt(100000)+"."+extension);
        try{
            ////////
//            System.out.println("BEfore the new stuff line 1");
//            BufferedImage bi =(BufferedImage) file.getResource();  // retrieve image
//            System.out.println("BTNS line 2");
//            File outputfile = new File(new_name);
//            System.out.println("BTNS line 3");
//            ImageIO.write(bi, "jpg", outputfile);
//            System.out.println("AFTer the new stuff");
            ///////
//            Files.copy(file.getInputStream(),this.root.resolve(new_name));
            //            System.out.println("after saving image");
//            new_name = file.getOriginalFilename();
            File fileObj = convertMultiPartFileToFile(file);
            s3Client.putObject(new PutObjectRequest(bucketName,new_name,fileObj));
            fileObj.delete();
            return new_name;
        }catch (Exception e){
            System.out.println(e);
            //this.init();
            throw new RuntimeException("Cloud not store the file.Errorrr:"+ e.getMessage());
        }

    }

    public String deleteFile(String fileName) {
        s3Client.deleteObject(bucketName, fileName);
        return fileName + " removed ...";
    }
    @Override
    public Resource load(String filename) {
        try{
            Path file = root.resolve(filename);
            Resource resource = new UrlResource(file.toUri());

            if(resource.exists() || resource.isReadable()){
                return resource;
            }  else {
                throw new RuntimeException("Cloud not read the file!");
            }
        } catch (MalformedURLException e){
            throw new RuntimeException("Error:"+e.getMessage());
        }
    }

    @Override
    public void deleteAll() {
        FileSystemUtils.deleteRecursively(root.toFile());
    }

    @Override
    public Stream<Path> loadAll() {
        try{
            return Files.walk(this.root,1).filter(path->!path.equals(this.root)).map(this.root::relativize);
        }catch(IOException e){
            throw new RuntimeException("Cloud not load the files");
        }
    }
    private File convertMultiPartFileToFile(MultipartFile file) {
        File convertedFile = new File(file.getOriginalFilename());
        try (FileOutputStream fos = new FileOutputStream(convertedFile)) {
            fos.write(file.getBytes());
        } catch (IOException e) {
            log.error("Error converting multipartFile to file", e);
        }
        return convertedFile;
    }
}
