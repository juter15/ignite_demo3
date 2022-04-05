package com.example.ignite_demo3;

import org.apache.ignite.Ignite;
import org.apache.ignite.IgniteException;
import org.apache.ignite.Ignition;
import org.apache.ignite.cache.CacheAtomicityMode;
import org.apache.ignite.cache.CacheMode;
import org.apache.ignite.cache.CacheWriteSynchronizationMode;
import org.apache.ignite.cluster.ClusterState;
import org.apache.ignite.configuration.*;
import org.apache.ignite.spi.discovery.tcp.TcpDiscoverySpi;
import org.apache.ignite.spi.discovery.tcp.ipfinder.vm.TcpDiscoveryVmIpFinder;
import org.apache.ignite.springdata22.repository.config.EnableIgniteRepositories;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Repository;

import java.util.Arrays;
import java.util.Collections;

@Configuration
@EnableIgniteRepositories
public class IgniteConfig {

    @Bean(name = "igniteCfgInstance")
    public IgniteConfiguration igniteInstance(){
        IgniteConfiguration igniteConfiguration = new IgniteConfiguration();
        //igniteConfiguration.setClientMode(true);
        igniteConfiguration.setPeerClassLoadingEnabled(true);
        igniteConfiguration.setDeploymentMode(DeploymentMode.CONTINUOUS);
//        ConnectorConfiguration connectorConfiguration = new ConnectorConfiguration();
//        connectorConfiguration.setPort(47500);
        igniteConfiguration.setMetricsLogFrequency(60000);
        igniteConfiguration.setQueryThreadPoolSize(2);
        igniteConfiguration.setDataStreamerThreadPoolSize(1);
        igniteConfiguration.setManagementThreadPoolSize(2);
        igniteConfiguration.setRebalanceThreadPoolSize(1);
        igniteConfiguration.setAsyncCallbackPoolSize(2);
        //IgniteLogger log = new Slf();
        //igniteConfiguration.setGridLogger(log);

        // Ignite persistence configuration.
        DataStorageConfiguration storageCfg = new DataStorageConfiguration()
                .setWalMode(WALMode.LOG_ONLY)
                .setStoragePath("C:\\apache-ignite-2.12.0-bin\\work")
                .setWalPath("C:\\apache-ignite-2.12.0-bin\\work" + "/wal")
                .setWalArchivePath("C:\\apache-ignite-2.12.0-bin\\work" + "/wal/archive");

        // Enabling the persistence.
        storageCfg.getDefaultDataRegionConfiguration().setPersistenceEnabled(true);

        // Applying settings.
        igniteConfiguration.setDataStorageConfiguration(storageCfg);

        igniteConfiguration.setIgniteInstanceName("test-2");
//        BinaryConfiguration binaryConfiguration = new BinaryConfiguration();
//        binaryConfiguration.setCompactFooter(false);
//        igniteConfiguration.setBinaryConfiguration(binaryConfiguration);
        TcpDiscoverySpi tcpDiscoverySpi = new TcpDiscoverySpi();
        TcpDiscoveryVmIpFinder tcpDiscoveryVmIpFinder = new TcpDiscoveryVmIpFinder();
        tcpDiscoveryVmIpFinder.setAddresses(Collections.singletonList("127.0.0.1:47500..47509"));
        tcpDiscoverySpi.setIpFinder(tcpDiscoveryVmIpFinder);
        igniteConfiguration.setDiscoverySpi(tcpDiscoverySpi);
        return igniteConfiguration;
    }

    @Bean(destroyMethod = "close", name="igniteInstance")
    public Ignite ignite(IgniteConfiguration igniteConfiguration) throws IgniteException {
        Ignite ignite = Ignition.start(igniteConfiguration);
        ignite.cluster().state(ClusterState.ACTIVE);

        // Cache configuration
        CacheConfiguration<String, ToyModel> test1 = new CacheConfiguration<String, ToyModel>("Toy")
                .setCacheMode(CacheMode.PARTITIONED)
                //.setBackups(1)
                .setAtomicityMode(CacheAtomicityMode.TRANSACTIONAL)
                .setWriteSynchronizationMode(CacheWriteSynchronizationMode.PRIMARY_SYNC)
                .setIndexedTypes(String.class, ToyModel.class);

        ignite.getOrCreateCaches(Arrays.asList(test1)); //캐시 생성. 여러개 추가 가능성
        return ignite;
    }

//    @Bean
//    public Ignite igniteInstance() {
//        IgniteConfiguration cfg = new IgniteConfiguration();
//
//        // Setting some custom name for the node.
//        cfg.setIgniteInstanceName("springDataNode");
//
//        // Enabling peer-class loading feature.
//        cfg.setPeerClassLoadingEnabled(true);
//
//        // Defining and creating a new cache to be used by Ignite Spring Data
//        // repository.
//        CacheConfiguration ccfg = new CacheConfiguration("Toy ");
//
//        // Setting SQL schema for the cache.
//        ccfg.setIndexedTypes(String.class, ToyModel.class);
//
//        cfg.setCacheConfiguration(ccfg);
//
//
//
//        return Ignition.start(cfg);
//    }
}
