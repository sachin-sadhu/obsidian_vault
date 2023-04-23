##Inserting information

db.{table}.insertOne({id:1,name:"Katy Perry"})
db.{table}.insertMany([{id:1,name:"Katy Perry"},{id:2,name:"Justin Bieber"}])

##Querying information

db.{table}.find({id:1})

db.{table}.find({}) *returns all rows*

##Updating information

db.{table}.updateOne({query},{update})

eg.  db.authors.updateOne({'name':'Taylor Swift'}, {$set: {'highestChart':1}})

##Deleting information

db.{table}.drop delete entire table
db.{table}.deleteOne({name:"Katy Perry"})