package net.suncaper.projecttracking.web;

import net.suncaper.projecttracking.domain.ProjectDept;
import org.springframework.format.Formatter;

import java.text.ParseException;
import java.util.Locale;

public class ProjectDeptFormatter implements Formatter<ProjectDept> {
    @Override
    public ProjectDept parse(String text, Locale locale) throws ParseException {
        Integer id = Integer.valueOf(text);

        ProjectDept projectDept = new ProjectDept();
        projectDept.setId(id);

        return projectDept;
    }

    @Override
    public String print(ProjectDept object, Locale locale) {
        return String.valueOf(object.getId());
    }
}
