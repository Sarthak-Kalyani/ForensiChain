# ForensiChain

### AI-Powered Digital Evidence Investigation Platform

ForensiChain is a digital forensics and evidence management platform designed to securely store, analyze, verify, and investigate digital evidence.

The system maintains evidence integrity using SHA-256 cryptographic hashing and provides forensic analysis, OCR-based content extraction, metadata analysis, chain-of-custody tracking, and AI-assisted investigation summaries.

## Key Features

- Digital evidence upload and management
- SHA-256 evidence integrity verification
- Tamper detection
- Forensic metadata extraction
- OCR-based text extraction
- Evidence risk assessment
- Case and investigation management
- Chain-of-custody tracking
- Evidence verification and audit logs
- AI-powered investigation summaries
- PDF investigation report generation
- Evidence-to-case relationship tracking
- Secure MySQL database storage

## Technology Stack

- Java
- Spring Boot
- Spring Data JPA
- MySQL
- Thymeleaf
- Bootstrap
- SHA-256
- Tesseract OCR
- Apache PDFBox / OpenPDF
- Maven
- AWS RDS
- AWS EC2

## System Architecture

```text
User
  |
  v
ForensiChain Web Interface
  |
  v
Spring Boot Application
  |
  +---- Case Management
  |
  +---- Evidence Management
  |
  +---- SHA-256 Integrity Verification
  |
  +---- OCR & Metadata Analysis
  |
  +---- Chain of Custody
  |
  +---- AI Investigation Analysis
  |
  +---- PDF Report Generation
  |
  v
MySQL Database
(AWS RDS)

Digital Evidence Workflow
Upload Evidence
       |
       v
Generate SHA-256 Hash
       |
       v
Store Evidence Metadata
       |
       v
OCR & Forensic Analysis
       |
       v
Risk Assessment
       |
       v
Integrity Verification
       |
       v
Chain of Custody
       |
       v
AI Investigation Summary
       |
       v
Investigation Report
Security

ForensiChain uses SHA-256 cryptographic hashing to generate a unique fingerprint for uploaded evidence.

During verification, the evidence can be re-hashed and compared with its stored fingerprint to detect possible modification or tampering.

Current Deployment

The application is being prepared for cloud deployment using:

AWS EC2 for application hosting
AWS RDS for MySQL database hosting
Project Status

The core ForensiChain investigation platform has been implemented, including evidence management, forensic analysis, integrity verification, chain-of-custody tracking, AI investigation summaries, and PDF reporting.

Cloud deployment and production configuration are being completed.

Author

Sarthak Kalyani