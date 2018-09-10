package org.vaadin.teemusa.sidemenu.demo.service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import org.springframework.stereotype.Service;

@Service
public class HelloServis {

    public String helloMan(String ime) {
        LocalDateTime ldt = LocalDateTime.now();
        return "Zdravo " + ime + ". Time : " + ldt.format(DateTimeFormatter.ofPattern("dd.M.yyyy hh:mm:ss SSS"));
    }
}
