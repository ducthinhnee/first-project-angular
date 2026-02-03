export interface CompanyJob {
  id: number;
  title: string;
  type: 'Full Time' | 'Part Time' | 'Internship';
  remainingDays: number;
  status: 'ACTIVE' | 'EXPIRED';
  applications: number;
}