INSERT INTO users (id, email, password, role, status) VALUES
(1, 'admin@job.com', '$2a$10$admin', 'ADMIN', 'ACTIVE'),

(2, 'employer1@job.com', '$2a$10$employer', 'EMPLOYER', 'ACTIVE'),
(3, 'employer2@job.com', '$2a$10$employer', 'EMPLOYER', 'ACTIVE'),

(4, 'candidate1@job.com', '$2a$10$candidate', 'CANDIDATE', 'ACTIVE'),
(5, 'candidate2@job.com', '$2a$10$candidate', 'CANDIDATE', 'ACTIVE'),
(6, 'candidate3@job.com', '$2a$10$candidate', 'CANDIDATE', 'ACTIVE');

INSERT INTO companies (id, name, description, website, location) VALUES
(1, 'Tech Solutions', 'Software outsourcing company', 'https://techsolutions.com', 'Ho Chi Minh'),
(2, 'FinTech Hub', 'Financial technology startup', 'https://fintechhub.com', 'Ha Noi'),
(3, 'AI Factory', 'AI & Data company', 'https://aifactory.com', 'Da Nang');

INSERT INTO candidate_profiles (id, user_id, full_name, phone, summary) VALUES
(1, 4, 'Nguyen Van A', '0909000001', 'Java Backend Developer'),
(2, 5, 'Tran Thi B', '0909000002', 'Frontend Angular Developer'),
(3, 6, 'Le Van C', '0909000003', 'Fullstack Developer');

INSERT INTO skills (id, name) VALUES
(1, 'Java'),
(2, 'Spring Boot'),
(3, 'MySQL'),
(4, 'Angular'),
(5, 'React'),
(6, 'Docker');

INSERT INTO profile_skills (profile_id, skill_id) VALUES
-- Candidate 1
(1, 1),
(1, 2),
(1, 3),

-- Candidate 2
(2, 4),
(2, 5),

-- Candidate 3
(3, 1),
(3, 4),
(3, 6);

INSERT INTO experiences (id, profile_id, company_name, position, start_date, end_date) VALUES
(1, 1, 'FPT Software', 'Java Developer', '2020-01-01', '2022-12-31'),
(2, 1, 'VNPay', 'Backend Engineer', '2023-01-01', NULL),

(3, 2, 'Tiki', 'Frontend Developer', '2021-06-01', '2023-06-01'),

(4, 3, 'VNG', 'Fullstack Developer', '2022-03-01', NULL);

INSERT INTO resumes (id, profile_id, file_url) VALUES
(1, 1, 'https://cdn.job.com/cv/nguyenvana.pdf'),
(2, 2, 'https://cdn.job.com/cv/tranthib.pdf'),
(3, 3, 'https://cdn.job.com/cv/levanc.pdf'),
(4, 3, 'https://cdn.job.com/cv/levanc_v2.pdf');

INSERT INTO jobs (
  company_id, title, description, location,
  salary_min, salary_max, job_type, level, status
) VALUES
(1, 'Java Backend Developer',
 'Build REST APIs with Spring Boot',
 'Ho Chi Minh', 1500.00, 2500.00, 'FULL_TIME', 'MIDDLE', 'OPEN'),

(1, 'Angular Developer',
 'Develop frontend using Angular',
 'Remote', 1200.00, 2000.00, 'FULL_TIME', 'JUNIOR', 'OPEN'),

(2, 'Backend Engineer',
 'Develop microservices',
 'Ha Noi', 1800.00, 3000.00, 'FULL_TIME', 'SENIOR', 'OPEN'),

(3, 'Fullstack Developer',
 'Java + Angular fullstack',
 'Da Nang', 1600.00, 2800.00, 'FULL_TIME', 'MIDDLE', 'OPEN'),

(1, 'DevOps Engineer',
 'Manage CI/CD pipelines and cloud infrastructure',
 'Ho Chi Minh', 2000.00, 3500.00, 'FULL_TIME', 'SENIOR', 'OPEN'),

(2, 'React Developer',
 'Build modern web applications with React',
 'Ha Noi', 1400.00, 2200.00, 'FULL_TIME', 'MIDDLE', 'OPEN'),

(3, 'Python Developer',
 'Develop backend services with Django/Flask',
 'Remote', 1300.00, 2100.00, 'FULL_TIME', 'JUNIOR', 'OPEN'),

(1, 'Mobile Developer',
 'Build iOS and Android apps with Flutter',
 'Ho Chi Minh', 1600.00, 2600.00, 'FULL_TIME', 'MIDDLE', 'OPEN'),

(2, 'QA Engineer',
 'Manual and automation testing',
 'Ha Noi', 1000.00, 1800.00, 'FULL_TIME', 'JUNIOR', 'OPEN'),

(3, 'Data Engineer',
 'Build data pipelines and ETL processes',
 'Da Nang', 2200.00, 3800.00, 'FULL_TIME', 'SENIOR', 'OPEN'),

(1, 'UI/UX Designer',
 'Design user interfaces and experiences',
 'Remote', 1100.00, 1900.00, 'FULL_TIME', 'MIDDLE', 'OPEN'),

(2, 'NodeJS Developer',
 'Build scalable backend services with NodeJS',
 'Ho Chi Minh', 1500.00, 2400.00, 'FULL_TIME', 'MIDDLE', 'OPEN'),

(3, 'Java Spring Developer',
 'Develop enterprise applications with Spring Framework',
 'Ha Noi', 1700.00, 2900.00, 'FULL_TIME', 'SENIOR', 'OPEN'),

(1, 'Frontend Intern',
 'Learn and develop with React/Angular',
 'Ho Chi Minh', 300.00, 600.00, 'INTERN', 'JUNIOR', 'OPEN'),

(2, 'Product Manager',
 'Manage product development lifecycle',
 'Remote', 2500.00, 4000.00, 'FULL_TIME', 'SENIOR', 'OPEN'),

(3, 'Business Analyst',
 'Analyze business requirements and create specifications',
 'Da Nang', 1200.00, 2000.00, 'FULL_TIME', 'MIDDLE', 'OPEN'),

(1, 'Security Engineer',
 'Implement security measures and conduct audits',
 'Ho Chi Minh', 2000.00, 3500.00, 'FULL_TIME', 'SENIOR', 'OPEN'),

(2, 'Scrum Master',
 'Facilitate Agile development processes',
 'Ha Noi', 1800.00, 2800.00, 'FULL_TIME', 'MIDDLE', 'OPEN'),

(3, 'Machine Learning Engineer',
 'Build and deploy ML models',
 'Remote', 2500.00, 4500.00, 'FULL_TIME', 'SENIOR', 'OPEN'),

(1, 'Part-time Java Developer',
 'Develop and maintain Java applications',
 'Ho Chi Minh', 800.00, 1200.00, 'PART_TIME', 'JUNIOR', 'OPEN');

INSERT INTO applications (id, job_id, candidate_id, resume_id, status) VALUES
(1, 1, 4, 1, 'APPLIED'),
(2, 2, 5, 2, 'VIEWED'),
(3, 3, 6, 3, 'INTERVIEW'),
(4, 4, 6, 4, 'APPLIED');

INSERT INTO saved_jobs (job_id, candidate_id) VALUES
(1, 4),
(2, 4),
(3, 5),
(4, 6);
