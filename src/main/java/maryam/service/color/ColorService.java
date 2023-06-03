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
        try {
        System.out.println(31);
        Optional<Color> optionalColor;
        System.out.println(32);
        System.out.println(color);

        optionalColor = colorRepository.findById(color.getId());

        System.out.println(33);
        if (optionalColor.isPresent()){
            System.out.println(34);
            article.setColor(optionalColor.get());
            System.out.println(35);
            return optionalColor.get();
        }
        else{
            return null;
        }
        }
        catch (Exception e){
            System.out.println(e);
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
