import { Component } from "@angular/core";
import { HeroSectionComponent } from "../../components/hero-section/hero-section.component";
import { FeaturedJobsComponent } from "../../components/featured-jobs/featured-jobs.component";

@Component({
  standalone: true,
  selector: 'app-home-page',
  imports: [
    HeroSectionComponent,
    FeaturedJobsComponent
  ],
  templateUrl: './home-page.component.html'
})
export class HomePageComponent {}