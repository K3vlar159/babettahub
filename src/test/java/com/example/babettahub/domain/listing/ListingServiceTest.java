package com.example.babettahub.domain.listing;

import com.example.babettahub.common.ResourceNotFoundException;
import com.example.babettahub.domain.listing.dto.ListingRequest;
import com.example.babettahub.domain.listing.dto.ListingResponse;
import com.example.babettahub.domain.user.User;
import com.example.babettahub.domain.user.UserRepository;
import org.jspecify.annotations.NonNull;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ListingServiceTest {

    @Mock
    private ListingRepository listingRepository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private ListingService listingService;

    private static final LocalDateTime CREATED_AT = LocalDateTime.of(2026, 11, 2, 10, 0);

    @Test
    void getById_nonExistingId_throwsNotFound() {
        when(listingRepository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class,
                () -> listingService.getById(99L));
    }

    @Test
    void create_noEmailOrNumber_throwsIllegalArg(){
        ListingRequest request = new ListingRequest(
                "Babetta 210",
                "Pekný stav",
                500,
                null,
                null,
                1L
        );
        assertThrows(IllegalArgumentException.class,() -> listingService.create(request));
        verify(listingRepository, never()).save(any());
    }

    @Test
    void getById_existingId_returnsMappedResponse() {
        Listing listing = createTestListing();

        when(listingRepository.findById(1L)).thenReturn(Optional.of(listing));

        ListingResponse response = listingService.getById(1L);

        assertAll(
                () -> assertEquals(1L, response.id()),
                () -> assertEquals(7L, response.sellerId()),
                () -> assertEquals("babettar", response.sellerUsername()),
                () -> assertEquals("Babetta 210", response.title()),
                () -> assertEquals("Pekný stav, pojazdná", response.description()),
                () -> assertEquals(500, response.price()),
                () -> assertEquals("0900123456", response.contactNumber()),
                () -> assertEquals("predaj@example.com", response.contactEmail()),
                () -> assertEquals(ListingStatus.ACTIVE, response.status()),
                () -> assertEquals(CREATED_AT, response.createdAt())
        );
    }

    @Test
    void archive_changesStatusToArchived(){
        Listing listing = createTestListing();

        when(listingRepository.findById(1L)).thenReturn(Optional.of(listing));
        when(listingRepository.save(any())).thenReturn(listing);

        ListingResponse response = listingService.archive(1L);

        assertEquals(ListingStatus.ARCHIVED,response.status());
        verify(listingRepository, times(1)).save(listing);
    }

    private static @NonNull Listing createTestListing() {
        User user = new User();
        user.setId(7L);
        user.setUsername("babettar");

        Listing listing = new Listing();
        listing.setId(1L);
        listing.setUser(user);
        listing.setTitle("Babetta 210");
        listing.setDescription("Pekný stav, pojazdná");
        listing.setPrice(500);
        listing.setContactNumber("0900123456");
        listing.setContactEmail("predaj@example.com");
        listing.setStatus(ListingStatus.ACTIVE);
        listing.setCreatedAt(CREATED_AT);
        return listing;
    }

}