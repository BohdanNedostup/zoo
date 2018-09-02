package zoowebapp.mapper;

import org.modelmapper.ModelMapper;
import zoowebapp.dto.NewsDtoAdmin;
import zoowebapp.dto.NewsDtoBoard;
import zoowebapp.dto.NewsDtoOneNews;
import zoowebapp.dto.NewsDtoSave;
import zoowebapp.entity.News;

public interface NewsMapper {

    static NewsDtoAdmin convertNewsToNewsDtoAdmin(News news){
        return new ModelMapper().map(news, NewsDtoAdmin.class);
    }

    static News convertNewsDtoAdminToNews(NewsDtoAdmin newsDtoAdmin){
        return new ModelMapper().map(newsDtoAdmin, News.class);
    }

    static News convertNewsDtoSaveToNews(NewsDtoSave newsDtoSave){
        return new ModelMapper().map(newsDtoSave, News.class);
    }

    static NewsDtoBoard convertNewsToNewsDtoBoard(News news){
        return new ModelMapper().map(news, NewsDtoBoard.class);
    }

    static NewsDtoOneNews convertNewsToNewsDtoOneNews(News news){
        NewsDtoOneNews newsDtoOneNews = new ModelMapper().map(news, NewsDtoOneNews.class);
        return newsDtoOneNews;
    }
}
