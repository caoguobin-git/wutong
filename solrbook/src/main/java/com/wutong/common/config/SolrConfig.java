/***********************************************
 * File Name: SolrCOnfig
 * Author: caoguobin
 * mail: caoguobin@live.com
 * Created Time: 05 12 2019 19:57
 ***********************************************/

package com.wutong.common.config;

import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class SolrConfig {
    @Value("${solr.host}")
    private String solrUrl;

    private HttpSolrClient solrClient;

    private SolrConfig() {
    }

    @Bean
    public HttpSolrClient solrClient() {
        if (this.solrClient==null){
            this.solrClient=new HttpSolrClient.Builder(solrUrl)
                    .withConnectionTimeout(10000)
                    .withSocketTimeout(60000)
                    .build();
        }
        return this.solrClient;
    }
}
