package it.disim.univaq.sose.walletservice.service;


import it.disim.univaq.sose.walletservice.domain.Wallet;
import it.disim.univaq.sose.walletservice.domain.dto.ChargeWalletRequest;
import it.disim.univaq.sose.walletservice.domain.dto.CreateWalletRequest;
import it.disim.univaq.sose.walletservice.domain.dto.WalletResponse;
import it.disim.univaq.sose.walletservice.repository.WalletRepository;
import jakarta.ws.rs.core.Response;
import lombok.extern.slf4j.Slf4j;
import org.apache.cxf.feature.Features;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.UUID;

/**
 * The WalletServiceImpl class is the implementation of the AccountService interface.
 */
@Slf4j
@Service
@Features(features = {"org.apache.cxf.ext.logging.LoggingFeature"})
public class WalletServiceImpl implements WalletService {

    private final WalletRepository walletRepository;

    public WalletServiceImpl(WalletRepository walletRepository) {
        this.walletRepository = walletRepository;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Response createWallet(CreateWalletRequest createWalletRequest) {
        walletRepository.findByAccountId(createWalletRequest.getAccountId()).ifPresent(wallet -> {
            log.warn("Wallet already exists for account {}", createWalletRequest.getAccountId());
            Response.status(Response.Status.CONFLICT).build();
        });
        log.info("Creating wallet for account {}", createWalletRequest.getAccountId());
        Wallet wallet = new Wallet();
        wallet.setAccountId(createWalletRequest.getAccountId());
        wallet.setWalletId(UUID.randomUUID());
        wallet.setBalance(BigDecimal.valueOf(1000.0f));
        walletRepository.save(wallet);
        WalletResponse walletResponse = toWalletResponse(wallet);
        return Response.status(Response.Status.CREATED).entity(walletResponse).build();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Response getWallet(UUID walletId) {
        log.info("Retrieving wallet with id {}", walletId);
        Optional<Wallet> opt = walletRepository.findByWalletId(walletId);
        if (opt.isPresent()) {
            WalletResponse walletResponse = toWalletResponse(opt.get());
            return Response.ok(walletResponse).build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Response getWallet(long accountId) {
        log.info("Retrieving wallet with account id {}", accountId);
        Optional<Wallet> opt = walletRepository.findByAccountId(accountId);
        if (opt.isPresent()) {
            return Response.ok(toWalletResponse(opt.get())).build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Response chargeWallet(ChargeWalletRequest chargeWalletRequest) {
        UUID walletId = chargeWalletRequest.getWalletId();
        BigDecimal amount = chargeWalletRequest.getAmount();
        log.info("Charging wallet with id {} with amount {}", walletId, amount);
        Optional<Wallet> opt = walletRepository.findByWalletId(walletId);
        if (opt.isPresent()) {
            Wallet wallet = opt.get();
            if(wallet.getBalance().compareTo(amount) < 0) {
                return Response.status(Response.Status.BAD_REQUEST).build();
            }
            wallet.setBalance(wallet.getBalance().subtract(amount));
            walletRepository.save(wallet);
            return Response.ok(toWalletResponse(wallet)).build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Response reChargeWallet(ChargeWalletRequest chargeWalletRequest) {
        UUID walletId = chargeWalletRequest.getWalletId();
        BigDecimal amount = chargeWalletRequest.getAmount();
        log.info("Recharging wallet with id {} with amount {}", walletId, amount);
        Optional<Wallet> opt = walletRepository.findByWalletId(walletId);
        if (opt.isPresent()) {
            Wallet wallet = opt.get();
            wallet.setBalance(wallet.getBalance().add(amount));
            walletRepository.save(wallet);
            return Response.ok(toWalletResponse(wallet)).build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }

    private WalletResponse toWalletResponse(Wallet wallet) {
        return new WalletResponse(wallet.getId(), wallet.getWalletId(),
                wallet.getAccountId(), wallet.getBalance(), wallet.getUpdatedAt(), wallet.getCreatedAt());
    }
}