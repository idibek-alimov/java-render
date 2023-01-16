package maryam.service.color;

import lombok.RequiredArgsConstructor;
import maryam.data.product.ColorRepository;
import maryam.models.product.Article;
import maryam.models.product.Color;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@Service @RequiredArgsConstructor @Transactional
public class ColorService {

    private final ColorRepository colorRepository;
    public Color addColor(Color color, Article article){
        System.out.println("inside add color function");
        Optional<Color> optionalColor = colorRepository.findByName(color.getName());
        System.out.println("1");
          Color newColor;
        System.out.println("2");
          if(!optionalColor.isPresent()){
              System.out.println("3");
              newColor = colorRepository.save(color);
              System.out.println("4");
          }else{
              newColor = optionalColor.get();
          }
        System.out.println("5");
//        Color newColor = color;
//        if(!colorRepository.findByName(color.getName()).isPresent()){
//            newColor = colorRepository.save(color);
//        }
        article.setColor(newColor);
        System.out.println("6");
        return newColor;
    }
}
