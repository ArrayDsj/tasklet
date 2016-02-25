# Tasklet
------
## 简介

Tasklet是一个非常简单的任务管理工具，主要用于管理执行有先后顺序的多个任务(如刷量程序的登录->搜索->点击->下载)。
它只有4个类

* Task 抽象的任务接口，
* TaskContext 任务间上下文对象，可保存任务间共享数据
* TaskGroup 一个Task特例，逻辑上的任务组。
* TaskPerformer 核心类，任务执行入口。

## 原理

![程序执行流程图](tasklet_flowchart.png)

## 使用

1. 添加maven依赖
```xml
<dependency>
    <groupId>net.wisedream</groupId>
    <artifactId>tasklet</artifactId>
    <version>1.0</version>
</dependency>
```

2. 一个简单的例子
```java
@org.junit.Test
public void simpleTest() throws Exception {
  Task payload3 = (cxt) -> {
      System.out.println("sending payload 3");
      return Terminator.SUCCESS;
  };
  Task payload2 = (cxt) -> {
      System.out.println("sending payload 2");
      return payload3;
  };
  Task payload1 = (cxt) -> {
      System.out.println("sending payload 1");
      return payload2;
  };
  TaskPerformer job = new TaskPerformer(payload1);
  switch (job.perform(null)) {
      case SUCCESS:
          System.out.println("job finished");
          break;
      default:
          System.out.println("something went wrong");
          break;
  }
}
```
示例输出
```bash
sending payload 1
sending payload 2
sending payload 3
job finished
```