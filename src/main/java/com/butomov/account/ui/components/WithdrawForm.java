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
public class WithdrawForm extends VerticalLayout implements KeyNotifier {

    @Autowired
    private OperationService operationService;

    private TextField accountId = new TextField("Account ID");
    private TextField amount = new TextField("Amount");

    private Button withdraw = new Button("Withdraw", VaadinIcon.MONEY_DEPOSIT.create());
    private Button cancel = new Button("Cancel");
    private HorizontalLayout actions = new HorizontalLayout(withdraw, cancel);

    public WithdrawForm() {
        add(accountId, amount, actions);

        setSpacing(true);

        withdraw.getElement().getThemeList().add("primary");

        addKeyPressListener(Key.ENTER, e-> refill());
        withdraw.addClickListener(e -> refill());
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
                new RefillRequest(accountId, amount),
                headers
        );

        try {
            ResponseEntity<String> response = restTemplate.exchange("http://localhost:8080/api/withdraw",
                HttpMethod.POST,
                request,
                String.class);

            Notification.show(response.getBody().replaceAll("\"",""));
        } catch (Exception e) {
            Notification.show(e.getMessage());
        }
    }
}

