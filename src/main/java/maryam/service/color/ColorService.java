package maryam.service.color;

import lombok.RequiredArgsConstructor;
import maryam.data.product.ColorRepository;
import maryam.models.product.Article;
import maryam.models.product.Color;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service @RequiredArgsConstructor @Transactional
public class ColorService {

    private final ColorRepository colorRepository;
    public Color addColor(Color color, Article article){
        if(color!=null && color.getId() !=null){
            Optional<Color> optionalColor = colorRepository.findById(color.getId());
            if(optionalColor.isPresent()){
                article.setColor(optionalColor.get());
                return optionalColor.get();
            }
        }
        return null;
    }
    public Color updateColor(Color color,Article article){
        return addColor(color,article);
    }
    public Color save(Color color){
        Optional<Color> optionalColor = colorRepository.findByName(color.getName());
        if (optionalColor.isPresent()){
            return optionalColor.get();
        }
        else {
            return colorRepository.save(color);
        }
    }
//    public List<Color> getAllColors(){
//        return colorRepository.findAll();
//    }
    public List<Color> getColorBySimilarName(String name){
        return colorRepository.findBySimilarName(name);
    }
}
