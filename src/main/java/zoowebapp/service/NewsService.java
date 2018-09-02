package zoowebapp.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import zoowebapp.dto.filter.NewsAdminFilter;
import zoowebapp.entity.News;

import java.util.List;

public interface NewsService {

    void save(News news);

    void update(News news);

    void deleteById(Long id);

    List<News> findAll();

    News findById(Long id);

    Page<News> findByFilterByPage(NewsAdminFilter newsAdminFilter, Pageable pageable);

    News findByTitle(String title);

    Page<News> findByPage(Pageable pageable);
}
