 Hidden Layer: State layer not directly observable by the observer. Each state will have a matrix of transition probabilities, along with an emission probabiliti matrix which are the probabilities of emitting something observable.  

## Calculating probabilities

Just need to oberse the data, for example if the 2 hidden states are sunny/raining, in order to figure out the probabiliti of the next day being sunny given today was raining, we look at all the pairs of raining -> sunny sequences and normalise the probabilit.

If the emission states are happy/sad, to calculate the probability of P(Happy | Sunny), we just calculate the nromalised probability of us being happy when its a sunny day 

## Viterbi Algorithm

Given a sequence of observations, helps us determine the most likely sequence of hidden states that produced those observations. 

Does this usign estimation maximum. For example if the hidden states are happy/sad, and emission states are colours of hats we wear red/blue. 

Lets say on day 1 we observe a red hat, and day 2 we observe a green hat. 

Then since we have initaly probabilities, we can calculate the probabilities of us observing a red hat given that it was happy/sad, and we choose the maximum one and store this probability. 

Then, to reach day 2, we could either have come from a happy/sad day, and we want to figure out whether day 2 is more likley happy/sad. So we calculate the first path assuming day 1 was happy, then calculate probabilit of transitioning to a  sad/happy day on day 2 and the emission probability given day 2 is happy/sad. Do the second for the second path assuming day 1 was sad. 

So whenever we want to calcualte whether the current day is more lieklyl for a hidden state, we just look at the previoulsy stored probabilities. 

## Forward Backward algorithm

It tells you **how likely the HMM was in each hidden state at each time step**, given the entire observation sequence.

If we have 3 hidden states, will give us a probability distribution function outlining the probabailiy of us being in hidden state 1, 2, 3 for all time steps.

Like whats the probability we were sad on day 2 given we observed red,green,blue hats from day 1 -3 

- Assumes that emission probabilities of observed states given a hidden state is known
- Assume that transition probabilities between hidden states is known
- Assume initial distribution of hidden state is known

### Forward Algorithm
![[Screenshot 2026-01-13 at 5.15.38 PM.png]]
Compute $P(z_k, x_{1:k})$ for all k = 1..n.

Basically calculate $P(Z_2,x_{1:2})$ etc, probability of seeing observations up to time $t$ and being in state $i$ at time t

Having seen all prior observations up to time $k$, what is the probability of being in state $k$ at time t.

#### Alpha function

$$\alpha_t(i)=P(O_1,O_2,...,O_t,q_t=S_i|\lambda)$$
Basically, alpha is the probability of seeing all the observations up to time t, and ending up in state $S_i$ at time t given the inital parameters of the model

Base case: $\alpha_1(i)=\pi_ib_i(O_1)$, basically calcualting $P(O_1,q_1=S_i|\lambda)$, so we calcualte the probabily of starting off in state $S_i$,, and the probability of emitting $O_1$ given we are in state $S_i$

Now the inductive step is $$\alpha_{t+1}(j)=(\sum_{i=1}^{N}\alpha_t(i)a_{ij})\times b_j(O_{t+1})$$
Basically saying sum over all the way we can see all the observations up to t-1 and end up in state i at time t - 1, then calculate the probability of transitioning from state i to state j, and then calculate the probability of emitting the observation at time t+1 given we are in state j 

Final step is to sum over all i, $$P(O|\lambda)=\sum_{i=1}^{N}\alpha_T(i)$$
basically, could have made all the observations and ended up in state 1, or made all the observations and ended up in state 2, so we need to sum over all of them.

### Backward Algorithm
Compute $$P(x_{k+1:n}|z_k)$$
Calculates the probability of seeing future observations given you are in state $i$  at time $t$

$$\beta_t(i)=P(O_{t+1},O_{t+2})$$

## EM Algorithm

Image trying to get height distrbution for men and women. Can sample heights of $n$  people, but let's say we don't know whether each sample is a man or woman. Then, cannot find height distribution. 

What we can do is randomly assign half of the samples to men and half to women, then we go through each group and see which of the 2 distributions more closely matches, and switch that sample to the other group.

Continue doing this until no more updates are needed.

## Baum - Welch algorithm

Basically, an algorihtm for getting values for our initial state probability, transmission probabilities, and emission probabilities 

Start with a random guess for all probabilities. Use those guess to try and estimate what the hidden states were that produced those emissions. Use probable hidden states to update parameters. Repeat until convergence

## My Model

Top layer of hierarchy, tells us which chord progressions. Eg if C major currently, probability of transitioning to a nother chord 

Second layer would have a HMM where the hidden layer is the melodic state, emission state is the actual note emitted. 

So hidden is layer is melodic state and this would probably be independent of  what chord we are in. However, given that we are in a ascending and in C major, we would probably want to output certain notes. 

Chord tone is a note that appears in chord. Eg in C major, chord tones would be C, E, G. form skeleton of the melody, usually empahsies on stronger beats and longer durations. Non-chord tones are notes that don't appear in the chord, usually briefer, appearing for shorter durations 

Dont ask, what notes appear when ascending in C major or any specific chord. Instead, ask what intervals occur when ascending towards a chord tone (regardless of which chord). Here internals refers to pitch distance between notes, usually measured in semitones 

![[Screenshot 2026-01-03 at 10.01.42 PM.png]]

So we would get probability distribution of how many intervals to step up/down when we are ascending to a chord tone. 

Basically, when we are ascending and landing on a chord tone, usually fine to do big jumps and it sounds good. However, if we are ascending and we land on a non chord tone, usually want to do smaller jumps. 

### Melodic States

- ascending_step - interval of 1 to 2 steps
- descending_step - interval or -1 to -2 steps
- leap_up - interval of {3,4,5,7,12} steps
- leap_down - interval of <= {-3,-4,-5,-7,-12} steps
- repeat - interval of 0 steps
- reset - interval of 0 steps

If when training i get an interval outside of this range, map it to the nearest step

### Training
## Determining melodic state

We know last note pitch played, get current note and calculate the difference in pitch. Then classify according to the values defined above. 

## Determining if pitch we are moving to is a chord tone

First identify time note is played, then can search through POP909 dataset to find the identified chord being played at that point time time. 

Once chord has been identified, we then identify the 3 chord notes of that chord.

## Generation 

### Initials

Given a key, identify the most common starting chord. Then sample from there with markov chain. 

For a new time step, check if can still use current chord or if time to sample new one. If don't need to sample new one, then say we're current ascending 

Sample melodic state, then given the when we want to generate a new pitch, we look at melodic interval. lets say its ascending, then can either be +1 or +2, we sample both situations, see whether they will be chord notes, and then sample the P(+1 | ascending, chord/non-chord


### Preprocessing


Want to also add the interval to a note, how many semitones up/down it is from the previous note. Once we have this, we can then classify it into the melodic state.

Then want to add whether each note is a chord tone or not

        want to store 
        - start time
        - end time
        - duration
        - pitch
        - identified chord
        - interval from previous note

### Clean up

Get rid of notes outside a valid MIDI range.
Remove notes that last for a very short/long time
Map very rare note intervals to common intervals
Handle edge cases such as first note that has no previous note

## To 

Basically when training, we want to have 2 seperate meldoci states transition probs depending on whether resulting note is a chord tone or not

So for ascending, would have  2 transition matrices one when moving to a chord tone, 1 when moving to a non-chord tone

### Chord Notes

Esentially, when we have a melodic interval, we find the candidate intervals we can choose from.

For example for ascending, can either do + 1 or + 2, we check whether +1 and +2 takes us to a chord tone or not, and sample from the relevant probabilties. P(interval | melodic_state, is_chord_tone). For example, when ascending to a chord tone, P(+2) will be greater than P(+1).


## Questions

- 

figure out most common notes in a chord, bias more closely to those

## Beta-bernoulli conjugate prior

http://sap.ist.i.kyoto-u.ac.jp/members/yoshii/papers/ismir-2016-ojima.pdf uses it

Useful when i want to answer which pitches are likely for a certain chord. If a note is never seen, does not just result in 0 probability, 

Basically, for each chord, want to figure out which notes are present in them. For each note, I already have the chord labelled so should be pretty easy. 

All i need to do is iterate through the note list, for whichever chord is present in it I increment the note pitch counter
### Rhythm
https://infoscience.epfl.ch/server/api/core/bitstreams/9ed01fc5-643d-41cd-93e1-8addcb13b72f/content

Talks about using a standard HMM to train rhythm generation. along with a distance model to enforce more global constraints. Then HMM generates some possibliiltes, and use some search through with the global constraints to find the best candiage. 

## Note format

- start_time (float): The start time of the note

- end_time (float): The end time of the note

- duration (float): The duration of the note (end_time - start_time)

- pitch (int/float): The MIDI pitch value of the note

- chord: The chord that is active during this note's duration

- interval (int/float): The pitch interval from the previous note

- melodic_state (str): The melodic interval of the note

- chord_note (bool): Whether the pitch of the note is part of the current active_chord

## HHHMM

Top layer is the section of the song,
each layer emits a certain pattern of chord progressions,
each chord has a certain notes it emits

Each section has its own transition probabilities for  chords. example for verse section $P(I > V)$  might be different then for a different section .

However, for notes, use a shared state. For example the probability matrix of C major chord should be the same no matter if its C major chord from verse or chorus

Basically, when i have a section decided, I use that sections intial and transition matrix to generate a sequence of chords

Problem right now is that we have 1 sequence that we want to emit multiple sequences, however standard HMM training assumes that 1 hidden state emits 1 observation.

Try out using hidden semi markov model, if that doesnt work, then transition to using iterative refinement

For hidden semi markov model, can provide some basic guidenlines on how long each section should be.

### Chord -> Note emission

Have a hidden state of pattern type , eg appregio, sustained block chord.
This way of playing should be independant of which chord currently in. ie, appregio pattern in C major is same as in A minor. Same pattern different pitches

For each hidden state and each chord, calculate P(note sequence | pattern, chord )

## Step by step 

Given C major for 8 beats
- Initalise hidden state, state = 'appregio'
- Generate notes under P (notes | appregio, C major)
- Decide whether to transition 

We want data for pattern to be chord relative,
### Labelling
For any major chord:
  root = 0 semitones from root
  major 2nd = 2 semitones
  major 3rd = 4 semitones
  perfect 5th = 7 semitones
  major 6th = 9 semitones
  major 7th = 11 semitones
  octave = 12 semitones

For any minor chord:
  root = 0 semitones
  major 2nd = 2 semitones
  minor 3rd = 3 semitones
  perfect 5th = 7 semitones
  minor 6th = 8 semitones
  minor 7th = 10 semitones
  octave = 12 semitones

  Chord: C major (root = C = MIDI 60 in octave 4)
  Pattern cluster: arpeggio
  Notes: C4, E4, G4, C5, E4, G4, C5, E4, 
	    60, 64, 67, 72, 64, 67, 72, 64

Convert to chord-relative:
  C4 (60) → 60 - 60 = 0 semitones → "root, octave 4"
  E4 (64) → 64 - 60 = 4 semitones → "major 3rd, octave 4"
  G4 (67) → 67 - 60 = 7 semitones → "5th, octave 4"
  C5 (72) → 72 - 60 = 12 semitones → "octave, octave 5"
  E4 (64) → "major 3rd, octave 4"

Chord-relative sequence:
  root/4, 3rd/4, 5th/4, octave/5, 3rd/4, 5th/4, octave/5, 3rd/4
  (number = pitch class, /4 or /5 = which octave relative to chord root)

Pool all appregio data across all chords together, then learn a single probability distribution across chord-relative pitch classes


### Major vs Minor chords

Have seperate appregio emission for major vs minor chords

Learn separate distributions:

arpeggio_major → for major chords
arpeggio_minor → for minor chords

Major uses: root, 2, 3, 5, 6, 7
Minor uses: root, 2, b3, 5, b6, b7

This captures that major and minor sound different

### Note emission

Instead of condition note on specific chords and patterns, eg P(note | 'C major', 'appregio), condition on type of chord example tonic (I), dominant (V)
B{arpeggio, I} = notes for tonic function arpeggio
B{arpeggio, ii} = notes for supertonic function arpeggio
B{arpeggio, IV} = notes for subdominant function arpeggio
B{arpeggio, V} = notes for dominant function arpeggio
B{arpeggio, vi} = notes for submediant function arpeggio

Basically lets say we generate an appregio, 
- look up current chord
- generate notes according to P(notes | appregio, current_chord_function)
- look at pattern transition matrix -> sample next pattern eg. another appregio

In order to decide how many notes to generate, do postition aware sampling. Basically, to sample at speicfic time positions in the bar

## Training

Basically, I want the Baum-welch to identify something as a appregio, it then looks to the label of the chord function eg tonic (I), it then adds this to the B{appregio, I} stat. 

One potential issue is if an appregio in the tonic (I) is super different to a tonic in the subdominant (IV), then model might learn this as a 2 seperate state. However, I do not think this would be a major issue because the seperate state learned in the tonic appregio will only be added to the B{appregio, I} stat and not the B{appregio, IV} stat, therefore, it should not interfere with it.

Hidden state is only the pattern

When getting emission probability

### Read about conditional hidden markov models 



### Implementation of Baum-Welch Algorithm for playing pattern

Hidden states are playing pattern -> observed state are a sequence of notes

Increases log-likelihood each iteration, however could still get stuck on a local minimum. Having a good initalisation helps us prevent getting stuck on local minimum and helps us improve chances of reaching global maximum. 

### Weighted Counting

Basically, for each bar/chord, we are going to get a PDF of patterns. Example, might say 80% chance this bar contains an appregio vs broken chord. We then want to incorporate this uncertainty into our calcualtions. So if lets say 

Observation (root,0,eighth,0.0):
  Bar 1: appears once × 0.9 = 0.9
  Bar 2: appears once × 0.8 = 0.8
  Bar 3: doesn't appear × 0.3 = 0
  Bar 4: appears once × 0.85 = 0.85
  Total: 0.9 + 0.8 + 0 + 0.85 = 2.55

Observation (3rd,0,eighth,0.5):
  Bar 1: appears once × 0.9 = 0.9
  Bar 2: appears once × 0.8 = 0.8
  Bar 3: doesn't appear × 0.3 = 0
  Bar 4: appears once × 0.85 = 0.85
  Total: 0.9 + 0.8 + 0 + 0.85 = 2.55

Sum of all weighted counts: 2.55 + 2.55 = 5.10

To calculate P((root, 0, eighth, 0.0), | appregio, I) = 2.55 / 5.10

#### Training Walkthrough

For a song, have a 2D list of observations which are the notes played.  

observations = {  Bar 0 {('root', 0, 0.5, 0.0), ('3rd', 0, 0.5, 0.5), ('5th', 0, 0.5, 1.0), ...}, 
			   Bar 1 {('root', 0, 0.5, 0.0), ('3rd', 0, 0.5, 0.5), ('5th', 0, 0.5, 1.0), ...},  
			   Bar 2 {('root', 0, 4.0, 0.0), ('3rd', 0, 4.0, 0.0), ('5th', 0, 4.0, 0.0)], ... }

also have a list representing the sequence of chord progressions 
chords = {I, IV, V, I,...,I}
##### Model Parameters

K = 10 (hidden states, patterns to learn)
F = 7 (number of chord functions)

Parameters to learn:
- $\pi$: initial hidden state PDF distribution (K values)
- A: transition matrix ($k\times k$ values)
- D: Duration distribution ($k\times \text{max\_duration}$)  
- B: Emission distribution ($k\times f$ distributions) 

###### Step 0 Initalisation (Random) 

Give $\pi$, A, D all random distribution.

For initalisation B set bars to random patterns, make sure  to use the correct chord function label though

###### Step 1 Running forward backward

Run forward backward algorithm to receive the new parameters.

Update parameters.

Since using custom emission distrbution where B{pattern, chord}, will need to custom implement the reestimate function provided. 

#### Implementing reestimate function 

Allows for emission distribution matrix to be be 'learnt'. Updates $B[function, pattern]$. 

Takes in 2 parameters
- gamma{pattern, bar}: PDF of patterns being used at this bar. eg 0.8 for appregio, 0.2 for chord etc
- observations{bar}: note observations for a bar

For each (pattern, chord_function) pair
- find all bars with that chord_function
- using the gamma{pattern, bar}, find the probability that this bar was equal to the current loop pattern
- for each note in the bar, multiply the notes weight by the gamma{pattern, bar} probability. Example, if ('root', 0, 0.5, 0.0) was present in the bar and had a weight of 0.85 of being an appregio. Then we need to weight it appropriately. Idea is after iterating through all the bars, we will have a total weighting of that particular note appearing, and a total weighting of all the notes in {pattern, bar}, then we can appropriately update the emission probability.
- after have total weights, normalise to probabilities
- store new probabilities in B and move on

###### Implementing likelihood function

Used in alpha and beta function when performing forward-backward algorithm. Basically allows us to calculate probability of making a certain observation of notes given we are in a certain pattern. 

So basically, if we are in state appregio with chord function 1, and make some observations we want to look at emissions probability B{appregio, chord} = some_note

Example:

('root', 0, 0.5, 0.0), ('3rd', 0, 0.5, 0.5), ('5th', 0, 0.5, 1.0) are the observations and we are in appregio state with chord function 1.

Then we would look up the probabilities for 
- P('root', 0, 0.5, 0.0) | apprgio, 'I')
- P(('3rd', 0, 0.5, 0.5) | appregio, 'I)

etc and multiply these probabilities all together

#### Emission class

likelihood and reestimate functions don't allow me to pass in chord functions in method signature. Work around is to store the chord functions in the emission class itself, so it can be accessed from both the likelihood and restimate functions. 

### Quantisation of note duration

Need to quantise notes so i have enough occurences to train on. eg for a quaver (0.5) beats, most quavers probably won't be played exactly 0.5 beats so need to quantise. 

DURATION_BINS = { 
'semiquaver': 0.25, 'quaver': 0.5, 'dotted_quaver': 0.75, 'crotchet': 1.0, 'dotted_crotchet': 1.5, 'minim': 2.0, 'dotted_minim': 3.0, 'semibreve': 4.0
}

MIDI stores ticks per beats, and each note has a certain number of ticks stored. So to get beat duration of note, do ticks / ticks per beat. example if 240 ticks and 480 ticks per beat, then note was played for 0.5 beats and is therefore a quaver

### Grouping notes into bars

MIDI stores delta time stamps (time since last event). However, in order to group notes into bars, we need to get their absolute time (time since start). 

Every time we get a note, add the current_tick += msg.delta 

Use an active notes data structure. When a note is being played add it here, when we see the cooresponding note pitch with a 'note_off' or 'velocity = 0' signal, then we know the notes has finished playing. Set the tick_duration as current_tick - start_time. Remove note from active notes dictionary.

Once all notes have been processed, sort by start_tick time.

## Identifying chord present while bar is being played

Look at when bar starts playing and when it ends, look at which chord is presetn at that time. If multiple chords are present use the chord that is present for the greatest amount of time

