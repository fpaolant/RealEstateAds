import { Component, inject, signal } from '@angular/core';
import { AdsList } from '../../components/ads-list/ads-list.component';
import { InputTextModule } from 'primeng/inputtext';
import { ButtonModule } from 'primeng/button';
import { InputGroupModule } from 'primeng/inputgroup';
import { Ad, SearchAdsService, SearchByTitleRequest } from '../../services/search-ads.service';
import { debounceTime, filter, map, Subscription } from 'rxjs';
import { CommonModule } from '@angular/common';
import { FormControl, ReactiveFormsModule } from '@angular/forms';
import { RouterModule } from '@angular/router';
import { AdResponse, GetAdsRequest, PublishService } from '../../services/publish.service';

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
  publishService = inject(PublishService);
  searchAdsService = inject(SearchAdsService);

  searchControl = new FormControl('');
  ads: Ad[] = [];
  featuredAds = signal<AdResponse[]>([]);

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
    const request: GetAdsRequest = {
      page: 0,
      size: 5,
      sortBy: 'createdAt',
      sortOrder: 'desc',
      status: 'PUBLISHED'
    };

    this.subscriptionFeatured = this.publishService.getAds(request).pipe(
      map(response => response.content)      
    ).subscribe(ads => {
      this.featuredAds.set(ads);
    });
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
