package com.elevenst.common.listener;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import skt.tmall.common.util.esocache.ESOCacheException;
import skt.tmall.common.util.esocache.ESOCachePool;
import skt.tmall.common.util.esocache.ESOCachePoolContainer;
import skt.tmall.common.util.esocache.ESOCachePoolContainerConfig;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.Serializable;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Properties;

public class ESOCacheContextListener implements ServletContextListener {

    private static final Log log = LogFactory.getLog(com.elevenst.common.listener.ESOCacheContextListener.class);
    private static final String paramKeyPropertyFile = "PropertyFileName";
    private static final String paramKeyPropertyFileReplacer = "[$server_type]";
    private static final String paramKeyESOCacheEnabled = "esocache.enable";
    private static final boolean paramDefaultESOCacheEnabled = false;
    private static final String paramKeyPoolConTimeout = "esocache.pool.con.timeout";
    private static final int paramDefaultPoolConTimeout = 1000;
    private static final String paramKeyPoolConMax = "esocache.pool.con.max";
    private static final int paramDefaultPoolConMax = 50;
    private static final String paramKeyPoolIdleMax = "esocache.pool.idle.max";
    private static final int paramDefaultPoolIdleMax = 10;
    private static final String paramKeyPoolIdleMin = "esocache.pool.idle.min";
    private static final int paramDefaultPoolIdleMin = 3;
    private static final String paramKeyPoolIdleEvictionTime = "esocache.pool.idle.evictiontime";
    private static final long paramDefaultPoolIdleEvictionTime = 3600000L;
    private static final String paramKeyCacheTtl = "esocache.cache_ttl";
    private static final int paramDefaultCacheTtl = 3600;
    private static final String paramClusters = "esocache.clusters";
    private static final String paramKeyZooHost = "esocache.zookeeper.host";
    private static final String paramDefaultZooHosts = "localhost:2181";
    private static final String paramKeyZooPath = "esocache.zookeeper.path";
    private static final String paramDefaultZooPath = "/ESOCache";
    private static final String paramKeyZooTimeout = "esocache.zookeeper.timeout";
    private static final int paramDefaultZooTimeout = 1000;
    private static final String paramkeyWasInstanceName = "wasInstanceName";
    private static final String paramKeyCacheServerType = "cacheServer.type";
    private static final String paramKeyServerType = "server.type";
    private static final String paramDefaultServerType = "real";
    private static final long serialVersionUID = 8831176474293283132L;
    private Properties properties = new Properties();
    private String valZooHost;
    private String valZooPath;
    private int valZooTimeout;
    private int valCacheTtl;
    private int valPoolConTimeout;
    private int valPoolConMax;
    private int valPoolIdleMax;
    private int valPoolIdleMin;
    private long valPoolIdleEvictionTime;
    private String valClusters;
    private String wasInstanceName = null;
    private Boolean isESOCacheEnabled = false;

    public ESOCacheContextListener() {
    }

    public void contextInitialized(ServletContextEvent argv) {
        log.info("Starting ESOCache context...");
        if (!this.loadProperty(argv)) {
            log.info("ESOCache Disabled");
        } else {
            this.isESOCacheEnabled = this.getBoolParam("esocache.enable", false);
            if (!this.isESOCacheEnabled.booleanValue()) {
                this.isESOCacheEnabled = false;
                log.info("ESOCache is disabled.");
            }

            this.wasInstanceName = getInstanceName();
            if (this.wasInstanceName == null) {
                this.isESOCacheEnabled = false;
                log.info("wasInstanceName is missing.");
            }

            this.isESOCacheEnabled = true;
            log.info("ESOCache enabled.");
            log.info("Initializing ESOCache pool.");
            this.valZooHost = this.getStringParam("esocache.zookeeper.host", "localhost:2181");
            this.valZooPath = this.getStringParam("esocache.zookeeper.path", "/ESOCache");
            this.valZooTimeout = this.getIntParam("esocache.zookeeper.timeout", 1000);
            this.valPoolConTimeout = this.getIntParam("esocache.pool.con.timeout", 1000);
            this.valPoolConMax = this.getIntParam("esocache.pool.con.max", 50);
            this.valPoolIdleMax = this.getIntParam("esocache.pool.idle.max", 10);
            this.valPoolIdleMin = this.getIntParam("esocache.pool.idle.min", 3);
            this.valPoolIdleEvictionTime = this.getLongParam("esocache.pool.idle.evictiontime", 3600000L);
            this.valCacheTtl = this.getIntParam("esocache.cache_ttl", 3600);
            this.valClusters = this.getStringParam("esocache.clusters", (String)null);
            ESOCachePoolContainerConfig config = new ESOCachePoolContainerConfig(this.valZooHost, this.valZooPath, (long)this.valZooTimeout);
            config.setTimeout(this.valPoolConTimeout);
            config.setInitTimeout((long)this.valZooTimeout);
            config.setMaxActive(this.valPoolConMax);
            config.setMaxIdle(this.valPoolIdleMax);
            config.setMinIdle(this.valPoolIdleMin);
            config.setMinEvictableIdleTimeMillis(this.valPoolIdleEvictionTime);
            config.setCacheTtl(this.valCacheTtl);

            try {
                new ESOCachePoolContainer(config);
            } catch (ESOCacheException var10) {
                log.error("ESOCache is disabled.");
                log.error(var10.getMessage(), var10);
                return;
            }

            if (this.valClusters != null && this.valClusters.length() > 0) {
                String[] clustersArray = this.valClusters.trim().split(" ");
                String[] var7 = clustersArray;
                int var6 = clustersArray.length;

                for(int var5 = 0; var5 < var6; ++var5) {
                    String clusterName = var7[var5];

                    try {
                        ESOCachePool pool = ESOCachePoolContainer.activate(clusterName);
                        if (pool == null) {
                            log.info("ESOCache cluster hasn't activated : " + clusterName);
                        } else {
                            log.info("ESOCache cluster [" + clusterName + "] has been activated.");
                        }
                    } catch (ESOCacheException var9) {
                        log.info("ESOCache cluster hasn't activated : " + clusterName);
                        log.info(var9.getMessage(), var9);
                    }
                }
            }

        }
    }

    public void contextDestroyed(ServletContextEvent arg0) {
        if (this.isESOCacheEnabled.booleanValue()) {
            log.info("Stopping ESOCache context...");
            ESOCachePoolContainer.destory();
            log.info("Stopped ESOCache context.");
        }

    }

    public boolean getESOCacheEnabled() {
        Boolean var1 = this.isESOCacheEnabled;
        synchronized(this.isESOCacheEnabled) {
            return this.isESOCacheEnabled.booleanValue();
        }
    }

    private boolean loadProperty(ServletContextEvent argv) {
        ServletContext context = argv.getServletContext();
        String propertyFileName = context.getInitParameter("PropertyFileName");
        if (propertyFileName == null) {
            log.error("Parameter is not initialized. \" PropertyFileName\" in web.xml");
            return false;
        } else {
            String serverType = System.getProperty("cacheServer.type");
            if (serverType == null || serverType.length() == 0) {
                serverType = System.getProperty("server.type");
            }

            if (serverType == null || serverType.length() == 0) {
                serverType = "real";
            }

            propertyFileName = replace(propertyFileName, "[$server_type]", serverType);
            log.info("Loading property file : " + propertyFileName);

            try {
                Resource resource = new ClassPathResource(propertyFileName);

                this.properties.load(resource.getInputStream());
                return true;
            } catch (FileNotFoundException var6) {
                log.error("Not found property file \"" + propertyFileName + "\"", var6);
                return false;
            } catch (IOException var7) {
                log.error("Error during load properties file \"" + propertyFileName + "\"", var7);
                return false;
            }
        }
    }

    public String getStringParam(String key, String def) {
        String val = this.properties.getProperty(key);
        log.debug("ESOCache configure - " + key + ":" + val);
        return val != null && val.length() != 0 ? val.trim() : def;
    }

    public int getIntParam(String key, int def) {
        String val = this.properties.getProperty(key);
        log.debug("ESOCache configure - " + key + ":" + val);
        if (val != null && val.length() != 0) {
            try {
                int valInt = Integer.parseInt(val.trim());
                return valInt;
            } catch (NumberFormatException var6) {
                return def;
            }
        } else {
            return def;
        }
    }

    public long getLongParam(String key, long def) {
        String val = this.properties.getProperty(key);
        log.debug("ESOCache configure - " + key + ":" + val);
        if (val != null && val.length() != 0) {
            try {
                long valLong = Long.parseLong(val.trim());
                return valLong;
            } catch (NumberFormatException var8) {
                return def;
            }
        } else {
            return def;
        }
    }

    public boolean getBoolParam(String key, boolean def) {
        String val = this.properties.getProperty(key);
        log.debug("ESOCache configure - " + key + ":" + val);
        return val != null && val.length() != 0 ? stringToBoolean(val.trim()) : def;
    }

    public static boolean stringToBoolean(String str) {
        if (str == null) {
            return false;
        } else if (str.length() == 0) {
            return false;
        } else if (str.toLowerCase().equals("true")) {
            return true;
        } else {
            return str.toLowerCase().equals("1");
        }
    }

    public static String getInstanceName() {
        String instanceName = System.getProperty("wasInstanceName");
        String hostname = null;
        String ip = null;

        try {
            InetAddress local = InetAddress.getLocalHost();
            hostname = local.getHostName();
            ip = local.getHostAddress();
        } catch (UnknownHostException var4) {
            hostname = "UnknownHost";
            ip = "UnknownIP";
        }

        String clientName = hostname + ":" + ip + ":" + instanceName;
        log.debug("Client name : " + clientName);
        return clientName;
    }

    private static String replace(String srcStr, String oldStr, String newStr) {
        StringBuffer sb = new StringBuffer(srcStr);
        int start = 0;
        int end = 0;
        int pos = 0;

        while(true) {
            start = srcStr.indexOf(oldStr, pos);
            if (start == -1) {
                return sb.toString();
            }

            end = start + oldStr.length();
            sb.replace(start, end, newStr);
            pos = start + newStr.length();
            srcStr = sb.toString();
        }
    }
}