package maryam.controller.product;

import lombok.RequiredArgsConstructor;
import maryam.models.product.Color;
import maryam.service.color.ColorService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path="/api/color")
@RequiredArgsConstructor
public class ColorController {
    private final ColorService colorService;

    @PostMapping(path = "/seller/create")
    public Color createColor(@RequestPart("color") Color color){
        return colorService.save(color);
    }

    @GetMapping(path = "/name/similar/{name}")
    public List<Color> similarNameColor(@PathVariable("name")String name){
        return colorService.getColorBySimilarName(name);
    }
}
