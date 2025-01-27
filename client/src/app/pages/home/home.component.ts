import { Component, inject } from '@angular/core';
import { AdsList } from '../../components/ads-list/ads-list.component';
import { InputTextModule } from 'primeng/inputtext';
import { ButtonModule } from 'primeng/button';
import { InputGroupModule } from 'primeng/inputgroup';
import { Ad, SearchAdsService, SearchByLatLongRequest, SearchByTitleRequest } from '../../services/search-ads.service';
import { debounceTime, Subscription } from 'rxjs';
import { CommonModule } from '@angular/common';
import { FormControl, ReactiveFormsModule } from '@angular/forms';
import { RouterModule } from '@angular/router';

@Component({
  selector: 'rea-home',
  imports: [
    CommonModule,
    RouterModule,
    ReactiveFormsModule,
    AdsList,
    InputTextModule,
    ButtonModule,
    InputGroupModule,
  ],
  templateUrl: './home.component.html',
  styleUrl: './home.component.scss'
})
export class HomePage {
  searchAdsService = inject(SearchAdsService);

  searchControl = new FormControl('');
  position = {
    latitude: 42.35,
    longitude: 13.4
  }
  ads: Ad[] = [];
  featuredAds: Ad[] = [];

  private subscriptionSearchByTitle: Subscription = new Subscription;
  private subscriptionFeatured: Subscription = new Subscription;

  
  ngOnInit() {
    // Ascolta i cambiamenti del controllo con debounceTime
    this.searchControl.valueChanges.pipe(
      debounceTime(300),
    ).subscribe((query) => {
      this.onSearch(query);
    });
    this.loadFeaturedAds();
  }

  loadFeaturedAds(): void {
    const searchRequest: SearchByLatLongRequest = {
      latitude: this.position.latitude,
      longitude: this.position.longitude,
      radius: 100,
      status: 'PUBLISHED',
      sortBy: 'createdAt',
      sortOrder: 'desc',
      page: 0,
      size: 10
    };
    this.searchAdsService.searchByLatLong(searchRequest).subscribe(res => {
      this.featuredAds = Array.isArray(res) ? res : [];;
    });
  }

  getLocation() {
    if ('geolocation' in navigator) {
      navigator.geolocation.getCurrentPosition(
        (position) => {
          this.position.latitude = position.coords.latitude;
          this.position.longitude = position.coords.longitude;
          this.loadFeaturedAds();
        },
        (error) => {
          this.loadFeaturedAds();
        }
      );
    }
  }

  onSearch(text: string | null) {
    if (text && text.trim()) {
      const request: SearchByTitleRequest = {
        title: text,
        page: 0,
        size: 10,
        status: 'PUBLISHED',
        sortBy: 'price',
        sortOrder: 'asc'
      };
      
      this.subscriptionSearchByTitle = this.searchAdsService.searchByTitle(request).subscribe(
        (response) => {
          // Handle the response, e.g., update a list of ads
          this.ads = Array.isArray(response) ? response : [];
        },
        (error) => {
          // Handle any error that might occur
          console.error('Error during search:', error);
        }
      );
    }
  }

  ngOnDestroy() {
    if (this.subscriptionSearchByTitle) {
      this.subscriptionSearchByTitle.unsubscribe();
    }

    if (this.subscriptionFeatured) {
      this.subscriptionFeatured.unsubscribe();
    }

  }
  
}
