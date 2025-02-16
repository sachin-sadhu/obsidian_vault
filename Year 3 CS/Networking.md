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

Controls how data packets are forwarded. Process of creating routing table is part of control plane. Abstraction of messages and protocols to support this control info. Typically use same physical infrastructure, especially for internet, but is logically distinct from data plane.
### Examples
* TCP - Setting up connections, providing reliability, adapting sending rate.
* Name resolution (DNS)
* Network layer management (ICMP)
* Address resolution (ARP)

In-band control protocols use same communications channels as data plane, for example : TCP

Out-of-band protocols use logically separate communication channels, for example : ARP

## ICMP
ICMP (Internet Control Message Protocol) is a **network-layer protocol** used for sending **error messages, diagnostics, and network status information** between devices on a network. It is primarily used by network devices like **routers and hosts** to communicate problems in IP packet delivery.

* Ping - Used to check if host is reachable and how long packets take to travel. If host it online, will reply with **ICMP Echo Reply**
* Traceroute - Uses ICMP Time Exceeded messages when Time to Live expires. 
* Unreachable - Sends a ICMP **Destination Unreachable** message. 
![[Pasted image 20250209123454.png]]
### Spoofing Attack
A malicious device sends out many ICMP echo requests to different hosts on networks with return address set to a victim host. All the different hosts who received the request will think it came from the victim, and flood them with tons of ICMP responses, overwhelming the target. 

## Address Resolution Protocol (ARP)
Used to find a given hardware address (MAC address) for a given IP address. Needs this MAC address for sending ethernet frames, which are used to carry data at link layer. 
### ARP Tables
Stores entries mapping IP addresses to MAC addresses. 
### ARP Discover
Device performing the discovery will send out a broadcast message asking "Who has the IP address 'x'". All nodes on the network will recieve this message, and the device who has the target IP address will respond with it's MAC address. Discoverer will then add this entry to its ARP table. 

