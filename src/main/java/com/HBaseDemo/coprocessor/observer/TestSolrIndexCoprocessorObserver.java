package com.HBaseDemo.coprocessor.observer;

import org.apache.hadoop.hbase.Cell;
import org.apache.hadoop.hbase.client.Durability;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.coprocessor.BaseRegionObserver;
import org.apache.hadoop.hbase.coprocessor.ObserverContext;
import org.apache.hadoop.hbase.coprocessor.RegionCoprocessorEnvironment;
import org.apache.hadoop.hbase.regionserver.wal.WALEdit;
import org.apache.hadoop.hbase.util.Bytes;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.ConcurrentUpdateSolrServer;
import org.apache.solr.common.SolrInputDocument;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.List;
import java.util.NavigableMap;


/**
 * Created by zmm on 2017-01-15.
 */
public class TestSolrIndexCoprocessorObserver extends BaseRegionObserver{
    private  static final Logger LOG = LoggerFactory.getLogger(TestSolrIndexCoprocessorObserver.class);
    private  static final String solrUrl = "http://192.168.21.130:8080/solr/collection1";
    private static final SolrServer solrServer = new ConcurrentUpdateSolrServer(solrUrl,10000,20);


    /**
     * 建立solr索引
     *
     * @throws  IOException
     */
    @Override
    public void postPut(final ObserverContext<RegionCoprocessorEnvironment> e,final Put put,final WALEdit eidt,final Durability durability) throws IOException {
        String tablename = Bytes.toString(e.getEnvironment().getRegion().getRegionInfo().getTable().getName());
        if(tablename.startsWith("hbase:")){
            return;
        }

        SolrInputDocument doc = getInputDoc(put);
        if(doc==null){
            LOG.error("error!" + Bytes.toString(put.getRow()));
        }else{
            try{
                solrServer.add(doc);
                solrServer.commit(true,true,true);
            } catch (SolrServerException ex) {
                ex.printStackTrace();
                LOG.error("solr server exception :"+ex.getMessage());
            }
        }
    }


    public SolrInputDocument getInputDoc(Put put){

        SolrInputDocument doc = new SolrInputDocument();
        String rowkey = Bytes.toString(put.getRow());
        doc.addField("rowkey",rowkey);
        String cQualifiter = null;
        String cValue = null;
        NavigableMap<byte [], List<Cell>> map =  put.getFamilyCellMap();
        for(List<Cell> cells : map.values()){
            for(Cell cell : cells){
                cQualifiter = Bytes.toString(cell.getQualifierArray(),cell.getQualifierLength(),cell.getQualifierOffset());
                cValue = Bytes.toString(cell.getValueArray(),cell.getValueLength(),cell.getValueOffset());

                doc.addField(cQualifiter,cValue);
            }
        }
        return doc;
    }
}
