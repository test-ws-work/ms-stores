package br.com.wswork.module.stores.services;

import br.com.wswork.module.stores.configs.BusinessException;
import br.com.wswork.module.stores.dtos.requests.CreateStoreDtoRequest;
import br.com.wswork.module.stores.dtos.responses.StoreDtoResponse;
import br.com.wswork.module.stores.entities.Store;
import br.com.wswork.module.stores.repositories.StoreRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.Collection;

@Service
public class StoreService {

    private static final Logger LOGGER = LogManager.getLogger(StoreService.class.getName());
    private final StoreRepository storeRepository;

    public StoreService(StoreRepository storeRepository) {
        this.storeRepository = storeRepository;
    }

    public ResponseEntity<StoreDtoResponse> create(@Valid final CreateStoreDtoRequest dto) {

        Store store = new Store(dto.getName(),
                  dto.getAddress(),
                  dto.getNumber(),
                  dto.getComplement(),
                  dto.getNeighbor(),
                  dto.getState(),
                  dto.getCity(),
                  dto.getCountry(),
                  dto.getPersonId());

        LOGGER.info("Saving new Store in  database...");
        Store newStore = storeRepository.save(store);
        LOGGER.info("Saved.");

        StoreDtoResponse storeResponse = storeResponse(newStore);

        return ResponseEntity.ok(storeResponse);
    }

    public ResponseEntity<Collection<StoreDtoResponse>> find(Long userId) {

        LOGGER.info("Searching Store by personId...");
        Collection<Store> stores = storeRepository.findAllByUserId(userId);
        LOGGER.info("Found.");

        if (stores.isEmpty()) {
            throw new BusinessException(HttpStatus.NOT_FOUND.value(),HttpStatus.NOT_FOUND.getReasonPhrase(), "Store not found.");
        }

        Collection<StoreDtoResponse> response = new ArrayList<>();

        for (Store store : stores) {
            StoreDtoResponse storeResponse = storeResponse(store);

            response.add(storeResponse);
        }

        return ResponseEntity.ok(response);
    }

    private static StoreDtoResponse storeResponse(Store store) {
        StoreDtoResponse storeResponse = new StoreDtoResponse();
        storeResponse.setAddress(store.getAddress());
        storeResponse.setCity(store.getCity());
        storeResponse.setComplement(store.getComplement());
        storeResponse.setCountry(store.getCountry());
        storeResponse.setId(store.getId());
        storeResponse.setName(store.getName());
        storeResponse.setNeighbor(store.getNeighbor());
        storeResponse.setNumber(store.getNumber());
        storeResponse.setPersonId(store.getUserId());
        storeResponse.setState(store.getState());

        return storeResponse;
    }
}