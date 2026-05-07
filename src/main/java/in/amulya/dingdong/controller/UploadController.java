package in.amulya.dingdong.controller;

import in.amulya.dingdong.model.Product;
import in.amulya.dingdong.repository.ProductRepository;
import in.amulya.dingdong.service.FileUploadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
public class UploadController {

    @Autowired
    private FileUploadService service;

    @Autowired
    private ProductRepository repo;

    @PostMapping("/upload")
    public Product uploadFile(
            @RequestParam("file") MultipartFile file,
            @RequestParam("name") String name
    ) {

        String imageUrl = service.upload(file);

        Product product = new Product(name, imageUrl);

        return repo.save(product);
    }
}

