import { Routes } from '@angular/router';
import { AppLayout } from './app/layout/component/app.layout';
import { Dashboard } from './app/pages-ex/dashboard/dashboard';
import { Documentation } from './app/pages-ex/documentation/documentation';
import { Landing } from './app/pages-ex/landing/landing';
import { Notfound } from './app/pages-ex/notfound/notfound';
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
    {
        path: 'demo',
        component: AppLayout,
        children: [
            { path: '', component: Dashboard },
            { path: 'uikit', loadChildren: () => import('./app/pages-ex/uikit/uikit.routes') },
            { path: 'documentation', component: Documentation },
            { path: 'pages', loadChildren: () => import('./app/pages-ex/pages.routes') }
        ]
    },
    { path: 'landing', component: Landing },
    { path: 'notfound', component: Notfound },
    { path: 'auth', loadChildren: () => import('./app/pages/auth/auth.routes') },
    { path: '**', redirectTo: '/notfound' }
];
