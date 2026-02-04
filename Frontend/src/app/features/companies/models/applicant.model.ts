export interface Applicant {
  applicationId: number;
  candidateId: number;
  email: string;
  fullName: string | null;
  resumeUrl: string;
  skills: string[];
  status: string;
  appliedAt: string | null;
}
