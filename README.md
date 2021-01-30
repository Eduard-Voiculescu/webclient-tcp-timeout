# webclient-tcp-timeout
Test webclient timeout with long requests. Seems the timeout is 15 minutes as there are 15 retries before getting ACK or SYN between 2 packets in tcp protocol.

Difference between 5.2.5 and 5.3 is the use of exchange method https://github.com/spring-projects/spring-framework/issues/25751

Another interesting issue https://github.com/reactor/reactor-netty/issues/839
