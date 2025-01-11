package com.surfer.codes.catalogue_service.domain;

import com.surfer.codes.catalogue_service.ApplicationProperties;
import jakarta.transaction.Transactional;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class ProductService {

    private final ProductRepository productRepository;
    private final ApplicationProperties properties;

    public ProductService(ProductRepository productRepository, ApplicationProperties applicationProperties) {
        this.productRepository = productRepository;
        this.properties = applicationProperties;
    }

    public PagedResult<Product> getProducts(int pageNo) {
        Sort sort = Sort.by("name").ascending();
        pageNo = pageNo <= 1 ? 0 : (pageNo - 1); // 1based idx to 0based idx
        Pageable pageable = PageRequest.of(pageNo, properties.pageSize(), sort);
        Page<ProductEntity> products = productRepository.findAll(pageable);
        return getProductPagedResult(products);
    }

    public Optional<Product> getProductByCode(String code) {
        Optional<ProductEntity> productEntity = productRepository.findByCode(code);
        return productEntity.map(this::mapToProduct);
    }

    private PagedResult<Product> getProductPagedResult(Page<ProductEntity> productEntityPage) {
        return new PagedResult<>(
                productEntityPage.getContent().stream().map(this::mapToProduct).toList(),
                productEntityPage.getTotalElements(),
                productEntityPage.getNumber() + 1,
                productEntityPage.getTotalPages(),
                productEntityPage.isFirst(),
                productEntityPage.isLast(),
                productEntityPage.hasNext(),
                productEntityPage.hasPrevious());
    }

    private Product mapToProduct(ProductEntity productEntity) {
        return new Product(
                productEntity.getName(),
                productEntity.getCode(),
                productEntity.getDescription(),
                productEntity.getImageUrl(),
                productEntity.getPrice());
    }
}
