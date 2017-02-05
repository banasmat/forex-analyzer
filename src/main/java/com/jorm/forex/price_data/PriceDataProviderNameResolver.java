package com.jorm.forex.price_data;

import org.apache.commons.io.FilenameUtils;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

// TODO consider merging it with PriceDataProviderFactory
// TODO or consider renaming to PriceDataProviderServiceResolver

@Service
public class PriceDataProviderNameResolver {

    public String resolveFromResource(Resource resource){
        return PriceDataProviderName.PRICE_DATA_PROVIDER_NAME_PREFIX + FilenameUtils.getExtension(resource.getFilename());
    }
}