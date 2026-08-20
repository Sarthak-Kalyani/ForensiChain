# ForensiChain


### AI-Powered Digital Evidence Investigation Platform
- Secure MySQL database storage
- User authentication and access control


---


## Technology Stack


### Backend
- Java
- Spring Boot
- Spring Data JPA
- Maven


### Database
- MySQL
- AWS RDS


### Frontend
- Thymeleaf
- HTML
- CSS
- Bootstrap
- Bootstrap Icons
- JavaScript


### Digital Forensics
- SHA-256
- Tesseract OCR
- Forensic metadata extraction
- Apache PDFBox / OpenPDF


### Cloud
- AWS EC2
- AWS RDS


---


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
        +-------------+-------------+
        |             |             |
        v             v             v
   Case Management  Evidence     Authentication
                    Management
                        |
        +---------------+----------------+
        |               |                |
        v               v                v
   SHA-256          OCR Analysis    Metadata Analysis
   Integrity
        |               |                |
        +---------------+----------------+
                        |
                        v
                Risk Assessment
                        |
                        v
               Chain of Custody
                        |
                        v
              AI Investigation
                  Analysis
                        |
                        v
              PDF Report Generation
                        |
                        v
                 MySQL Database
                    (AWS RDS)


Digital Evidence Files
        |
        v
     AWS EC2
Digital Evidence Workflow
Upload Evidence
       |
       v
Validate Evidence
       |
       v
Generate SHA-256 Hash
       |
       v
Store Evidence Metadata
       |
       +------> OCR Text Extraction
       |
       +------> Forensic Metadata Extraction
       |
       +------> Risk Assessment
       |
       v
Store Evidence Record
       |
       v
Chain-of-Custody Tracking
       |
       v
Integrity Verification
       |
       +------> VALID - File Not Modified
       |
       +------> TAMPERED - File Changed
       |
       v
AI Investigation Analysis
       |
       v
Investigation Report
Evidence Integrity

ForensiChain uses SHA-256 cryptographic hashing to generate a unique fingerprint for uploaded evidence.

When evidence is uploaded, its SHA-256 hash is calculated and stored with the evidence record.

During verification, the physical evidence file is hashed again.

The newly generated hash is compared with the original stored hash.

Original Evidence
       |
       v
SHA-256 Hash
       |
       v
Stored Fingerprint
       |
       |
       | Verification
       v
Current Evidence
       |
       v
SHA-256 Hash
       |
       v
Compare Hashes
       |
       +---- Same ------> VALID
       |
       +---- Different -> TAMPERED

This allows the system to detect whether an evidence file has potentially been modified after registration.

OCR Analysis

For supported image evidence, ForensiChain uses Tesseract OCR to extract readable text from uploaded images.

The extracted content is stored with the evidence record and displayed on the evidence details page.

Example workflow:

Image Evidence
      |
      v
Tesseract OCR
      |
      v
Extracted Text
      |
      v
Evidence Database
      |
      v
Evidence Details

OCR is configured for the deployed Linux environment on AWS EC2.

Forensic Metadata

ForensiChain extracts technical information from uploaded evidence files.

Depending on the file type, metadata may include:

File name
File size
File extension
MIME type
Image dimensions
Color information
Compression information
File timestamps
File type information

This information helps investigators understand the technical characteristics of the evidence.

Chain of Custody

ForensiChain maintains an audit trail of important evidence operations.

Examples include:

UPLOAD
VERIFY
DOWNLOAD
DELETE

Each action can be associated with:

User
Evidence
Action
Date and time

This provides a traceable history of evidence interactions.

Investigation Management

Evidence can be associated with investigation cases.

Each case can contain information such as:

Case number
Case title
Case type
Priority
Status
Assigned officer
Investigator
Evidence records

This allows digital evidence to be organized within an investigation context.

Security

ForensiChain uses several security mechanisms including:

User authentication
Session-based access control
Evidence ownership validation
SHA-256 integrity verification
Evidence status management
Audit logging
Soft deletion of evidence
Database-backed evidence records
Cloud Deployment

The application is deployed on Amazon Web Services.

Application Server
AWS EC2
    |
    v
Spring Boot
    |
    v
ForensiChain Application
Database
Spring Boot
     |
     v
MySQL
     |
     v
AWS RDS

The current application can be accessed through the deployed EC2 instance.

Project Structure
ForensiChain/
│
├── src/
│   └── main/
│       ├── java/
│       │   └── com/sdcems/sdcems/
│       │       ├── controller/
│       │       ├── model/
│       │       ├── repository/
│       │       └── service/
│       │
│       └── resources/
│           ├── templates/
│           └── application.properties
│
├── uploads/
├── pom.xml
├── mvnw
└── README.md
Current Project Status

The core ForensiChain platform has been implemented and deployed for testing.

Currently implemented components include:

User authentication
Investigation case management
Digital evidence upload
SHA-256 integrity protection
Evidence verification
Tamper detection logic
OCR text extraction
Forensic metadata extraction
Evidence risk assessment
Chain-of-custody tracking
Audit logging
Evidence download
Soft deletion
AI-assisted investigation analysis
PDF report generation
AWS EC2 deployment
MySQL database hosted through AWS RDS
Current Testing

The following functionality has been successfully tested:

Evidence upload
SHA-256 fingerprint generation
Evidence verification
OCR text extraction
Forensic metadata extraction
Evidence details display
Chain-of-custody recording

Further end-to-end security and tamper-detection testing is being performed.

Future Improvements

Possible future improvements include:

Production domain with HTTPS
Nginx reverse proxy
Automated application startup using systemd
Improved role-based access control
Multi-language OCR
Advanced AI forensic analysis
Automated anomaly detection
Evidence similarity detection
Real-time investigation monitoring
Enhanced security hardening
Author

Sarthak Kalyani

B.Tech Computer Science Engineering

ForensiChain — AI-Powered Digital Evidence Investigation Platform



### Then in VS Code


Save `README.md`, then:


```powershell
git add README.md
git commit -m "Update ForensiChain README"
git push origin main