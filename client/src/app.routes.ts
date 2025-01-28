import { Routes } from '@angular/router';
import { AppLayout } from './app/layout/component/app.layout';
import { Notfound } from './app/pages/notfound/notfound';
import { HomePage } from './app/pages/home/home.component';
import { Logout } from './app/pages/auth/logout';

export const appRoutes: Routes = [
    {
        path: '',
        component: AppLayout,
        children: [
            { path: '', component: HomePage },
            { path: 'pages', loadChildren: () => import('./app/pages/pages.routes') },
            { path: 'logout', component: Logout}
        ]
    },
    { path: 'notfound', component: Notfound },
    { path: 'auth', loadChildren: () => import('./app/pages/auth/auth.routes') },
    { path: '**', redirectTo: '/notfound' }
];
