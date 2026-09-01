package com.example.babettahub.domain.listing;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "listing_images")
@Getter
@Setter
public class ListingImage {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private long id;

    @ManyToOne
    @JoinColumn(name = "listing_id", nullable = false)
    private Listing listing;

    @Column(nullable = false)
    private String imagePath;

    @Column(nullable = false)
    private Boolean isPrimary;
}
