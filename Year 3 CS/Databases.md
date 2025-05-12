## ER Diagram (Silberschatz notation)
* Rectangle represents entity sets
* Diamonds represent relationship sets (foreign keys are not used in the ER model)
* Directed line represents "one", undirected represents "many"
* Double line represents total participation in a relationship
* Single line represents partial participation in a relationship
# Relational Query Languages
Language in which user requests information from database. Relations refer to specific table within a database. 
## Select
$\sigma$ is used as select symbol. Returns all records in a table that satisfy some predicate. $$\sigma_{A=B\land D\gt5}(r)$$ This will select all records from table 'r' that satisfy the predicate 'A=B' and 'D > 5'.
## Project
$\pi$ is used a as project symbol. Returns all specified columns from a table. $$\pi_{A,C}(r)$$
This will select columns A and C from table r. 
## Union
![[Pasted image 20250209141706.png]]
Both r and s must have same arity (same number of attributes). Each attribute domain must be compatible (same/similar types)
## Cartesian Product
Combines 2 tables together. Each row in r is joined with each row in s $$r\times s$$![[Pasted image 20250209143145.png]]
## Natural Join
$\bowtie$ is used as natural join symbol. Combines 2 tables by matching values of common attributes
![[Pasted image 20250209143809.png]]
# Functional Dependancies
Let A and B be sets of attributes of a relational schema R
B is functionally dependant on A ($A\to B$) if each possible value of A in R is associated with exactly one value of B in R. 
Basically means that if we know what A is, we can then tell what B is.
![[Pasted image 20250216140512.png]]
Here ID$\to$ dept_name. As the one ID is always associated with one department. However the converse is not true as 1 dept_name could have mutliple ID's.
## Full/Partial Functional Dependancies
B is partially functionally dependant on A, if B is functinally dependant on a strict subset of A. Basically, if you remove an attribute from A, the dependancy relationship still exists. 

Full dependancy is the opposite, remove any attribute from A results in the relationship breaking.
## Transitive Dependencies
If $A\to B$ and $B\to C$, then we have $A\to C$ 
## Closure
Closure of a set of attributes X, is denoted as $X^+$. This is the set of all attributes that can be functionally determined by X. Closure allows us to determine which attributes are possible candidate keys.
![[Pasted image 20250217103853.png]]
### Armstrong's Axioms
Helps us to infer all possible functional dependancies.
* Reflexivity - If B is a subset of A, then $A\to B$ always holds (An attribute always functionally determines itself)
* Augmentation - If $A\to B$ holds, then $A\gamma \to B\gamma$ also holds (Adding the same attributes to both sides does not affect validity)
* Transitivity - If $A\to B$ and $B\to C$, then $A\to C$ must hold
# Normalisation
Procedure of 'sanitizing'  relations to ensure there is no redundancy in our database.
## First Normal Form
* Atomicity: A cell must never contain more than 1 value eg. Column: {skill_id, skill_name}
* Row uniqueness: Each row in table must be unique, can be achieved by having a primary key
* Each column name must be unique
## Second Normal Form
* Already be in first normal form
* All non-key attributes must be fully functionally dependant on entire primary key
![[Pasted image 20250220205320.png]]
## Third Normal Forma
* Primary key must define all non-key columns
* Non-key columns must not depend on any other keys but the primary key
![[Pasted image 20250220210133.png]]
Here, emp No is the primary key, but dept name does not depend on it, it depends on Dept No
- Get rid of all non-prime attribute transitive dependancies
- Transitive dependances are only allowed if the attribute on the right-hand side is part of the candidate key
## Boyce-Codd Normal Form
Must be in 3N F and for every functional depednacy $\alpha\rightarrow\beta$, if $\alpha$ uniquely determines the value of any other attributes, $\alpha$ must be a superkey for that relation. 
## Transactions
### ACID Properties
- Atomicity - Each transaction is treated as a single indivisible unit. It either completes fully or not at all
- Consistency - A transaction must bring a database from one valid state to another, maintaining all predefined rules.
- Isolation - Even with concurrent execution of transactions, it appears to each each transaction that no other transaction was active at the same time (appears to execute serially)
- Durability - Once a transaction has been logically completed, the changes made to a database will persist even after failures
### Transaction States
- Active - Initial State, transaction stays in this state while executing
- Partially committed - After the final statement in application logic has been executed
- Failed - Normal execution can no longer proceed
- Aborted - Transaction has been rolled back and database restored to prior state. Can either attempt to restart the transaction or kill it completely
- Commited - Successfull completion (updated rows might not have been written to disk yet)

Commited refers to successful logical completetion of a transaction, however, changes might not have been written to disk. 
### Schedules
For a schedule that specifies 2 different transactions, we only need to consider read and write instructions. If we have 2 consecutive transactions that each belong to a different transaction
- if they refer to different data items, swapping them has no effect
- if they refer to the same data item, swapping them might have an effect

Operations on the same data item can be swapped only if they are both read operations

Two schedules are conflict equivalent if:
- one can be transformed into the other by a series of swaps of non-conflicting adjacent instructions

A schedule is conflict serialisable if it is conflict equivalent to some serial schedule
### Lock-based protocols
Mechanism to control concurrent access to a data item. 2 types of locks
- Shared. Transaction holding this lock can read but not write
- Exclusive. Transaction holding this lock can both read and write

Multiple shared locks can be handed out to different transactions at the same time, since they are only reading data. If a shared lock is held by a transaction and a differen transaction requests an exclusive lock, this later transaction will be made to wait.

Similarly, if an exclusive lock is held by a transaction, any transaction requesting both an exclusive and shared lock will be made to wait. 
#### Growing/Shrinking Phase
Protocol used to help ensure serialisability. Transactions are split into 2 main sections:
- Growing phase. Transaction acquires all the locks it requires for the transaction
- Shrinking phase. Transaction releases the locks it acquired. Once a transaction starts releasing locks, it cannot start acquiring new ones. 
### Deadlock
Deadlocks can be determined by finding cycles in a wait-for graph. For example, if Transaction 1 is waiting on an item acquired by Transaction 2, an edge will be drawn from 1 to 2. A deadlock will occur if a cycle exists in this wait-for graph. 
#### Deadlock-Prevention 
##### wait-die scheme
- older transactions are allowed to wait for locks
- younger transactions are aborted (die) and restart later with the original timestamp
For example, let:
- T1 have a timestamp of 5 (younger)
- T2 have a timestamp of 10 (older)
If T1 requests a resource held by T2, since T1 is older, it waits.
If T2 requests a resource held by T1, since it is younger, it dies and restarts later.
##### wound-wait scheme
- older transactions requesting locks held by younger transactions for the rollback of the younger transaction
- younger transactions can wait for old ones
For example, let:
- T1 have a timestamp of 5 (younger)
- T2 have a timestamp of 10 (older)
If T1 requests a resource held by T2, since T1 is older, T2 is rolled back and T1 gains the lock.
If T2 requests a resource held by T1, since it is younger, waits for T1 to release the lock.
##### timeout-based schemes
- transaction can wait on a lock only for a specified amount of time
- if transaction times out, it is rolled back. 
Prevents deadlocks by releasing locks. 
### Recovery
When a failure occurs, need to restore database to a consistent state. No updates made by transactions that haven't been committed (ensure atomicity), all updates made by transactions that have been committed (ensure durability)
### Deferred Database Modification
Records all modifications to a log, but defers all writes until committing. Upon finishing all updates, a commit message is written to the log, and then the updates are actually written to database. 

During recovery, transaction needs to be redone if and only if start and commit are present in log. redo(T_i) sets the value of all data items updated by transaction to new values. 
### Immediate Database Modification
Allows for updates as soon as writes are issues, undoing might be needed, therefore log must store both old and new value. 

During recovery, updates made by an uncommitted transaction must be undone, therefore requires both an undo and redo operation.
### Query Processing
Steps:
- Parser and translator - Verify syntax and relations. Convert into relational algebra form
- Optimiser - Convert to a more efficient form. Must be equivalent to original query
- Create query evaluation/execution plan
- Evaluation - Execute query and return answer
#### Equivalent Expressions
- Perform selections as early as possible (get rid of data we do not need)
- Perform projections of columns as early as possible (get rid of columns we do not need)
- Re-order joins to produce smallest intermediate table at each stage
##### Selections
Apply selections to individual tables before the join operation. Smaller table results in a les expensive join operation. 
##### Conjunctive Selection
Changes selections of the form $\phi_{P_1\land P_2}A$ to selections of the form $\phi_{P_1}(\phi_{P_2}(A))$
Place the more expensive predicate on the outside. 
##### Projection
Perform projections as early as possible to reduce the size of each tuple of a relation. Be careful not to lose attributes we might need later in joins

In MariaDB, when filtering before the join, use an inner query eg:
```
SELECT * FROM building NATURAL JOIN (SELECT * FROM booking WHERE guest_id = 1) AS b;
```
#### Predicting selection result size
Estimate size of selection $\phi_{A=a}(r)$? with $$\frac{n_r}{V(A,r)}$$
where $n_r$ is the number of tuples in relation r and V(A,r) is the number of distinct values appearining for attribute A in relation r. Example if there are 10 rows in r and 2 distinct values for a, we can estimate 5 entries each.

However, this rule relies on the fact that there is a uniform distribution of values, which in many cases, isn't entirely true. 
## Indices
For primary index, table must be sorted to whatever the search key is. Not the same for secondary index.
### Primary/Clustering Indices
Index whose search key also defines the sequential order of the file
### Secondary Indices
Indices whose search key specifies an order different from the sequential order of the file.

For example in past paper, first level contains sorted order of names, each ones points to a bucket of pointers, where each pointer in the bucket points to the record.
#### Dense index
Index entry appears for every search-key value in the file
#### Sparse Index
Index entry appears for only some of the search-key values. Can only be used if the relation is stored in sorted order of the search key. 
### Insertion
Must first lookup using the search-key value that appears in the record to be inserted

- Dense indices
	- If search key value is not represent in index table, system inserts appropriate index entry
	- Otherwise
		- If index entry stores pointers to all records with the same search-key value, system adds an additional pointer
		- Otherwise, the index entry only stores a pointer to the first entry with that search key value
- Sparse indices
	- If the system has to create a new block, it creates an entry pointing to that new block. Else if the new value has the least search-key value in the block, the pointer now points to the new value. Else no actions to the index need to be changed. 
### Deletion
* Dense indices
	* If deleted value was the only record with its search-key value, then index entry is deleted
	* Else if index entry stores pointers to all records with search-key value, particular pointer is deleted. Else if index entry stores pointer to first record with search key value, set to point to next one
* Sparse indices
	* If deleted entry is what the pointer points to, pointer points to next  cooresponding entry, if no next cooresponding entry, entry in index can be deleted. 
## $B^+$ Tree Index Files
- Balanced tree (every path from root to a leaf must be the same length)
- Every nonleaf node has between [n/2] and n children, except for root node.

Pointers to the right of keys leads to subtrees containing values greater than or equal to keys on the left. 
### Insertion
Since each node has a max number of keys, in the case that a node is full, we need to split that node in 2, and push the middle value into the parent node. Splitting is recursive, if parent node is full as well, do the same thing. 
### Deletion
When deleting an entry that causes the minimum entry count for a node to be violated, try to merge from a sibling node. For example, take the smallest value from the right sibling, place it as the new parent, and place the replaced parent node in the deleted key's node. 
## LSM Trees
Stands for Log-structured merge tree. Optimised for alot of writes when compared to B-tree. Comprises multiple B-tree $L_0,...,L_n$ , with an increasing size threshold
- $L_0$ should fit in memory
- Insert records into in-memory tree
- When $L_0$ is full, move records down to $L_1$ 

When a record is first inserted, it is inserted into $L_0$ B-tree, until it runs out of memory. At this point, the B-tree in $L_0$ needs to be merged with the B-tree in $L_1$ (which resides on disk). Leaf level of $L_0$ is scanned in increasing key order, and entries are merged with leaf level entries of $L_1$At this point, old $L_0\text{ and }L_1$ trees can be deleted. 

Benefits:
- Reduced number of I/O operations per second
- Full leaves, avoids space wastage
Disadvantages:
- Queries have to search multiple trees
- Entire content of each level copied multiple times
### Buffer Trees
Each internal node of $B^+$ tree has a buffer to store inserts. When an insertion is added to a node, index record is stored at the root buffer. If the index buffer is full, then appropriate inserts in the buffer are pushed 1 level down to appropriate child buffer. If child is a leaf node, then appropriate insertions are carried out. 

Benefits:
- Less overhead on queries
- Can be used with any tree index structure
Disadvantages:
- More random I/O than LSM trees
## Hash Indices
Another method to obtain fast lookup times for entries. Several methods for overflowing such as chaining, extensible hashing etc. Not suitable for finding a range of entries. 
### Extensible Hashing
Main hash-table is stored in memory, individual bins stored in disk. Main table stores global depth, outlining the number of LSB. Individual bins stored local depth, which helps to outline how many pointers point to this bucket.  

If bucket is full and local depth = global depth, need to double number of buckets, allocate new page and redistribute overflowed bucket.  If local depth < global depth, no need to double number of buckets, just allocate space fro new page
## Storage
### Fixed-Length Records
- Records size is fixed
- Each file has records of one type only
- Different files are used for different relations
- Each record is contained entirely in a single block
- No single record is larger than a block

Attributes are stored in order. 
- Record i stored from byte $n\times i$ , where n is size of each record
- Need max allocation per field. "Wu" takes same space as "Srinivasan"
- Block size unlikely to be multiple of record size (likely storage wasted)
#### Deletion
Deletion of record $i$
- Compact records (shift remaining records left) into new empty spot
- Move last record into the gap
- Fill gap with next inserted entry . Header contains pointer to first empty record, empty records each point to next empty record, forming a linked list. On insertion use the pointer from the header, then update header with next free line. If no free space, enter record at end of file
### Variable-Length Records
Allocating appropriate number of bytes for each attribute, records might take up different number of bytes. 

Usually split into 3 parts. Information about the variable length attributes, stored with 2 values
- offset (where in the record the attribute information begins)
- length (number of bytes for the attribute)

Followed by fixed-length attributes, then followed by the actual data of the variable length records. 

Might also contain a bitmap, which indicates which attriutes of the values should be treated as "NULL", and therefore ignord. For example, if a record contains 4 attributes, bitmap might be set to '0000' where each bit represents whether an attribute is NULL or not.

#### Slotted Page
Data structure used to organise records within a block of memory. Header contains the following: 
- Number of record entries
- Pointer to end of free space in the block
- Array whose entries contain location and size of each record
Records are stored contigouosly, therefore when a record gets deleted, header is updated, and all records before the deleted entry are shifted down. 

### Storage Access
Buffer is the portion of main memory available to store copies of disk blocks. Seek to minimise block transfers between disk and memory, by keeping relevant blocks in main memory. 

Program cllas buffer manager when they need block from disk. 
- If block is already in buffer, manager returns address of block in main memory
- Otherwise
	- Managers allocates space in buffer for the block by throwing out some block using some replacement strategy
	- Reads requested block into newly created space. 


	