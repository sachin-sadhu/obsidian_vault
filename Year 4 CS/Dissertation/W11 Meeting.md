Top layer HMM :
- Models chord progression
- Doesn't emit no emission state

Bottom layer HMM:
- Hidden state - 7 scale degrees
- Emission state - actual pitch and timing

Top layer determines what chord currently in. Bottom layer should then have a transition probability that depends on what chord we are in. 

Do this by having a transition matrix for each of the chords in the bottom layer HMM.

Steps for determining scale degree.
1. First determine what key the piece is in
2. Extract pitch class from each note (ignore octace) Map C5 -> C, E5 -> E
3. Map to scale degree. Relative to the key. Example for C Major: - C -> 1, D -> 2 etc

How to generate LHS?

Maybe simplest approach is to generate melody of RHS first. Generate LHS based on melody (will probably also want to include chord progression as well )

Or have the RHS and LHS both generate in parallel, with both of them not looking at each other when conditioning.

look at guitar sheet chord, dictionary of common chord names, grep for chords. online guitar archive, see what is online

temperature parameters, how likely to leave key/random chord. Add accidental

for combining pitch and timing. see if better to combine pitch and timing

copied state, see if can generate harmonies, programatically generate melody of the arpegio 

see HMM models in python, formalise model, draw figure