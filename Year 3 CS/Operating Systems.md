# Intro
Operating system is software that manages physical hardware, on behalf of other programs. Needs to be **convenient**, **efficient** and **flexible**. Users use system and application level programs.
## Roles of OS
* Resource allocation of CPU, memory storage and network
* Concerned with fairness and efficiency. 
* Manages life cycle of machine/applications.
* Prevents users/programs accessing data/hardware they shouldn't.
* Provides consistent API for file system, sockets, graphics etc.
# Kernel
Core of the OS. Interfaces with and abstracts over hardware. Unit of work is called 'job'/'task'. Typical cycle is fetch -> decode -> execute. Interrupts are always handled by kernel.
### Device Trees
Config file that tells the OS where to find devices in the system. "Static" discovery as the location and config of devices are known in advance.
## Interrupts
Interrupts cause the CPU to pause current execution and transfer control to an **Interrupt Service Routine (ISR)**. Hardware/software can both send interrupts to the CPU. Hardware sending interrupts is an asynchronous event. Software sending interrupts is a synchronous event.
### Sources of Interrupts
#### Hardware
* I/O operation complete
* Timer expired
* Hardware failure
#### Software
* Request for system call
* Divide by zero
* Illegal instruction
* Illegal memory access
* Known exceptions
# Booting
### Power On
System is in a unknown state. Only known details are the mode of the CPU, and the value of the program counter (PC).

Difference modes are:
* Real mode (startup mode) 
* Protected mode
* Long mode
### BIOS/UEFI
Stands for Basic Input/Output System. Firmware that prepares system to load an OS. UEFI is the new BIOS.

### POST
Stands for Power On Self Test. Tests include CPU checks, memory checks, device & BIOS checks.

### ACPI
Stands for Advanced Configuration and Power Interface. One of first jobs of OS is to locate the ACPI tables. Once the tables are parsed, the OS can start initialising devices. OS finds the ACPI table by scanning through lower memory.

### Bootloader
Program that actually loads the operating system and transfers control to it. 

unified l1 cache, cache that does not split data and instructions
diff parts of physical address space do diff things

os must make sure it does not allocate pages to where it currently is

# Memory 
Main memory is used to store data and programs for immediate use. An **address space** defines a range of discrete addresses that can be accessed.

## Physical Memory
Actual real memory that stores data. Physical address space contains addresses that correspond to **physical memory**, but also to things such as BIOS ROM etc. Physical address space on x86 systems comprises of 52-bit addresses. This means there are up to $2^{52}$ addresses available. Each address is generally 1 byte. 
### Paging
The smallest unit of management is called a **page**, typical size of a page is 4 KiB (4096 bytes). Pages are a fixed size, contiguous in the address space and aligned to multiples of their size.

## Buddy System
Technique of allocating memory blocks to different processes. Basic idea is to maintain lists of free power of two sized blocks. The exponent of the power is the block's order. i.e order 0 will contain blocks of $2^0$  = 1 page. order 2 will contain blocks of $2^2$ = 4 page.

### Allocation
We then insert all blocks into list of highest order. When a request for a block comes in, we check the list of that order to see if there are any free blocks. If there are we allocate that block, if not we go to the next highest order and split the block in half and allocate it.
### Free
When we free a block of memory we insert that block into the list of order. If the block's buddy is also present in that order, we combine the 2 and move it upwards to a higher order. The buddy of a block is calculated by **starting_number XOR order**.

## Page Tables
Technique for converting logical addresses into physical addresses. Idea is to store a table containing entries that store physical address.

For a given 10 bit logical address, use first 3 bits to store index into page table, and remaining 7 bits to store offset into that given page. In this scenario, the page table could store 8 different entries and each page would be 128 bytes. 

Having an array that stores these address mappings would be super memory intensive, and also most of virtual address space is not in use as it is so big. Solution is to use multi level page tables.

### Multi-Level Page Tables
Split the page table up into multiple levels. i.e for a 2 level page table, the first page table's entry would point to the next page table. The deeper page table would then point to the actual location of physical memory. 

For a given 10 bit logical address, use first 2 bits to store index of first page table, next 2 bits to store index of second page table, and last 6 bits to store offset into the page. 

Much more efficient as in practice, only the first page table must exist, if there are no mappings in the second level page table, it does not need to be created until it is needed.

### Translation Lookaside Buffer (TLB) 

This added level of indirection means there are additional steps to obtaining a physical memory address. TLB is a cache of high speed associative registers. Keys are virtual page numbers and values are physical page numbers. When we need to find the cooresponding physica page number for a virtual page number, we first check the TLB. If we get a hit, great, otherwise we do the page walk and create a new entry in the TLB. 

## Operating System Design
OS function are partitioned into different layers. Lowest (Layer 0) is the actual hardware, while the highest is the actual user interface. A layer is an implementation of an abstract object, this means it encapsulates its data and operations. Layers only use the functions of the next lowest layer. Adding more layers of abstraction introduces more inefficiency.

### Monolithic Kernels
eg. : Linux, kernel code is huge and in one block. File system, Inter process communication (IPC), scheduler, devices and virtual memory all contained in kernel code.

### Microkernels
Removes non-essential functionality from the kernel. Results in smaller, simpler kernel.
* Contains only lowest level functions such as scheduler, VM, IPC.
* Use message passing to call services, instead of system calls. 

Pros : Easier to extend or modify OS service. Increased reliability. Simplifies porting.
Cons : More performance overheads due to communication between services. More expensive than simple system call.

### Modular Kernels
Uses an OOP approach. Dynamically loads in additional functionality via modules which the kernel can load in. Similar to microkernel in that most functionality is outside the core kernel, but can still run in kernel mode. Results in no overhead message passing.

## Devices

A device is a piece of **hardware** that performs a particular function in a computer, can be external/internal.
* Keyboard
* Mouse
* Timer
* Interrupt controller
* RAM

A **device driver** is a piece of software that can speak the language of the device, known how to program/operate/read from it etc. Usually implements some API for the rest of the OS to use.
![[Pasted image 20241012230632.png]]
Devices are usually connected to upstream controllers, which are also devices. 
![[Pasted image 20241012230821.png]]

### Bus
Method of communication that allows multiple components to speak to each other. 
Some features of different buses are :
* Discover - Seeing connected devices
* DMA
* Packet/stream based I/O

## Peripheral Component Interface (PCI)
PCI is the main hardware bus / way different devices are connected. 
![[Pasted image 20241012233304.png]]
Every device will have a configuration space that contains hardware IDs and resource requirements. Firmware then allocates memory address ranges to each device. The device will respond to any memory request by CPU to an address in those ranges. 

PCI devices can also send interrupts.
* Legacy Interrupts : physical pins that go high to signal an interrupt
* Message signalled interrupts : Writes to a particular location in memory to signal an interrupt

## Universal Serial Bus (USB)
Supports hot plugging and automatic device discovery. Unlike PCI, no raw memory access, no direct interrupts. Transfers are packet/stream based. Devices expose endpoints which packets are addressed to. Basically no memory address to write to that will speak to the USB
## C++ Tips

__attribute(packed)__ forces compiler to not align fields in structs and therefore use the minimum amount of bytes.



