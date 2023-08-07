package maryam.service.order;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import maryam.data.order.PPPictureRepository;
import maryam.data.order.PickPointRepository;
import maryam.models.order.PPPicture;
import maryam.models.order.PickPoint;
import maryam.storage.FileStorageService;
import maryam.storage.StorageConfig;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class PickPointService {
    private final PickPointRepository pickPointRepository;
    private final FileStorageService fileStorageService;
    private final PPPictureRepository ppPictureRepository;

    public List<PickPoint> getAllPickPoints(){
        return pickPointRepository.findAll();
    }
    public PickPoint createPickPoint(PickPoint pickPoint, List<MultipartFile> pictures){
        pickPoint = pickPointRepository.save(pickPoint);
        String pictureName;
        PPPicture newPic;
        List<PPPicture> pictureList = new ArrayList<>();
        for(MultipartFile pic:pictures){
            pictureName = fileStorageService.save(pic);
            newPic = ppPictureRepository.save(new PPPicture("https://s3.timeweb.com/448bb2be-maryambucket/"+pictureName));
            newPic.setPickPoint(pickPoint);
            pictureList.add(newPic);
        }
        pickPoint.setPictures(pictureList);
        return pickPoint;
    }
}
