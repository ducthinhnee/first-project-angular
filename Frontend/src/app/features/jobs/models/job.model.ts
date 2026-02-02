export interface Job {
  id: number;
  title: string;
  companyName: string;
  location: string;
  description?: string;

  salaryMin: number;
  salaryMax: number;

  jobType: 'FULL_TIME' | 'PART_TIME' | 'INTERNSHIP';
  level: 'JUNIOR' | 'MIDDLE' | 'SENIOR';
  status: 'OPEN' | 'CLOSED';
}
