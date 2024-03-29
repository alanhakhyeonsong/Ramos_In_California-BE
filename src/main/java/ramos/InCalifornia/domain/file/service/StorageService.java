package ramos.InCalifornia.domain.file.service;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface StorageService {
    List<String> store(List<MultipartFile> files);

    void delete(String path);

    Boolean exists(String path);

    void deleteAll(List<String> paths);
}
