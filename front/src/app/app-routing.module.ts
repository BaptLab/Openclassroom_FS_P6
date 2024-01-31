import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { HomeComponent } from './pages/home/home.component';
import { LoginComponent } from './pages/login/login.component';
import { RegisterComponent } from './pages/register/register.component';
import { ThemesComponent } from './pages/themes/themes.component';
import { ArticlesComponent } from './pages/articles/articles.component';
import { ProfileComponent } from './pages/profile/profile.component';
import { ArticleCreationComponent } from './pages/article-creation/article-creation.component';
import { ArticleDetailsComponent } from './pages/article-details/article-details.component';
import { AuthGuard } from './guards/auth.guard';
import { UnauthGuard } from './guards/unauth.guard';

const routes: Routes = [
  { path: '', canActivate: [UnauthGuard], component: HomeComponent },
  { path: 'login', canActivate: [UnauthGuard], component: LoginComponent },
  { path: 'register', canActivate: [AuthGuard], component: RegisterComponent },
  { path: 'themes', canActivate: [AuthGuard], component: ThemesComponent },
  { path: 'articles', canActivate: [AuthGuard], component: ArticlesComponent },
  { path: 'profile', canActivate: [AuthGuard], component: ProfileComponent },
  {
    path: 'article/creation',
    canActivate: [AuthGuard],
    component: ArticleCreationComponent,
  },
  {
    path: 'article/details',
    canActivate: [AuthGuard],
    component: ArticleDetailsComponent,
  },
  {
    path: 'article/:id',
    canActivate: [AuthGuard],
    component: ArticleDetailsComponent,
  },
  { path: '**', canActivate: [UnauthGuard], component: HomeComponent },
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule],
})
export class AppRoutingModule {}
