package net.suncaper.projecttracking.client;

import net.suncaper.projecttracking.common.PagableResponse;
import net.suncaper.projecttracking.common.PageRequest;
import net.suncaper.projecttracking.domain.Project;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.cloud.openfeign.SpringQueryMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(value = "PROJECT-PROVIDER")
public interface ProjectClient {
    @PostMapping("/project/save")
    Boolean saveProject(Project project);

    @GetMapping("/project/detail")
    Project getProjectDetailById(@RequestParam("id") Integer id);

    @GetMapping("/project/search")
    PagableResponse<List<Project>> search(@SpringQueryMap PageRequest request,@RequestParam("keyword") String keyword);
}
