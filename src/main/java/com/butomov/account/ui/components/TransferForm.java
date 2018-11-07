package com.butomov.account.ui.components;

import com.butomov.account.api.request.TransferDetails;
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
public class TransferForm extends VerticalLayout implements KeyNotifier {

    @Autowired
    private OperationService operationService;

    private TextField senderId = new TextField("Sender Account ID");
    private TextField payeeId = new TextField("Payee Account ID");
    private TextField amount = new TextField("Amount ID");

    private Button transfer = new Button("Transfer", VaadinIcon.MONEY_EXCHANGE.create());
    private Button cancel = new Button("Cancel");
    private HorizontalLayout actions = new HorizontalLayout(transfer, cancel);

    public TransferForm() {
        add(senderId, payeeId, amount, actions);

        setSpacing(true);

        transfer.getElement().getThemeList().add("primary");

        addKeyPressListener(Key.ENTER, e-> refill());
        transfer.addClickListener(e -> refill());
        cancel.addClickListener(e -> cancel());
        setVisible(false);
    }

    void refill() {
        callRestService(Long.valueOf(senderId.getValue()),
                Long.valueOf(payeeId.getValue()),
                Double.valueOf(amount.getValue()));
        setVisible(false);
    }

    void cancel() {
        setVisible(false);
    }

    public final void expandForm() {
        setVisible(true);
        senderId.focus();
    }

    private void callRestService(long sendertId, long payeeId, double amount) {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.add("Accept", MediaType.APPLICATION_JSON_VALUE);

        HttpEntity<TransferDetails> request = new HttpEntity<>(
                new TransferDetails(sendertId, payeeId, amount),
                headers
        );

        try {
            ResponseEntity<String> response = restTemplate.exchange("http://localhost:8080/api/transfer",
                    HttpMethod.POST,
                    request,
                    String.class);

            Notification.show(response.getBody().replaceAll("\"", ""));
        } catch (Exception e) {
            Notification.show(e.getMessage());
        }
    }
}
