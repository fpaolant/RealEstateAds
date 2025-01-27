import { CommonModule } from '@angular/common';
import { Component, OnInit, signal, ViewChild } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { ButtonModule } from 'primeng/button';
import { ConfirmDialogModule } from 'primeng/confirmdialog';
import { DialogModule } from 'primeng/dialog';
import { IconFieldModule } from 'primeng/iconfield';
import { InputIconModule } from 'primeng/inputicon';
import { InputNumberModule } from 'primeng/inputnumber';
import { InputTextModule } from 'primeng/inputtext';
import { RadioButtonModule } from 'primeng/radiobutton';
import { RippleModule } from 'primeng/ripple';
import { SelectModule } from 'primeng/select';
import { Table, TableModule } from 'primeng/table';
import { TagModule } from 'primeng/tag';
import { TextareaModule } from 'primeng/textarea';
import { ToastModule } from 'primeng/toast';
import { ToolbarModule } from 'primeng/toolbar';
import { AdResponse, GetAdsRequest, PublishService } from '../../services/publish.service';
import { ConfirmationService, MessageService } from 'primeng/api';
import { TooltipModule } from 'primeng/tooltip';

interface Column {
  field: string;
  header: string;
  customExportHeader?: string;
}

interface ExportColumn {
  title: string;
  dataKey: string;
}


@Component({
  selector: 'rea-admin-ads',
  imports: [
    CommonModule,
    TableModule,
    FormsModule,
    ButtonModule,
    RippleModule,
    ToastModule,
    ToolbarModule,
    InputTextModule,
    TextareaModule,
    SelectModule,
    RadioButtonModule,
    InputNumberModule,
    DialogModule,
    TagModule,
    InputIconModule,
    IconFieldModule,
    ConfirmDialogModule,
    TooltipModule
  ],
  templateUrl: './ads.html',
  providers: [MessageService, ConfirmationService]
})
export class AdsPage implements OnInit {
  adDialog: boolean = false;
  publishDialog: boolean = false;
  publishDialogAction: 'reject' | 'approve' = 'approve';

  pager = {
    page: 0,
    size: 5,
    f: 0,
    r: 5
  };
  ads = signal<AdResponse[]>([]);
  ad!: AdResponse;
  totalAds: number = 0;


  selectedAds!: AdResponse[] | null;
  submitted!: boolean;
  statuses!:any[];

  rejectReason!: string;

  @ViewChild('dt') dt!: Table;
  exportColumns!: ExportColumn[];
  cols!: Column[];

  constructor(
    private publishService: PublishService,
    private messageService: MessageService,
    private confirmationService: ConfirmationService
) {}


  ngOnInit() {
    // Carica gli annunci iniziali
    this.loadAds(0);
  }

  loadAds(page: number): void {
    const request: GetAdsRequest = {
      page: page,
      size: this.pager.size,
      sortBy: 'id',
      sortOrder: 'desc'
    };

    this.publishService.getAds(request).subscribe(response => {
      this.ads.set(response.content);
      this.totalAds = response.totalItems;
    });
  }
  
  onGlobalFilter(table: Table, event: Event) {
    table.filterGlobal((event.target as HTMLInputElement).value, 'contains');
  }

  openNew() {
    this.ad = {
      id: 0,
      title: '',
      description: '',
      squareMeters: 0,
      price: 0,
      status: '',
      location: '',
      latitude: 0,
      longitude: 0,
      accountId: 0
    };
    this.submitted = false;
    this.adDialog = true;
  }

  editAd(ad: AdResponse) {
    this.ad = { ...ad };
    this.adDialog = true;
  }

  publishOpen(ad: AdResponse, action: 'reject' | 'approve') {
    this.ad = { ...ad };
    this.publishDialog = true;
    this.publishDialogAction = action;
    if(action === 'reject') {
      this.rejectReason = '';
    }
  }

  rejectAd(ad: AdResponse) {
    this.submitted = true;
    const rejectRequest = {
        reason: this.rejectReason
    };

    this.publishService.rejectAd(ad.id, rejectRequest).subscribe(() => {
        this.messageService.add({
            severity: 'success',
            summary: 'Success',
            detail: 'Ad rejected',
            life: 3000
        });
        this.publishDialog = false;
        this.loadAds(this.pager.page);
    });
  }

  approveAd(ad: AdResponse) {
    this.publishService.approveAd(this.ad.id).subscribe(() => {
        this.messageService.add({
            severity: 'success',
            summary: 'Success',
            detail: 'Ad approved',
            life: 3000
        });
        this.publishDialog = false;
        this.loadAds(this.pager.page);
    });
  }

  deleteAd(ad: AdResponse) {
    this.confirmationService.confirm({
      message: 'Sei sicuro di voler eliminare l\'annuncio?',
      header: 'Confirm',
      icon: 'pi pi-exclamation-triangle',
      accept: () => {
          // this.products.set(this.products().filter((val) => !this.selectedProducts?.includes(val)));
          // this.selectedProducts = null;
          this.messageService.add({
              severity: 'success',
              summary: 'Successful',
              detail: 'Products Deleted',
              life: 3000
          });
      }
  });
  }

  getSeverity(status: string) {
    switch (status) {
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

  hideDialog() {
    this.adDialog = false;
    this.submitted = false;
  }

  saveAd() {
  }

  exportCSV() {
    this.dt.exportCSV();
  }

  deleteSelectedAds() {
  }

  // paginator handlers
  onRowsChange(event: any): void {
    console.log('rowsChange event r:', event);
    this.pager.size = event;
    this.pager.page = this.pager.f / this.pager.size;
    this.loadAds(this.pager.page);
  }

  onFirstChange(event: any): void {
    console.log('firstChange event f:', event);
    this.pager.f = event;
    this.pager.page = this.pager.f / this.pager.size;
    this.loadAds(this.pager.page);
  }

}
