import { CommonModule } from '@angular/common';
import { Component, inject, Input, OnDestroy, OnInit } from '@angular/core';
import { TagModule } from 'primeng/tag';
import { Ad } from '../../services/search-ads.service';
import { Subscription } from 'rxjs';
import { PublishService } from '../../services/publish.service';

@Component({
  selector: 'rea-ad-item-grid',
  imports: [
    CommonModule,
    TagModule,
  ],
  templateUrl: './ad-item-grid.component.html'
})
export class AdItemGridComponent implements OnInit, OnDestroy{

  @Input() item: Ad = {} as Ad;
  @Input() showStatus: boolean = false;
  @Input() showId: boolean = false;

  status: string = 'WAITING';

  publishService = inject(PublishService);

  pollSubscription: Subscription = new Subscription();

  ngOnInit(): void {
    if(this.showStatus) {
      this.status = this.item.status;
      this.pollSubscription = this.publishService.pollStatus(this.item.id!).subscribe((response) => {
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
