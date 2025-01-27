import { Routes } from '@angular/router';
import { AdvancedSearchPage } from './advanced-search/advanced-search.component';
import { AccountPage } from './account/account.component';
import { authGuard } from '../services/auth.guard';
import { AppLayout } from '../layout/component/app.layout';
import { WalletPage } from './wallet/wallet.component';
import { MyAdsPage } from './myads/myads.component';
import { PublishAdPage } from './publish-ad/publish-ad.component';

export default [
    { path: 'advanced-search', component: AdvancedSearchPage },
    { path: 'account', component: AccountPage, canActivate: [authGuard] },
    { path: 'publish-ad', component: PublishAdPage, canActivate: [authGuard] },
    { path: 'myads', component: MyAdsPage, canActivate: [authGuard] },
    { path: 'wallet', component: WalletPage, canActivate: [authGuard] },

    //{ path: 'admin', component: AdminPage, canActivate: [authGuard], data: { role: 'ADMIN' } },  
    { path: 'admin',
        loadChildren: () => import('./admin/admin.routes'), 
        canActivate: [authGuard], data: { role: 'ADMIN' }
    },  
    { path:  'auth',
        component: AppLayout,
        loadChildren: () => import('./auth/auth.routes') 
    },
    { path: '**', redirectTo: '/notfound' }
] as Routes;
