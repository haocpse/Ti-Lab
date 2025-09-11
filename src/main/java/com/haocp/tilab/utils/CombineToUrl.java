package com.haocp.tilab.utils;

import com.haocp.tilab.entity.Bag;
import com.haocp.tilab.entity.Collection;
import org.springframework.beans.factory.annotation.Value;

public final class CombineToUrl {

    @Value("${app.image.url}")
    static String imageUrl;

    private CombineToUrl() {}

    public static String collectionThumbnail(Long collectionId) {
        return imageUrl + "collection/" + collectionId;
    }

    public static String bagImages(String bagId, boolean main, String baseUrl){
        return main ?
                imageUrl + bagId + "/main/" + baseUrl
                : imageUrl + bagId + "/details/" + baseUrl;
    }

}


