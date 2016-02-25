# Tasklet
------
## Introduction 

Tasklet is a simple common lib for task management, it's aimed at the execution and management of sequential tasks. Tasklet only consists four classes, these are

* Task The abstraction of task to be performed
* TaskContext An context object is used to hold shared data between tasks
* TaskGroup A specific task that holds a group of task
* TaskPerformer The entry to perform tasks

![flowchart of tasklet](tasklet_flowchart.png)

## Usage

1. add dependency to maven project
```xml
<dependency>
    <groupId>net.wisedream</groupId>
    <artifactId>tasklet</artifactId>
    <version>1.0</version>
</dependency>
```

2. a simple test
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
the sample output is
```bash
sending payload 1
sending payload 2
sending payload 3
job finished
```