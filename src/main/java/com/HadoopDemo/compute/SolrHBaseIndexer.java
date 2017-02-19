package com.HadoopDemo.compute;

import com.HadoopDemo.common.MapReduce;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.Cell;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.client.Scan;
import org.apache.hadoop.hbase.io.ImmutableBytesWritable;
import org.apache.hadoop.hbase.mapreduce.TableMapper;
import org.apache.hadoop.hbase.util.Bytes;
import org.apache.hadoop.io.Text;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrServer;
import org.apache.solr.common.SolrInputDocument;

import java.io.IOException;
import java.net.URISyntaxException;

/**
 * 文件名：SolrHBaseIndexer
 * 功能： 通过MR操作简历solr索引
 * 创建人：zhuminming
 * 创建日期：2017-02-09
 */
public class SolrHBaseIndexer{
    private static  String SOLR_SERVER="http://192.168.21.130:8983/solr"; // Solr服务器地址
    private static String HBASE_TABLE_NAME; // 需要建立Solr索引的HBase表名称
    private static String HBASE_TABLE_FAMILY; // HBase表的列族

    public static class SolrHBaseIndexerMapper extends TableMapper<Text,Text> {


        @Override
        public void map(ImmutableBytesWritable row, Result result, Context context) throws IOException {

            Configuration conf = context.getConfiguration();

            HttpSolrServer solrServer = new HttpSolrServer("http://192.168.21.130:8983/solr");
            solrServer.setDefaultMaxConnectionsPerHost(100);
            solrServer.setMaxTotalConnections(1000);
            solrServer.setSoTimeout(20000);
            solrServer.setConnectionTimeout(20000);
            SolrInputDocument solrDoc = new SolrInputDocument();
            try {
                solrDoc.addField("rowkey", new String(result.getRow()));
                for (Cell cell : result.listCells()) {
                    String fieldName = Bytes.toString(cell.getQualifierArray(), cell.getQualifierOffset(), cell.getQualifierLength());
                    String fieldValue = Bytes.toString(cell.getValueArray(), cell.getValueOffset(), cell.getValueLength());
                    solrDoc.addField(fieldName, fieldValue);
                }
                solrServer.add(solrDoc);
                solrServer.commit(true, true, true);
            } catch (SolrServerException e) {
                System.err.println("更新Solr索引异常:" + Bytes.toString(result.getRow()));
            }
        }
    }
    public static void main(String[] args) throws IOException,InterruptedException, ClassNotFoundException, URISyntaxException {

        if (args.length !=1) {
            System.err.println("输入参数: <outputPath>");
            System.exit(1);
        }

        String outputPath = args[0];
        Scan scan = new Scan();
        scan.addFamily(Bytes.toBytes(HBASE_TABLE_FAMILY));
        scan.setCaching(500); // 设置缓存数据量来提高效率
        scan.setCacheBlocks(false);

        MapReduce mr = MapReduce.getMapReduce(false,"SolrHBaseIndexer",SolrHBaseIndexer.class,SolrHBaseIndexerMapper.class,Text.class,Text.class,null,null,null,null,outputPath,scan);
        mr.waitForCompletion();
    }
}
