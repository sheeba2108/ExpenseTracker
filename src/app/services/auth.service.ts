import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { GoogleLoginProvider, SocialAuthService, SocialUser } from '@abacritt/angularx-social-login';
import { BehaviorSubject, Observable } from 'rxjs';
import { Router } from '@angular/router';

@Injectable({
  providedIn: 'root'
})
export class AuthService {
  private apiUrl = 'http://localhost:8080/api/users';  // Your backend URL
  private currentUserSubject: BehaviorSubject<SocialUser | null>;
  public currentUser: Observable<SocialUser | null>;

  constructor(
    private http: HttpClient,
    private authService: SocialAuthService,
    private router: Router
  ) {
    this.currentUserSubject = new BehaviorSubject<SocialUser | null>(null);
    this.currentUser = this.currentUserSubject.asObservable();
  }
 // Add this method for signup
 signup(user: any): Observable<any> {
  return this.http.post(`${this.apiUrl}/signup`, user);
}
  // To login with Email/Password
  loginWithEmailPassword(email: string, password: string) {
    return this.http.post(`${this.apiUrl}/login`, { email, password });
  }

  // To login with Google
  loginWithGoogle(socialUser: SocialUser) {
    const googleUser = {
      email: socialUser.email,
    };
    return this.http.post(`${this.apiUrl}/login/google`, googleUser);
  }

  // Handle Google sign-in
  signInWithGoogle(): void {
    this.authService.signIn(GoogleLoginProvider.PROVIDER_ID).then((user) => {
      this.currentUserSubject.next(user);
      this.loginWithGoogle(user).subscribe(
        (response) => {
          console.log('Google login successful!', response);
          this.router.navigate(['/dashboard']);
        },
        (error) => {
          console.error('Google login failed', error);
        }
      );
    });
  }

  // To log out
  logout() {
    this.authService.signOut().then(() => {
      this.currentUserSubject.next(null);
      this.router.navigate(['/login']);
    });
  }

  // To get the current logged-in user
  getCurrentUser(): Observable<SocialUser | null> {
    return this.currentUser;
  }
}
