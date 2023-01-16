package maryam.service.picture;

import lombok.RequiredArgsConstructor;
import maryam.data.picture.PictureRepository;
import maryam.models.picture.Picture;
import maryam.models.product.Article;
import maryam.models.product.Product;
import maryam.storage.FileStorageService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import javax.transaction.Transactional;
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
            System.out.println("one");
            picname = storageService.save(pic);
            System.out.println("two");
            newPic = new Picture(picname);
            System.out.println("three");
            newPic.setArticle(article);
            System.out.println("four");
            pictureList.add(pictureRepository.save(newPic));
            System.out.println("five");
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
}
