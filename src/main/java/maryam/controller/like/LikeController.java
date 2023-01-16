package maryam.controller.like;

import lombok.RequiredArgsConstructor;
import maryam.models.like.Like;
import maryam.service.like.LikeService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/api/like")
@RequiredArgsConstructor
public class LikeController {

    public final LikeService likeService;

    @GetMapping(path = "/{id}")
    public Like likeProduct(@PathVariable("id") Long id){
        return likeService.like_article(id);
    }
}