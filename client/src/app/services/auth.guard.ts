import { inject } from '@angular/core';
import { CanActivateFn, Router } from '@angular/router';
import { catchError, map, of, tap } from 'rxjs';
import { AuthService } from './auth.service';

export const authGuard: CanActivateFn = (route, state) => {
  const authService = inject(AuthService); // Ottieni l'istanza di AccountService
  const router = inject(Router); // Ottieni l'istanza del Router
  
  // Verifica se il token è valido
  return authService.isTokenValid().pipe(
    map((isValid) => {
      if (isValid) {

        // Ottieni il token decodificato
        const decodedToken = authService.decodeToken;

        // Verifica il ruolo richiesto
        const requiredRole = route.data['role'] as string;
        if (requiredRole && authService.getRole()!==requiredRole.toUpperCase()) {
          // Se il ruolo non è presente, reindirizza ad accesso negato
          router.navigate(['/auth/access']);
          return false;
        }

        return true; // Token valido, utente autenticato
      } else {
        router.navigate(['/auth/login'], { queryParams: { returnUrl: state.url } });
        return false;
      }

      
    }),
    catchError(() => {
      // Gestione degli errori (ad esempio, errore nella chiamata HTTP)
      router.navigate(['/auth/login'], { queryParams: { returnUrl: state.url } });
      return of(false); // Restituisce false in caso di errore
    })
  );
};