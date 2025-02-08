# Measuring Network
Allows you to identify any potential bottlenecks in the data transmission across the network. Such as latency/bandwidth issues

## Delays
### Transmission delay
Time it takes for a sender to push all bits of a packet onto the transmission medium. $$\text{Transmission delay}=\frac{\text{Packet size (bits)}}{\text{Bandwidth (bps)}}$$ 
Example : 
To send a 1000 bit packet over a 10Mbps link, transmission delay is: $$\frac{1000 bits}{10^7}=0.1ms$$
### Propagation delay
Time it takes for a single bit to travel from sender to the receiver over transmission medium. $$\text{Propagation delay}=\frac{\text{Distance}}{\text{Speed}}$$
The overall delay over a link is the sum of both the transmission and propagation delay. $$T_d=T_x+T_p+T_q+T_u$$
* $T_d$ one-way delay on a link
* $T_x$ transmission delay
* $T_p$ propagation delay
* $T_q$ queuing delay at each hop
* $T_u$ processing delay at each hop
# Control Plane

Controls how data packets are fowarded. Process of creating routing table is part of control plane. 


