package zoowebapp.service;

import zoowebapp.entity.UploadFile;

public interface UploadFileService {

    void save(UploadFile uploadFile);

    UploadFile findOneById(Long id);
}
