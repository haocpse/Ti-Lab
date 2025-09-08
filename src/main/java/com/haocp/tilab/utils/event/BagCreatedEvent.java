package com.haocp.tilab.utils.event;

import com.haocp.tilab.dto.request.Bag.SaveImageBagRequest;
import com.haocp.tilab.entity.Bag;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.experimental.FieldDefaults;
import org.springframework.context.ApplicationEvent;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Getter
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class BagCreatedEvent extends ApplicationEvent {

    Bag bag;
    List<MultipartFile> imageBags;

    public BagCreatedEvent(Object source, Bag bag, List<MultipartFile> imageBags) {
        super(source);
        this.bag = bag;
        this.imageBags = imageBags;
    }

}
