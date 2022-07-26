package ramos.InCalifornia.domain.file.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class LocalStorageServiceImpl implements StorageService {

    @Value("${file.dir}")
    private String fileDir;

    public List<String> store(List<MultipartFile> files) {
        return files.stream().map(multipartFile -> {
            String path = fileDir + multipartFile.getOriginalFilename();
            File file = new File(path);
            try {
                multipartFile.transferTo(file);
            } catch (IOException e) {
                throw new RuntimeException(); // INTERNAL_SERVER_ERROR;
            }
            return path;
        }).collect(Collectors.toList());
    }

    public void setFileDir(String fileDir) {
        this.fileDir = fileDir;
    }

    @Override
    public void delete(String path) {
        if (exists(path)) {
            new File(path).delete();
        }
    }

    @Override
    public Boolean exists(String path) {
        return new File(path).exists();
    }

    @Override
    public void deleteAll(List<String> paths) {
        for (String path : paths) {
            this.delete(path);
        }
    }
}
