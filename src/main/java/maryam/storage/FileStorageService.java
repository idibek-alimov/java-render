package maryam.storage;

import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.FileSystemUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Random;
import java.util.stream.Stream;

@Service
public class FileStorageService implements FileStorageServiceInterface {

    private final Path root = Paths.get("uploads");

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
        Random random = new Random();

        String extension = file.getOriginalFilename().split("\\.")[1];
        String new_name = String.format("%s%s",System.currentTimeMillis(),random.nextInt(100000)+"."+extension);
        try{
            System.out.println("before saving image");
            System.out.println(file.getInputStream());
            ////////
            System.out.println("BEfore the new stuff line 1");
            BufferedImage bi =(BufferedImage) file.getResource();  // retrieve image
            System.out.println("BTNS line 2");
            File outputfile = new File(new_name);
            System.out.println("BTNS line 3");
            ImageIO.write(bi, "jpg", outputfile);
            System.out.println("AFTer the new stuff");
            ///////

            System.out.println(this.root.resolve(new_name));
            Files.copy(file.getInputStream(),this.root.resolve(new_name));
            System.out.println("after saving image");
            return new_name;
        }catch (Exception e){
            System.out.println("something vent wrong");
            System.out.println(e);
            throw new RuntimeException("Cloud not store the file.Error:"+ e.getMessage());
        }

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
}
