import { Component, inject } from '@angular/core';
import { ErrorResponse, WalletResponse, WalletService } from '../../services/wallet.service';
import { AuthService } from '../../services/auth.service';
import { CommonModule, DatePipe } from '@angular/common';
import { ButtonModule } from 'primeng/button';
import { InputGroupModule } from 'primeng/inputgroup';
import { InputGroupAddonModule } from 'primeng/inputgroupaddon';
import { InputNumberModule } from 'primeng/inputnumber';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'rea-wallet',
  imports: [CommonModule, FormsModule, InputNumberModule, InputGroupModule, InputGroupAddonModule, ButtonModule, DatePipe],
  templateUrl: './wallet.component.html',
  styleUrl: './wallet.component.scss'
})
export class WalletPage {
  walletService = inject(WalletService);
  authService = inject(AuthService);

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
      }, (error) => {
        this.error = error;
      });
    }
  }


}
