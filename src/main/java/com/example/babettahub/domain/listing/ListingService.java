package com.example.babettahub.domain.listing;

import com.example.babettahub.common.ResourceNotFoundException;
import com.example.babettahub.domain.listing.dto.ListingRequest;
import com.example.babettahub.domain.listing.dto.ListingResponse;
import com.example.babettahub.domain.user.User;
import com.example.babettahub.domain.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.jspecify.annotations.NonNull;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class ListingService {

    private final ListingRepository listingRepository;
    private final UserRepository userRepository;

    public ListingResponse create(ListingRequest request){

        if(request.contactEmail() == null && request.contactNumber() == null){
            throw new IllegalArgumentException("Musíš zadať aspoň email alebo telefónne číslo");
        }

        User user = userRepository.findById(request.userId())
                .orElseThrow(() -> new ResourceNotFoundException("User neexistuje"));

        Listing listing = new Listing();
        listing.setUser(user);
        listing.setTitle(request.title());
        listing.setDescription(request.description());
        listing.setPrice(request.price());
        listing.setStatus(ListingStatus.ACTIVE);
        listing.setContactEmail(request.contactEmail());
        listing.setContactNumber(request.contactNumber());
        listing.setCreatedAt(LocalDateTime.now());

        Listing savedListing = listingRepository.save(listing);

        return mapToResponse(savedListing);
    }

    public ListingResponse getById(Long listingId){

        Listing listing = listingRepository.findById(listingId).orElseThrow(() -> new ResourceNotFoundException("Inzerát s týmto ID neexistuje"));

        return mapToResponse(listing);
    }

    public List<ListingResponse> getAllActive(){

        List<Listing> activeListings = listingRepository.findByStatus(ListingStatus.ACTIVE);

        return activeListings.stream()
                .map(ListingService::mapToResponse)
                .toList();
    }

    public ListingResponse update(Long id, ListingRequest request){
        Listing listing = listingRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Inzerát s týmto ID neexistuje"));

        if(request.contactEmail() == null && request.contactNumber() == null){
            throw new IllegalArgumentException("Musíš zadať aspoň email alebo telefónne číslo");
        }

        listing.setTitle(request.title());
        listing.setDescription(request.description());
        listing.setPrice(request.price());
        listing.setContactNumber(request.contactNumber());
        listing.setContactEmail(request.contactEmail());

        Listing savedListing = listingRepository.save(listing);

        return mapToResponse(savedListing);

    }


    public ListingResponse archive(Long id){
        Listing listing = listingRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Inzerát s týmto ID neexistuje"));

        listing.setStatus(ListingStatus.ARCHIVED);

        Listing savedListing = listingRepository.save(listing);

        return mapToResponse(savedListing);

    }

    private static @NonNull ListingResponse mapToResponse(Listing savedListing) {
        return new ListingResponse(
                savedListing.getId(),
                savedListing.getUser().getId(),
                savedListing.getUser().getUsername(),
                savedListing.getTitle(),
                savedListing.getDescription(),
                savedListing.getPrice(),
                savedListing.getContactNumber(),
                savedListing.getContactEmail(),
                savedListing.getStatus(),
                savedListing.getCreatedAt()
        );
    }

}
