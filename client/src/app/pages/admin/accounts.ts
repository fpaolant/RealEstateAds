import { Component, inject, OnInit, signal, ViewChild } from '@angular/core';
import { AccountResponse, AccountService, GetAccountsRequest, getPossibleRoles } from '../../services/account.service';
import { CommonModule, DatePipe } from '@angular/common';
import { Table, TableModule } from 'primeng/table';
import { FormsModule } from '@angular/forms';
import { ButtonModule } from 'primeng/button';
import { RippleModule } from 'primeng/ripple';
import { ToastModule } from 'primeng/toast';
import { ToolbarModule } from 'primeng/toolbar';
import { InputTextModule } from 'primeng/inputtext';
import { TextareaModule } from 'primeng/textarea';
import { SelectModule } from 'primeng/select';
import { RadioButtonModule } from 'primeng/radiobutton';
import { InputNumberModule } from 'primeng/inputnumber';
import { DialogModule } from 'primeng/dialog';
import { TagModule } from 'primeng/tag';
import { InputIconModule } from 'primeng/inputicon';
import { IconFieldModule } from 'primeng/iconfield';
import { ConfirmDialogModule } from 'primeng/confirmdialog';
import { TooltipModule } from 'primeng/tooltip';
import { ConfirmationService, MessageService } from 'primeng/api';
import { AuthService } from '../../services/auth.service';

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
  selector: 'rea-admin-accounts',
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
    TooltipModule,
    DatePipe
  ],
  templateUrl: './accounts.html',
  providers: [MessageService, ConfirmationService]
})
export class AccountsPage implements OnInit {
  authService = inject(AuthService);
  accountService = inject(AccountService);
  messageService = inject(MessageService);
  confirmationService = inject(ConfirmationService);

  accountDialog: boolean = false;

  pager = {
    page: 0,
    size: 5,
    f: 0,
    r: 5
  };

  accounts = signal<AccountResponse[]>([]);
  account!: AccountResponse;
  totalAccounts!: number;
  roles!: any[];

  selectedAccounts!: AccountResponse[] | null;
  submitted!: boolean;

  currentUserUserID = this.authService.getUserId();

  @ViewChild('dt') dt!: Table;
  exportColumns!: ExportColumn[];
  cols!: Column[];

 
  ngOnInit() {
    // Carica gli annunci iniziali
    this.loadAccount(0);
    const roles = getPossibleRoles();
    this.roles = roles.map(role => ({ label: role, value: role }));
  }


  loadAccount(page: number) {
    const request: GetAccountsRequest = {
      page: page,
      size: this.pager.size,
      sortBy: 'id',
      sortOrder: 'asc'
    };
    this.accountService.getAccounts(request).subscribe((response) => {
      this.accounts.set(response.content);
      this.totalAccounts = response.totalItems;
    });
  }

  onGlobalFilter(table: Table, event: Event) {
    table.filterGlobal((event.target as HTMLInputElement).value, 'contains');
  }

  openNew() {
    this.submitted = false;
    this.accountDialog = true;
  }

  editAccount(account: AccountResponse) {
    this.account = { ...account };
    this.accountDialog = true;
  }

  deleteAccount(account: AccountResponse) {
    this.confirmationService.confirm({
      message: 'Are you sure you want to delete ' + account.username + '?',
      header: 'Confirm',
      icon: 'pi pi-exclamation-triangle',
      accept: () => {
        // this.accounts.set(this.accounts.get().filter(val => val.id !== account.id));
        // this.account = {};
        this.messageService.add({ severity: 'success', summary: 'Successful', detail: 'Account Deleted', life: 3000 });
      }
    });
  }

  hideDialog() {
    this.accountDialog = false;
    this.submitted = false;
  }

  saveAccount() {
  }


  exportCSV() {
    this.dt.exportCSV();
  }

  deleteSelectedAccounts() {
  }

  promoteAccount(account: AccountResponse) {
    this.confirmationService.confirm({
      message: 'Sei sicuro di voler promuovere l\'utente "' + account.username + '" ad admin?',
      header: 'Conferma promozione',
      icon: 'pi pi-exclamation-triangle',
      accept: () => {
        this.accountService.promoteAccount(account.id).subscribe(() => {
          this.messageService.add({ severity: 'success', summary: 'Successo', detail: 'Account Promosso.', life: 3000 });
          this.loadAccount(this.pager.page);
        });
      }
    });
  }

  demoteAccount(account: AccountResponse) {
    this.confirmationService.confirm({
      message: 'Sei sicuro di voler impostare l\'utente "' + account.username + '" come user?',
      header: 'Conferma',
      icon: 'pi pi-exclamation-triangle',
      accept: () => {
        this.accountService.demoteAccount(account.id).subscribe(() => {
          this.messageService.add({ severity: 'success', summary: 'Successo', detail: 'Account aggiornato.', life: 3000 });
          this.loadAccount(this.pager.page);

        });
      }
    });
  }


  // paginator handlers
  onRowsChange(event: any): void {
    console.log('rowsChange event r:', event);
    this.pager.size = event;
    this.pager.page = this.pager.f / this.pager.size;
    this.loadAccount(this.pager.page);
  }

  onFirstChange(event: any): void {
    console.log('firstChange event f:', event);
    this.pager.f = event;
    this.pager.page = this.pager.f / this.pager.size;
    this.loadAccount(this.pager.page);
  }


}
