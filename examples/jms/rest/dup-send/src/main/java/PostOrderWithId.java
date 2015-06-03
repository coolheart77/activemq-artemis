/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements. See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License. You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
import org.jboss.resteasy.client.ClientRequest;
import org.jboss.resteasy.client.ClientResponse;
import org.jboss.resteasy.spi.Link;

public class PostOrderWithId
{
   public static void main(String[] args) throws Exception
   {
      if (args.length < 1 || args[0] == null) throw new RuntimeException("You must pass in a parameter");

      // first get the create URL for the shipping queue
      ClientRequest request = new ClientRequest("http://localhost:9095/queues/jms.queue.orders");
      ClientResponse res = request.head();
      Link create = res.getHeaderAsLink("msg-create-with-id");


      Order order = new Order();
      order.setName(args[0]);
      order.setItem("iPhone4");
      order.setAmount("$199.99");

      res = create.request().pathParameter("id", args[0]).body("application/xml", order).post();

      if (res.getStatus() != 201) throw new RuntimeException("Failed to post");

      System.out.println("Sent order " + args[0]);
   }
}
