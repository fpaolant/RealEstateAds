import { Component, inject, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Ad, SearchAdsService } from '../../../services/search-ads.service';
import { CommonModule, Location } from '@angular/common';
import { MapComponent } from '../../../components/map/map.component';
import { TagModule } from 'primeng/tag';
import { Subscription } from 'rxjs';
import { PublishService } from '../../../services/publish.service';
import { AuthService } from '../../../services/auth.service';

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
  publishService = inject(PublishService);
  authService = inject(AuthService);
  location = inject(Location);
  adId: number | null = null;

  ad: Ad | null = null;
  status: string = 'WAITING';


  pollSubscription: Subscription = new Subscription();

  constructor() {
    const id = this.route.snapshot.paramMap.get('id');
    this.adId = id !== null ? +id : null; // Ottieni l'ID dalla rotta e convertilo in numero
  }

  ngOnInit(): void {
    if (this.adId !== null) {
      this.searchService.searchById(this.adId).subscribe(
        (ad) => {
          this.ad = ad;
          this.updateStatus();
        },
        (error) => {
          console.error('Error:', error);
        }
      );
    }
  }

  ngOnDestroy(): void {
    if(this.pollSubscription)
      this.pollSubscription.unsubscribe();
  }

  updateStatus() {
    this.status = this.ad!.status;

    if(this.authService.isLogged() && this.ad!.status === 'PENDING_APPROVAL') {
      this.pollSubscription = this.publishService.pollStatus(this.ad!.id!, 5000).subscribe((response) => {
        this.status = response.status;
      });
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
