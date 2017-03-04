package com.jorm.forex.price_data;

import org.apache.commons.io.FilenameUtils;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

// TODO consider implementing PriceDataProviderFactory

@Service
public class PriceDataProviderServiceResolver {

    public String resolveFromResource(Resource resource){
        return PriceDataProviderName.PRICE_DATA_PROVIDER_NAME_PREFIX + FilenameUtils.getExtension(resource.getFilename());
    }
}