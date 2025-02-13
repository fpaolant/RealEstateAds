import { CommonModule } from '@angular/common';
import { Component, AfterViewInit, Output, EventEmitter, OnChanges, Input, SimpleChanges, OnDestroy, inject } from '@angular/core';
import { Router } from '@angular/router';
import * as L from 'leaflet';

// Correggi il percorso delle icone predefinite
(L.Icon.Default as any).prototype.options.iconUrl = '../leaflet/marker-icon.png';
(L.Icon.Default as any).prototype.options.iconRetinaUrl = '../leaflet/marker-icon-2x.png'; 
(L.Icon.Default as any).prototype.options.shadowUrl = '../leaflet/marker-shadow.png';

@Component({
  selector: 'rea-map',
  imports: [
    CommonModule
  ],
  template: `
    <div [id]="idMap" [ngStyle]="{'height': mapHeight, 'width': mapWidth}"></div>
  `
})
export class MapComponent implements AfterViewInit, OnChanges, OnDestroy {
  private map: L.Map | undefined;
  private circle: L.Circle | undefined;

  private labelMarker: L.Marker | undefined;
  private markers: L.Marker[] = []; // Array per tracciare i marker

  router = inject(Router);


  @Input() idMap: string = 'map'; // Default: id map
  // Input per il raggio del cerchio in metri
  @Input() radius: number | null = 0; // Default: 0 km

  // Input per le dimensioni della mappa
  @Input() mapHeight: string = '500px'; // Default: altezza 500px
  @Input() mapWidth: string = '100%';   // Default: larghezza 100%
  @Input() mapCenter: L.LatLngExpression = [42.35, 13.4]; // Coordinate di partenza (Abruzzo)

  @Input() points: {lat: number; lng: number; opts?: any }[] = [];


  // Evento per comunicare le coordinate selezionate al componente genitore
  @Output() pointSelected = new EventEmitter<{ lat: number; lng: number }>();
  // Evento per comunicare le coordinate al componente genitore
  @Output() centerChanged = new EventEmitter<{ lat: number; lng: number }>();


  ngAfterViewInit(): void {
    // Inizializza la mappa
    this.map = L.map(this.idMap).setView(this.mapCenter, 13); // Coordinate di partenza (Abruzzo)

    // Aggiungi un layer tiles (OpenStreetMap)
    L.tileLayer('https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png', {
      attribution: '© OpenStreetMap contributors'
    }).addTo(this.map);

    // Disegna il cerchio iniziale
    this.drawCircle();

    // Aggiungi i punti sulla mappa
    this.addPoints(this.points);

    // Gestisci il click sulla mappa
    this.map.on('click', (event: any) => {
      const lat = event.latlng.lat;
      const lng = event.latlng.lng;

      // Emetti le coordinate al componente genitore
      this.pointSelected.emit({ lat, lng });
    });

    // Gestisci il cambio di posizione del centro mappa
    this.map.on('moveend', () => {
      if (this.map) {
        const center = this.map.getCenter();

        this.updateCircle();
        // Emetti le coordinate al componente genitore
        this.centerChanged.emit({ lat: center.lat, lng: center.lng });
      }
    });
  }

  ngOnChanges(changes: SimpleChanges): void {
    if (changes['radius'] && this.map) {
      // Aggiorna il cerchio quando il raggio cambia
      this.drawCircle();
    }
    if (this.map && changes['points'] 
      && changes['points'].currentValue.length > 0
      && changes['points'].currentValue.length !== changes['points'].previousValue.length) {
      this.addPoints(this.points);
    }
  }

  ngOnDestroy(): void {
    if (this.map) {
      this.map.off(); // Rimuovi tutti gli eventi associati alla mappa
      this.map.remove(); // Rimuovi la mappa
    }
  }

  private drawCircle(): void {
    if (!this.map || !this.radius || this.radius<0) return;

    const center = this.map.getCenter();
    // Rimuovi il cerchio precedente, se esistente
    if (this.circle) {
      this.circle.remove();
    }

    // Disegna un nuovo cerchio con il raggio specificato
    this.circle = L.circle(center, {
      radius: this.radius, // Raggio in metri
      color: 'blue',
      fillColor: '#cce5ff',
      fillOpacity: 0.5
    }).addTo(this.map);

    // Rimuovi il marker precedente, se esistente
    if (this.labelMarker) {
      this.labelMarker.remove();
    }

    // Crea un marker con un'icona personalizzata contenente l'etichetta
    const radiusKm = this.radius / 1000;
    this.labelMarker = L.marker(center, {
      icon: L.divIcon({
        className: 'custom-label',
        html: `<div style="text-align: center; font-size: 14px; font-weight: bold;">${radiusKm} Km</div>`,
        iconSize: [30, 30],
        iconAnchor: [15, 15] // Posiziona il centro dell'icona esattamente sul centro del marker
      }),
      interactive: false // Impedisce che il marker sia cliccabile
    }).addTo(this.map);

    // Adatta lo zoom per includere il cerchio
    const bounds = this.circle.getBounds();
    this.map.fitBounds(bounds, { padding: [20, 20] });
  }

  private updateCircle(): void {
    if (!this.map || !this.circle || !this.labelMarker) return;

    // Aggiorna la posizione del cerchio al nuovo centro della mappa
    const center = this.map.getCenter();
    this.circle.setLatLng(center);


    // Aggiorna la posizione e il contenuto dell'etichetta
    const radiusKm = (this.radius)? this.radius / 1000: 0;
    this.labelMarker.setLatLng(center);
    this.labelMarker.setIcon(
      L.divIcon({
        className: 'custom-label',
        html: `<div style="text-align: center; font-size: 14px; font-weight: bold;">${radiusKm} Km</div>`,
        iconSize: [30, 30],
        iconAnchor: [15, 15]
      })
    );
  }

  addPoints(points: {lat: number; lng: number, opts?: any }[]): void {
    if (this.map) {
      // Rimuove tutti i marker precedenti
      this.clearMarkers();
      // Aggiunge i nuovi marker alla mappa e li memorizza
      points.forEach(point => {
        const marker = L.marker([point.lat, point.lng]).addTo(this.map!);
        if (point.opts) {
          (point.opts.title) ? marker.bindPopup(point.opts.title): null;
          (point.opts.icon) ? marker.setIcon(point.opts.icon): null;
          if(point.opts.route) {
            marker.on('click', () => {
              this.router.navigate([point.opts.route]);
            });
          }
        }
        this.markers.push(marker); // Memorizza il marker nell'array
      });
    }
  }
  
  // Metodo per rimuovere tutti i marker dalla mappa
  clearMarkers(): void {
    this.markers.forEach(marker => this.map!.removeLayer(marker)); 
    this.markers = []; // Svuota l'array dei marker
  }
}
