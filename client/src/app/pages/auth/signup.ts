import { Component, inject, OnInit } from "@angular/core";
import { FormControl, FormGroup, FormsModule, ReactiveFormsModule, Validators } from "@angular/forms";
import { ActivatedRoute, Router, RouterModule } from "@angular/router";
import { ButtonModule } from "primeng/button";
import { CheckboxModule } from "primeng/checkbox";
import { InputTextModule } from "primeng/inputtext";
import { PasswordModule } from "primeng/password";
import { RippleModule } from "primeng/ripple";
import { AppFloatingConfigurator } from "../../layout/component/app.floatingconfigurator";
import { OpenAccountRequest } from "../../services/account.service";
import { IconField } from "primeng/iconfield";
import { InputIcon } from "primeng/inputicon";
import { MessageModule } from "primeng/message";
import { CommonModule } from "@angular/common";
import { PublishService } from "../../services/publish.service";
import { ToastModule } from "primeng/toast";
import { MessageService } from "primeng/api";

@Component({
  selector: "rea-signup",
  imports: [
    CommonModule,
    ReactiveFormsModule,
    ButtonModule,
    CheckboxModule,
    InputTextModule,
    PasswordModule,
    FormsModule,
    RouterModule,
    RippleModule,
    AppFloatingConfigurator,
    IconField,
    InputIcon,
    MessageModule,
    ToastModule,
  ],
  providers: [MessageService],
  templateUrl: './signup.html'
})
export class Signup implements OnInit {
  messageService = inject(MessageService);
  publishService: PublishService = inject(PublishService);

  returnUrl: string = "/"; // Default redirect

  submitted = false;
  signupForm = new FormGroup({
    name: new FormControl<string>('fabio', [Validators.required, Validators.nullValidator]),
    surname: new FormControl<string>('paol', [Validators.required, Validators.nullValidator]),
    username: new FormControl<string>('admin', [Validators.required, Validators.nullValidator]),
    password: new FormControl<string>('admin', [Validators.required, Validators.nullValidator]),
    email: new FormControl<string>('fabio@realestateads.it', [Validators.required, Validators.email, Validators.nullValidator]),
    mobile: new FormControl<string>('3316667023', [Validators.required, Validators.nullValidator]),
  });



  constructor(
    private route: ActivatedRoute,
    private router: Router
  ) {}

  //checked: boolean = false;

  ngOnInit() {
    this.route.queryParams.subscribe((params) => {
      this.returnUrl = params["returnUrl"] || "/";
    });
  }

  onLogin() {
    this.router.navigate(["/auth/login"], {
      queryParams: { returnUrl: this.returnUrl },
    });
  }

  onSignup() {
    this.submitted = true;

    this.signupForm.disable();
    if (this.signupForm.invalid) {
      this.signupForm.enable();
      return;
    }

    const userData: OpenAccountRequest = Object.assign({}, this.signupForm.getRawValue(), {
      name: this.signupForm.get('name')?.value ?? '',
      surname: this.signupForm.get('surname')?.value ?? '',
      username: this.signupForm.get('username')?.value ?? '',
      password: this.signupForm.get('password')?.value ?? '',
      email: this.signupForm.get('email')?.value ?? '',
      mobile: this.signupForm.get('mobile')?.value ?? ''
    });

    this.publishService.openPublishAccount(userData).subscribe({
      next: () => {
        this.submitted = false;
        this.showMessage(
          "success",
          "Registrazione effettuata! Verrai reinderizzato alla pagina di login a breve",
          "Registrazione effettuata"
        );
        setTimeout(() => {
          this.router.navigate(["/auth/login"]);
        }, 3000);
        this.signupForm.enable();
      },
      error: (err) => {
        this.showMessage("error", "Errore durante la registrazione", "Errore");
        console.log(err);
        this.submitted = false;
        this.signupForm.enable();
      },
    });
  }

  showMessage(
    severity: string = "success",
    message: string = "Message Content",
    summary: string = "Success Message"
  ) {
    this.messageService.add({
      severity: severity,
      summary: summary,
      detail: message,
      key: "br",
      life: 3000,
    });
  }
}
