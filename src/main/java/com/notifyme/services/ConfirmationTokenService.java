package com.notifyme.services;

import com.notifyme.error.exceptions.TokenNotFoundException;
import com.notifyme.persistence.ConfirmationToken;
import com.notifyme.repository.ConfirmationTokenRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class ConfirmationTokenService {

    private final ConfirmationTokenRepository confirmationTokenRepository;

    public void save(ConfirmationToken confirmationToken) {
        confirmationTokenRepository.save(confirmationToken);
    }

    public ConfirmationToken findByTokenAndUnconfirmedAndValid(Integer id, LocalDateTime localDateTime) {
        return confirmationTokenRepository.findByTokenAndUnconfirmedAndValid(id, localDateTime).orElseThrow(TokenNotFoundException::new);
    }
}
