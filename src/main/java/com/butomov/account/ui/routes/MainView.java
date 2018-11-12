package com.butomov.account.ui.routes;

import com.butomov.account.repository.UserRepository;
import com.butomov.account.ui.components.*;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import org.springframework.beans.factory.annotation.Autowired;

@Route
public class MainView extends VerticalLayout {

    public static final String NEW_USER = "New User";
    public static final String CREATE_ACCOUNT = "Create Account";
    public static final String REFILL_ACCOUNT = "Refill the account";
    public static final String WITHDRAW_ACCOUNT = "Withdraw the account";
    public static final String TRANSFER_MONEY = "Transfer Money";
    public static final String GET_MONEY = "Get amount of money";

    private final Button createUserButton;
    private final Button createAccountButton;
    private final Button refillButton;
    private final Button withdrawButton;
    private final Button transferButton;
    private final Button moneyButton;
    private final UserCreator userCreator;
    private final AccountCreator accountCreator;
    private final RefillForm refillForm;
    private final WithdrawForm withdrawForm;
    private final TransferForm transferForm;
    private final MoneyForm moneyForm;

    @Autowired
    private UserRepository userRepository;

    public MainView(UserCreator userCreator, AccountCreator accountCreator,
                    RefillForm refillForm, WithdrawForm withdrawForm,
                    TransferForm transferForm, MoneyForm moneyForm) {
        this.userCreator = userCreator;
        this.accountCreator = accountCreator;
        this.refillForm = refillForm;
        this.withdrawForm = withdrawForm;
        this.transferForm = transferForm;
        this.moneyForm = moneyForm;

        createUserButton = new Button(NEW_USER, VaadinIcon.PLUS.create());
        createAccountButton = new Button(CREATE_ACCOUNT, VaadinIcon.PLUS.create());
        refillButton = new Button(REFILL_ACCOUNT, VaadinIcon.MONEY_DEPOSIT.create());
        withdrawButton = new Button(WITHDRAW_ACCOUNT, VaadinIcon.MONEY_WITHDRAW.create());
        transferButton = new Button(TRANSFER_MONEY, VaadinIcon.MONEY_EXCHANGE.create());
        moneyButton = new Button(GET_MONEY, VaadinIcon.MONEY.create());

        add(createUserButton, userCreator, createAccountButton, accountCreator,
                refillButton, refillForm, withdrawButton, withdrawForm,
                transferButton, transferForm, moneyButton, moneyForm);

        createUserButton.addClickListener(e -> userCreator.createUser());
        createAccountButton.addClickListener(e -> accountCreator.createAccount());
        refillButton.addClickListener(e -> refillForm.expandForm());
        withdrawButton.addClickListener(e -> withdrawForm.expandForm());
        transferButton.addClickListener(e -> transferForm.expandForm());
        moneyButton.addClickListener(e -> moneyForm.expandForm());
    }
}
