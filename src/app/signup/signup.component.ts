import { Component } from '@angular/core';
import { AuthService } from '../services/auth.service';

@Component({
  selector: 'app-signup',
  standalone:false,
  templateUrl: './signup.component.html',
  styleUrls: ['./signup.component.css']
})
export class SignupComponent {
  email: string = '';
  password: string = '';

  constructor(private authService: AuthService) {}

  onSignup() {
    console.log('Signup form submitted!');
    const user = { email: this.email, password: this.password };
    
    this.authService.signup(user).subscribe(
      (response: any) => {  // Explicitly define response type
        console.log('Signup successful', response);
        // Redirect to dashboard or login after successful signup
      },
      (error: any) => {  // Explicitly define error type
        console.error('Signup failed', error);
      }
    );
  }
}
