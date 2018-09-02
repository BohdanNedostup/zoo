package zoowebapp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class GalleryController {

    @GetMapping("gallery")
    public String showGallery(){
        return "gallery";
    }
}
