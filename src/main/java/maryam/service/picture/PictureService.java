package maryam.service.picture;

import lombok.RequiredArgsConstructor;
import maryam.data.picture.PictureRepository;
import maryam.models.picture.Picture;
import maryam.models.product.Article;
import maryam.models.product.Product;
import maryam.storage.FileStorageService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import jakarta.transaction.Transactional;
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
            picname = storageService.save(pic);
            newPic = new Picture(picname);
            newPic.setArticle(article);
            pictureList.add(pictureRepository.save(newPic));
          }
        return pictureList;
    }

    public void removePictures(List<String> leftoverPictures,Article article){
        List<Picture> pictureList = new ArrayList<>();
        List<Picture> deletePictureList = new ArrayList<>();
        for(Picture picture:article.getPictures()){
            if(leftoverPictures.contains(picture.getName())){
               deletePictureList.add(picture);
            }
            else {
                pictureList.add(picture);
            }
        }
        pictureRepository.deleteAll(deletePictureList);
        article.setPictures(pictureList);
    }
    public void removePicturesFromArticle(List<Long> picIdList,Article article){
        List<Picture> deletePics = new ArrayList<>();
        List<Picture> picList = new ArrayList<>();
        System.out.println(picIdList);
        for (Picture picture:article.getPictures()){
            System.out.println(picture.getId());
            System.out.println(!picIdList.contains(picture.getId()));
            if (!picIdList.contains(picture.getId())){
                deletePics.add(picture);
            }
            else {
                picList.add(picture);
            }
        }
        System.out.println(deletePics.size());
        pictureRepository.deleteAll(deletePics);
        if(picIdList.size()==0){
            article.setStatus(Article.Status.NoPicture);
        }
        article.setPictures(picList);

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
