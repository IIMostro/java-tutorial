# Java8新特性
1. <a href="https://developer.ibm.com/zh/articles/j-understanding-functional-programming-1/" target="_blank">函数式编程</a>
    * 函数不依赖外部的状态，也不修改外部的状态，函数调用的结果不依赖调用时间和位置，这样写出的代码容易推理，不容易出错。
        1. 不变性: <a href="https://www.ibm.com/developerworks/cn/java/j-ft4/index.html" target="_blank">
        不变性</a>不用再多个线程中共享状态，不会造成资源的抢占，不用使用锁机制，也不会出现死锁。
        2. 引用透明: 系统提供了优化函数式编程的空间，包括<a href="https://zh.wikipedia.org/wiki/%E6%83%B0%E6%80%A7%E6%B1%82%E5%80%BC" target="_blank">惰性求值
        </a>和<a href="https://zh.wikipedia.org/wiki/%E5%B9%B6%E8%A1%8C%E8%AE%A1%E7%AE%97" target="_blank">并行计算</a>
    * 常用函数
      1. Supplier\<T> 生成函数
      2. Consumer\<T> 消费函数
      3. Predicate\<T> 断言函数
      4. Function\<T, R> 转换函数
      5. @FunctionalInterface 自定义函数

2. Stream流式编程
    * Java 8 中的 <a href="https://www.ibm.com/developerworks/cn/java/j-java-streams-3-brian-goetz/index.html" target="_blank">
    Stream</a> 是对集合（Collection）对象功能的增强，它专注于对集合对象进行各种非常便利、
    高效的<a href="https://developer.ibm.com/zh/articles/j-java-streams-2-brian-goetz/" target="_blank">
    聚合操作（aggregate operation）</a>，或者大批量数据操作 (bulk data operation)。
    <a href="https://www.ibm.com/developerworks/cn/java/j-java-streams-1-brian-goetz/index.html" target="_blank">Stream API</a> 借助于同样新出现的 Lambda 表达式，
    极大的提高编程效率和程序可读性。同时它提供串行和<a href="https://www.ibm.com/developerworks/cn/java/j-java-streams-4-brian-goetz/index.html?ca=drs-" target="_blank">
    并行</a>两种模式进行汇聚操作，并发模式能够充分利用多核处理器的优势，
    使用 <a href="http://gee.cs.oswego.edu/dl/papers/fj.pdf" target="_blank">fork/join</a> 并行方式来拆分任务和加速处理过程。通常编写并行代码很难而且容易出错,
    但使用 Stream API 无需编写一行多线程的代码，就可以很方便地写出高性能的并发程序。
    * Stream操作
      * 中间操作(Intermediate):一个流可以后面跟随零个或多个 intermediate 操作。其目的主要是打开流，做出某种程度的数据映射/过滤，然后返回一个新的流，交给下一个操作使用。这类操作都是惰性化的（lazy），就是说，仅仅调用到这类方法，并没有真正开始流的遍历
        包括：map (mapToInt, flatMap 等)、 filter、 distinct、 sorted、 peek、 limit、 skip、 parallel、 sequential、 unordered
      * 中断(Terminal):一个流只能有一个 terminal 操作，当这个操作执行后，流就被使用“光”了，无法再被操作。所以这必定是流的最后一个操作。Terminal 操作的执行，才会真正开始流的遍历，并且会生成一个结果，或者一个 side effect
        包括：forEach、 forEachOrdered、 toArray、 reduce、 collect、 min、 max、 count、 anyMatch、 allMatch、 noneMatch、 findFirst、 findAny、 iterator
      * 短路(Short-circuiting): anyMatch、 allMatch、 noneMatch、 findFirst、 findAny、 limit
    * 注意
      * 避免使用stream操作char
      * 如非必须，请勿使用parallel串行操作stream
3. Java8工具类
    1. <a href="https://docs.oracle.com/javase/8/docs/api/java/util/Optional.html" target="_blank">Optional</a> 优雅的防止空指针(NPE)
    2. <a href="https://docs.oracle.com/javase/8/docs/api/java/util/concurrent/CompletableFuture.html" target="_blank">CompletableFuture</a> Java8异步框架
