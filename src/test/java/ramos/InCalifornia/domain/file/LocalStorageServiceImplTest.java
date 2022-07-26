package ramos.InCalifornia.domain.file;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;
import ramos.InCalifornia.domain.file.service.LocalStorageServiceImpl;
import ramos.InCalifornia.domain.file.service.StorageService;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class LocalStorageServiceImplTest {

    private StorageService storageService = new LocalStorageServiceImpl();
    private List<MultipartFile> files;
    private String path;

    @BeforeEach
    void init() {
        files = new ArrayList<>();
        MockMultipartFile file = new MockMultipartFile("files", "file_name.txt",
                        MediaType.TEXT_PLAIN_VALUE, "contents".getBytes());
        files.add(file);

        path = storageService.store(List.of(file)).get(0);
    }

    @DisplayName("multi part storage에 저장, 삭제")
    @Test
    public void storeTest() {
        //when
        List<String> newFiles = storageService.store(files);

        //then
        assertThat(files.size()).isEqualTo(newFiles.size());
        assertThat(path).isNotEqualTo(newFiles.get(0));

        storageService.delete(path);
        storageService.deleteAll(newFiles);
    }
}
