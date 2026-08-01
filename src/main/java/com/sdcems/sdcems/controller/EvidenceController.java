package com.sdcems.sdcems.controller;

import com.sdcems.sdcems.model.Evidence;
import com.sdcems.sdcems.model.AuditLog;
import com.sdcems.sdcems.repository.EvidenceRepository;
import com.sdcems.sdcems.repository.AuditLogRepository;
import com.sdcems.sdcems.service.HashService;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;

@RestController
@RequestMapping("/evidence")
public class EvidenceController {

    private final EvidenceRepository repo;
    private final AuditLogRepository auditRepo;

    public EvidenceController(EvidenceRepository repo, AuditLogRepository auditRepo) {
        this.repo = repo;
        this.auditRepo = auditRepo;
    }

    // -------- UPLOAD FILE --------
    @PostMapping("/upload")
    public String upload(@RequestParam("file") MultipartFile file,
                     @RequestParam("userId") int userId, @RequestParam("caseId") int caseId) {
    try {
        File uploadDir = new File(System.getProperty("user.dir") + "/uploads");
        if (!uploadDir.exists()) uploadDir.mkdir();

        File savedFile = new File(uploadDir, file.getOriginalFilename());
        file.transferTo(savedFile);

        String hash = HashService.generateFileHash(new FileInputStream(savedFile));

        Evidence evidence = new Evidence();
        evidence.setCaseId(caseId);
        evidence.setFileName(savedFile.getName());
        evidence.setHashValue(hash);
        evidence.setUserId(userId);

        Evidence saved = repo.save(evidence);

        AuditLog log = new AuditLog();
        log.setUserId(userId);
        log.setEvidenceId(saved.getId());
        log.setAction("UPLOAD");
        auditRepo.save(log);

        return "<h3>File Uploaded Successfully</h3>" +
               "<p>Evidence ID: " + saved.getId() + "</p>" +
               "<a href='/uploadPage'>Upload Another</a><br>" +
               "<a href='/verifyPage'>Verify File</a>";

    } catch (Exception e) {
        return "Upload Failed";
    }
}

    // -------- VERIFY FILE (OWNER CHECK + LOG) --------
    @GetMapping("/verify/{id}")
    public String verify(@PathVariable int id, @RequestParam int userId) {
        try {
            Evidence evidence = repo.findById(id).orElseThrow();

            // Ownership validation
            if (evidence.getUserId() != userId)
                return "ACCESS DENIED - Not Owner";

            File file = new File(System.getProperty("user.dir") + "/uploads/" + evidence.getFileName());

            if (!file.exists())
                return "FILE MISSING";

            String newHash = HashService.generateFileHash(new FileInputStream(file));

            // Log verification action
            AuditLog log = new AuditLog();
            log.setUserId(userId);
            log.setEvidenceId(evidence.getId());
            log.setAction("VERIFY");
            auditRepo.save(log);

            if (newHash.equals(evidence.getHashValue()))
                return "VALID - File Not Modified";
            else
                return "TAMPERED - File Changed";

        } catch (Exception e) {
            return "ERROR VERIFYING FILE";
        }
    }
}