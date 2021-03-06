package com.butomov.account.ui.components;

import com.butomov.account.api.request.RefillRequest;
import com.butomov.account.service.OperationService;
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
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

@SpringComponent
@UIScope
public class RefillForm extends VerticalLayout implements KeyNotifier {

    public static final String CANCEL = "Cancel";
    public static final String LOCALHOST = "http://localhost:8080";

    @Autowired
    private OperationService operationService;

    private TextField accountId = new TextField("Account ID");
    private TextField amount = new TextField("Amount");

    private Button refill = new Button("Refill", VaadinIcon.MONEY_DEPOSIT.create());
    private Button cancel = new Button(CANCEL);
    private HorizontalLayout actions = new HorizontalLayout(refill, cancel);

    public RefillForm() {
        add(accountId, amount, actions);

        setSpacing(true);

        refill.getElement().getThemeList().add("primary");

        addKeyPressListener(Key.ENTER, e -> refill());
        refill.addClickListener(e -> refill());
        cancel.addClickListener(e -> cancel());
        setVisible(false);
    }

    void refill() {
        callRestService(Long.valueOf(accountId.getValue()),
                Double.valueOf(amount.getValue()));
        setVisible(false);
    }

    void cancel() {
        setVisible(false);
    }

    public final void expandForm() {
        setVisible(true);
        accountId.focus();
    }

    private void callRestService(long accountId, double amount) {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.add("Accept", MediaType.APPLICATION_JSON_VALUE);

        HttpEntity<RefillRequest> request = new HttpEntity<>(
                RefillRequest.builder().accountId(accountId).amount(amount).build(),
                headers
        );

        try {
            ResponseEntity<String> response = restTemplate.exchange(LOCALHOST + "/operations/refill",
                    HttpMethod.POST,
                    request,
                    String.class);

            Notification.show(response.getBody().replaceAll("\"", ""));
        } catch (Exception e) {
            Notification.show(e.getMessage());
        }
    }
}
