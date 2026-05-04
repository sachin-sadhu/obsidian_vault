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

![[Pasted image 20260502232011.png]]

![[Pasted image 20260503000001.png]]

Overview first, zoom and filter, then details on demand
![[Pasted image 20260503000543.png]]

![[Pasted image 20260503004815.png]]

![[Pasted image 20260503010602.png]]
![[Pasted image 20260504230825.png]]
![[Pasted image 20260504231306.png]]

![[Pasted image 20260505001808.png]]
### → Analyse

What the user is fundamentally trying to accomplish with the data.

#### → Consume

Using existing information (reading, not creating).

- **Discover** — finding something unknown; exploratory analysis where you don't know what you'll find
- **Present** — communicating a known finding to an audience; the insight already exists
- **Enjoy** — casual engagement with data for fun, no specific goal

#### → Produce

Creating new information or artifacts from the data.

- **Annotate** — adding tags, labels, or notes to specific data points
- **Record** — saving a state (e.g., a bookmark, screenshot, or snapshot) for later
- **Derive** — computing new data from existing data (e.g., creating a ratio column, running a calculation)

---

### → Search

How the user **locates** what they're looking for. Defined by two dimensions:

||**Target Known**|**Target Unknown**|
|---|---|---|
|**Location Known**|**Lookup** — you know what & where (just retrieve it)|**Browse** — you know where to look, but not exactly what you want|
|**Location Unknown**|**Locate** — you know what you want, but not where it is|**Explore** — you don't know what you want or where it is (open-ended discovery)|

This 2×2 is powerful because it forces designers to ask: _does my user already know what they're looking for?_

---

### → Query

The **level of specificity** of what the user wants to know.

- **Identify** — pinpointing a single specific data point ("what is the value of this dot?")
- **Compare** — examining the relationship between two or more items ("is A bigger than B? by how much?")
- **Summarize** — getting an overview of the entire dataset ("what's the overall distribution?")

---

#### Why this matters

Munzner's taxonomy gives designers a precise vocabulary to describe user goals **before** choosing a chart type. For example:

- A user who wants to **Summarize** needs an overview visualization (histogram, heatmap)
- A user who wants to **Lookup** needs a clear, searchable table
- A user who wants to **Explore** needs interactive filtering and navigation

Matching the visualization to the correct task type is central to her design methodology.