import { CommonModule } from '@angular/common';
import { Component, inject, Input, OnDestroy, OnInit } from '@angular/core';
import { TagModule } from 'primeng/tag';
import { Ad } from '../../services/search-ads.service';
import { PublishService } from '../../services/publish.service';
import { Subscription } from 'rxjs';
import { RouterModule } from '@angular/router';

@Component({
  selector: 'rea-ad-item-list',
  imports: [
    RouterModule,
    CommonModule,
    TagModule,

  ],
  templateUrl: './ad-item-list.component.html'
})
export class AdItemListComponent implements OnInit, OnDestroy {

  @Input() item: Ad = {} as Ad;
  @Input() showStatus: boolean = false;
  @Input() showId: boolean = false;
  @Input() itemIndex: number = 0;
  @Input() detailRoute: string = '';

  status: string = 'WAITING';

  publishService = inject(PublishService);

  pollSubscription: Subscription = new Subscription();


  ngOnInit(): void {
    if(this.showStatus) {
      this.status = this.item.status;
      this.pollSubscription = this.publishService.pollStatus(this.item.id!, 5000).subscribe((response) => {
        this.status = response.status;
      });
    }
    
  }

  ngOnDestroy(): void {
    if(this.pollSubscription)
      this.pollSubscription.unsubscribe();
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

  

}
