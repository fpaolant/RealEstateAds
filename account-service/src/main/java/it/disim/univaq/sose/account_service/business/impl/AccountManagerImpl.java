package it.disim.univaq.sose.account_service.business.impl;

import it.disim.univaq.sose.account_service.business.AccountManager;
import it.disim.univaq.sose.account_service.domain.Account;
import it.disim.univaq.sose.account_service.domain.Role;
import it.disim.univaq.sose.account_service.domain.dto.*;
import it.disim.univaq.sose.account_service.repository.AccountRepository;
import it.disim.univaq.sose.account_service.security.AuthenticationException;
import it.disim.univaq.sose.account_service.security.JWTGenerator;
import it.disim.univaq.sose.account_service.security.JWTVerify;
import it.disim.univaq.sose.account_service.security.PasswordService;
import it.disim.univaq.sose.account_service.service.NotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Implementation of the AccountManager interface.
 */
@Slf4j
@Service
public class AccountManagerImpl implements AccountManager {
    private final AccountRepository accountRepository;

    private final PasswordService passwordService;

    public AccountManagerImpl(AccountRepository accountRepository, PasswordService passwordService) {
        this.accountRepository = accountRepository;
        this.passwordService = passwordService;
    }

    /**
     * {@inheritDoc}
     */
    @Transactional(readOnly = true)
    public String getJwtToken(UserCredentials userCredentials) throws AuthenticationException {
        Account account = (Account)this.accountRepository.findByUsername(userCredentials.getUsername()).orElseThrow(() -> new AuthenticationException("Incorrect username or password. Please try again."));
        if (!this.passwordService.checkPassword(userCredentials.getPassword(), account.getPassword()))
            throw new AuthenticationException("Incorrect username or password. Please try again.");
        return JWTGenerator.createJwtToken(account.getUsername(), account.getId().longValue(), account.getRole());
    }

    public Boolean checkJwtToken(String token) {
        return Boolean.valueOf(JWTVerify.verifyJwtToken(token));
    }

    /**
     * {@inheritDoc}
     */
    @Transactional
    public AccountResponse createAccountUser(OpenAccountRequest request) {
        Account account = new Account();
        account.setName(request.getName());
        account.setSurname(request.getSurname());
        account.setUsername(request.getUsername());
        account.setPassword(this.passwordService.hashPassword(request.getPassword()));
        account.setEmail(request.getEmail());
        account.setRole(Role.USER);
        account.setMobile(request.getMobile());
        account = (Account)this.accountRepository.save(account);
        return new AccountResponse(account.getId(), account.getName(), account.getSurname(), account
                .getUsername(), account.getEmail(), account.getMobile(), account
                .getRole(), account.getUpdatedAt(), account.getCreatedAt());
    }

    /**
     * {@inheritDoc}
     */
    @Transactional
    public AccountResponse createAccountAdmin(OpenAccountRequest request) {
        Account account = new Account();
        account.setName(request.getName());
        account.setSurname(request.getSurname());
        account.setUsername(request.getUsername());
        account.setPassword(this.passwordService.hashPassword(request.getPassword()));
        account.setEmail(request.getEmail());
        account.setMobile(request.getMobile());
        account.setRole(Role.ADMIN);
        account = (Account)this.accountRepository.save(account);
        return new AccountResponse(account.getId(), account.getName(), account.getSurname(), account
                .getUsername(), account.getEmail(), account.getMobile(), account
                .getRole(), account.getUpdatedAt(), account.getCreatedAt());
    }

    /**
     * {@inheritDoc}
     */
    @Transactional(readOnly = true)
    public AccountResponse getAccountByIdAccount(Long idAccount) throws NotFoundException {
        Account account = (Account)this.accountRepository.findById(idAccount).orElseThrow(() -> new NotFoundException("Account not found"));
        return new AccountResponse(account.getId(), account.getName(), account.getSurname(), account.getUsername(), account
                .getEmail(), account.getMobile(), account.getRole(), account.getUpdatedAt(), account
                .getCreatedAt());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public AccountPaginatedResponse getAccounts(GetAccountsRequest getAccountsRequest) {
        String sortBy = getAccountsRequest.getSortBy();
        String sortOrder = getAccountsRequest.getSortOrder();
        int page = getAccountsRequest.getPage();
        int size = getAccountsRequest.getSize();
        Sort.Direction direction;
        if (sortBy == null || sortBy.isEmpty())
            sortBy = "createdAt";
        if (sortOrder == null || sortOrder.isEmpty())
            sortOrder = "desc";
        if (page < 0)
            page = 0;
        if (size <= 0)
            size = 10;
        try {
            direction = Sort.Direction.fromString(sortOrder);
        } catch (IllegalArgumentException e) {
            direction = Sort.Direction.ASC;
        }

        PageRequest pageRequest = PageRequest.of(page, size, Sort.by(direction, new String[] { sortBy }));
        Page<Account> accounts = this.accountRepository.findAll(pageRequest);

        return new AccountPaginatedResponse(
                accounts.getContent().stream().map(account ->
                        new AccountResponse(
                                account.getId(),
                                account.getName(),
                                account.getSurname(),
                                account.getUsername(),
                                account.getEmail(),
                                account.getMobile(),
                                account.getRole(),
                                account.getUpdatedAt(),
                                account.getCreatedAt())).toList(),
                accounts.getTotalPages(),
                accounts.getTotalElements()
        );
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void promoteUser(long accountId) throws NotFoundException {
        Account account = this.accountRepository.findById(Long.valueOf(accountId)).orElseThrow(() -> new NotFoundException("Account not found"));
        account.setRole(Role.ADMIN);
        this.accountRepository.save(account);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void demoteUser(long accountId) throws NotFoundException {
        Account account = this.accountRepository.findById(Long.valueOf(accountId)).orElseThrow(() -> new NotFoundException("Account not found"));
        account.setRole(Role.USER);
        this.accountRepository.save(account);
    }
}