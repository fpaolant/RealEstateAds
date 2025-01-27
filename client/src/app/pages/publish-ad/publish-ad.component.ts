import { Component, inject, OnInit } from '@angular/core';
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
    MapComponent
  ],
  templateUrl: './publish-ad.component.html',
  styleUrl: './publish-ad.component.scss',
  providers: [MessageService]
})
export class PublishAdPage implements OnInit {
  publishService = inject(PublishService);
  authService = inject(AuthService);
  messageService = inject(MessageService);
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


  ngOnInit() {
    
  }
  

  publishAd() {
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
    
    
      console.log(adData);

    this.publishService.publishAd(adData).subscribe(
      (response) => {
        console.log(response);
        this.messageService.add({
          severity: 'success',
          summary: 'Annuncio pubblicato',
          detail: 'Il tuo annuncio è stato pubblicato con successo.'
        });
        this.newAdForm.reset();
        this.newAdForm.enable();
      },
      (error) => {
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

}
