package com.sdcems.sdcems.controller;

import com.sdcems.sdcems.model.InvestigationCase;
import com.sdcems.sdcems.repository.EvidenceRepository;
import com.sdcems.sdcems.repository.InvestigationCaseRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/cases")
public class InvestigationCaseController {

    private final InvestigationCaseRepository repo;
    private final EvidenceRepository evidenceRepo;

    public InvestigationCaseController(
            InvestigationCaseRepository repo,
            EvidenceRepository evidenceRepo) {

        this.repo = repo;
        this.evidenceRepo = evidenceRepo;
    }

    // ===========================
    // SHOW ALL CASES
    // ===========================

    @GetMapping
    public String allCases(Model model) {

        model.addAttribute("cases", repo.findAll());

        return "cases";
    }

    // ===========================
    // CREATE CASE PAGE
    // ===========================

    @GetMapping("/new")
    public String newCase(Model model) {

        model.addAttribute("caseObj", new InvestigationCase());

        return "create-case";
    }

    // ===========================
    // SAVE CASE
    // ===========================

    @PostMapping("/save")
    public String saveCase(
            @ModelAttribute("caseObj") InvestigationCase c) {

        if (c.getCaseNumber() == null || c.getCaseNumber().isBlank()) {

            c.setCaseNumber("CASE-" + System.currentTimeMillis());

        }

        if (c.getStatus() == null || c.getStatus().isBlank()) {

            c.setStatus("OPEN");

        }

        repo.save(c);

        return "redirect:/cases";

    }

    // ===========================
    // CASE DETAILS
    // ===========================

    @GetMapping("/{id}")
    public String caseDetails(
            @PathVariable Integer id,
            Model model) {

        InvestigationCase investigationCase =
                repo.findById(id).orElseThrow();

        model.addAttribute("case", investigationCase);

        model.addAttribute(
                "evidenceList",
                evidenceRepo.findByCaseId(id)
        );

        model.addAttribute(
                "totalEvidence",
                evidenceRepo.findByCaseId(id).size()
        );

        return "case-details";
    }

}