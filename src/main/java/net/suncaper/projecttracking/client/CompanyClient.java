package net.suncaper.projecttracking.client;

import net.suncaper.projecttracking.domain.JointCompany;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@FeignClient(value = "PROJECT-PROVIDER", contextId = "COMPANY-PROVIDER")
public interface CompanyClient {
    @GetMapping("/company/list")
     List<JointCompany> list();
}
