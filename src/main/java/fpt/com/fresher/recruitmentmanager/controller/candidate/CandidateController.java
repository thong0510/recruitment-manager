package fpt.com.fresher.recruitmentmanager.controller.candidate;

import fpt.com.fresher.recruitmentmanager.object.entity.Candidates;
import fpt.com.fresher.recruitmentmanager.object.filter.CandidateFilter;
import fpt.com.fresher.recruitmentmanager.object.mapper.CandidateMapper;
import fpt.com.fresher.recruitmentmanager.object.request.CandidateRequest;
import fpt.com.fresher.recruitmentmanager.object.response.CandidateResponse;
import fpt.com.fresher.recruitmentmanager.service.CandidateService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequiredArgsConstructor
public class CandidateController {

    private final CandidateService candidateService;
    private final CandidateMapper candidateMapper;

    @GetMapping("/hr/list-potential-candidate")
    public String getCandidatesList(Model model) {

        CandidateFilter filter = new CandidateFilter();
        Page<Candidates> listOfCandidate = candidateService.getAllCandidates(filter);
        List<CandidateResponse> listOfCandidateResponse = listOfCandidate.getContent().stream().map(candidateMapper::entityToCandidateResponse).collect(Collectors.toList());
        model.addAttribute("listC", listOfCandidateResponse);

        return "hr/list";
    }

    @GetMapping("/hr/create-candidate")
    public String createCandidate(Model model) {
        CandidateRequest candidateRequest = new CandidateRequest();
        model.addAttribute("candidate", candidateRequest);
        return "hr/create-candidate";
    }

    @PostMapping("/hr/create-candidate")
    public String createCandidate(@ModelAttribute(name = "candidate") CandidateRequest candidateRequest) {
        candidateService.createCandidate(candidateRequest);
        return "redirect:/hr/list-potential-candidate";
    }

//    @GetMapping("/hr/info-potential-candidate")
//    public String detailCandidates(Model model, @RequestParam(name = "id") int id) {
//        CandidatesDTO CandidatesDTO = CandidatesService.findOne(id);
//        model.addAttribute("candidate", CandidatesDTO);
//
//        return "/hr/potential-candidate";
//    }
//
//    @GetMapping("/hr/delete-potential-candidate")
//    public String deleteCandidates(Model model, @RequestParam(name = "id") int id) {
//
//        CandidatesService.deleteCandidate(id);
//
//        return "redirect:/hr/list-potential-candidate";
//    }
//
//    @GetMapping("/hr/update-potential-candidate")
//    public String updateCandidates(Model model, @RequestParam(name = "id") int id) {
//        CandidatesDTO CandidatesDTO = CandidatesService.findOne(id);
//        model.addAttribute("candidate", CandidatesDTO);
//
//        return "/hr/potential-candidate-edit";
//    }
//
//    @PostMapping("/hr/update-potential-candidate")
//    public String updateCandidates(@ModelAttribute(name = "candidate") CandidatesDTO CandidatesDTO) {
//        CandidatesService.updateCandidate(CandidatesDTO);
//        return "redirect:/hr/list-potential-candidate";
//    }
//
//    @GetMapping("/hr/create-potential-candidate")
//    public String createCandidates(Model model) {
//        CandidatesDTO CandidatesDTO = new CandidatesDTO();
//        model.addAttribute("candidate", CandidatesDTO);
//
//        return "/hr/potential-candidate-create";
//    }
//
//    @PostMapping("/hr/create-potential-candidate")
//    public String createCandidates(@ModelAttribute(name = "candidate") CandidatesDTO CandidatesDTO) {
//        CandidatesService.createCandidate(CandidatesDTO);
//        return "redirect:/hr/list-potential-candidate";
//    }
//
//    @PostMapping("/hr/find-potential-candidate")
//    public String getCandidatessByName(Model model, @RequestParam(name = "candidateName", required = false) String candidateName) {
//        List<CandidatesDTO> CandidatesDTOS = CandidatesService.findByName(candidateName);
//        model.addAttribute("candidates", CandidatesDTOS);
//
//        return "/hr/potential-candidate-search";
//    }
}
