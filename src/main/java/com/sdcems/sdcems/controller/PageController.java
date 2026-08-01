package com.sdcems.sdcems.controller;

import com.sdcems.sdcems.model.Evidence;
import com.sdcems.sdcems.repository.EvidenceRepository;
import com.sdcems.sdcems.repository.InvestigationCaseRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class PageController {

    private final EvidenceRepository evidenceRepo;
    private final InvestigationCaseRepository caseRepo;

    public PageController(EvidenceRepository evidenceRepo,
                          InvestigationCaseRepository caseRepo) {
        this.evidenceRepo = evidenceRepo;
        this.caseRepo = caseRepo;
    }

    @GetMapping("/")
    public String home() {
        return "login";
    }

    @GetMapping("/uploadPage")
    public String uploadPage(Model model) {

        model.addAttribute("cases", caseRepo.findAll());

        return "upload";
    }

    @GetMapping("/verifyPage")
    public String verifyPage() {
        return "verify";
    }

    @GetMapping("/dashboard")
    public String dashboard(Model model) {

        model.addAttribute("evidences", evidenceRepo.findAll());
        model.addAttribute("totalEvidence", evidenceRepo.count());
        model.addAttribute("totalCases", caseRepo.count());

        return "dashboard";
    }

    @GetMapping("/evidence/{id}")
    public String evidenceDetails(@PathVariable int id, Model model) {

        Evidence evidence = evidenceRepo.findById(id).orElseThrow();

        model.addAttribute("evidence", evidence);

        return "evidence-details";
    }

}