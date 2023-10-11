package br.com.wswork.module.stores.services;

import br.com.wswork.module.stores.configs.BusinessException;
import br.com.wswork.module.stores.constants.StoreStatusEnum;
import br.com.wswork.module.stores.dtos.requests.CreateProductDtoRequest;
import br.com.wswork.module.stores.dtos.responses.ProductDtoResponse;
import br.com.wswork.module.stores.entities.Product;
import br.com.wswork.module.stores.entities.Store;
import br.com.wswork.module.stores.repositories.ProductRepository;
import br.com.wswork.module.stores.repositories.StoreRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class ProductService {

    private static final Logger LOGGER = LogManager.getLogger(StoreService.class.getName());

    private final ProductRepository productRepository;
    private final StoreRepository storeRepository;

    public ProductService(final ProductRepository productRepository, final StoreRepository storeRepository) {
        this.productRepository = productRepository;
        this.storeRepository = storeRepository;
    }
    public ResponseEntity<ProductDtoResponse> create(final CreateProductDtoRequest dto, final Long userId) {

        LOGGER.info("Searching store by id...");
        Store store = storeRepository.findByIdAndUserIdAndStatus(dto.getStoreId(), userId, StoreStatusEnum.ACTIVE)
                .orElseThrow(() -> new BusinessException(HttpStatus.NOT_FOUND.value(), HttpStatus.NOT_FOUND.getReasonPhrase(), "Store not found."));
        LOGGER.info("Found.");

        Product newProduct = new Product();
        newProduct.setCategory(dto.getCategory());
        newProduct.setName(dto.getName());
        newProduct.setDescription(dto.getDescription());
        newProduct.setPrice(dto.getPrice());
        newProduct.setStock(dto.getStock());
        newProduct.setStore(store);

        LOGGER.info("Saving product in database...");
        Product product = productRepository.save(newProduct);
        LOGGER.info("Saved.");

        ProductDtoResponse response = productResponse(product);

        return ResponseEntity.ok(response);
    }

    private static ProductDtoResponse productResponse(final Product product) {
        ProductDtoResponse response = new ProductDtoResponse();
        response.setCategory(product.getCategory());
        response.setDescription(product.getDescription());
        response.setId(product.getId());
        response.setPrice(product.getPrice());
        response.setStock(product.getStock());
        response.setName(product.getName());
        response.setStore(product.getStore());

        return response;
    }
}
