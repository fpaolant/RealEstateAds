import { Component, inject } from '@angular/core';
import { FormControl, FormGroup, FormsModule, ReactiveFormsModule, Validators } from '@angular/forms';
import { PublishAdRequest, PublishService } from '../../services/publish.service';
import { ButtonModule } from 'primeng/button';
import { InputTextModule } from 'primeng/inputtext';
import { InputNumberModule } from 'primeng/inputnumber';
import { TextareaModule } from 'primeng/textarea';
import { CommonModule } from '@angular/common';
import { MapComponent } from '../../components/map/map.component';
import { AuthService } from '../../services/auth.service';
import { MessageService } from 'primeng/api';
import { ToastModule } from 'primeng/toast';
import { Router } from '@angular/router';

@Component({
  selector: 'rea-publish-ad',
  imports: [
    CommonModule,
    FormsModule,
    ReactiveFormsModule,
    ButtonModule,
    InputTextModule,
    InputNumberModule,
    TextareaModule,
    MapComponent,
    ToastModule
  ],
  templateUrl: './publish-ad.component.html',
  providers: [MessageService]
})
export class PublishAdPage {
  publishService = inject(PublishService);
  authService = inject(AuthService);
  messageService = inject(MessageService);
  router = inject(Router);
  accountId: number | null = this.authService.getUserId();

  ad: PublishAdRequest = {} as PublishAdRequest;

  submitted = false;

  newAdForm = new FormGroup({
    title: new FormControl('', [Validators.required]),
    description: new FormControl('', [Validators.required]),
    price: new FormControl<number>(100000, [Validators.required]),
    squareMeters: new FormControl(100, [Validators.required]),
    location: new FormControl('', [Validators.required]),
    latitude: new FormControl<number|null>(0, [Validators.required]),
    longitude: new FormControl<number|null>(0, [Validators.required]),
    accountId: new FormControl<number|null>(this.accountId, [Validators.required])
  });
  

  publishAd() {
    this.submitted = true;

    this.newAdForm.disable();
    if (this.newAdForm.invalid) {
      this.newAdForm.enable();
      return;
    }
     // Assicurati che accountId non sia null
    const accountId = this.accountId;
    if (accountId === null) {
      console.error('Account ID is required but was null.');
      return;
    }

    // Inserisci accountId nel modello del form
    const adData: PublishAdRequest = this.newAdForm.getRawValue();

    this.publishService.publishAd(adData).subscribe(
      (response) => {
        this.showMessage('success', 'Il tuo annuncio è stato pubblicato con successo. Verrai rinderizzato alla pagina dei tuoi annunci', 'Annuncio preso in carico');

        setTimeout(() => {
          this.newAdForm.reset();
          this.newAdForm.enable();
          this.submitted = false;
          this.router.navigate(['pages/myads']);
        } , 3000);
        
      },
      (error) => {
        this.showMessage('error', 
          'Si è verificato un errore durante la pubblicazione del tuo annuncio. \
          Riprova più tardi. Consultare i log del browser per maggiori dettagli.', 'Errore di pubblicazione');
        this.newAdForm.enable();
        console.error(error);
      }
    );
  }

  onPointSelected(event: { lat: number; lng: number }) {
    this.newAdForm.patchValue({
      latitude: event.lat,
      longitude: event.lng
    });
  }

  showMessage(severity: string='success', message: string='Message Content', summary: string='Success Message') {
    this.messageService.add({ severity: severity, summary: summary, detail: message, key: 'br', life: 3000 });
  }  

}
