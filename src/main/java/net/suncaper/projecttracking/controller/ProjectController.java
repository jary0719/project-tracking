package net.suncaper.projecttracking.controller;

import net.suncaper.projecttracking.client.*;
import net.suncaper.projecttracking.common.PagableResponse;
import net.suncaper.projecttracking.common.PageRequest;
import net.suncaper.projecttracking.domain.Project;
import net.suncaper.projecttracking.domain.SysDict;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/project")
public class ProjectController {
    // @formatter:off
    @Autowired private ProjectClient projectClient;
    @Autowired private DeptClient deptClient;
    @Autowired private CompanyClient companyClient;
    @Autowired private SysDictClient sysDictClient;
    @Autowired private UserClient userClient;
    @Autowired private JointDeptClient jointDeptClient;
    // @formatter:on

    @GetMapping("add")
    public String addPage(Model model) {
        model.addAttribute("project", new Project());
        model.addAttribute("allDepts", deptClient.listDepts());
        model.addAttribute("allCompanies", companyClient.list());
        model.addAttribute("allLeaders", userClient.getLeaders());
        model.addAttribute("allJointDepts", jointDeptClient.list());
        model.addAttribute("page", "project/project-edit");

        return "index";
    }

    @GetMapping("edit")
    public String editPage(Model model, @RequestParam("id") Integer id) {

        model.addAttribute("project", projectClient.getProjectDetailById(id));
        model.addAttribute("allDepts", deptClient.listDepts());
        model.addAttribute("allCompanies", companyClient.list());
        model.addAttribute("allLeaders", userClient.getLeaders());
        model.addAttribute("allJointDepts", jointDeptClient.list());
        model.addAttribute("page", "project/project-edit");

        return "index";
    }

    @GetMapping
    public String listPage(Model model) {
        model.addAttribute("page", "project/project-list");
        return "index";
    }

    @PostMapping("/save")
    @ResponseBody
    public Boolean saveProject(Project project) {
        return projectClient.saveProject(project);
    }

    @GetMapping("/search")
    @ResponseBody
    public PagableResponse<List<Project>> searchProject(PageRequest request, @RequestParam("keyword") String Keyword) {
        return projectClient.search(request, Keyword);
    }

    @ModelAttribute("dictMap")
    public Map<String, List<SysDict>> getSysDictMap() {
        return sysDictClient.getSysDictsMap();
    }
}
