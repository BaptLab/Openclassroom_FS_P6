import { Injectable } from '@angular/core';
import {
  CanActivate,
  Router,
  ActivatedRouteSnapshot,
  RouterStateSnapshot,
} from '@angular/router';
import { SessionService } from 'src/services/session/session.service';

@Injectable({ providedIn: 'root' })
export class AuthGuard implements CanActivate {
  constructor(private router: Router, private sessionService: SessionService) {}

  canActivate(
    route: ActivatedRouteSnapshot,
    state: RouterStateSnapshot
  ): boolean {
    // Check if the route is one of the allowed routes for unauthenticated users
    const allowedRoutes = ['/', '/login', '/register'];
    if (allowedRoutes.includes(state.url)) {
      return true; // Allow access to the specified routes
    }

    // Check if the user is logged in
    if (!this.sessionService.isLogged) {
      this.router.navigate(['/login']);
      return false;
    }

    return true;
  }
}
