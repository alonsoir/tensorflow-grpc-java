# Tensorflow Image recognition using Grpc Client

This project includes java code example for making use of tensorflow
image recognition over GRPC.



## Build and run this project

```
mvn clean compile exec:java -Dexec.args="localhost:9000 example.jpg"
```
This assumes that tensorflow inception is being served at `localhost:9000` and
 aslo the `example.jpg` file exists.


To build this jar as an addon to tika, run

```mvn clean compile assembly:single```

and then use the jar `target/tensorflow-java-1.0-jar-with-dependencies.jar`

## Setup Tensorflow serving on localhost:9000

Requires Docker


```
# pull and start the prebuilt container, forward port 9000
docker run -it -p 9000:9000 tgowda/inception_serving_tika

# Inside the container, start tensorflow service
root@8311ea4e8074:/# /serving/server.sh

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
	
	














