package com.fiona.mq.rabbitmq.util;

/** Snowflake */
public class IdGen {
  private static IdGen erhan=new IdGen(0,0);

  // 私有化构造器
  private IdGen (){

  }
  //提供获得对象的方法
  public static  IdGen getInstance(){
    return erhan;
  }

  private  final long twepoch = 1288834974657L;
  private  final long workerIdBits = 5L;
  private  final long datacenterIdBits = 5L;
  private  final long maxWorkerId = -1L ^ (-1L << workerIdBits);
  private  final long maxDatacenterId = -1L ^ (-1L << datacenterIdBits);
  private  final long sequenceBits = 12L;
  private  final long workerIdShift = sequenceBits;
  private  final long datacenterIdShift = sequenceBits + workerIdBits;
  private  final long timestampLeftShift = sequenceBits + workerIdBits + datacenterIdBits;
  private  final long sequenceMask = -1L ^ (-1L << sequenceBits);

  private long workerId;
  private long datacenterId;
  private long sequence = 0L;
  private long lastTimestamp = -1L;

  public  IdGen(long workerId, long datacenterId) {
    if (workerId > maxWorkerId || workerId < 0) {
      throw new IllegalArgumentException(String.format("worker Id can't be greater than %d or less than 0", maxWorkerId));
    }
    if (datacenterId > maxDatacenterId || datacenterId < 0) {
      throw new IllegalArgumentException(String.format("datacenter Id can't be greater than %d or less than 0", maxDatacenterId));
    }
    this.workerId = workerId;
    this.datacenterId = datacenterId;
  }

  public  synchronized long nextId() {
    long timestamp = timeGen();
    if (timestamp < lastTimestamp) {
      throw new RuntimeException(String.format("Clock moved backwards.  Refusing to generate id for %d milliseconds", lastTimestamp - timestamp));
    }
    if (lastTimestamp == timestamp) {
      sequence = (sequence + 1) & sequenceMask;
      if (sequence == 0) {
        timestamp = tilNextMillis(lastTimestamp);
      }
    } else {
      sequence = 0L;
    }

    lastTimestamp = timestamp;

    return ((timestamp - twepoch) << timestampLeftShift) | (datacenterId << datacenterIdShift) | (workerId << workerIdShift) | sequence;
  }

  protected  long tilNextMillis(long lastTimestamp) {
    long timestamp = timeGen();
    while (timestamp <= lastTimestamp) {
      timestamp = timeGen();
    }
    return timestamp;
  }

  protected  long timeGen() {
    return System.currentTimeMillis();
  }

  public static void main(String[] args) {
    IdGen idWorker = new IdGen(0, 0);
    for (int i = 0; i < 1000; i++) {
      long id = idWorker.nextId();
      System.out.println(id);
    }
  }
}
