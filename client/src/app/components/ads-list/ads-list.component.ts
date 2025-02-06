import { CommonModule } from '@angular/common';
import { Component, Input } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { ButtonModule } from 'primeng/button';
import { DataViewModule } from 'primeng/dataview';
import { SelectButtonModule } from 'primeng/selectbutton';
import { TagModule } from 'primeng/tag';
import { Ad } from '../../services/search-ads.service';
import { SkeletonModule } from 'primeng/skeleton';
import { AdItemListComponent } from '../ad-item-list/ad-item-list.component';
import { AdItemGridComponent } from "../ad-item-grid/ad-item-grid.component";

@Component({
    selector: 'rea-list',
    standalone: true,
    imports: [
    CommonModule,
    DataViewModule,
    FormsModule,
    SelectButtonModule,
    TagModule,
    ButtonModule,
    SkeletonModule,
    AdItemListComponent,
    AdItemGridComponent
],
    templateUrl: './ads-list.component.html',
    styles: `
        .smaller-text {
            font-size: smaller;
        }

        ::ng-deep {
            .p-orderlist-list-container {
                width: 100%;
            }
        }
    `
})
export class AdsList {
    layout: 'list' | 'grid' = 'list';

    options = ['list', 'grid'];



    @Input() ads: Ad[] = [];
    @Input() showStatus: boolean = false;
    @Input() title: string = '';
    @Input() gridSelect: boolean = true;
    @Input() loading: boolean = false;



    getStatus(ad: Ad) {
        switch (ad.status) {
            case 'PUBLISHED':
                return 'success';

            case 'PENDING_APPROVAL':
                return 'warn';

            case 'REJECTED':
                return 'danger';

            default:
                return 'info';
        }
    }
}
