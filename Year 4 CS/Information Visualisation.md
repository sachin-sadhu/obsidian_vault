![[Screenshot 2026-02-08 at 4.29.24 PM.png]]

#### Terminology

- Data Attributes: Refers to columns/fields on on a data set. e.g. ID, name, age etc![[Screenshot 2026-02-08 at 4.33.00 PM.png]]
- Items/Data points: Rows in a dataset

### Attribute Types

##### Categorical Attributes
- Discrete values in a single category
- No intrinsic ordering
- Example: Movie genres, city names, pets
##### Ordinal Attributes
- Also discrete values in a single category
- Have an implicit well-defined ordering
- Ranking is possible but other mathematical operations don't make sense
- Examples: Movie rankings
##### Quantitative Attributes
- Measurement of magnitude
- Math operations make sense
- Examples: Height, Temperature
#### Types of ordering
- Sequential 
	- Range from minimum to maximum
- Diverging
	- Two sequences starting in opposite directions and meeting at one point. Such as temperature
- Cyclic
	- Values wrap back to starting point. Such as time
### Marks

##### Point
- Specific Position (2D or 3D)
- But can have a size and a shape
##### Line
- One dimension
- Variable length/size
- Can have a width
##### Area
- 2 dimensions (2D)
### Visual Variables

Position, size, shape, colour, orientation, 

### Reading for Week 4


### Why for analysing tasks abstractly

Allows you to spot similarities behind different descriptions. Use the vocab of the framework to understand why people are using vis.  Verbs for actions, nouns for targets

Breath of choices can sometimes be a negative, if users don't have a deep understanding of many vis design issues. 

### Analyse

Either consume existing info or produce new info

For consumption
- present something user already understands
- user discover something new or analyse data not yet fully understood
- user to enjoy a vis to induldge their causal interests in a topic

For production
- annotate: adding graphical or textual annotations to preexisting vis elements. eg. adding a text label to a cluster of points
- recrod: basically, taking snapshots of different vis toosl
- derive: producing new data elemetns based on existing data elements. could be transforming the type, such as from floating point to some binary classification

Search:
- lookup: identity and location of targets are both known 
- locate: target known, location unknown
- browse: location known, target unknown
- explore: neither target nor location are known 

Query: ONce we have have a target or set of targets, might watn to do further analaysi on these results
- identify: works on a single target. identify returns the characteristics. Example, US election map with colour can IDENTIFY the winning party
- compare: works on multiple targets
- summarise: works on all possible targets. can be thought of as generating an overview

### Targets

Things users are trying to find or understand in a vis

- Trends: High-level patterns in the data. eg increases, decreases
- Outliers: Points that don't fit the overall pattern
- Features: Other task-specific structures of interest

Specific data attributes
- Single values
- Extremes (min/max)
- Distribution (how values are spread)

Multi-attribute targets
- Dependency: How one attributes value's might affect another (causal relationship)
- Correlation: Values that tend to move together, not necessarily causually
- Simiarily: Quantiative measure of how alike 2 attributes are overall

## Arrange

Keys and values. 
- keys are indenpedant attribute used as unique index to look up items in table. Can be categorical or ordinal
- values are depedant attributes, values of the cell in a table. Can be any of the three types

core design decision for visually encoding table related to ho many keys and values do the attributes of the table have. Scatterplot shows 2 values (plotting 2 value attributes against each other), bar charts use 1 key and 1 value (eg, country and popluation)

Keys primarily serve to define regions of space, where each item lives in the vis tool. 