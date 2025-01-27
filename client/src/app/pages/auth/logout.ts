import { Component, inject, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { AuthService } from '../../services/auth.service';

@Component({
    selector: 'rea-logout',
    template: '',
  })
  export class Logout implements OnInit {
    authService = inject(AuthService);
    router = inject(Router);
  
    ngOnInit(): void {
      this.authService.logout(); // Esegui il logout
        this.router.navigate(['/auth/login']); // Reindirizza alla pagina di login
    }
  }
