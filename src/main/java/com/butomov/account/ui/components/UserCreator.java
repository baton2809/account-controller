package com.butomov.account.ui.components;

import com.butomov.account.api.request.UserRequest;
import com.butomov.account.api.response.UserResponse;
import com.butomov.account.domain.User;
import com.butomov.account.repository.UserRepository;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.KeyNotifier;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.spring.annotation.UIScope;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestTemplate;

@SpringComponent
@UIScope
public class UserCreator extends VerticalLayout implements KeyNotifier {

    public static final String DEFAULT_PASSWORD = "password";

    @Autowired
    private UserRepository userRepository;

    private TextField name = new TextField("User name");

    private Button save = new Button("Save", VaadinIcon.CHECK.create());
    private Button cancel = new Button("Cancel");
    private HorizontalLayout actions = new HorizontalLayout(save, cancel);

    // TODO можно убрать
    private Binder<User> binder = new Binder<>(User.class);
    private ChangeHandler changeHandler;

    public UserCreator() {
        add(name, actions);

        binder.bindInstanceFields(this);
        setSpacing(true);

        save.getElement().getThemeList().add("primary");

        addKeyPressListener(Key.ENTER, e-> save());
        save.addClickListener(e -> save());
        cancel.addClickListener(e -> cancel());
        setVisible(false);
    }

    void save() {
        // TODO catch exceptions
        callRestService(name.getValue(), DEFAULT_PASSWORD);
        changeHandler.onChange();
        setVisible(false);
    }

    void cancel() {
        setVisible(false);
    }

    public final void createUser() {
        setVisible(true);
        name.focus();
    }

    // На будущее
    public interface ChangeHandler {
        void onChange();
    }

    public void setChangeHandler(ChangeHandler h) {
        changeHandler = h;
    }

    private void callRestService(String name, String password) {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.add("Accept", MediaType.APPLICATION_JSON_VALUE);

        HttpEntity<UserRequest> request = new HttpEntity<>(
                new UserRequest(name, password),
                headers
        );

        try {
            HttpEntity<UserResponse> response = restTemplate
                    .exchange("http://localhost:8080/api/users",
                            HttpMethod.PUT,
                            request,
                            UserResponse.class);
            Notification.show(response.getBody().getUserId());
        } catch (Exception e) {
            Notification.show(e.getMessage());
        }
    }
}
