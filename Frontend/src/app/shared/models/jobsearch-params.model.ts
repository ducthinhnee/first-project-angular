export interface JobSearchParams {
  keyword?: string;
  location?: string;
  jobType?: string;
  level?: string;
  minSalary?: number;
  maxSalary?: number;
  page?: number;
  size?: number;
}