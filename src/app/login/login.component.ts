import { Component } from '@angular/core';
import { AuthService } from '../services/auth.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-login',
  standalone:false,
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent {
  email: string = '';
  password: string = '';
  wrongAttempts: number = 0;
  showForgotPassword: boolean = false;

  constructor(private authService: AuthService, private router: Router) {}

  onLogin() {
    const credentials = { email: this.email, password: this.password };

    this.authService.loginWithEmailPassword(credentials.email, credentials.password).subscribe({
      next: (response: any) => {
        console.log('Login successful!', response);
        this.router.navigate(['/dashboard']);
      },
      error: (error) => {
        console.error('Login failed', error);
        this.wrongAttempts++;
        if (this.wrongAttempts >= 2) {
          this.showForgotPassword = true;
        }
      }
    });
  }

  onGoogleLogin() {
    this.authService.signInWithGoogle();
  }
}
