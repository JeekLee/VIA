package com.via.account.app.storage;

import com.via.account.domain.model.vo.MemberImage;

import java.io.InputStream;

public interface MemberImageStorage {
    MemberImage save(String imageUrl);
    MemberImage save(InputStream inputStream, String fileName, String contentType, long size);
    void delete(MemberImage memberImage);
}
