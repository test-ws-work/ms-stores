package br.com.wswork.module.stores.controllers;

import br.com.wswork.module.stores.dtos.requests.CreateStoreDtoRequest;
import br.com.wswork.module.stores.dtos.responses.StoreDtoResponse;
import br.com.wswork.module.stores.services.StoreService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController()
@RequestMapping(path = "api/v1/stores", produces = MediaType.APPLICATION_JSON_VALUE)
public class StoreController {

    private final StoreService storeService;

    public StoreController(StoreService storeService) {
        this.storeService = storeService;
    }

    @PostMapping("{userId}")
    public ResponseEntity<StoreDtoResponse> create(
            @PathVariable(name = "userId") final Long userId,
            @RequestBody(required = true) final CreateStoreDtoRequest request) {

        return storeService.create(userId, request);
    }

    @GetMapping("by-user/{userId}")
    public ResponseEntity<Collection<StoreDtoResponse>> find(
            @PathVariable(name = "userId", required = true) final Long userId) {

        return storeService.find(userId);
    }

    @GetMapping("by-store/{userId}")
    public ResponseEntity<StoreDtoResponse> findById(
            @PathVariable(name = "userId", required = true) final Long userId,
            @RequestParam(name = "storeId", required = true) final Long storeId) {

        return storeService.findById(storeId, userId);
    }

    @DeleteMapping("{storeId}")
    public ResponseEntity<?> delete(
            @PathVariable(name = "storeId", required = true) final Long storeId) {
        storeService.delete(storeId);
        return ResponseEntity.noContent().build();
    }
}
