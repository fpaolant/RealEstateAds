package it.disim.univaq.sose.publishservice.service;

import it.disim.univaq.sose.account_service.api.AccountServiceDefaultClient;
import it.disim.univaq.sose.account_service.model.AccountResponse;
import it.disim.univaq.sose.account_service.model.OpenAccountRequest;
import it.disim.univaq.sose.ads_service.webservice.*;
import it.disim.univaq.sose.ads_service.webservice.AdResponse;
import it.disim.univaq.sose.publishservice.client.AccountServiceClient;
import it.disim.univaq.sose.publishservice.client.AdsServiceClient;
import it.disim.univaq.sose.publishservice.client.WalletServiceClient;
import it.disim.univaq.sose.publishservice.domain.dto.*;
import it.disim.univaq.sose.walletservice.api.WalletServiceDefaultClient;
import it.disim.univaq.sose.walletservice.model.ChargeWalletRequest;
import it.disim.univaq.sose.walletservice.model.CreateWalletRequest;
import it.disim.univaq.sose.walletservice.model.WalletResponse;
import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.container.AsyncResponse;
import jakarta.ws.rs.core.Response;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * The PublishServiceImpl class is an implementation of the PublishService interface.
 */
@Slf4j
@Service
public class PublishServiceImpl implements PublishService {
    private final WalletServiceClient walletServiceClient;
    private final AccountServiceClient accountServiceClient;
    private final AdsServiceClient adsServiceClient;

    @Value("${spring.application.ad.price}")
    private BigDecimal AD_PRICE;


    public PublishServiceImpl(AdsServiceClient adsServiceClient, AccountServiceClient accountServiceClient, WalletServiceClient walletServiceClient) {
        this.accountServiceClient = accountServiceClient;
        this.adsServiceClient = adsServiceClient;
        this.walletServiceClient = walletServiceClient;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void publishAd(PublishAdRequest ad, AsyncResponse asyncResponse) {
        (new Thread(() -> {
            log.info("Publishing ad: {}", ad);
            try {
                // controllo se il wallet l'account esiste
                WalletResponse wallet = getWallet(ad.getAccountId());
                log.info("Wallet found: {}", wallet.toString());
                // controllo se il wallet ha abbastanza soldi
                if (wallet.getBalance().compareTo(AD_PRICE) < 0) {
                    Response response = Response.status(Response.Status.PRECONDITION_FAILED).build();
                    asyncResponse.resume(response);
                    Thread.currentThread().interrupt();
                }
                log.info("Wallet has enough money: {}", wallet.getBalance().toString());
                // reperisco il servizio ads
                AdsService adsService = this.adsServiceClient.getAdsService();
                // crea la richiesta per il servizio ads
                CreateAd createAdRequest = getCreateAd(ad);
                // esegui chiamata al servizio ads createAd
                AdResponse adResponse = adsService.createAd(createAdRequest).getGetAdResponse();
                log.info("Ad created: {}", adResponse.getId());
                // crea la risposta
                PublishAdResponse response = new PublishAdResponse();
                response.setAdId(adResponse.getId());
                response.setTitle(adResponse.getTitle());

                // addebito il wallet
                log.info("Charging wallet id {}: {}", wallet.getWalletId(), AD_PRICE.toString());
                chargeWallet(wallet.getWalletId(), AD_PRICE);
                log.info("Wallet charged: {}", AD_PRICE.toString());

                // invia la risposta
                asyncResponse.resume(response);
            } catch (ServiceUnavailableException e) {
                Response response = Response.status(Response.Status.SERVICE_UNAVAILABLE).build();
                asyncResponse.resume(response);
                Thread.currentThread().interrupt();
            } catch (WalletNotFoundException e) {
                Response response = Response.status(Response.Status.NOT_FOUND).build();
                asyncResponse.resume(response);
                Thread.currentThread().interrupt();
            }
        })).start();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Response getAdStatus(Long adId) {
        // reperisco il servizio ads
        try {
            AdsService adsService = this.adsServiceClient.getAdsService();
            // crea la richiesta per il servizio ads
            GetAdDetails getAdRequest = new GetAdDetails();
            getAdRequest.setId(adId);
            GetAdDetailsResponse adDetails = adsService.getAdDetails(getAdRequest);
            PublishAdStatusResponse response = new PublishAdStatusResponse();
            response.setStatus(adDetails.getGetAdDetailsResponse().getStatus());
            return Response.ok(response).build();
        } catch (ServiceUnavailableException e) {
            return Response.status(Response.Status.SERVICE_UNAVAILABLE).build();
        } catch (NotFoundException_Exception e) {
            return Response.status(Response.Status.NOT_FOUND).build();
        } catch (AdException_Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void approveAd(long adId, AsyncResponse asyncResponse) {
        log.info("Approving ad: {}", adId);
        (new Thread(() -> {
            try {
                // reperisco il servizio adsService
                AdsService adsService = adsService = this.adsServiceClient.getAdsService();

                // crea la richiesta per il servizio ads approveAd
                ApproveAd approveAdRequest = new ApproveAd();
                approveAdRequest.setId(adId);
                // esegui chiamata al servizio ads approveAd
                ApproveAdResponse approvedAdResponse = adsService.approveAd(approveAdRequest).getGetAdApproveResponse();
                log.info("Ad approved: {}", approvedAdResponse.isSuccess());
                // crea la risposta
                PublishAdApproveResponse response = new PublishAdApproveResponse();
                response.setAdId(approvedAdResponse.getAdId());
                response.setSuccess(approvedAdResponse.isSuccess());
                response.setMessage(approvedAdResponse.getMessage());
                response.setStatus(approvedAdResponse.getStatus());

                // invia la risposta
                asyncResponse.resume(response);

            } catch (ServiceUnavailableException e) {
                Response response = Response.status(Response.Status.SERVICE_UNAVAILABLE).build();
                asyncResponse.resume(response);
                Thread.currentThread().interrupt();
            } catch (NotFoundException_Exception e) {
                Response response = Response.status(Response.Status.NOT_FOUND).build();
                asyncResponse.resume(response);
                Thread.currentThread().interrupt();
            }
        })).start();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void rejectAd(long adId, PublishAdRejectRequest publishAdRejectRequest, AsyncResponse asyncResponse) {
        log.info("Rejecting ad: {}", adId);
        (new Thread(() -> {
            try {
                // reperisco il servizio adsService
                AdsService adsService = adsService = this.adsServiceClient.getAdsService();

                // crea la richiesta per il servizio ads rejectAd
                RejectAd rejectAdRequest = new RejectAd();
                rejectAdRequest.setId(adId);
                rejectAdRequest.setReason(publishAdRejectRequest.getReason());

                // esegui chiamata al servizio ads rejectAd
                GetAdRejectResponse getAdRejectResponse = adsService.rejectAd(rejectAdRequest);
                log.info("Ad rejected: {}", getAdRejectResponse.getGetAdRejectResponse().isSuccess());

                // reperisco l'accountId dall'annuncio
                long accountId = getAdDetails(adId).getGetAdDetailsResponse().getAccountId();


                // reperisco il wallet
                WalletResponse wallet = getWallet(accountId);
                // ricarico il wallet
                rechargeWallet(wallet.getWalletId(), AD_PRICE);


                // crea la risposta
                PublishAdRejectResponse response = new PublishAdRejectResponse();
                response.setAdId(getAdRejectResponse.getGetAdRejectResponse().getAdId());
                response.setSuccess(getAdRejectResponse.getGetAdRejectResponse().isSuccess());
                response.setMessage(getAdRejectResponse.getGetAdRejectResponse().getMessage());
                response.setStatus(getAdRejectResponse.getGetAdRejectResponse().getStatus());
                response.setRejectionReason(getAdRejectResponse.getGetAdRejectResponse().getRejectionReason());

                // invia la risposta
                asyncResponse.resume(response);
            } catch (ServiceUnavailableException e) {
                Response response = Response.status(Response.Status.SERVICE_UNAVAILABLE).build();
                asyncResponse.resume(response);
                Thread.currentThread().interrupt();
            } catch (WalletNotFoundException e) {
                Response response = Response.status(Response.Status.NOT_FOUND).build();
                asyncResponse.resume(response);
                Thread.currentThread().interrupt();
            } catch (AdException_Exception e) {
                Response response = Response.status(Response.Status.BAD_REQUEST).build();
                asyncResponse.resume(response);
                Thread.currentThread().interrupt();
            } catch (NotFoundException_Exception e) {
                Response response = Response.status(Response.Status.NOT_FOUND).build();
                asyncResponse.resume(response);
                Thread.currentThread().interrupt();
            }
        })).start();

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void getAds(GetAdsRequest getAdsRequestParam, AsyncResponse asyncResponse) {
        log.info("Getting ads: {}", getAdsRequestParam.toString());
        (new Thread(() -> {
            try {
                // reperisco il servizio adsService
                AdsService adsService = adsService = this.adsServiceClient.getAdsService();

                // crea la richiesta per il servizio ads getAds
                GetAds getAdsRequest = new GetAds();
                getAdsRequest.setSortBy(getAdsRequestParam.getSortBy());
                getAdsRequest.setSortOrder(getAdsRequestParam.getSortOrder());
                getAdsRequest.setPage(getAdsRequestParam.getPage());
                getAdsRequest.setSize(getAdsRequestParam.getSize());
                getAdsRequest.setStatus(getAdsRequestParam.getStatus());
                log.info("GetAdsRequest In status: {}", getAdsRequest.toString());

                // esegui chiamata al servizio ads getAds
                GetAdPaginatedResponse getAdPaginatedResponse = adsService.getAds(getAdsRequest);


                // converto la risposta soap in una risposta rest di publish service
                AdPaginatedResponse response = new AdPaginatedResponse();
                List<it.disim.univaq.sose.publishservice.domain.dto.AdResponse> ads =
                        getAdPaginatedResponse.getGetAdPaginatedResponse().getAdResponse().stream()
                                .map(adResponse -> new it.disim.univaq.sose.publishservice.domain.dto.AdResponse(
                                        adResponse.getId(),
                                        adResponse.getTitle(),
                                        adResponse.getDescription(),
                                        adResponse.getSquareMeters(),
                                        adResponse.getPrice(),
                                        adResponse.getLocation(),
                                        adResponse.getLatitude(),
                                        adResponse.getLongitude(),
                                        adResponse.getStatus(),
                                        adResponse.getAccountId())).collect(Collectors.toList());

                response.setContent(ads);
                response.setCurrentPage(getAdPaginatedResponse.getGetAdPaginatedResponse().getCurrentPage());
                response.setTotalPages(getAdPaginatedResponse.getGetAdPaginatedResponse().getTotalPages());
                response.setTotalItems(getAdPaginatedResponse.getGetAdPaginatedResponse().getTotalItems());

                log.info("Ads retrieved: {}", response.toString());
                // invia la risposta
                asyncResponse.resume(response);
            } catch (ServiceUnavailableException e) {
                Response response = Response.status(Response.Status.SERVICE_UNAVAILABLE).build();
                asyncResponse.resume(response);
                Thread.currentThread().interrupt();
            }
        })).start();
    }

    @Override
    public void getAdsByAccountId(long accountId, AsyncResponse asyncResponse) {
        (new Thread(() -> {
            log.info("Getting ads by account id: {}", accountId);
            try {
                // reperisco il servizio adsService
                AdsService adsService = adsService = this.adsServiceClient.getAdsService();

                // crea la richiesta per il servizio ads getAdsByAccountId
                GetAdsByAccountId getAdsByAccountIdRequest = new GetAdsByAccountId();
                getAdsByAccountIdRequest.setAccountId(accountId);

                // esegui chiamata al servizio ads getAdsByAccountId
                GetAdsResponse getAdsResponse = adsService.getAdsByAccountId(getAdsByAccountIdRequest);
                List<AdResponse> adResponses = getAdsResponse.getGetAdsResponse();

                // converto la risposta soap in una risposta rest di publish service
                List<it.disim.univaq.sose.publishservice.domain.dto.AdResponse> ads = adResponses.stream()
                        .map(adResponse -> new it.disim.univaq.sose.publishservice.domain.dto.AdResponse(
                                adResponse.getId(),
                                adResponse.getTitle(),
                                adResponse.getDescription(),
                                adResponse.getSquareMeters(),
                                adResponse.getPrice(),
                                adResponse.getLocation(),
                                adResponse.getLatitude(),
                                adResponse.getLongitude(),
                                adResponse.getStatus(),
                                adResponse.getAccountId())).collect(Collectors.toList());

                // crea la risposta
                PublishGetAdsResponse response = new PublishGetAdsResponse();
                response.setAdsResponse(ads);

                // invia la risposta
                asyncResponse.resume(response);
            } catch (ServiceUnavailableException e) {
                Response response = Response.status(Response.Status.SERVICE_UNAVAILABLE).build();
                asyncResponse.resume(response);
                Thread.currentThread().interrupt();
            }
        })).start();

    }

    @Override
    public void openPublishAccount(OpenPublishAccountRequest openPublishAccountRequest, AsyncResponse asyncResponse) {
        log.info("Opening account: {}", openPublishAccountRequest);
        (new Thread(() -> {
            try {
                // creo l'account
                AccountServiceDefaultClient accountServiceClient = this.accountServiceClient.getAccountService();
                OpenAccountRequest openAccountRequest = getOpenAccountRequest(openPublishAccountRequest);
                AccountResponse accountResponse = accountServiceClient.openAccountUser1(openAccountRequest);
                log.info("Account opened: {}", accountResponse.getId());

                // creo il wallet
                WalletServiceDefaultClient walletServiceClient = this.walletServiceClient.getWalletService();
                CreateWalletRequest createWalletRequest = new CreateWalletRequest();
                createWalletRequest.setAccountId(accountResponse.getId());
                WalletResponse walletResponse = walletServiceClient.createWallet1(createWalletRequest);

                // creo la risposta
                OpenPublishAccountResponse response = new OpenPublishAccountResponse();
                response.setId(accountResponse.getId());
                response.setUsername(accountResponse.getUsername());
                response.setWalletId(walletResponse.getWalletId());

                // invia la risposta
                asyncResponse.resume(response);
            } catch (Exception e) {
                Response response = Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
                asyncResponse.resume(response);
                Thread.currentThread().interrupt();
            }
        })).start();
    }

    private static OpenAccountRequest getOpenAccountRequest(OpenPublishAccountRequest openPublishAccountRequest) {
        OpenAccountRequest openAccountRequest = new OpenAccountRequest();
        openAccountRequest.setUsername(openPublishAccountRequest.getUsername());
        openAccountRequest.setPassword(openPublishAccountRequest.getPassword());
        openAccountRequest.setEmail(openPublishAccountRequest.getEmail());
        openAccountRequest.setName(openPublishAccountRequest.getName());
        openAccountRequest.setSurname(openPublishAccountRequest.getSurname());
        openAccountRequest.setMobile(openPublishAccountRequest.getMobile());
        return openAccountRequest;
    }


    private static CreateAd getCreateAd(PublishAdRequest ad) {
        CreateAd createAdRequest = new CreateAd();
        AdRequest adRequest = new AdRequest();
        adRequest.setTitle(ad.getTitle());
        adRequest.setDescription(ad.getDescription());
        adRequest.setSquareMeters(ad.getSquareMeters());
        adRequest.setPrice(ad.getPrice());
        adRequest.setLocation(ad.getLocation());
        adRequest.setLatitude(ad.getLatitude());
        adRequest.setLongitude(ad.getLongitude());
        adRequest.setAccountId(ad.getAccountId());
        createAdRequest.setAdRequest(adRequest);
        return createAdRequest;
    }

    private WalletResponse getWallet(Long accountId) throws WalletNotFoundException {
        try {
            // reperisco il servizio wallet
            WalletServiceDefaultClient client = walletServiceClient.getWalletService();
            return client.getWalletByAccountId1(accountId);
        } catch (Exception e) {
            throw new WalletNotFoundException("Wallet not found for your account");
        }
    }

    private WalletResponse chargeWallet(UUID walletId, BigDecimal amount) throws WalletNotFoundException {
        try {
            // reperisco il servizio wallet
            WalletServiceDefaultClient client = walletServiceClient.getWalletService();
            ChargeWalletRequest chargeWalletRequest = new ChargeWalletRequest();
            chargeWalletRequest.setWalletId(walletId);
            chargeWalletRequest.setAmount(amount);
            return client.chargeWallet1(chargeWalletRequest);
        } catch (WebApplicationException e) {
            // Se il codice di stato è 404, significa che il wallet non è stato trovato
            if (e.getResponse().getStatus() == 404) {
                throw new WalletNotFoundException("Wallet not found");
            }
            throw e;
        } catch (Exception e) {
            // Gestione generica di altre eccezioni (es. problemi di connessione, timeout, ecc.)
            throw new RuntimeException("An error occurred while charging the wallet", e);
        }
    }

    private WalletResponse rechargeWallet(UUID walletId, BigDecimal amount) throws WalletNotFoundException {
        try {
            // reperisco il servizio wallet
            WalletServiceDefaultClient client = walletServiceClient.getWalletService();
            ChargeWalletRequest chargeWalletRequest = new ChargeWalletRequest();
            chargeWalletRequest.setWalletId(walletId);
            chargeWalletRequest.setAmount(amount);
           return client.reChargeWallet1(chargeWalletRequest);
        } catch (WebApplicationException e) {
            // Se il codice di stato è 404, significa che il wallet non è stato trovato
            if (e.getResponse().getStatus() == 404) {
                throw new WalletNotFoundException("Wallet not found");
            }
            throw e;
        } catch (Exception e) {
            throw new RuntimeException("An error occurred while charging the wallet", e);
        }
    }

    private GetAdDetailsResponse getAdDetails(Long adId) throws ServiceUnavailableException, NotFoundException_Exception, AdException_Exception {
        // reperisco il servizio adsService
        AdsService adsService = adsService = this.adsServiceClient.getAdsService();
        // crea la richiesta per il servizio ads getAdDetails
        GetAdDetails getAdRequest = new GetAdDetails();
        getAdRequest.setId(adId);
        return adsService.getAdDetails(getAdRequest);
    }
}
