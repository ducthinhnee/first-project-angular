CREATE TABLE users (
  id BIGINT AUTO_INCREMENT PRIMARY KEY,
  email VARCHAR(255) NOT NULL UNIQUE,
  password VARCHAR(255) NOT NULL,
  role ENUM('CANDIDATE', 'EMPLOYER', 'ADMIN') NOT NULL,
  status ENUM('ACTIVE', 'INACTIVE', 'BANNED') DEFAULT 'ACTIVE',
  created_at DATETIME DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE companies (
  id BIGINT AUTO_INCREMENT PRIMARY KEY,
  name VARCHAR(255) NOT NULL,
  description TEXT,
  logo_url VARCHAR(500),
  website VARCHAR(255),
  location VARCHAR(255),
  created_at DATETIME DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE candidate_profiles (
  id BIGINT AUTO_INCREMENT PRIMARY KEY,
  user_id BIGINT NOT NULL UNIQUE,
  full_name VARCHAR(255),
  phone VARCHAR(50),
  summary TEXT,

  FOREIGN KEY (user_id) REFERENCES users(id)
);

CREATE TABLE skills (
  id BIGINT AUTO_INCREMENT PRIMARY KEY,
  name VARCHAR(100) NOT NULL UNIQUE
);

CREATE TABLE profile_skills (
  profile_id BIGINT NOT NULL,
  skill_id BIGINT NOT NULL,
  PRIMARY KEY (profile_id, skill_id),

  FOREIGN KEY (profile_id) REFERENCES candidate_profiles(id),
  FOREIGN KEY (skill_id) REFERENCES skills(id)
);

CREATE TABLE experiences (
  id BIGINT AUTO_INCREMENT PRIMARY KEY,
  profile_id BIGINT NOT NULL,
  company_name VARCHAR(255),
  position VARCHAR(255),
  start_date DATE,
  end_date DATE,

  FOREIGN KEY (profile_id) REFERENCES candidate_profiles(id)
);

CREATE TABLE resumes (
  id BIGINT AUTO_INCREMENT PRIMARY KEY,
  profile_id BIGINT NOT NULL,
  file_url VARCHAR(500) NOT NULL,
  created_at DATETIME DEFAULT CURRENT_TIMESTAMP,

  FOREIGN KEY (profile_id) REFERENCES candidate_profiles(id)
);

CREATE TABLE jobs (
  id BIGINT AUTO_INCREMENT PRIMARY KEY,
  company_id BIGINT NOT NULL,
  title VARCHAR(255) NOT NULL,
  description TEXT,
  location VARCHAR(255),
  salary_min DECIMAL(12,2),
  salary_max DECIMAL(12,2),
  job_type ENUM('FULL_TIME', 'PART_TIME', 'INTERN', 'CONTRACT'),
  level ENUM('JUNIOR', 'MIDDLE', 'SENIOR'),
  status ENUM('DRAFT', 'OPEN', 'CLOSED') DEFAULT 'DRAFT',
  created_at DATETIME DEFAULT CURRENT_TIMESTAMP,

  FOREIGN KEY (company_id) REFERENCES companies(id)
);

CREATE TABLE applications (
  id BIGINT AUTO_INCREMENT PRIMARY KEY,
  job_id BIGINT NOT NULL,
  candidate_id BIGINT NOT NULL,
  resume_id BIGINT NOT NULL,
  status ENUM('APPLIED', 'VIEWED', 'INTERVIEW', 'REJECTED') DEFAULT 'APPLIED',
  applied_at DATETIME DEFAULT CURRENT_TIMESTAMP,

  UNIQUE (job_id, candidate_id),

  FOREIGN KEY (job_id) REFERENCES jobs(id),
  FOREIGN KEY (candidate_id) REFERENCES users(id),
  FOREIGN KEY (resume_id) REFERENCES resumes(id)
);

CREATE TABLE saved_jobs (
  id BIGINT AUTO_INCREMENT PRIMARY KEY,
  job_id BIGINT NOT NULL,
  candidate_id BIGINT NOT NULL,
  saved_at DATETIME DEFAULT CURRENT_TIMESTAMP,

  UNIQUE (job_id, candidate_id),

  FOREIGN KEY (job_id) REFERENCES jobs(id),
  FOREIGN KEY (candidate_id) REFERENCES users(id)
);