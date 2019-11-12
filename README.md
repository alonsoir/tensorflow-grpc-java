# Tensorflow Image recognition using Grpc Client

This project includes java code example for making use of tensorflow
image recognition over GRPC.


It creates a client that tries to connect to an existing gRPC server running on some ip and port. Server code is not provided.
## Build and run this client grpc project

	To build this jar as an addon to tika, run

	```mvn clean compile assembly:single```

	Last login: Tue Nov 12 13:00:48 on ttys005
	~/g/tensorflow-grpc-java> java -jar target/tensorflow-java-1.0-jar-with-dependencies.jar localhost:9000 /Users/aironman/Pictures/example.jpg
	Creating channel host: localhost port= 9000
	WARNING: An illegal reflective access operation has occurred
	WARNING: Illegal reflective access by io.netty.util.internal.ReflectionUtil (file:/Users/aironman/gitProjects/tensorflow-grpc-java/target/tensorflow-java-1.0-jar-with-dependencies.jar) to constructor java.nio.DirectByteBuffer(long,int)
	WARNING: Please consider reporting this to the maintainers of io.netty.util.internal.ReflectionUtil
	WARNING: Use --illegal-access=warn to enable warnings of further illegal reflective access operations
	WARNING: All illegal access operations will be denied in a future release
	Channel initialized! io.grpc.internal.ManagedChannelImpl@2353b3e6
	TODO: test channel here with a sample image
	Image = /Users/aironman/Pictures/example.jpg
	Time taken : 323 ms
	[sweatshirt=10.206170082092285, cloak=6.861289024353027, sleeping bag=5.121708393096924, velvet=5.053798198699951, abaya=4.718129634857178]
	Closing the channel 

This assumes that tensorflow inception is being served at `localhost:9000` and
 also the `example.jpg` file exists.


## Setup Tensorflow serving on localhost:9000

Requires Docker up and running.

```
# pull and start the prebuilt grpc server container, forward port 9000
docker run -it -p 9000:9000 tgowda/inception_serving_tika

# Inside the container, start tensorflow service
root@8311ea4e8074:/# /serving/server.sh

# Inside the container, run tail -f to see logs:

	root@c2643e66348e:/# tail -f /serving/inception_log 
	I tensorflow_serving/sources/storage_path/file_system_storage_path_source.cc:147] File-system polling found servable version {name: default version: 157585} at path /serving/inception-export/00157585
	I tensorflow_serving/sources/storage_path/file_system_storage_path_source.cc:147] File-system polling found servable version {name: default version: 157585} at path /serving/inception-export/00157585
	...

```
If you want to use a different port

May modify the `/serving/server.sh`, which has
`/serving/bazel-bin/tensorflow_serving/example/inception_inference --port=9000 /serving/inception-export/  &> /serving/inception_log &`

TODO

Added a Dockerfile for grpc client, but i got trouble with it:

	Last login: Tue Nov 12 12:13:07 on ttys004
	aironman@MacBook-Pro-de-Alonso tensorflow-grpc-java % fish
	Welcome to fish, the friendly interactive shell
	aironman@MacBook-Pro-de-Alonso ~/g/tensorflow-grpc-java> docker images
	REPOSITORY                           TAG                 IMAGE ID            CREATED              SIZE
	aironman/tensorflow-grpc-java        1.0                 8388cf0ff520        About a minute ago   346MB
	tgowda/inception_serving_tika        latest              f2ccd1767fe2        3 years ago          5.39GB
	aironman@MacBook-Pro-de-Alonso ~/g/tensorflow-grpc-java> docker run 8388cf0ff520
	Your container args are:
	Creating channel host: 0.0.0.0 port= 9000
	Channel initialized! io.grpc.internal.ManagedChannelImpl@3d8c7aca
	TODO: test channel here with a sample image
	Image = /opt/app/ferrari.jpg
	Something went WRONG! UNAVAILABLE: Transport closed for unknown reason
	io.grpc.StatusRuntimeException: UNAVAILABLE: Transport closed for unknown reason
		at io.grpc.stub.ClientCalls.toStatusRuntimeException(ClientCalls.java:210)
		at io.grpc.stub.ClientCalls.getUnchecked(ClientCalls.java:191)
		at io.grpc.stub.ClientCalls.blockingUnaryCall(ClientCalls.java:124)
		at edu.usc.irds.tensorflow.grpc.InceptionBlockingStub.classify(InceptionBlockingStub.java:64)
		at edu.usc.irds.tensorflow.grpc.TensorflowObjectRecogniser.recognise(TensorflowObjectRecogniser.java:68)
		at edu.usc.irds.tensorflow.grpc.Main.main(Main.java:51)
	















