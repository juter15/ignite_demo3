package com.example.ignite_demo3;

import lombok.extern.slf4j.Slf4j;
import org.apache.ignite.*;
import org.apache.ignite.cache.CacheAtomicityMode;
import org.apache.ignite.cache.CacheMode;
import org.apache.ignite.cache.CacheWriteSynchronizationMode;
import org.apache.ignite.cluster.ClusterNode;
import org.apache.ignite.cluster.ClusterState;
import org.apache.ignite.configuration.*;
import org.apache.ignite.logger.log4j.Log4JLogger;
import org.apache.ignite.spi.communication.tcp.TcpCommunicationSpi;
import org.apache.ignite.spi.discovery.tcp.TcpDiscoverySpi;
import org.apache.ignite.spi.discovery.tcp.ipfinder.multicast.TcpDiscoveryMulticastIpFinder;
import org.apache.ignite.spi.discovery.tcp.ipfinder.vm.TcpDiscoveryVmIpFinder;
import org.apache.ignite.springdata22.repository.config.EnableIgniteRepositories;
import org.apache.logging.slf4j.SLF4JLogger;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Repository;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;

@Configuration
@Slf4j
//@EnableIgniteRepositories
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
        //IgniteLogger log = new Log4JLogger();
        //igniteConfiguration.setGridLogger(log);
        //igniteConfiguration.setGridLogger(log);

        //?????? ?????????
        //igniteConfiguration.setAuthenticationEnabled(true);

        // Ignite persistence configuration.
        DataStorageConfiguration storageCfg = new DataStorageConfiguration()
                .setWalMode(WALMode.LOG_ONLY)
                .setStoragePath("/root/ignite/ignite/work"+"/storage")
                .setWalPath("/root/ignite/ignite/work"+"/wal")
                .setWalArchivePath("/root/ignite/ignite/work"+"/wal/archive");
        igniteConfiguration.setSnapshotPath("/root/ignite/ignite/work"+"/snapshots");
        // Enabling the persistence.
        storageCfg.getDefaultDataRegionConfiguration().setPersistenceEnabled(true);
        // Applying settings.
        igniteConfiguration.setDataStorageConfiguration(storageCfg);


        TcpDiscoveryMulticastIpFinder tcpDiscoveryMulticastIpFinder = new TcpDiscoveryMulticastIpFinder();

        tcpDiscoveryMulticastIpFinder.setAddresses(Arrays.asList("localhost:47500..47509"));
        //tcpDiscoveryMulticastIpFinder.setAddresses(Arrays.asList("192.168.0.109:47500..47509","192.168.0.111:47500..47509","192.168.0.112:47500..47509"));

        TcpDiscoverySpi tcpDiscoverySpi = new TcpDiscoverySpi();

//        TcpDiscoveryVmIpFinder tcpDiscoveryVmIpFinder = new TcpDiscoveryVmIpFinder();
//        tcpDiscoveryVmIpFinder.setAddresses(Collections.singletonList("127.0.0.1:47500..47509"));

        tcpDiscoverySpi.setIpFinder(tcpDiscoveryMulticastIpFinder);
//        //????????? ?????? ?????? // ??? ????????? ?????? ??????????????????
//        igniteConfiguration.setShutdownPolicy(ShutdownPolicy.GRACEFUL);

        igniteConfiguration.setDiscoverySpi(tcpDiscoverySpi);
        return igniteConfiguration;
    }

    @Bean(destroyMethod = "close", name="igniteInstance")
    public Ignite ignite(IgniteConfiguration igniteConfiguration) throws IgniteException {
        Ignite ignite = Ignition.start(igniteConfiguration);

        //persistence????????? clusterStat Active??????
        ignite.cluster().state(ClusterState.ACTIVE);
        //Collection<ClusterNode> nodes = ignite.cluster().forServers().nodes();
        //log.info("nodes: {}", nodes);
       // ignite.cluster().setBaselineTopology(nodes);

        //baseline node ?????????
        //ignite.cluster().baselineAutoAdjustEnabled(true);
        //ignite.cluster().baselineAutoAdjustTimeout(1000);
        //server Node??? ?????? ???????????? persistence storage ?????? ??????
        // Cache configuration
/*        CacheConfiguration<String, ToyModel> test1 = new CacheConfiguration<String, ToyModel>("Toy")
                .setCacheMode(CacheMode.PARTITIONED)
                *//* Backups=1?????????
                *  primary 1???, Backup 1??? ??? 2?????? ???????????? ??????
                * *//*
                .setBackups(1)
                .setAtomicityMode(CacheAtomicityMode.TRANSACTIONAL)
                .setWriteSynchronizationMode(CacheWriteSynchronizationMode.PRIMARY_SYNC)
                .setIndexedTypes(String.class, ToyModel.class);*/

//        ignite.getOrCreateCaches(Arrays.asList(test1)); //?????? ??????. ????????? ?????? ?????????
        return ignite;
    }


}
