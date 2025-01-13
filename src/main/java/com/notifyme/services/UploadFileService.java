package com.notifyme.services;

import com.amazonaws.AmazonClientException;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.AmazonS3Exception;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.transfer.TransferManager;
import com.amazonaws.services.s3.transfer.Upload;
import com.notifyme.configs.storage.TransferManagerFactory;
import com.notifyme.error.NotifyMeErrorEnum;
import com.notifyme.error.exceptions.CustomException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;


@Slf4j
@Service
@RequiredArgsConstructor
public class UploadFileService {

    private static final String INVALID_ARGUMENT_ERROR_CODE = "InvalidArgument";
    private static final String RESPONSE_DETAILS_KEY = "Details";
    private final TransferManagerFactory transferManagerFactory;
    private final AmazonS3 amazonS3;



    public String uploadFile(String nomeArquiovo, MultipartFile file) {
        log.info("Entrou no salva arquivo " + file.getOriginalFilename());

        try {
            PutObjectRequest putObjectRequest = buildRequestForUpload("notifyme-images", nomeArquiovo, file,true);
            uploadFile(putObjectRequest);
            return getUrl("notifyme-images", "35671871845");
        } catch (Exception e) {
            throw new CustomException(NotifyMeErrorEnum.ERRO_AO_SALVAR_ARQUIVO, file.getOriginalFilename());
        }
    }

    private void uploadFile(final PutObjectRequest request) {

        TransferManager manager = transferManagerFactory.createTransferManager();
        log.info("Starting S3 upload");
        try {
            Upload upload = manager.upload(request);
            upload.waitForUploadResult();
            log.info("Upload finished");
        } catch (AmazonS3Exception e) {
            if (isGCSInvalidArgumentErrorWithDetails(e)) {
                throw new CustomException(NotifyMeErrorEnum.ERRO_AO_EXCLUIR_ARQUIVO, null);
            }
            throw new CustomException(NotifyMeErrorEnum.ERRO_AO_EXCLUIR_ARQUIVO, null);
        } catch (AmazonClientException | InterruptedException e) {
            throw new CustomException(NotifyMeErrorEnum.ERRO_AO_EXCLUIR_ARQUIVO, null);
        } finally {
            manager.shutdownNow(false);
        }
    }

    private boolean isGCSInvalidArgumentErrorWithDetails(AmazonS3Exception e) {
        return e.getRequestId() == null
                && e.getErrorCode().equals(INVALID_ARGUMENT_ERROR_CODE)
                && e.getAdditionalDetails() != null
                && e.getAdditionalDetails().get(RESPONSE_DETAILS_KEY) != null;
    }

    private String getUrl(String storageName, String key) {
        return amazonS3.getUrl(storageName, key).toString();
    }

    private PutObjectRequest buildRequestForUpload(String storageName, String key, MultipartFile file, boolean isPublic) {
        try {
            // Criar metadados do objeto
            ObjectMetadata metadata = new ObjectMetadata();
            metadata.setContentLength(file.getSize()); // Obtém o tamanho do arquivo
            metadata.setContentType(file.getContentType()); // Define o tipo de conteúdo (opcional)

            // Converte o MultipartFile em InputStream
            InputStream content = file.getInputStream();

            // Cria o PutObjectRequest
            PutObjectRequest request = new PutObjectRequest(storageName, key, content, metadata);

            // Define permissões públicas, se necessário
            if (isPublic) {
                log.info("Arquivo é PÚBLICO");
                request = request.withCannedAcl(CannedAccessControlList.PublicRead);
            }

            return request;

        } catch (IOException e) {
            log.error("Erro ao processar o arquivo: {}", e.getMessage(), e);
            throw new RuntimeException("Falha ao construir o PutObjectRequest para upload", e);
        }
    }


}
