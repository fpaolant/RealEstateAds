import { Component, inject, OnInit } from '@angular/core';
import { AccountService } from '../../services/account.service';

@Component({
  selector: 'rea-account',
  imports: [],
  templateUrl: './account.component.html',
  styleUrl: './account.component.scss'
})
export class AccountPage implements OnInit {
  
  accountService = inject(AccountService);

  //account = this.accountService.getAccount();

  ngOnInit(): void {
    throw new Error('Method not implemented.');
  }

}
