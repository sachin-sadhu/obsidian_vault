## Day 1 - 2

Fully learn more about how HMMs work

## Day 3 - 5

Explore more datasets, figure out how to represent datasets in such a way that i can perform analysis on 

## Day 6 - 7

Convert dataset into HHMM training format. 

- How to encode notes (pitch, duration, onset time etc)
- How to represent chords
- How to mark section boundaries

## Day 8 - 14

Build production level first. I.e level of HHMM that actually emits a note. 

Learn probability distributions
- P(pitch | chord)
- P(duration | chord/context)
- P (rest or a note)

Then generate chord sequences per section type (verse vs chorus vs bridge)

First need to seperate songs into sections. Then gather statistics for the 

Then should model section level transitions (verse -> chorus etc)

## Day 15 - 17

Connect the 3 different layers 

When in 'verse state' -> activate verse chord progression
When chord is in 'c major' -> activate note generation for C major
