package org.vaadin.teemusa.sidemenu.demo.views;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;
import javax.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.vaadin.teemusa.sidemenu.demo.service.HelloServis;

@SpringView(name = ErrView.NAME)
public class ErrView extends VerticalLayout implements View {

    public static final String NAME = "greska";

    @Autowired
    HelloServis mojServis;

    @PostConstruct
    void init() {
        addComponent(new Label("This is the \"Greška\" view"));
        addComponent(new Label(mojServis.helloMan("---------------->  moj servis")));
    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event) {
    }
}
