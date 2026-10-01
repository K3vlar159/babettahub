package com.example.babettahub.domain.listing;

import com.example.babettahub.domain.listing.dto.ListingRequest;
import com.example.babettahub.domain.listing.dto.ListingResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/listings")
@RequiredArgsConstructor
public class ListingController {

    private final ListingService listingService;

    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public ListingResponse create(@Valid @RequestBody ListingRequest request){
        return listingService.create(request);
    }

    @GetMapping("/{id}")
    public ListingResponse getById(@PathVariable Long id){
        return listingService.getById(id);
    }

    @GetMapping()
    public List<ListingResponse> getAllActive(){
        return listingService.getAllActive();
    }

    @PutMapping("/{id}")
    public ListingResponse update(@PathVariable Long id, @Valid @RequestBody ListingRequest request) {
        return listingService.update(id,request);
    }

    @PatchMapping("/{id}/archive")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void archive(@PathVariable Long id){
        listingService.archive(id);
    }

}
