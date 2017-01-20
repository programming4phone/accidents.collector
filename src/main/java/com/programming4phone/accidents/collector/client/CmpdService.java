package com.programming4phone.accidents.collector.client;

import static java.util.concurrent.TimeUnit.MINUTES;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.cache.CacheManager;
import javax.cache.annotation.CacheDefaults;
import javax.cache.annotation.CacheResult;
import javax.cache.configuration.MutableConfiguration;
import javax.cache.expiry.Duration;
import javax.cache.expiry.TouchedExpiryPolicy;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.cache.JCacheManagerCustomizer;
import org.springframework.stereotype.Component;

import com.programming4phone.accidents.charlotte.wsdl.CMPDAccidentsResponse;

@Component
@CacheDefaults(cacheName = "accidents")
public class CmpdService {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(CmpdService.class);
	
	@Autowired
	private CmpdSoapClient cmpdSoapClient;
	
    @Component
    public static class CachingSetup implements JCacheManagerCustomizer {
      @Override
      public void customize(CacheManager cacheManager) {
    	  /*
    	   * Information in this CMPD CAD 911 system is updated every three minutes. 
    	   * For details, see http://maps.cmpd.org/trafficaccidents/
    	   */
        cacheManager.createCache("accidents", new MutableConfiguration<>()
          .setExpiryPolicyFactory(TouchedExpiryPolicy.factoryOf(new Duration(MINUTES, 3)))
          .setStoreByValue(false)
          .setStatisticsEnabled(true));
      }
    }

    /**
     * Transform the CMPDAccidentsResponse into a List of Accident objects.
     * The list is cached and the actual CMPDAccidents SOAP API is only
     * invoked once every three minutes.
     * @param accidentsCacheKey
     * @return List of Accident objects
     */
	@CacheResult
	public List<Accident> getAccidentList(String accidentsCacheKey) {
		LOGGER.info("AccidentList  not found in cache. TimeStamp: {}", new Date());
		CMPDAccidentsResponse response = cmpdSoapClient.getAccidents();
		List<Accident> accidents = new ArrayList<Accident>();
		response.getCMPDAccidentsResult().getACCIDENTS().forEach(a->accidents.add(new Accident(a)));
		return accidents;
	}

}

