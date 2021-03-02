package net.suncaper.projecttracking.client;

import net.suncaper.projecttracking.domain.ProjectDept;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@FeignClient(value = "PROJECT-PROVIDER", contextId = "DEPT-PROVIDER")
public interface DeptClient {
    @GetMapping("/dept/list")
    List<ProjectDept> listDepts();
}
