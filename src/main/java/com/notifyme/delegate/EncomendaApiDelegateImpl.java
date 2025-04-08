package com.notifyme.delegate;

import com.notifyme.controller.EncomendaApiDelegate;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class EncomendaApiDelegateImpl implements EncomendaApiDelegate {

    public ResponseEntity<Void> upImagemEncomenda(MultipartFile imagemEncomenda) {
        return null;
    }
}
