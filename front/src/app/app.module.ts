import { NgModule } from '@angular/core';
import { MatButtonModule } from '@angular/material/button';
import { BrowserModule } from '@angular/platform-browser';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { HomeComponent } from './pages/home/home.component';
import { RegisterComponent } from './pages/register/register.component';
import { LoginComponent } from './pages/login/login.component';
import { ArticlesComponent } from './pages/articles/articles.component';
import { ThemesComponent } from './pages/themes/themes.component';
import { ArticleDetailsComponent } from './pages/article-details/article-details.component';
import { ArticleCreationComponent } from './pages/article-creation/article-creation.component';
import { ProfileComponent } from './pages/profile/profile.component';
import { NotFoundComponent } from './pages/not-found/not-found.component';
import { CustomButtonComponent } from './components/custom-button/custom-button.component';

@NgModule({
  declarations: [AppComponent, HomeComponent, RegisterComponent, LoginComponent, ArticlesComponent, ThemesComponent, ArticleDetailsComponent, ArticleCreationComponent, ProfileComponent, NotFoundComponent, CustomButtonComponent],
  imports: [
    BrowserModule,
    AppRoutingModule,
    BrowserAnimationsModule,
    MatButtonModule,
  ],
  providers: [],
  bootstrap: [AppComponent],
})
export class AppModule {}
