package com.jorm.forex.price_record;

import com.jorm.forex.model.PriceRecord;
import com.jorm.forex.model.Symbol;
import com.jorm.forex.price_data.PriceDataProvider;
import com.jorm.forex.price_data.PriceDataProviderFactory;
import com.jorm.forex.price_data.PriceDataProviderServiceResolver;
import com.jorm.forex.repository.PriceRecordSearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class PriceRecordCreator {

    @Autowired
    private PriceRecordSearchService priceRecordSearchService;

    @Autowired
    private PriceDataProviderServiceResolver priceDataProviderServiceResolver;

    @Autowired
    private PriceDataProviderFactory priceDataProviderFactory;

    @Autowired
    private EntityManager em;

    public PriceRecordCreator(PriceRecordSearchService priceRecordSearchService, PriceDataProviderServiceResolver priceDataProviderServiceResolver, PriceDataProviderFactory priceDataProviderFactory, EntityManager em) {
        this.priceRecordSearchService = priceRecordSearchService;
        this.priceDataProviderServiceResolver = priceDataProviderServiceResolver;
        this.priceDataProviderFactory = priceDataProviderFactory;
        this.em = em;
    }

    public List<PriceRecord> createPriceRecords(Resource dataResource, Symbol symbol){

        String priceDataProviderName = priceDataProviderServiceResolver.resolveFromResource(dataResource);
        PriceDataProvider priceDataProvider = priceDataProviderFactory.getPriceDataProvider(priceDataProviderName);

        // TODO no need to instantiate all objects. Only first and last date should be checked.
        List<PriceRecord> newPriceRecords = priceDataProvider.getData(dataResource);

        List<PriceRecord> existingPriceRecords = priceRecordSearchService.findBySymbolBetweenDates(symbol, newPriceRecords.get(0).getDateTime(), newPriceRecords.get(newPriceRecords.size()-1).getDateTime());

        //TODO we should persist only new records
        if(existingPriceRecords.size() != newPriceRecords.size()){

            for(PriceRecord priceRecord : newPriceRecords){
                priceRecord.setSymbol(symbol);
                em.persist(priceRecord);
            }
            em.flush();
            return newPriceRecords;
        }

        return existingPriceRecords;
    }
}
