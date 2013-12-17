Create a custom Logged annotation that can be added to both classes and methods.
Create a proxy factory that creates a custom proxy for a given interface being backed up by a custom implementation instance. The proxy should check whether the methods or the class are annotated with the previously defined annotation.
* in case the class or the methods being invoked are annotated, the proxy should log information about the method, object, object type and parameters of the invoked method
* in case there is no annotation on the class or on the invoked method, the proxy should just serve as a delegator
