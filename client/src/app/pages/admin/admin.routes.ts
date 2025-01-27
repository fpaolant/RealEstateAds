import { Routes } from '@angular/router';
import { AdsPage } from './ads';
import { AccountsPage } from './accounts';


export default [
    { path: 'accounts', component: AccountsPage },
    { path: 'ads', component: AdsPage }
] as Routes;
