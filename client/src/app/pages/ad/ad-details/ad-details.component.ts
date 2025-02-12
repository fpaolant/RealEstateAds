import { Component, inject, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Ad, SearchAdsService } from '../../../services/search-ads.service';
import { CommonModule, Location } from '@angular/common';
import { MapComponent } from '../../../components/map/map.component';
import { TagModule } from 'primeng/tag';

@Component({
  selector: 'rea-ad-details',
  imports: [
    CommonModule,
    MapComponent,
    TagModule
  ],
  templateUrl: './ad-details.component.html'
})
export class AdDetailsPage implements OnInit {
  route = inject(ActivatedRoute);
  searchService = inject(SearchAdsService);
  location = inject(Location);
  adId: number | null = null;

  ad: Ad | null = null;

  constructor() {
    const id = this.route.snapshot.paramMap.get('id');
    this.adId = id !== null ? +id : null; // Ottieni l'ID dalla rotta e convertilo in numero
  }

  ngOnInit(): void {
    if (this.adId !== null) {
      this.searchService.searchById(this.adId).subscribe(
        (ad) => {
          this.ad = ad;
        },
        (error) => {
          console.error('Error:', error);
        }
      );
    }
  }

  getSeverity(status:string) {
    switch (status) {
      case 'PUBLISHED':
        return 'success';

      case 'PENDING_APPROVAL':
        return 'warn';

      case 'REJECTED':
        return 'danger';

      default:
        return 'warn';
    }
  }

  

  goBack() {
    this.location.back(); // Torna alla pagina precedente
  }


}
