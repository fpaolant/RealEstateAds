import { Component } from '@angular/core';
import { ButtonModule } from 'primeng/button';

@Component({
  selector: 'rea-apidoc',
  imports: [
    ButtonModule
  ],
  templateUrl: './apidoc.component.html',
})
export class ApidocPage {

  openWS(url: string): void {
    window.open(url, '_blank');
  }

}
