package net.suncaper.projecttracking.client;

import net.suncaper.projecttracking.domain.SysDict;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;
import java.util.Map;

@FeignClient(value = "PROJECT-PROVIDER", contextId = "SYS-DICT")
public interface SysDictClient {
    @GetMapping("/sys-dict/map")
    Map<String, List<SysDict>> getSysDictsMap();
}
