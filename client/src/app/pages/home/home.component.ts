import { Component, effect, inject, signal } from '@angular/core';
import { AdsList } from '../../components/ads-list/ads-list.component';
import { InputTextModule } from 'primeng/inputtext';
import { ButtonModule } from 'primeng/button';
import { InputGroupModule } from 'primeng/inputgroup';
import { Ad, SearchAdsService, SearchByLatLongRequest, SearchByTitleRequest } from '../../services/search-ads.service';
import { debounceTime, Subscription } from 'rxjs';
import { CommonModule } from '@angular/common';
import { FormControl, FormsModule, ReactiveFormsModule } from '@angular/forms';
import { RouterModule } from '@angular/router';
import { SelectModule } from 'primeng/select';

@Component({
  selector: 'rea-home',
  imports: [
    CommonModule,
    FormsModule,
    RouterModule,
    ReactiveFormsModule,
    AdsList,
    InputTextModule,
    ButtonModule,
    InputGroupModule,
    SelectModule
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
  radius = signal(100);
  radiusOptions = [
    { name: '50 km', value: 50 },
    { name: '100 km', value: 100 },
    { name: '150 km', value: 150 },
    { name: '200 km', value: 200 },
    { name: '1000 km', value: 1000 }
  ];

  adsLoading = signal(false)
  featuredAdsLoading = signal(false)

  private subscriptionSearchByTitle: Subscription = new Subscription;
  private subscriptionFeatured: Subscription = new Subscription;

  constructor() {
    // 🔹 Usa un effect() per monitorare radius e ricaricare gli annunci
    effect(() => {
      this.loadFeaturedAds();
    });
  }
  
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
      radius: this.radius(),
      status: 'PUBLISHED',
      sortBy: 'createdAt',
      sortOrder: 'desc',
      page: 0,
      size: 10
    };
    this.featuredAdsLoading.set(true);
    this.searchAdsService.searchByLatLong(searchRequest).subscribe(
      (response) => {
        this.featuredAdsLoading.set(false);
        this.featuredAds = response;
      },
      (error) => {
        this.featuredAdsLoading.set(false);
        // Handle any error that might occur
        console.error('Error during loading featured ads:', error);
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

      this.adsLoading.set(true);
      
      this.subscriptionSearchByTitle = this.searchAdsService.searchByTitle(request).subscribe(
        (response) => {
          // Handle the response, e.g., update a list of ads
          this.ads = response
          this.adsLoading.set(false);
        },
        (error) => {
          this.adsLoading.set(false);
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

  get selectedRadius(): number {
    return this.radius();
  }
  
  set selectedRadius(value: number) {
    this.radius.set(value);
  }
  
}
