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

# TCP 
Stands for Transmission Control Protocol. 
* Connection-oriented
* Bi-directional byte-stream
* Reliable data transfer
* Ordered data transfer
* Flow controlled (prevent sender from overwhelming receiver)
* Congestion controlled (prevent sender from overwhelming network)
![[Pasted image 20250226105106.png]]
TCP segment contains a part of a byte stream being sent. Sequence number numbers bytes, with the 'Sequence Number' field indicating first byte contained in the segment. Every segment is acknowledged, acknowledgements indicate the sequence number of next expected bytes. So ack number field would be sequence number received + length of segment + 1.
## Triple duplicate acknowledgements
go-back-N causes unnecessary transmissions. TCP uses something slightly different.
![[Pasted image 20250226110057.png]]
Sender uses triple duplicate acknowledgements as a signal to retransmit S2. Can do this before retransmission timeout (RTO) for S2 expires. This is called **fast retransmit** as it allows lost packets to be resent before their RTO expires.
## RTO estimation
RTO controls retransmission events. Initial RTO is set to 1s. Minimum RTO is also 1s. Usually a maximum RTO of 60s is also set, but not required.

RTO is a smooth mean of round trip time (RTT) and is a function of
* measured RTT value (r)
* variance of r,v
* smoothed RTT value, s
![[Pasted image 20250226111530.png]]
Under normal data transfer
* RTO timer is start when a segment is sent
* Timer is reset when acknowledgement is received
* Timer is halted when all outstanding segments have been acknowledged

Under irregular data transfer
* Send earliest unacknowledged segment
* Set RTO to double (to help with congestion control, most lost packets are due to too much traffic in the network. Doubling the RTO helps to reduce congestion in the network)
* Restart RTO timer
## Flow control
Protects receiver from being overwhelmed with too much data at once. Uses sliding window approach to do this. Receiver can vary the window size. TCP acknowledgement from receiver contains sequence number of data being acknowledged, and window size for sender to use (W). Sender can send (W-u) bytes, where u is the number of bytes in-flight (sent but now acknowledged).

Sending a segment shift left of of window to the right. Acknowledging a segment shift right of window to the right. 
## Congestion control
Congestion occurs when network contains more traffic than it can handle. Results in higher delays, packet loss, loss of service. Caused by buffer capacity in routers, unpredictable changes in traffic, route changes, time of day traffic variations.
![[Pasted image 20250226115113.png]]
Idea is to send as many packets as possible by increasing transmission window (wnd) and seeing how much the path can handle. Signals of packet loss indicate congestion, and sender will reduce wnd.
wnd = min(rwnd, cwnd), where cwnd is congestion window, and rwnd is flow control window
![[Pasted image 20250304111800.png]]
## Connection Management
TCP is a stateful protocol. Connection establishment is needed to exchange port numbers and window sizes, and to synchronise start of byte stream.
### Establishment
![[Pasted image 20250304111923.png]]
* Client sends server a TCP segment with SYN flag set, along with a initial sequence number (ISN) which is random.
* Server responds to client with a segment with SYN and ACK flags set. Also sends its out ISN along with ACK that is clients ISN + 1
* Client sends back a final ACK equal to servers ISN + 1.
* Data transfer begins
### Termination
There are 2 types of terminations. First type is graceful termination, where each side sends FIN packets and waits for all data to be transferred, before ending the connection. The second type is a abortive termination, where one side ends the connection using a RST packet, indicating that the other side should not send any more data or wait for any more acknowledgements.
#### Graceful
1. Either client or server sends a FIN packet. This indicates side has no more data to send
2. Other side acknowledges this FIN packet by sending a ACK packet. However, this side can still continue to send data
3. Once second side is also finished sending data, it sends its own FIN packet.
4. First side acknowledges this FIN with an ACK, and the connection is now closed

Once connection is closed, client enters the time-wait period (2MSL), where MSL is the maximum segment lifetime (how long a TCP packet survives in the network). This is to these old packets do not interfere with any new TCP connections.
#### Abortive
A reset packet (RST flag set) can be sent to terminate a connection at any time. Once the receiver gets this RST packet, all unsent/unacknowledged data is discarded. Must not continue to send any more data.
## Security/Privacy
TCP has no inherent security/privacy features. Entire header is visible to everyone. 
Some attacks include forging 
* RST packets to terminate connections. 
* Flooding hosts with SYN packets. 
* Injecting data by forging packets
# Real-time interactive communcication
## Delay tolerances
Real time applications for internet need to be adaptive: path characteristics can change
### Delay elastic applications
![[Pasted image 20250313091340.png]]
Delay elastic applications are applications that can handle variances in delay and higher delays. Obviously they prefer low delays, but having higher delays is not world ending. These applications usually prefer guarantee of reliability.
### Delay inelastic applications
![[Pasted image 20250313091502.png]]
Shows how different applications may function. Some cannot handle higher delays, but can handle higher loss rates, and can adapt to changes in delays/rates across the network
#### Delay Budget
Total amount of delay that an application can handle
## Network links and paths
More demand for higher capacity networks. Optical links are becoming more common, as these links are more reliable. However, they have a higher propagation delays
$$T_{p\_copper}=2.9\times10^8m/s$$
$$T_{p\_optical}=2.0\times10^8m/s$$

Radio links are also becoming popular, but they have higher bit error rates than wired links.

Applications can control delay by controlling packet sizes. 
Smaller packets:
	- can be 'filled' more quickly (audio packet only need 20ms of audio rather than 40ms)
	- are transferred more quickly
	- have lower end-to-end delay
	- have a smaller impact on application if lost (losing 20ms of audio is better than losing 40ms)
Smaller packets have higher load on the network, since header to payload ration is higher.
## IPv4 fragmentation and reassembly
![[Pasted image 20250313094618.png]]
A segment or datagram at the transport layer is broken up into many IP packets. Each packet gets its own IP header. Headers are then resassembled at destination link.

Fragmentation introduces some problems:
- how long should a receiver wait for all fragments?
- packet loss becomes problematic, error recovery is not present at IP layer, therefore, if a single fragment is lost, entire packet needs to be retransmitted

## RTP/RTCP
Real-time transport protocol is a general transport encapsulation mechanism for audio and video on the internet. Specifies a framework. RTCP is simple out-of-band control plane for RTP. Senders and receivers transmit information about the progress of a flow using Sender Reports (SRs) and Receiver Reports (RRs)
![[Pasted image 20250317150235.png]]
![[Pasted image 20250317150250.png]]
### Controlling Data Rate
Application can control its data rate with application-specific changes to media stream. By changing encoding, using compressions schemes. Can also change sampling rate, audio bandwith, pricture sizes, FPS, colour depth
### Controlling Error/Loss
* Forward error correction can be used
* Each packet sends current frame, and also previous frame in a lower quality. If a frame arrives corrupted, use the lower quality version. 
![[Pasted image 20250314072658.png]]
## Security
### Tunnelling
Allows for a layer to provide an interface for itself. Example IP packets can be 'tunnelled' inside other IP packets, where the inner IP packet contains real data, and the outer IP packet provides a mechanism for transporting the real data.

One flow of layer n is inside another layer n flow
![[Pasted image 20250321080531.png]]
Tunnels also need end-points, which allow the tunnel to exist, can be thought of as points of control. Needs to be a mechanism to understand how to interpret data travelling through the tunnel, such as:
- how to deal with naming, addressing and routing
- how to 'wrap' and 'unwrap' the inner packet
- how to deal with errors

Tunnels an provide a protected end to end channel, where the end might be an individual host or another network.
### IPSec
Stands for IP Security. Provides 2 main services: packet protection and key/policy management at the IP layer. Authentication is provided based on keys that have been provided out of band, and IPSec uses a key-hashed mechanism for authentication. Privacy is provided using secret key encryption.
![[Pasted image 20250321080934.png]]
There are many different security choices 2 hosts might like to use, Security Association (SA) is a unidirectional policy that describes how security communication will take place.

Policy is held at each endpoint, and each packet contains a Security Parameter Index (SPI) which tells the other end what SA to use for the packet. 2 main modes of operation for IPSec are:
- Encapsulating Security Payload (ESP), which protects whole of IP packet,  also provides encryption
- Authentication Header (AH), which only provides authentication for the IP packet, ESP in tunnel mode already provides everything AH can, therefore, it is rarely used in practice.
### AH Services
- AH mode provides authentication for header and payload, no privacy protection
- AH can be used in conjunction with IPSec ESP
###  ESP Services
- ESP provides data privacy using secret key encryption
- Can be used in transport or tunnel mode
#### Transport Mode
Only provides protection for the payload, none for the IP header
![[Pasted image 20250321081511.png]]
#### Tunnel Mode
Provides protection for the payload and the header
![[Pasted image 20250321081518.png]]

Link local address is mainly used for bootstrapping device to the network, such as finding routers, neighbour discovery etc. Global unicast address is responsible for communicating with devices across different networks.