# Measuring Network
Allows you to identify any potential bottlenecks in the data transmission across the network. Such as latency/bandwidth issues
- Passive measurement - Using existing traffic to measure and analyse network characteristics
- Active measurement - Sending probes to measure and analyse network characteristics

- Bandwidth - Volume of data that can pass through a link at a point in time. Similar to volume of water that can flow through a pipe.
- Throughput - Volume of data that actually passes through a link
- Goodput - Application layer throughput, volume of useful data. Excludes protocol overhead, retransmitted packets etc.

- Latency - Time difference between 2 different events (packet being sent and packet being received on the other end)
- Round-trip time (RTT) - Time difference between a packet being sent, and reply to that packet being received.
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
### Bandwidth Delay Product
$$\text{BDP}=bandwidth\times latency$$
Used to calculate the windows size of a link to keep it fully utilised at all times. Thinking of a link as a pipe, the latency can be thought of as the length of the pipe, and the bandwidth can be thought of as the diameter of the pipe.
# Control Plane
Controls how data packets are forwarded. Process of creating routing table is part of control plane. Abstraction of messages and protocols to support this control info. Typically use same physical infrastructure, especially for internet, but is logically distinct from data plane.
### Examples
* TCP - Setting up connections, providing reliability, adapting sending rate.
* Name resolution (DNS)
* Network layer management (ICMP)
* Address resolution (ARP)

- In-band control protocols use same communications channels as data plane, for example : TCP
- Out-of-band protocols use logically separate communication channels, for example : ARP
## ICMP
ICMP (Internet Control Message Protocol) is a **network-layer protocol** used for sending **error messages, diagnostics, and network status information** between devices on a network. It is primarily used by network devices like **routers and hosts** to communicate problems in IP packet delivery.

Header contains:
- Type field (8 bits) 
- Code field (8 bits)
- Checksum field (16 bits)

Type field defines high-level category of the ICMP message
- 0 - Echo reply
- 3 - Destination unreachable
- 8 - Echo Request
- 11 - Time exceeded

Code field provides additional detail within the given type.

* Ping - Used to check if host is reachable and how long packets take to travel. If host it online, will reply with **ICMP Echo Reply**
* Traceroute - Uses ICMP Time Exceeded messages when Time to Live expires. 
* Unreachable - Sends a ICMP **Destination Unreachable** message. 
### Traceroute
Source sends a series of probe packets with each one having a higher TTL value.
- First probe has a TTL = 1, which only reaches the first router
- Second probe has a TTL =2, which reaches the second router
- ... so on until packet reaches final destination

At an intermediate router where the TTL expires, node responds with an ICMP TIME-EXCEEDED type. Upon reaching destination router, node responds with an ICMP DESTINATION_UNREACHABLE packet.

![[Pasted image 20250209123454.png]]
### Spoofing Attack
A malicious device sends out many ICMP echo requests to different hosts on networks with return address set to a victim host. All the different hosts who received the request will think it came from the victim, and flood them with tons of ICMP responses, overwhelming the target. 
## Address Resolution Protocol (ARP)
Used to find a given hardware address (MAC address) for a given IP address. Needs this MAC address for sending ethernet frames, which are used to carry data at link layer. 
### ARP Tables
Stores entries mapping IP addresses to MAC addresses. 
### ARP Discover
Device performing the discovery will send out a broadcast message asking "Who has the IP address 'x'". All nodes on the network will recieve this message, and the device who has the target IP address will respond with it's MAC address. Discoverer will then add this entry to its ARP table. 
# Protocol Design
### Byte Stream 
Converting our data structure into a series of bytes, sending the bytes over the network, and the receiver then converting the received bytes back into the data structure.
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

Parity checks can only detect an error has occured, not where in the data the error occured, and therefore cannot correct the data.
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

For example, if packet 2 is lost but 3,4,5 arrive correctly. Receiver will acknolwedge packets 3,4,5, but not acknowledge packet 2. Upon receiving duplicate acks, sender will only resend packet 2. 
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

Sending a SYN-ACK packet is a form of piggy-backing, where ack for previous SYN message and own SYN packet are sent at the same time.
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
Upon losing a packet, sshthresh is set to wnd/2, cwnd = 1 (if RTO is triggered). This incidicates that RTO being triggered should result in a large decrease of transmission. cwnd = sshthresh (if triple duckplice ack)
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
Smaller packets have higher load on the network, since header to payload ratio is higher.
## IPv4 fragmentation and reassembly
![[Pasted image 20250313094618.png]]
A segment or datagram at the transport layer is broken up into many IP packets. Each packet gets its own IP header. Headers are then resassembled at destination link.

Fragmentation introduces some problems:
- how long should a receiver wait for all fragments?
- packet loss becomes problematic, error recovery is not present at IP layer, therefore, if a single fragment is lost, entire packet needs to be retransmitted

Fragmented packets that belong to the same original packet will have the same *identification*  value, and the fragment offset helps to identify how to reassemble the packet. 

### MTU and Path MTU
Since IPv6 does not support fragmentation, end hosts are responsible for sizing their IP packets appropriately. Path MTU is minimum MTU for entire end-to-end path. Path MTU discovery is the process of determining the Path MTU, works by sending a packet with 'Don't Fragment' flag set, when a router encounters a packet that is too large for its MTU, it sends an ICMP "Packet Too Big". 

## RTP/RTCP
Real-time transport protocol is a general transport encapsulation mechanism for audio and video on the internet. Specifies a framework. RTCP is simple out-of-band control plane for RTP. Senders and receivers transmit information about the progress of a flow using Sender Reports (SRs) and Receiver Reports (RRs). Built on top of UDP .
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

ESP header include SPI number so receiver can identify which SA an incoming packet belongs to and hence which algorithms and keys to apply to that packet. 
#### Transport Mode
Only provides protection for the payload, none for the IP header
![[Pasted image 20250321081511.png]]
#### Tunnel Mode
Provides protection for the payload and the header
![[Pasted image 20250321081518.png]]

Link local address is mainly used for bootstrapping device to the network, such as finding routers, neighbour discovery etc. Global unicast address is responsible for communicating with devices across different networks.
## Virtual Private Networks
- You make a request to a website
- VPN client encrypts the data and wraps it inside a new packet
- Packet is sent to VPN server over a protocol such as IPSec
- VPN server decrypts packet and forwards it to actual destination (website thinks request came from VPN server and not you)
- VPN encrypts response and sends it back to your device
#### Multi-Site VPN
Might want to have differnet physical locations being treated as a logical private network. Can use VPNs to achieve this.

- Office A Network: 192.168.1.0/24 
- Office B Network: 192.168.2.0/24 
VPN tunnel is established between Office A's VPN gateway and Office B's VPN gateway.

When Office A requests a resource from Office B:
- Office A's VPN will forward request through the tunnel to Office B's VPN gateway. Since it is encypted through the tunnel, no one over the internet can see requests for the file
- Request reaches Office B's VPN gateway decrypts it, and forwards it to appropriate device on network. 
- Sends response back using same method. 
## Radio Transmission
#### Noise
Anything that causes distortion of the signal such as
- Interference from other electromagnetic sources
- Thermal noise (movement of atoms)
- Transmission media itself
Multiple signal levels are desirable, but this is more susceptible to error.  
#### Bandwidth & Capacity
Generally capacity of a radio link is proportional to the bandwidth of the link. Where the bandwidth is the physical range of frequencies. For example, a $20\text{ MHz}$  Wi-Fi channel has a bandwidth of 20 MHz, meaning it occupies a 20 million Hertz-wide slice of the radio frequency spectrum. 
#### Hartley-Shannon 
Relationship between bandwidth of channel, B, number of signal levels, M, and capacity of channel, C, in bits per second

- S is signal power
- N is noise power
- $\frac{S}{N}$ is signal to noise ratio
$$C=Blog_2(\frac{S}{N}+1)$$
Capacity is proportional to bandwidth, but improving the signal to noise ratio gets you diminishing returns. 

+1 is important because without it, having signal = 0, would result in $log_2(0)$, which is undefined. 
### Modulation/Demodulation
Radio waves are simply just waves, modulation is the process of converting our data into the cooresponding data waves. The different properties of waves are as follows:

- Amplitude Modulation - Adjusts wave height
- Frequency modulation - Adjusts wave frequency
- Phase Modulation - Adjusts wave phase (relative timing of wave peaks)
- QAM - Adjusts both amplitude and phase

Sender converts data into cooresponding waves. The receiver is in charge of demodulation, which is the process of converting these waves back into the cooresponding data. Sender and receiver need to coordinate on a protocol so they can both understand how to communicate using these waves. 
### Multiplexing
Methods for sharing the same radio spectrum across different users, where each user should not interfere with each other

- Frequency Division Multiplexing (Signals are transmitted across different freqeuencies)
- Time Division Multiplexing (Chanel is divided into different time slots)
#### Network Structure
- Point-to-point: Devices communicating directly with each other via radio transmissions. Can be anything from phones/computers etc. (how bluetooth works)
- Passive satellite: Satellites simply reflect transmissions they receive. Does not process or amplify the signal it receives
- Active satellite: Satellites that receive, process, and retransmit signals they receive

Points that transmit to satellites are not normal phones/computers, they are high powered devices.  
### Medium Access Control (MAC)
The radio channel is a shared medium, and therefore, need some protocol to allow different users to communicate via the channel
#### Centralised MAC
Single controller (base station), that coordinates users transmitted data. Devices request permission to send data, controller can then grant time slots/channels. However, now introduce a single point of failure.
#### Distributed MAC
No central controller, each device decides when to transmit, using protocols to avoid collisions.
##### CSMA/CA
Stands for carrier sense multiple access with collision avoidance. 
- Stations listen and 'sense' if the channel is free, back-ff for a random amount of time if busy
- any station can transmit if channel is free (mutliple access)
- if collisions occur, sender backs-off, waits for a random amount of time and then tries to transmit again, with successive collisions, double wait time before retrying
### Hidden terminal problem
Happens when 2 nodes who cannot hear each other attempt to send data to some common node. Since neither node can hear each other, they will both try to transmit to the common node, resulting in the receiver needing to handle collisions.
### Exposed terminal problem
Happens when stations in neighbouring cells prevent each other from transmitting unnecessarrily
### RTS/CTS
Method of establishing connectivity between 2 nodes before sending data, to help mitigate the problems listed above. 
- Source first sends a request-to-send (RTS) message to destination
- If destination is ready to accept, sends a clear-to-send (CTS) message
- If destination is busy, replies with an RxBUSY message
### NAV
Extension to RTS/CTS, where connection establishement messages contain a network allocation vector (NAV) that specifies time for which stations should defer sensing.
- Source sends a RTS with NAV
- Destination replies with a CTS and also with a NAV
- Other stations that sense this NAV will prevent transmission for the duration of the NAV
### Power Management
Nodes can either be in active or power save mode (PSM) state
- Stations signal to AP that they are entering PSM state
- AP can then buffer packets destined for sleeping stations until they wake
- Sleeping stations will periodically pole the AP to check if they have any packets for them
## IPv6
Was created because IPv4 has a maximum of $2^{32}$ addresses, which is too small a space. IPv6 has a total address space of $2^{128}$, and is split into 8 groups of 16 bits each, represented using hex notation
$$xxxx.xxxx.xxxx.xxxx$$
Short cut notation abbreviates contiguous zero components

CIDR notation can be used in the same way as in IPv4, to indicate length of mask:
- 2001:db8::8:800:200c:417a/64 - mask for top 64 bits
### Address types
- Global Unicast Address - Globally routable, unique address
- Link-local address - Locally unique IPv6 addresses
- Multicast addresses - Used for sending/receiving multicast messages
- Loopback addresses

Difference between link-local and private IP addresses, is that link-local only exist on the current link, such as an ethernet switch in your house. Private IP addresses might span mulitiple links.

A device sending a packet to a multicast address will have the destination IP as the multicast address, and the source IP is the unicast address of the sender. 

Loopback addresses are where the device sends IP packets to itself, such as localhost : 127.0.0.1
### Neighbour Discovery
#### Router Discovery
Used to discover routers on a link. Host sends an RS (router solicitation) message to the 'all-routers' multicast address. Routers respond with a RA (router advertisment) message, outlining router information. 
#### Neighbour Solicitation
IPv6 equivalent of ARP, given an IPv6 address, might want a link-local address such as devices MAC address. Host sends an NS (neighbour solicitation) message to target's link-local multicast address. Target responds with MAC address through a NA (neighbour advertisment) message.

Reason we cannot send to nodes unicast address is because we do not know the MAC address yet. Switch/Router does not know where to physically send ethernet frame given just the IP address. 
#### Duplicate Address Detection (DAD)
Way of checking if any other devices on a link have the same address. 
- Generate the IPv6 address to use
- Send out a NS message for that particular address
- Check if any nodes respond, if they do means address already in use, else no one using that address. 
#### SLAAC
Stands for Stateless Address Auto-Configuration. Allows end systems to automatically generate their interface ID. 

- Obtain network prefix from router using RS message
- Append interface identifier (MAC address or randomly generated)
- From a valid IPv6 address
- Perform DAD on this address to ensure no one else is using this

Using MAC address is not a good idea as it introduces forms of tracking and security concerns, therefore, better idea to use randomly generate identifier. 
### NAT64
Since not all nodes support IPv6, NAT64 is a way to map IPv6 packet into IPv4 packets.
- Internal IPv6 address uses 64:ff9b::/96, the lower 32 bits contains the 32-bits used for IPv4 address
- NAT64 maps outgoing IPv6 packets into IPv4 packets, and does the reverse for incoming IPv4 packets. 

Example when the NAT64 gateway receives the packet, extracts IPv4 part from IPv6 address. If destination address is 64::ff9b::5db8:d822, extracts 5db8:d822 and sends it to this address instead. 



