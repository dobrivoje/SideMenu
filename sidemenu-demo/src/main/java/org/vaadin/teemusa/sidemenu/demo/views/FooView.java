package org.vaadin.teemusa.sidemenu.demo.views;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;
import javax.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.vaadin.teemusa.sidemenu.demo.service.HelloServis;

@SpringView(name = FooView.NAME)
public class FooView extends VerticalLayout implements View {
    
    public static final String NAME = "mojVju";
    private HelloServis mojServis;
    
    @PostConstruct
    public void init() {
        addComponent(new Label("FooView view : @PostConstruct init()"));
        addComponent(new Label("Spring service call :  " + mojServis.helloMan("Ok")));
    }
    
    @Autowired
    public FooView(HelloServis mojServis) {
        this.mojServis = mojServis;
    }
    
    public FooView(String text) {
        super.addComponent(new Label(text));
    }
    
    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event) {
        addComponent(new Label(mojServis.helloMan("dobriiiiiiiiiiiii")));
    }
}
