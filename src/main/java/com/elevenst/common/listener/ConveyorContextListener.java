package com.elevenst.common.listener;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.core.io.Resource;
import skt.tmall.conveyor.ServerType;
import skt.tmall.conveyor.core.Conveyor;
import skt.tmall.conveyor.core.ConveyorConfig;
import skt.tmall.conveyor.core.Producible;
import skt.tmall.conveyor.core.exception.ConveyorConnectionExcpetion;
import skt.tmall.conveyor.core.exception.ConveyorException;
import skt.tmall.conveyor.core.exception.ConveyorSerializableException;
import skt.tmall.conveyor.core.pool.ConveyorPool;
import skt.tmall.conveyor.core.pool.ConveyorPoolConfig;
import skt.tmall.conveyor.product.commondb.CommonDBModule;
import skt.tmall.conveyor.product.commondb.DBRowMap;
import skt.tmall.conveyor.product.commondb.servlet.ConveyorContext;
import skt.tmall.conveyor.product.commondb.servlet.LocalBuffer;
import skt.tmall.conveyor.rabbitmq.api.QueueMapBuffer;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;
import org.springframework.core.io.ClassPathResource;

public class ConveyorContextListener implements ServletContextListener {




    private final Log log = LogFactory.getLog(ConveyorContextListener.class);

    private static final String CONVEYOR_PRODUCER_NAME = CommonDBModule.MODULE;

    private static final String CONVEYOR_CONTEXT_PROPERTIES_PATH_KEY = "ConveyorContextPropertiesPath";
    private static final String serverTypeString = ServerType.getServerType().getName();

    public static final String KEY_CONVEYOR_MQ_HOST = ".conveyor.mq.host";
    public static final String KEY_CONVEYOR_MQ_PORT = ".conveyor.mq.port";
    public static final String KEY_CONVEYOR_MQ_USER = ".conveyor.mq.user";
    public static final String KEY_CONVEYOR_MQ_PASS = ".conveyor.mq.pass";
    public static final String KEY_CONVEYOR_MQ_VHOST = ".conveyor.mq.vhost";
    public static final String KEY_CONVEYOR_MQ_ADMIN_HOST = ".conveyor.mq.admin.host";
    public static final String KEY_CONVEYOR_MQ_ADMIN_PORT = ".conveyor.mq.admin.port";
    public static final String KEY_CONVEYOR_MQ_ADMIN_USER = ".conveyor.mq.admin.user";
    public static final String KEY_CONVEYOR_MQ_ADMIN_PASS = ".conveyor.mq.admin.pass";

    public static final String KEY_LOCAL_BUFFER_WORKER_THREAD = ".buffer.worker.thread";
    public static final String KEY_LOCAL_BUFFER_MAX_QUEUE_SIZE = ".buffer.max.queue.size";
    public static final String KEY_LOCAL_BUFFER_ERROR_LOGGING_THRESHOLD = ".buffer.error.logging.threshold";
    public static final String KEY_LOCAL_BUFFER_DATA_PARSISTENCE = ".buffer.data.parsistence";
    public static final String KEY_LOCAL_BUFFER_DATA_DIR = ".buffer.data.dir";

    public static final int DEFAULT_LOCAL_BUFFER_WORKER_THREAD = 1;
    public static final int DEFAULT_LOCAL_BUFFER_MAX_QUEUE_SIZE = 1000 * 100;
    public static final int DEFAULT_LOCAL_BUFFER_ERROR_LOGGING_THRESHOLD = 1000 * 50;
    public static final boolean DEFAULT_LOCAL_BUFFER_DATA_PARSISTENCE = true;
    public static final String DEFAULT_LOCAL_BUFFER_DATA_DIR = System.getProperty("java.io.tmpdir") + File.separator + "conveyor.local.buffer";

    private String mqHost;
    private int mqPort;
    private String mqUser;
    private String mqPass;
    private String mqVHost;

    private String mqAdminHost;
    private int mqAdminPort;
    private String mqAdminUser;
    private String mqAdminPass;

    private int localBufferWorkerThread;
    private int localBufferMaxQueueSize;
    private int localBufferErrorLoggingThreshold;
    private boolean localBufferParsistence;
    private String localBufferDataDir;

    private String propertyFileName;
    private Properties prop = new Properties();

    private LocalBuffer localBuffer = LocalBuffer.getInstance();;

    private boolean workerRunnable = true;
    private List<Thread> localBufferWorkerList;
    private ThreadGroup localBufferWorkerGroup;


    private ConveyorPool conveyorPool;

    public void contextInitialized(ServletContextEvent argv) {
        log.info("Initializing conveyor context...");
        ServletContext context = argv.getServletContext();

        // loading property file.
        propertyFileName = context.getInitParameter(CONVEYOR_CONTEXT_PROPERTIES_PATH_KEY);

        try {
            //prop.load(context.getResourceAsStream(propertyFileName));
            Resource resource = new ClassPathResource(propertyFileName);
            prop.load(resource.getInputStream());
        } catch (IOException e) {
            // Properties 파일을 제대로 읽지 못했다면 종료한다.
            log.error("Cannot load properties file : " + propertyFileName, e);

            // stdout으로 출력하지 않으면 감지하기 어려움.
            System.out.println("Cannot load properties file : " + propertyFileName);
            e.printStackTrace();
            System.exit(1);
        }

        // loading mandatory property values
        mqHost = getProperty(serverTypeString + KEY_CONVEYOR_MQ_HOST, null);
        if(mqHost == null) {
            exitBecauseMandatory(serverTypeString + KEY_CONVEYOR_MQ_HOST);
        }
        mqPort = getPropertyInt(serverTypeString + KEY_CONVEYOR_MQ_PORT, null);
        if(mqHost == null) {
            exitBecauseMandatory(serverTypeString + KEY_CONVEYOR_MQ_PORT);
        }
        mqUser = getProperty(serverTypeString + KEY_CONVEYOR_MQ_USER, null);
        if(mqHost == null) {
            exitBecauseMandatory(serverTypeString + KEY_CONVEYOR_MQ_USER);
        }
        mqPass = getProperty(serverTypeString + KEY_CONVEYOR_MQ_PASS, null);
        if(mqHost == null) {
            exitBecauseMandatory(serverTypeString + KEY_CONVEYOR_MQ_PASS);
        }
        mqVHost = getProperty(serverTypeString + KEY_CONVEYOR_MQ_VHOST, null);
        if(mqHost == null) {
            exitBecauseMandatory(serverTypeString + KEY_CONVEYOR_MQ_VHOST);
        }

        mqAdminHost = getProperty(serverTypeString + KEY_CONVEYOR_MQ_ADMIN_HOST, null);
        if(mqHost == null) {
            exitBecauseMandatory(serverTypeString + KEY_CONVEYOR_MQ_ADMIN_HOST);
        }
        mqAdminPort = getPropertyInt(serverTypeString + KEY_CONVEYOR_MQ_ADMIN_PORT, null);
        if(mqHost == null) {
            exitBecauseMandatory(serverTypeString + KEY_CONVEYOR_MQ_ADMIN_PORT);
        }
        mqAdminUser = getProperty(serverTypeString + KEY_CONVEYOR_MQ_ADMIN_USER, null);
        if(mqHost == null) {
            exitBecauseMandatory(serverTypeString + KEY_CONVEYOR_MQ_ADMIN_USER);
        }
        mqAdminPass = getProperty(serverTypeString + KEY_CONVEYOR_MQ_ADMIN_PASS, null);
        if(mqHost == null) {
            exitBecauseMandatory(serverTypeString + KEY_CONVEYOR_MQ_ADMIN_PASS);
        }

        // loading optional property values
        localBufferWorkerThread = getPropertyInt(serverTypeString + KEY_LOCAL_BUFFER_WORKER_THREAD, DEFAULT_LOCAL_BUFFER_WORKER_THREAD);
        localBufferMaxQueueSize = getPropertyInt(serverTypeString + KEY_LOCAL_BUFFER_MAX_QUEUE_SIZE, DEFAULT_LOCAL_BUFFER_MAX_QUEUE_SIZE);
        localBufferErrorLoggingThreshold = getPropertyInt(serverTypeString + KEY_LOCAL_BUFFER_MAX_QUEUE_SIZE, DEFAULT_LOCAL_BUFFER_MAX_QUEUE_SIZE);
        localBufferParsistence = getPropertyBoolean(serverTypeString + KEY_LOCAL_BUFFER_DATA_PARSISTENCE, DEFAULT_LOCAL_BUFFER_DATA_PARSISTENCE);
        localBufferDataDir = getProperty(serverTypeString + KEY_LOCAL_BUFFER_DATA_DIR, DEFAULT_LOCAL_BUFFER_DATA_DIR);

        // Local Buffer
        if(localBufferParsistence) {
            localBuffer.setDataDir(localBufferDataDir);
            localBuffer.loadDataFromFile();
            localBuffer.setMaxQueueSize(localBufferMaxQueueSize);
            localBuffer.setMaxErrorLoggingSize(localBufferErrorLoggingThreshold);
        }

        // Conveyor Pool
        ConveyorPoolConfig poolConfig = new ConveyorPoolConfig();
        poolConfig.maxActive = localBufferWorkerThread * 2;
        poolConfig.maxIdle = 2;

        ConveyorConfig conveyorConfig = new ConveyorConfig(mqHost, mqPort, mqUser, mqPass, mqVHost);
        conveyorConfig.setAutomaticRecoveryEnabled(true);

        conveyorPool = new ConveyorPool(poolConfig, conveyorConfig);

        // inititalizing local buffer worker threads
        localBufferWorkerList = new ArrayList<Thread>();
        localBufferWorkerGroup = new ThreadGroup("ConveyorLocalBufferWorkers");
        for(int i = 0; i < localBufferWorkerThread; i++) {
            Thread t = new Thread(localBufferWorkerGroup, new BufferWorker(), localBufferWorkerGroup.getName() + "-" + i);
            t.start();
            log.debug("BufferWorker starting...");
            localBufferWorkerList.add(t);
        }

        // Queue Monitoring을 위한 QueueMapBuffer를 Starting 한다.
        QueueMapBuffer.startMonitor(mqAdminHost, mqAdminPort, mqAdminUser, mqAdminPass);

        ConveyorContext.getInstance().active();
        log.info("Initialized conveyor context successfully.");
    }



    public void contextDestroyed(ServletContextEvent argv) {
        log.info("Destorying conveyor context...");

        ServletContext context = argv.getServletContext();
        ConveyorContext.getInstance().deactive();
        workerRunnable = false;
        int deadThread = 0;

        // QueueMapBuffer를 중지
        QueueMapBuffer.stopMonitor();

        // 동작중인 Worker를 중지
        while(localBufferWorkerList.size() > deadThread) {
            localBufferWorkerGroup.interrupt();
            deadThread = 0;
            for(Thread t : localBufferWorkerList) {
                if(!t.isAlive()) {
                    deadThread++;
                }
            }
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            log.debug("Waitting worker thread shutdown.");
        }


        if(localBufferParsistence) {
            localBuffer.writeDataToFile();
            localBuffer.clear();
        }
        log.info("Destoried conveyor context successfully...");
    }

    private Long getPropertyLong(String key, Long def) {
        String data = prop.getProperty(key);
        log.debug("Reading long property " + key + ":" + data);
        if(data == null || data.length() == 0) {
            return def;
        }
        return Long.parseLong(data);
    }

    private Integer getPropertyInt(String key, Integer def) {
        String data = prop.getProperty(key);
        log.debug("Reading int property " + key + ":" + data);
        if(data == null || data.length() == 0) {
            return def;
        }
        return Integer.parseInt(data);
    }

    private String getProperty(String key, String def) {
        String data = prop.getProperty(key);
        log.debug("Reading string property " + key + ":" + data);
        if(data == null || data.length() == 0) {
            return def;
        }
        return data;
    }

    private Boolean getPropertyBoolean(String key, Boolean def) {
        String data = prop.getProperty(key);
        log.debug("Reading boolean property " + key + ":" + data);
        if(data == null || data.length() == 0) {
            return def;
        }
        String dataLow = data.toLowerCase();
        if(dataLow.equals("true") || dataLow.equals("t")) {
            return true;
        }else if(dataLow.equals("false") || dataLow.equals("f")){
            return false;
        }else {
            log.info("Unknown string for boolean option, key:"+key+ ", value:" + data + ", going to return default value");
            return def;
        }
    }

    private void exitBecauseMandatory(String key) {
        log.error("Cannot find proper value '" + key + "' in properties file : " + propertyFileName);
        System.out.println("Cannot find proper value '" + key + "' in properties file : " + propertyFileName);
        System.exit(1);
    }

    private class BufferWorker implements Runnable {
        Conveyor conv = null;
        Producible<Object> prod = null;
        public void run() {
            long processedCount = 0;
            log.debug("Staring local buffer worker.");
            while(workerRunnable) {
                if(conv == null) {
                    try {
                        conv = conveyorPool.getResource();
                        prod = conv.getProducer(CONVEYOR_PRODUCER_NAME);
                    }catch(ConveyorException e) {
                        log.error(e.getMessage(), e);
                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException ie) {
                            log.debug(ie.getMessage(), ie);
                        }
                        continue;
                    }
                }

                // Consume local buffer.
                DBRowMap rowmap = localBuffer.poll();
                if(rowmap == null) {
                    continue;
                }

                try{
                    prod.produce(rowmap);
                    processedCount++;
                    if((processedCount % 10000) == 0) {
                        log.debug(Thread.currentThread().getName() + " - processed " + processedCount + ", remained " + localBuffer.size());
                    }
                }catch(ConveyorConnectionExcpetion e) {
                    log.error(e.getMessage(), e);
                    conveyorPool.returnBrokendResource(conv);
                    conv = null;
                    prod = null;

                    // 정상적으로 전달하지 못한 Object는 다시 LocalBuffer에 넣는다.
                    try {
                        localBuffer.add(rowmap);
                    } catch (IOException e1) {
                        log.error("Cannot return back into Local Buffer during MQ connection is abnormal. Probably the buffer is FULL. Data is losing.");
                    }

                }catch(ConveyorSerializableException e) {
                    log.error(e.getMessage(), e);
                }catch(ConveyorException e) {
                    log.error(e.getMessage(), e);
                }
            }

            if(conv != null) {
                conveyorPool.returnResource(conv);
                conv = null;
                prod = null;
            }

            log.debug("Finsiehd local buffer worker.");
        }
    }



}
