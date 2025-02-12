import { Component, inject } from '@angular/core';
import { AdsList } from '../../components/ads-list/ads-list.component';
import { InputTextModule } from 'primeng/inputtext';
import { ButtonModule } from 'primeng/button';
import { InputGroupModule } from 'primeng/inputgroup';
import { Ad, SearchAdsService, SearchByLatLongRequest } from '../../services/search-ads.service';
import { debounceTime, Subject, switchMap, tap } from 'rxjs';
import { CommonModule } from '@angular/common';
import { FormControl, ReactiveFormsModule } from '@angular/forms';
import { MapComponent } from '../../components/map/map.component';
import { SliderModule } from 'primeng/slider';
import { SkeletonModule } from 'primeng/skeleton';


@Component({
  selector: 'rea-advanced-search',
  imports: [
    CommonModule,
    ReactiveFormsModule,
    AdsList,
    InputTextModule,
    ButtonModule,
    InputGroupModule,
    MapComponent,
    SliderModule,
    SkeletonModule
  ],
  templateUrl: './advanced-search.component.html'
})
export class AdvancedSearchPage {
  searchAdsService = inject(SearchAdsService);
  adsLoading: boolean = false;
  radiusControl = new FormControl(500);
  selectedPoint: { lat: number; lng: number } | null = null;
  centerCoordinates: { lat: number; lng: number } | null = null;

  ads: Ad[] = [];
  
  private centerChangedSubject = new Subject<{ lat: number; lng: number }>();



  ngOnInit() {
    this.centerChangedSubject
      .pipe(
        debounceTime(500),
        tap(() => (this.adsLoading = true)),
        switchMap((coordinates) => {
          const searchRequest: SearchByLatLongRequest = {
            latitude: coordinates.lat,
            longitude: coordinates.lng,
            radius: this.radiusControl.value? this.radiusControl.value / 1000: 0,
            status: 'PUBLISHED',
            sortBy: 'price',
            sortOrder: 'asc',
            page: 0,
            size: 20
          };
          return this.searchAdsService.searchByLatLong(searchRequest);
        })
      )
      .subscribe(
        (response) => {
          // Aggiorna la lista degli annunci
          this.ads = response;
          this.adsLoading = false;
        },
        (error) => {
          console.error('Errore nella ricerca degli annunci:', error);
          this.adsLoading = false;
        }
      );

  }

  ngOnDestroy() {
    this.centerChangedSubject.unsubscribe();
  }

  onPointSelected(point: { lat: number; lng: number }): void {
    this.selectedPoint = point;
  }

  onCenterChanged(coordinates: { lat: number; lng: number }): void {
    this.centerCoordinates = coordinates;

    if (this.radiusControl.value && this.centerCoordinates) {
      this.centerChangedSubject.next(coordinates); // Invia l'evento al Subject
    }
  }

  // Getter per trasformare gli annunci in coordinate
  get adsPoints() {
    return this.ads.map(ad => ({
      opts: {route: '/pages/ad/' + ad.id!},
      lat: ad.latitude!,
      lng: ad.longitude!
    }));
  }

}
