package net.suncaper.projecttracking.web;

import net.suncaper.projecttracking.domain.JointCompany;
import org.springframework.format.Formatter;

import java.text.ParseException;
import java.util.Locale;

public class JointCompanyFormatter implements Formatter<JointCompany> {
    @Override
    public JointCompany parse(String text, Locale locale) throws ParseException {
        Integer id = Integer.parseInt(text);

        JointCompany jointCompany = new JointCompany();
        jointCompany.setId(id);

        return jointCompany;
    }

    @Override
    public String print(JointCompany object, Locale locale) {
        return String.valueOf(object.getId());
    }
}
