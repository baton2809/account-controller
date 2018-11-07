package com.butomov.account.ui.components;

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
public class MoneyForm extends VerticalLayout implements KeyNotifier {

    @Autowired
    private AccountService accountService;

    private TextField account = new TextField("Account ID");

    private Button get = new Button("Get", VaadinIcon.MONEY.create());
    private Button cancel = new Button("Cancel");
    private HorizontalLayout actions = new HorizontalLayout(get, cancel);

    public MoneyForm() {
        add(account, actions);

        setSpacing(true);

        get.getElement().getThemeList().add("primary");

        addKeyPressListener(Key.ENTER, e-> get());
        get.addClickListener(e -> get());
        cancel.addClickListener(e -> cancel());
        setVisible(false);
    }

    void get() {
        callRestService(account.getValue());
        setVisible(false);
    }

    void cancel() {
        setVisible(false);
    }

    public final void expandForm() {
        setVisible(true);
        account.focus();
    }

    private void callRestService(String accountId) {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.add("Accept", MediaType.APPLICATION_JSON_VALUE);

        try {
            HttpEntity<Double> response = restTemplate
                    .exchange(String.format("http://localhost:8080/api/accounts/%s", accountId),
                            HttpMethod.GET,
                            null,
                            Double.class);

            Notification.show(String.format("Account # %s has %s money",
                    accountId,
                    response.getBody()));
        } catch (Exception e) {
            Notification.show(e.getMessage());
        }
    }
}

