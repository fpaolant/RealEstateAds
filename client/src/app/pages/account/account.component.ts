import { Component, inject, OnInit } from '@angular/core';
import { AccountResponse, AccountService } from '../../services/account.service';
import { AuthService } from '../../services/auth.service';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'rea-account',
  imports: [
    CommonModule
  ],
  templateUrl: './account.component.html',
  styleUrl: './account.component.scss'
})
export class AccountPage implements OnInit {
  
  accountService = inject(AccountService);
  authService = inject(AuthService);

  account: AccountResponse | null = null;

  ngOnInit(): void {
    const accountId = this.authService.getUserId();
    console.log(accountId);
    if(accountId){
      this.accountService.getAccount(accountId).subscribe((account) => {
        this.account = account;
      });
    }
  }

}
