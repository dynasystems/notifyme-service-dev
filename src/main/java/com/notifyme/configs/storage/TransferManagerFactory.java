package com.notifyme.configs.storage;


import com.amazonaws.services.s3.transfer.TransferManager;

public interface TransferManagerFactory {

    TransferManager createTransferManager();
}
