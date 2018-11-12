package com.butomov.account.ui.components;

import com.butomov.account.api.response.AccountResponse;
import com.butomov.account.service.AccountService;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.KeyNotifier;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
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
public class AccountCreator extends VerticalLayout implements KeyNotifier {

    public static final String CANCEL = "Cancel";
    public static final String LOCALHOST = "http://localhost:8080";

    @Autowired
    private AccountService accountService;

    private TextField user = new TextField("User ID");

    private Button save = new Button("Save", VaadinIcon.CHECK.create());
    private Button cancel = new Button(CANCEL);
    private HorizontalLayout actions = new HorizontalLayout(save, cancel);

    public AccountCreator() {
        add(user, actions);

        setSpacing(true);

        save.getElement().getThemeList().add("primary");

        addKeyPressListener(Key.ENTER, e -> save());
        save.addClickListener(e -> save());
        cancel.addClickListener(e -> cancel());
        setVisible(false);
    }

    void save() {
        callRestService(user.getValue());
        setVisible(false);
    }

    void cancel() {
        setVisible(false);
    }

    public final void createAccount() {
        setVisible(true);
        user.focus();
    }

    private void callRestService(String userId) {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.add("Accept", MediaType.APPLICATION_JSON_VALUE);

        try {
            HttpEntity<AccountResponse> response = restTemplate
                    .exchange(String.format(LOCALHOST + "/accounts/%s", userId),
                            HttpMethod.PUT,
                            null,
                            AccountResponse.class);

            Notification.show(String.format("Account # %s created with %s money",
                    response.getBody().getAccountId(),
                    response.getBody().getAmount()));
        } catch (Exception e) {
            Notification.show(e.getMessage());
        }

    }
}
