package net.suncaper.projecttracking.client;

import net.suncaper.projecttracking.domain.JointDept;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@FeignClient(value = "PROJECT-PROVIDER", contextId = "JOINT-DEPT-PROVIDER")
public interface JointDeptClient {
    @RequestMapping("/joint-dept/all")
    List<JointDept> list();
}
