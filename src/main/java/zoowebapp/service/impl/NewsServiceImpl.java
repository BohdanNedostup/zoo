package zoowebapp.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import zoowebapp.dto.filter.NewsAdminFilter;
import zoowebapp.entity.News;
import zoowebapp.repository.NewsRepository;
import zoowebapp.service.NewsService;
import zoowebapp.service.utils.DateUtils;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.Calendar;
import java.util.List;

@Service
public class NewsServiceImpl implements NewsService {

    @Autowired
    private NewsRepository newsRepository;

    @Override
    public void save(News news) {
        newsRepository.save(news);
    }

    @Override
    public void update(News news) {
        newsRepository.save(news);
    }

    @Override
    public void deleteById(Long id) {
        newsRepository.deleteById(id);
    }

    @Override
    public List<News> findAll() {
        return newsRepository.findAll();
    }

    @Override
    public News findById(Long id) {
        return newsRepository.getOne(id);
    }

    @Override
    public Page<News> findByFilterByPage(NewsAdminFilter newsAdminFilter, Pageable pageable) {
        return newsRepository.findAll(getSpecification(newsAdminFilter), PageRequest.of(pageable.getPageNumber(), pageable.getPageSize()));
    }

    private Specification getSpecification(NewsAdminFilter newsAdminFilter){
        return new Specification() {
            @Override
            public Predicate toPredicate(Root root, CriteriaQuery criteriaQuery, CriteriaBuilder criteriaBuilder) {

                Predicate predicateTitle = criteriaBuilder.like(root.get("title"), "%" + newsAdminFilter.getTitle() + "%");

                Calendar calendarMin = Calendar.getInstance();
                Calendar calendarMax = Calendar.getInstance();
                calendarMin.set(1900, 0, 1);
                calendarMax.set(3000, 0, 1);

                newsAdminFilter.setCreatedAtTo(DateUtils.plusOneDay(newsAdminFilter.getCreatedAtTo()));

                Predicate predicateCreatedAt = newsAdminFilter.getCreatedAtFrom() == null ?
                        newsAdminFilter.getCreatedAtTo() == null ?
                                criteriaBuilder.between(root.get("createdAt"), calendarMin.getTime(), calendarMax.getTime()) :
                                criteriaBuilder.lessThanOrEqualTo(root.get("createdAt"), newsAdminFilter.getCreatedAtTo()) :
                        newsAdminFilter.getCreatedAtTo() == null ?
                                criteriaBuilder.greaterThanOrEqualTo(root.get("createdAt"), newsAdminFilter.getCreatedAtFrom()) :
                                criteriaBuilder.between(root.get("createdAt"), newsAdminFilter.getCreatedAtFrom(), newsAdminFilter.getCreatedAtTo());

                switch(newsAdminFilter.getSortBy()) {
                    case "id": {
                        criteriaQuery.orderBy(newsAdminFilter.getAscOrDesc().equals("asc") ? criteriaBuilder.asc(root.get("id")) : criteriaBuilder.desc(root.get("id")));
                        break;
                    }
                    case "title": {
                        criteriaQuery.orderBy(newsAdminFilter.getAscOrDesc().equals("asc") ? criteriaBuilder.asc(root.get("title")) : criteriaBuilder.desc(root.get("title")));
                        break;
                    }
                    case "createdAt": {
                        criteriaQuery.orderBy(newsAdminFilter.getAscOrDesc().equals("asc") ? criteriaBuilder.asc(root.get("createdAt")) : criteriaBuilder.desc(root.get("createdAt")));
                        break;
                    }
                }

                return criteriaBuilder.and(predicateTitle, predicateCreatedAt);
            }
        };
    }

    @Override
    public News findByTitle(String title) {
        return newsRepository.findByTitle(title);
    }

    @Override
    public Page<News> findByPage(Pageable pageable) {
        return newsRepository.findAll(PageRequest.of(pageable.getPageNumber(), pageable.getPageSize()));
    }
}
