Dispatcher是什么?
    在actor，dispatcher是任务的执行。
    由于Dispatcher继承了scala.concurrent.ExecutionContext 所以可以简单吧dispatcher看作为Thread Pool.
要想明白ExecutionContext，你应该去看Future知识

参考：akka-actor性能优化之单机版
https://github.com/deanzz/akka-dispatcher-test