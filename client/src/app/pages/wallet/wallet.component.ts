import { Component, inject } from '@angular/core';
import { ErrorResponse, WalletResponse, WalletService } from '../../services/wallet.service';
import { AuthService } from '../../services/auth.service';
import { CommonModule, DatePipe } from '@angular/common';
import { ButtonModule } from 'primeng/button';
import { InputGroupModule } from 'primeng/inputgroup';
import { InputGroupAddonModule } from 'primeng/inputgroupaddon';
import { InputNumberModule } from 'primeng/inputnumber';
import { FormsModule } from '@angular/forms';
import { ToastModule } from 'primeng/toast';
import { MessageService } from 'primeng/api';

@Component({
  selector: 'rea-wallet',
  imports: [CommonModule, FormsModule, InputNumberModule, InputGroupModule, InputGroupAddonModule, ButtonModule, DatePipe, ToastModule],
  templateUrl: './wallet.component.html',
  styleUrl: './wallet.component.scss',
  providers: [MessageService]
})
export class WalletPage {
  walletService = inject(WalletService);
  authService = inject(AuthService);
  messageService = inject(MessageService);

  accountId = this.authService.getUserId();

  wallet: WalletResponse | undefined
  error: ErrorResponse | undefined;

  amount: number = 50;

  ngOnInit() {
    this.loadWallet();
  }

  loadWallet() {
    if(this.accountId) {
      this.walletService.getWalletByAccountId(this.accountId).subscribe(
        (response) => {
          this.wallet = response;
      }, (error) => {
        this.error = error;
      });
    }
  }

  onReCharge() {
    if(this.wallet) {
      this.walletService.rechargeWallet({ walletId: this.wallet.walletId, amount: this.amount }).subscribe((response) => {
        this.wallet = response;
        this.amount = 50;
        this.showMessage('success', 'Wallet ricaricato con successo', 'Ricarica effettuata');
      }, (error) => {
        this.showMessage('error', 'Errore durante la ricarica del wallet', 'Errore');
        this.error = error;
      });
    }
  }

  showMessage(severity: string='success', message: string='Message Content', summary: string='Success Message') {
    this.messageService.add({ severity: severity, summary: summary, detail: message, key: 'br', life: 3000 });
  }


}
