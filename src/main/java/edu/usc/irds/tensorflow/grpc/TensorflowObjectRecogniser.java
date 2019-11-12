/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package edu.usc.irds.tensorflow.grpc;

import com.google.protobuf.ByteString;
import io.grpc.ManagedChannel;
import io.grpc.netty.NettyChannelBuilder;

import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;

import static edu.usc.irds.tensorflow.grpc.InceptionInference.InceptionRequest;
import static edu.usc.irds.tensorflow.grpc.InceptionInference.InceptionResponse;

/**
 * This class offers image recognition implementation.
 *
 */
public class TensorflowObjectRecogniser implements Closeable {

  //static final Logger LOG = LoggerFactory.getLogger(TensorflowObjectRecogniser.class);

  private ManagedChannel channel;
  private InceptionBlockingStub stub;

  public TensorflowObjectRecogniser(String host, int port) {
    System.out.println("Creating channel host: "+  host + " port= "  + port);
    try {
      channel = NettyChannelBuilder
          .forAddress(host, port)
          .usePlaintext(true)
          .build();
      stub = new InceptionBlockingStub(channel);
      System.out.println("Channel initialized! " + channel.toString());
      System.out.println("TODO: test channel here with a sample image");
    } catch (Exception e) {
      //LOG.error("Upps!" + e.getMessage());
      System.out.println(("Upps!" + e.getMessage()));
      throw new RuntimeException(e);
    }
  }

  public List<Map.Entry<String, Double>> recognise(InputStream stream) throws Exception {

    List<Map.Entry<String, Double>> objects = new ArrayList<>();
    ByteString jpegData = ByteString.readFrom(stream);
    InceptionRequest request = InceptionRequest.newBuilder()
        .setJpegEncoded(jpegData)
        .build();
    long st = System.currentTimeMillis();
    InceptionResponse response = stub.classify(request);
    long timeTaken = System.currentTimeMillis() - st;
    System.out.println("Time taken : " + timeTaken + " ms" );
    Iterator<String> classes = response.getClassesList().iterator();
    Iterator<Float> scores = response.getScoresList().iterator();
    while (classes.hasNext() && scores.hasNext()){
      String className = classes.next();
      Float score = scores.next();
      Map.Entry<String, Double>object = new AbstractMap.SimpleEntry<>(className, score.doubleValue());
      objects.add(object);
    }
    return objects;
  }

  @Override
  public void close() throws IOException {
    if (channel != null){
      System.out.println("Closing the channel ");
      channel.shutdownNow();
    }
  }
}
