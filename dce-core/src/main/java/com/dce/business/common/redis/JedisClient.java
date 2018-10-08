package com.dce.business.common.redis;

import org.apache.log4j.Logger;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

public class JedisClient {
    private final static Logger logger = Logger.getLogger(JedisClient.class);

    private JedisPool jedisPool;
    private String serverName;
    private boolean ALIVE = true;
    private boolean EXCEPTION_FALG = false;

    public String getString(String key) {
        Jedis conn = getConnection();
        if (conn == null) {
            return null;
        }
        try {
            String value = conn.get(key);
            returnConnection(conn);
            return value;
        } catch (Exception e) {
            returnBorkenConnection(conn);
        }
        return null;
    }

    public void setString(String key, String value, int exp) {
        Jedis conn = getConnection();
        if (conn == null) {
            return;
        }
        try {
            if (exp > 0) {
                conn.setex(key, exp, value);
            } else {
                conn.set(key, value);
            }
            returnConnection(conn);
        } catch (Exception e) {
            returnBorkenConnection(conn);
        }
    }

    public long delete(String key) {
        Jedis conn = getConnection();
        if (conn == null) {
            return -1;
        }
        try {
            long result = conn.del(key);

            returnConnection(conn);
            return result;
        } catch (Exception e) {
            returnBorkenConnection(conn);
        }
        return -1;
    }

    /**
     * 获取连接
     * 
     * @return
     */
    private Jedis getConnection() {
        Jedis jedis = null;
        try {
            if (ALIVE) {// 当前状态为活跃才获取连接，否则直接返回null
                jedis = jedisPool.getResource();
            }
        } catch (Exception e) {
            EXCEPTION_FALG = true;
        }
        return jedis;
    }

    /**
     * 关闭数据库连接
     * 
     * @param conn
     */
    private void returnConnection(Jedis jedis) {
        if (null != jedis) {
            try {
                jedisPool.returnResource(jedis);
            } catch (Exception e) {
                jedisPool.returnBrokenResource(jedis);
            }
        }
    }

    /**
     * 关闭错误连接
     * 
     * @param jedis
     */
    private void returnBorkenConnection(Jedis jedis) {
        if (null != jedis) {
            jedisPool.returnBrokenResource(jedis);
        }
    }

    public class MonitorThread extends Thread {

        @Override
        public void run() {
            int sleepTime = 30000;
            int baseSleepTime = 1000;
            while (true) {
                try {
                    // 30秒执行监听
                    int n = sleepTime / baseSleepTime;
                    for (int i = 0; i < n; i++) {
                        // 检查到异常，立即进行检测处理
                        if (EXCEPTION_FALG) {
                            break;
                        }
                        Thread.sleep(baseSleepTime);
                    }
                    // 连续做3次连接获取
                    int errorTimes = 0;
                    for (int i = 0; i < 3; i++) {
                        try {
                            Jedis jedis = jedisPool.getResource();
                            if (jedis == null) {
                                errorTimes++;
                                continue;
                            }
                            returnConnection(jedis);
                            break;
                        } catch (Exception e) {
                            errorTimes++;
                            continue;
                        }
                    }
                    if (errorTimes == 3) {// 3次全部出错，表示服务器出现问题
                        ALIVE = false;
                        EXCEPTION_FALG = false; // 只是在异常出现第一次进行跳出处理，后面的按异常检查时间进行延时处理
                        logger.error("redis[" + serverName + "] 服务器连接不上！ ！ ！");
                        // 修改休眠时间为5秒，尽快恢复服务
                        sleepTime = 5000;
                    } else {
                        if (ALIVE == false) {
                            ALIVE = true;
                            // 修改休眠时间为30秒，尽快恢复服务
                            sleepTime = 30000;
                            logger.info("redis[" + serverName + "] 服务器恢复正常！ ！ ！");
                        }
                        EXCEPTION_FALG = false;
                        Jedis jedis = jedisPool.getResource();
                        logger.info("redis[" + serverName + "] 当前记录数：" + jedis.dbSize());
                        returnConnection(jedis);
                    }
                } catch (Exception e) {
                }
            }
        }
    }

    /**
     * 设置连接池
     * 
     * @param 数据源
     */
    public void setJedisPool(JedisPool JedisPool) {
        this.jedisPool = JedisPool;
        // 启动监听线程
        new MonitorThread().start();
    }

    /**
     * 获取连接池
     * 
     * @return 数据源
     */
    public JedisPool getJedisPool() {
        return jedisPool;
    }

    public String getServerName() {
        return serverName;
    }

    public void setServerName(String serverName) {
        this.serverName = serverName;
    }

}
