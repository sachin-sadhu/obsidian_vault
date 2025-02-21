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
# Reliable Data Transfer
Physical links are imperfect, can lead to errors and loss in packet flows. Transmissions have bit errors that occur as signals propogate.

* Bit error rate - rate at which errors occur - BER of $10^{-8}$ means on average one error every $10^8$ bits
* Signalled error rate - Measures errors that are detected
* Residual error rate - Measures errors that are not detected/corrected
## Parity Bits
Extra bit used as error control
* message : size m bits
* codeword size c bits
	* $c=m+r$ (r error control bits)

 Even/odd parity. For even parity, bit is set such there is an even number of 1's in codeword, vice versa for odd parity.
## Cyclic redundancy check (CRC)
Makes use of polynomial arithmetic, representing binary data as a polynomial eg 101111000 as
$M(x)=x^7+x^5+x^4+x^3+x^2$. Goal is to produce a polynomial C(x) that is exactly divisible by some known generator polynomial G(x). Allows the reciever to check remainder upon receiving message, if remainder is 0, should be fine, if remainder is not 0, likely an error has occured.
![[Pasted image 20250219220746.png]]
Reason this works is as follows:
Let D be the message we want to send, we pad D with several bits at the end, effectively multiplying the number by 2. Hence, $R=(D*2^r)modG$, where R is the remainder when divided by the generator polynomial. $D*2^r=QG+R$ for some Q, hence $T=QG+R+R$, as we are dealing with mod 2, 2R = 0 mod 2. Therefore, $T=QG$, and hence the transmitted message is always divisible by the generator polynomial G.

Think of bit errors that might be introduced when travelling as polynomial E(x), receiver will see C(x) + E(x). CRC will fail if E(x) is also divisible by G(x).
## Internet Checksum
![[Pasted image 20250220092721.png]]
Checksum field is the sum of all 16 bit words in header, followed by taking it's 1's complement. Does not include actual payload data, only the header fields.

Checksum is recomputed at each hop, hop will then recompute the new checksum with new TTL value and update the checksum, before sending it to the next hop on the path. 
### TCP/UDP Checksum
Both also use same algorithm as IPv4 checksum. Checksum is optional for UDP, compulsory for TCP. TCP checksum includes both the header and the data.
## Forward Error Correction
### Hamming Codes
Basically strategically placing parity bits throughout the block of bits. Each parity bit will be in charge of a certain section of the block, having multiple parity bits target sections allows you to identify which rows/columns contain the error.
## Automatic Repeat Request (ARQ)
Methods to request retransmission of corrupted/lost packets.
### Idle-RQ
Transmitter sends a PDU, sets a timeout and retransmits data if acknowledgement isn't received within timeout. If acknowledgement is what is lost, receiver might receive duplicate copies. Therefore, PDUs must contain sequence numbers to allow duplicates to be identified. Poor utilisation of link and only 1 packet is sent and must wait for acknowledgement.
### Continuous-RQ
Sender sends a number of PDUs while also waiting for acknowledgement PDU. Must know capacity of pipe (how many packets can be in transit at any point in time)
### Go-back-N
Receiver will not send acknowledgement for any out-of-sequence/errored PDUs. Transmitter will then resend all those PDUs. Easy to implement, but inefficient use of network as sender might resend packets receiver already has.
### Selective retransmission
Receiver will acknowledge every PDU it receives, while ignoring corrupt/lost PDUs. Sender will then see which packets it did not receive an acknowledgement for, and retransmit only those.
