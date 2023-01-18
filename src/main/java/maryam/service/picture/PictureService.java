package maryam.service.picture;

import lombok.RequiredArgsConstructor;
import maryam.data.picture.PictureRepository;
import maryam.models.picture.Picture;
import maryam.models.product.Article;
import maryam.models.product.Product;
import maryam.storage.FileStorageService;
import org.apache.tomcat.util.codec.binary.Base64;
import org.apache.tomcat.util.http.fileupload.ByteArrayOutputStream;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import javax.transaction.Transactional;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service @RequiredArgsConstructor @Transactional
public class PictureService implements PictureServiceInterface{
    private final FileStorageService storageService;
    private final PictureRepository pictureRepository;

    @Override
    public Picture addPicture(Picture picture) {
        return pictureRepository.save(picture);
    }

    @Override
    public List<Picture> addPictures(List<MultipartFile> pictures, Article article) {
        String picname;
        List<Picture> pictureList = new ArrayList<>();
        Picture newPic;
        for(MultipartFile pic:pictures){
            //picname = storageService.save(pic);
            /////////
            newPic = new Picture(pic.getOriginalFilename());
            //////////
            //newPic = new Picture(picname);
            newPic.setArticle(article);
            pictureList.add(pictureRepository.save(newPic));
          }
        return pictureList;
    }

    @Override
    public void removePicture(Picture picture) {
        pictureRepository.delete(picture);
    }

    @Override
    public void removePictures(List<Picture> pictures) {
        pictureRepository.deleteAll(pictures);
    }

    public static String encodeToString(BufferedImage image, String type) {
        String imageString = null;
        ByteArrayOutputStream bos = new ByteArrayOutputStream();

        try {
            ImageIO.write(image, type, bos);
            byte[] imageBytes = bos.toByteArray();


            imageString = Base64.encodeBase64(imageBytes).toString();

            bos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return imageString;
    }
}




