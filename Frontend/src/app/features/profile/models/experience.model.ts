export interface ExperiencePayload {
  id?: number;
  companyName: string;
  position: string;
  startDate: string; // ISO string
  endDate?: string | null;
}
