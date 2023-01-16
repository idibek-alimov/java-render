package maryam.service.picture;

import maryam.models.picture.Picture;
import maryam.models.product.Article;
import maryam.models.product.Product;
import org.springframework.web.multipart.MultipartFile;
import java.util.List;

public interface PictureServiceInterface {
    public Picture addPicture(Picture picture);
    public List<Picture> addPictures(List<MultipartFile> pictures, Article article);
    public void removePicture(Picture picture);
    public void removePictures(List<Picture> pictures);

}
