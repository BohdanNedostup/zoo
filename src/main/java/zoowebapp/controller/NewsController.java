package zoowebapp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import zoowebapp.mapper.NewsMapper;
import zoowebapp.service.NewsService;

@Controller
@RequestMapping("/")
public class NewsController {

    @Autowired
    private NewsService newsService;

    @GetMapping("news")
    public String showNews(){
        return "news";
    }

    @GetMapping("news/{id}")
    public String showOneNews(@PathVariable Long id, Model model){
        model.addAttribute("news", NewsMapper.convertNewsToNewsDtoOneNews(newsService.findById(id)));
        return "oneNews";
    }
}
