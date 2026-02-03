import { Skill } from "./skill.model";

export interface CandidateProfile {
  id: number;
  fullName: string;
  phone: string;
  summary: string;
  skills: Skill[];
}