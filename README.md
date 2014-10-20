# Tasklet
------
### 简介

Tasklet是一个简单的java工具类，主要用于将一个大任务分割成许多较小的子任务，然后对这些子任务进行协调管理。Tasklet主要有以下4个类:

* Task    : 任务/子任务，多个Task组成一个完整的任务/功能。
* Manager : 任务/子任务的执行入口。通过内部维护一个Task容器来管理要运行的Task。
* Context : 为子任务提供运行环境的上下文消息和服务。
* Launcher: 启动一个完整的任务，主要用于配置任务的执行入口和Context环境信息。

Tasklet的主要思想是将一个大型任务分解为多个功能，每个功能又可分为多个Task； 一个或多个Task组成一个子功能，其入口就是Manager；一个或多个Manager/Task组成一个完整的任务，其入口就是Launcher。

### 目录结构

* tasklet: tasklet源工程, 可以从target文件夹中下载已编译好的jar文件。
* hiapk  : 示例程序, 简单模拟了从m.apk.hiapk.com上搜索并下载指定应用的功能。

