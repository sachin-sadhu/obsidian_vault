##CheatSheet Link
https://neo4j.com/docs/cypher-cheat-sheet/current/

##Key information
() is used for nodes, [] is used for relationshsips (edges)
:Label is used to match a node/edge with the label of Label

##querying information

MATCH (a:Person {name:'Tom Hanks'}) RETURN a

matches a node with the label person and attribute name = 'Tom Hanks'

##inserting information

CREATE (a:Person {name:'Brie Larson', born:1989}) RETURN a

creates a node with label person, attributes name = 'Brie Larson' and born = 1989

CREATE (p:Person {name:'Sachin Sadhu'}) - [:ACTED_IN] -> (m:Movie {title:'Apollo 13'})

creates a relationship between Person node and Movie node

##deleting information

MATCH (a:Person {name:'Brie Larson'}) DETACH DELETE a

detach deletes all relationships currently associated with that node, by default you cannot delete a node if there are existing relatiosnships connected to that node

eg.

MATCH (p:Person {name:'Ben Miles'}) - [:ACTED_IN] -> (m:Movie) return p,m
