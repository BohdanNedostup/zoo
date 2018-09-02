package zoowebapp.controller.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import zoowebapp.dto.NewsDtoBoard;
import zoowebapp.entity.News;
import zoowebapp.mapper.NewsMapper;
import zoowebapp.service.NewsService;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/news")
public class NewsBoardRestApiController {

    @Autowired
    private NewsService newsService;

    @GetMapping("")
    public Page<NewsDtoBoard> getAllNews(Pageable pageable){
        Page<News> newsList = newsService.findByPage(pageable);
        List<NewsDtoBoard> boardList = new ArrayList<>();
        newsList.forEach(news -> boardList.add(NewsMapper.convertNewsToNewsDtoBoard(news)));
        return new PageImpl<>(boardList, pageable, newsList.getTotalElements());
    }
}
