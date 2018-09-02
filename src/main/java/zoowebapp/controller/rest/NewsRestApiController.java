package zoowebapp.controller.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import zoowebapp.dto.NewsDtoAdmin;
import zoowebapp.dto.NewsDtoSave;
import zoowebapp.dto.filter.NewsAdminFilter;
import zoowebapp.entity.News;
import zoowebapp.mapper.NewsMapper;
import zoowebapp.service.CloudinaryService;
import zoowebapp.service.NewsService;
import zoowebapp.service.utils.DateUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/manage/news")
public class NewsRestApiController {

    @Autowired
    private NewsService newsService;

    @Autowired
    private CloudinaryService cloudinaryService;

    @GetMapping("")
    public Page<NewsDtoAdmin> getAllNews(@PageableDefault Pageable pageable,
                                         @RequestParam String title,
                                         @RequestParam String createdAtFrom,
                                         @RequestParam String createdAtTo,
                                         @RequestParam String sortBy,
                                         @RequestParam String ascOrDesc){
        NewsAdminFilter newsAdminFilter = new NewsAdminFilter(title, DateUtils.convertStringToDate(createdAtFrom),
                DateUtils.convertStringToDate(createdAtTo), sortBy, ascOrDesc);
        Page<News> newsPage = newsService.findByFilterByPage(newsAdminFilter, pageable);
        List<NewsDtoAdmin> news = new ArrayList<>();
        newsPage.forEach(news1 -> news.add(NewsMapper.convertNewsToNewsDtoAdmin(news1)));
        return new PageImpl<>(news, pageable, newsPage.getTotalElements());
    }

    @DeleteMapping("")
    public ResponseEntity<Void> deleteNewsById(@RequestParam(name = "id") Long id){
        newsService.deleteById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping("")
    public ResponseEntity<Void> updateAnimal(@RequestBody NewsDtoAdmin newsDtoAdmin) {
        News news = newsService.findById(newsDtoAdmin.getId());
        if (news != null) {
            news = NewsMapper.convertNewsDtoAdminToNews(newsDtoAdmin);
            newsService.update(news);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/image/{id}")
    public ResponseEntity<Void> uploadImage(@PathVariable Long id,
                                             @RequestParam("image") MultipartFile multipartFile) throws IOException {
        String imageUrl = cloudinaryService.uploadFile(multipartFile, "news/");
        News news = newsService.findById(id);
        news.setImageUrl(imageUrl);
        newsService.update(news);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("")
    public Long saveNewNews(@RequestBody NewsDtoSave newsDtoSave){
        if (newsService.findByTitle(newsDtoSave.getTitle()) != null){
            return -1L;
        }
        News news = NewsMapper.convertNewsDtoSaveToNews(newsDtoSave);
        newsService.save(news);
        return newsService.findByTitle(news.getTitle()).getId();
    }
}
