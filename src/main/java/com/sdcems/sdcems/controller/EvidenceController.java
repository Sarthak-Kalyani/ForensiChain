package com.sdcems.sdcems.controller;

import com.sdcems.sdcems.model.AuditLog;
import com.sdcems.sdcems.model.Evidence;
import com.sdcems.sdcems.repository.AuditLogRepository;
import com.sdcems.sdcems.repository.EvidenceRepository;
import com.sdcems.sdcems.service.HashService;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;

@RestController
@RequestMapping("/evidence")
public class EvidenceController {

    private final EvidenceRepository repo;
    private final AuditLogRepository auditRepo;

    public EvidenceController(EvidenceRepository repo,
                              AuditLogRepository auditRepo) {
        this.repo = repo;
        this.auditRepo = auditRepo;
    }

    // ==========================================
    // UPLOAD EVIDENCE
    // ==========================================

    @PostMapping("/upload")
    public String upload(@RequestParam("file") MultipartFile file,
                         @RequestParam("userId") int userId,
                         @RequestParam("caseId") int caseId) {

        try {

            File uploadDir = new File(System.getProperty("user.dir") + "/uploads");

            if (!uploadDir.exists()) {
                uploadDir.mkdir();
            }

            File savedFile = new File(uploadDir, file.getOriginalFilename());

            file.transferTo(savedFile);

            String hash = HashService.generateFileHash(
                    new FileInputStream(savedFile));

            Evidence evidence = new Evidence();
            evidence.setFileName(savedFile.getName());
            evidence.setHashValue(hash);
            evidence.setUserId(userId);
            evidence.setCaseId(caseId);
            evidence.setStatus("ACTIVE");
            // =========================
            // // Metadata
            // // =========================
            
            evidence.setFileSize(file.getSize());
            evidence.setContentType(file.getContentType());

            String fileName = file.getOriginalFilename();
            if (fileName != null && fileName.contains(".")) {
                evidence.setFileExtension(
                    fileName.substring(fileName.lastIndexOf(".") + 1).toUpperCase());
                } else {
                    evidence.setFileExtension("UNKNOWN");
                }

            Evidence saved = repo.save(evidence);

            AuditLog log = new AuditLog();
            log.setUserId(userId);
            log.setEvidenceId(saved.getId());
            log.setAction("UPLOAD");

            auditRepo.save(log);

            return "<h2>✅ File Uploaded Successfully</h2>"
                    + "<p><b>Evidence ID :</b> " + saved.getId() + "</p>"
                    + "<p><b>Case ID :</b> " + saved.getCaseId() + "</p>"
                    + "<br>"
                    + "<a href='/uploadPage'>Upload Another</a><br><br>"
                    + "<a href='/dashboard'>Dashboard</a>";

        } catch (Exception e) {

            return "UPLOAD FAILED : " + e.getMessage();

        }

    }

    // ==========================================
    // VERIFY EVIDENCE
    // ==========================================

    @GetMapping("/verify/{id}")
    public String verify(@PathVariable Integer id,
                         @RequestParam Integer userId) {

        try {

            Evidence evidence = repo.findById(id).orElseThrow();

            if (!evidence.getUserId().equals(userId)) {
                return "ACCESS DENIED - Not Owner";
            }

            File file = new File(
                    System.getProperty("user.dir")
                            + "/uploads/"
                            + evidence.getFileName());

            if (!file.exists()) {
                return "FILE MISSING";
            }

            String newHash = HashService.generateFileHash(
                    new FileInputStream(file));

            AuditLog log = new AuditLog();
            log.setUserId(userId);
            log.setEvidenceId(evidence.getId());
            log.setAction("VERIFY");

            auditRepo.save(log);

            if (newHash.equals(evidence.getHashValue())) {
                return "VALID - File Not Modified";
            }

            return "TAMPERED - File Changed";

        } catch (Exception e) {

            return "ERROR VERIFYING FILE";

        }

    }

    // ==========================================
    // DOWNLOAD EVIDENCE
    // ==========================================

    @GetMapping("/download/{id}")
    public ResponseEntity<Resource> download(@PathVariable Integer id,
                                             @RequestParam Integer userId) {

        try {

            Evidence evidence = repo.findById(id).orElseThrow();

            if (!evidence.getUserId().equals(userId)) {
                return ResponseEntity.badRequest().build();
            }

            File file = new File(
                    System.getProperty("user.dir")
                            + "/uploads/"
                            + evidence.getFileName());

            if (!file.exists()) {
                return ResponseEntity.notFound().build();
            }

            AuditLog log = new AuditLog();
            log.setUserId(userId);
            log.setEvidenceId(evidence.getId());
            log.setAction("DOWNLOAD");

            auditRepo.save(log);

            Resource resource = new FileSystemResource(file);

            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION,
                            "attachment; filename=\"" + file.getName() + "\"")
                    .body(resource);

        } catch (Exception e) {

            return ResponseEntity.internalServerError().build();

        }

    }

    // ==========================================
    // SOFT DELETE EVIDENCE
    // ==========================================

    @GetMapping("/delete/{id}")
    public String deleteEvidence(@PathVariable Integer id,
                                 @RequestParam Integer userId) {

        try {

            Evidence evidence = repo.findById(id).orElseThrow();

            if (!evidence.getUserId().equals(userId)) {
                return "ACCESS DENIED - Not Owner";
            }

            evidence.setStatus("DELETED");

            repo.save(evidence);

            AuditLog log = new AuditLog();
            log.setUserId(userId);
            log.setEvidenceId(evidence.getId());
            log.setAction("DELETE");

            auditRepo.save(log);

            return "<h2>✅ Evidence Deleted Successfully</h2>"
                    + "<br><a href='/cases/"
                    + evidence.getCaseId()
                    + "'>⬅ Back to Investigation Workspace</a>";

        } catch (Exception e) {

            return "DELETE FAILED : " + e.getMessage();

        }

    }

}