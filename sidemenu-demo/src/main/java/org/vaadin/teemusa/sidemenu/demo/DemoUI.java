package org.vaadin.teemusa.sidemenu.demo;

import org.vaadin.teemusa.sidemenu.SideMenu;
import org.vaadin.teemusa.sidemenu.SideMenu.MenuRegistration;
import com.vaadin.annotations.Theme;
import com.vaadin.annotations.Title;
import com.vaadin.annotations.Viewport;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.navigator.Navigator;
import com.vaadin.server.Resource;
import com.vaadin.server.ThemeResource;
import com.vaadin.server.VaadinRequest;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Notification.Type;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import org.springframework.beans.factory.annotation.Autowired;
import org.vaadin.teemusa.sidemenu.demo.service.HelloServis;
import org.vaadin.teemusa.sidemenu.demo.views.FooView;
import com.vaadin.spring.annotation.SpringUI;
import com.vaadin.spring.navigator.SpringViewProvider;
import org.vaadin.teemusa.sidemenu.demo.views.ErrView;

@SpringUI
@Theme("demo")
@Title("SideMenu Add-on Demo")
@Viewport("user-scalable=no,initial-scale=1.0")
public class DemoUI extends UI {

    @Autowired
    private HelloServis helloServis;

    @Autowired
    private SpringViewProvider springViewProvider;

    private final SideMenu sideMenu = new SideMenu();
    private boolean logoVisible = true;
    private final ThemeResource logo = new ThemeResource("images/linux-penguin.png");
    private final String menuCaption = "SideMenu Add-on";

    private MenuRegistration menuToRemove;

    @Override
    protected void init(VaadinRequest request) {
        System.err.println("-----------------> " + helloServis.helloMan("DOBRI") + "------------------");
        setContent(sideMenu);
        Navigator navigator = new Navigator(this, sideMenu);
        navigator.addProvider(springViewProvider);
        setNavigator(navigator);

        // NOTE: Navigation and custom code menus should not be mixed.
        // See issue #8
        navigator.addView(FooView.NAME, springViewProvider.getView(FooView.NAME));
        navigator.addView("Foo", springViewProvider.getView(FooView.NAME));
        navigator.setErrorView(springViewProvider.getView(ErrView.NAME));

        navigator.navigateTo(FooView.NAME);

        sideMenu.setMenuCaption(menuCaption, logo);

        // Navigation examples
        sideMenu.addNavigation("Initial View", FooView.NAME);
        sideMenu.addNavigation("Secondary View", VaadinIcons.AMBULANCE, "Foo");

        // Arbitrary method execution
        sideMenu.addMenuItem("My Menu Entry", () -> {
            VerticalLayout content = new VerticalLayout();
            content.addComponent(new Label("A layout"));
            sideMenu.setContent(content);
        });
        sideMenu.addMenuItem("Entry With Icon", VaadinIcons.ACCESSIBILITY, () -> {
            VerticalLayout content = new VerticalLayout();
            content.addComponent(new Label("Another layout"));
            sideMenu.setContent(content);
        })
                // Navigator has done its own setup, any menu can be selected.
                .select();

        // User menu controls
        sideMenu.addMenuItem("Show/Hide user menu", VaadinIcons.USER, () -> {
            sideMenu.setUserMenuVisible(!sideMenu.isUserMenuVisible());
        });

        menuToRemove = sideMenu.addMenuItem("Remove this menu item", () -> {
            if (menuToRemove != null) {
                menuToRemove.remove();
                menuToRemove = null;
            }
        });

        setUser("Guest", VaadinIcons.MALE);
    }

    private void setUser(String name, Resource icon) {
        sideMenu.setUserName(name);
        sideMenu.setUserIcon(icon);

        sideMenu.clearUserMenu();
        sideMenu.addUserMenuItem("Settings", VaadinIcons.WRENCH, () -> {
            Notification.show("Showing settings", Type.TRAY_NOTIFICATION);
        });
        sideMenu.addUserMenuItem("Sign out", () -> {
            Notification.show("Logging out..", Type.TRAY_NOTIFICATION);
        });

        sideMenu.addUserMenuItem("Hide logo", () -> {
            if (!logoVisible) {
                sideMenu.setMenuCaption(menuCaption, logo);
            } else {
                sideMenu.setMenuCaption(menuCaption);
            }
            logoVisible = !logoVisible;
        });
    }
}
