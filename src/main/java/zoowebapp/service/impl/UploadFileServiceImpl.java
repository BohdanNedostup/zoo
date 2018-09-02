package zoowebapp.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import zoowebapp.entity.UploadFile;
import zoowebapp.repository.UploadFileRepository;
import zoowebapp.service.UploadFileService;

public class UploadFileServiceImpl implements UploadFileService {

    @Autowired
    private UploadFileRepository uploadFileRepository;

    @Override
    public void save(UploadFile uploadFile) {
        uploadFileRepository.save(uploadFile);
    }

    @Override
    public UploadFile findOneById(Long id) {
        return uploadFileRepository.getOne(id);
    }
}
