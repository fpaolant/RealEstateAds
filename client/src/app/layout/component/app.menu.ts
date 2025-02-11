import { Component, inject } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule } from '@angular/router';
import { MenuItem } from 'primeng/api';
import { AppMenuitem } from './app.menuitem';
import { AuthService } from '../../services/auth.service';

@Component({
    selector: 'app-menu',
    standalone: true,
    imports: [CommonModule, AppMenuitem, RouterModule],
    template: `
    <ul class="layout-menu">
        <ng-container *ngFor="let item of model; let i = index">
            <li app-menuitem *ngIf="!item.separator" [item]="item" [index]="i" [root]="true"></li>
            <li *ngIf="item.separator" class="menu-separator"></li>
        </ng-container>
    </ul> 

    <ul class="layout-menu">
        <ng-container *ngFor="let item of modelUser; let i = index">
            <li app-menuitem *ngIf="!item.separator" [item]="item" [index]="i" [root]="true"></li>
            <li *ngIf="item.separator" class="menu-separator"></li>
        </ng-container>
    </ul> 

    <ul class="layout-menu">
        <ng-container *ngFor="let item of modelAdmin; let i = index">
            <li app-menuitem *ngIf="!item.separator" [item]="item" [index]="i" [root]="true"></li>
            <li *ngIf="item.separator" class="menu-separator"></li>
        </ng-container>
    </ul> 

    `
})
export class AppMenu {
    model: MenuItem[] = [];
    modelAdmin: MenuItem[] = [];
    modelUser: MenuItem[] = [];

    authService = inject(AuthService);
    

    ngOnInit() {
        this.model = [
            {
                items: [
                    { label: 'Home', icon: 'pi pi-fw pi-home', routerLink: ['/'] },
                    { label: 'Ricerca su mappa', icon: 'pi pi-fw pi-search', routerLink: ['/pages/advanced-search'] },
                ]
            },
        ];
        if(this.authService.getRole() === 'ADMIN'){
            this.modelAdmin = [
                {
                    label: 'Amministratore',
                    items: [
                        { label: 'Gestisci Account', icon: 'pi pi-fw pi-users', routerLink: ['/pages/admin/accounts'] },
                        { label: 'Gestisci Annunci', icon: 'pi pi-fw pi-images', routerLink: ['/pages/admin/ads'] }
                    ]
                },{
                    label: 'API',
                    items: [
                        { label: 'API docs', icon: 'pi pi-fw pi-link', routerLink: ['/pages/apidoc'] }
                    ]
                },
            ];
        }

        if(this.authService.isLogged()){
            this.modelUser = [
                {
                    label: 'Utente',
                    items: [
                        { label: 'Pubblica annuncio', icon: 'pi pi-fw pi-file-edit', routerLink: ['/pages/publish-ad'] },
                        { label: 'I miei Annunci', icon: 'pi pi-fw pi-images', routerLink: ['/pages/myads'] },
                        { label: 'Wallet', icon: 'pi pi-fw pi-wallet', routerLink: ['/pages/wallet'] },
                        // { label: 'Esci', icon: 'pi pi-fw pi-sign-out', routerLink: ['/logout'] }
                    ]
                },
            ];
        }

    }
}
