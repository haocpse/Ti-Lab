package com.haocp.tilab.utils;

import com.haocp.tilab.entity.Bag;
import com.haocp.tilab.entity.Collection;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class CombineToUrl {

    @Value("${app.image.url}")
    private String imageUrl;

    public String collectionThumbnail(Long collectionId) {
        return imageUrl + "collection/" + collectionId;
    }

    public String bagImages(String bagId, boolean main, String baseUrl){
        return main ?
                imageUrl + bagId + "/main/" + baseUrl
                : imageUrl + bagId + "/details/" + baseUrl;
    }

}


