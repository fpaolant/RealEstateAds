import { Component, inject, OnInit } from '@angular/core';
import { AdResponse, PublishGetAdsResponse, PublishService } from '../../services/publish.service';
import { AuthService } from '../../services/auth.service';
import { AdsList } from '../../components/ads-list/ads-list.component';

@Component({
  selector: 'rea-myads',
  imports: [AdsList],
  templateUrl: './myads.component.html',
  styleUrl: './myads.component.scss'
})
export class MyAdsPage implements OnInit {
  publishService = inject(PublishService);
  authService = inject(AuthService);

  accountId = this.authService.getUserId();

  ads: AdResponse[] = [];

  ngOnInit() {
    this.loadAds();
  }

  loadAds() {
    if(this.accountId) {
      this.publishService.getAdsAccount(this.accountId).subscribe((response: PublishGetAdsResponse) => {
        this.ads = response.adsResponse;
      });
    }
  }

}
