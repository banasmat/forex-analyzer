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
import org.apache.commons.collections4.ListUtils;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

// TODO rename to factory?
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

        List<PriceRecord> newPriceRecords = priceDataProvider.getData(dataResource);
        List<PriceRecord> existingPriceRecords = priceRecordSearchService.findBySymbolBetweenDates(symbol, newPriceRecords.get(0).getDateTime(), newPriceRecords.get(newPriceRecords.size()-1).getDateTime());

        if(existingPriceRecords.size() != newPriceRecords.size()){

            List<PriceRecord> newNotYetExistingPriceRecords = new ArrayList<>();
            Boolean alreadyExists;

            for(PriceRecord newPriceRecord : newPriceRecords){

                alreadyExists = false;

                // Not efficient, but safe way of preventing duplications
                for(PriceRecord existingPriceRecord : existingPriceRecords){
                    if(newPriceRecord.getDateTime() == existingPriceRecord.getDateTime()){
                        alreadyExists = true;
                        break;
                    }
                }

                if(false == alreadyExists){
                    newPriceRecord.setSymbol(symbol);
                    em.persist(newPriceRecord);
                    newNotYetExistingPriceRecords.add(newPriceRecord);
                }
            }
            em.flush();
            return ListUtils.union(existingPriceRecords, newNotYetExistingPriceRecords);
        }

        return existingPriceRecords;
    }
}
