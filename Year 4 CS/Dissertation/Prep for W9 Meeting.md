
MelodyRNN is LSTM geared toward generating melodies

### Encoding/Decoding

Encoding: Process of converting from original form into a strucutre that can be fed into the neural network. 

Decoding: Process of converting from output of RNN back into original form

For Magenta, this is defined in the EventSequenceRnnConfig class. 

Supported necodings for melody_rnn:
1. One hot vector, MIDI pitches between min_note and max_note
2. look_back encoding, includes info about previous notes/events
3. key-baed encoding. Not only encodes melody, but also info about the musical key as part of input vector
#### Keys
Melody_rnn_model has a DEFAULT_TRANSPOSE_TO_KEY varibale which is initally set to 0

### Building the model

Located in events_rnn_graph.py file, whic defines the actual neural network structure
- make_rnn_cell function is in charge of making a single RNN cell (uses LSTM as default)
- get_build_graph_fn returns a function that when called constructs and builds the TensorFlow graph. 

### Generating events

events_rnn_model.py file is responsible for this 

File provides functionality for
1. Initalising model with configs and hyperparameters
2. Builds tensorflow graph
3. Handles sequence generations

## note-seq repo

Repo that magenta uses. Provides a standard way to repreent musical data as NoteSequence objects.

Allows for:
- Creating NoteSequence objects from different formats like MIDI, abc and MusicXML
- Manipulate sequences (slicing, quantising)
- Extracting musical components (melodies, drums, chords)
- Exporting sequences
- Converting sequences into formats for ML (one hot vectors )

### Melody One-hot Encoding

Returns an integer index

Input: A Melody event value. -2 for no-event, -1 for a note-off event, [0,127] for a note-on event for that pitch
Output : 0 = no event, 1 = note-off event, [2,...] note-on event for pitches in the allowed range

### Possible Encodings Decodings

Really only one hot encoding is offered.

Defined in encoder_decoder.py file

1. One Hot Encoding for a single event
2. EventSequenceEncoderDecoder - Class for converting sequence of events to model inputs and output labels

### Output

Examps a zero-based index value to its equivalent melody event value
### Attention Rnn Parameters

Uses the key melody encoder and decoder, with min and max notes set to the default.

Sets attention parameter to 40 and uses 2 RNNs of 128 each 

LSTM has 2 layers, each with 128 neurons 

#### Encoding

Uses Lookback encoding along with attention

##### Lookback encoding

Builds off the Basic RNN, where the input was just a one-hot vecotr of previous event, and label was next target event. Lookback RNN adds the following additional info to each input vector:

- Inputs events from 1 and 2 bars ago
- Input whether the last event is repeating the event from 1 or 2 bars before. Singals if last event was new, or in a established melody. Allows model to more easily recognise patterns associated with repetivie or non-repetitive state. 
- Input current position within the bar, allows model to easily learn patterns associated with 4/4 time. 

Also introduces 2 new labels, one to repeat event from a bar ago, and one to repeat event from 2 bars ago

Uses the KeyMelodyEncoderDecoder class, which is able to encode repeated events, tie and key. 

- contains a lookback_distances parameter which is a list of step intervals to look back in history to encode both the following event and whether the current step is a repeat
- binary_counter_bits: number of input bits to use as a counter for the timing of the next note

Class creates a historagram of the key that the current melody is in, and also a histogram of the last key the last 3 notes are in.   However, only supports the 12 major keys

The historgram return a list of 12 ints, where each number is the total number of notes that could fit into that key

### Calculating Attention

$u_i^t=v^T\tanh(W_1'h_i+W_2'c_t)$    
$a^t_i=\text{softmax}(u_i^t)$    
$h_t'=\sum_{i=t-n}^{t-1}a_i^th_i$    

$h_i$ are RNN outputs from previous n steps. Vector $c_t$ is the current step's RNN cell state. Used to calculate $u_i^t$ which is a n-length vector with one value for previous n steps. These represents how much 'attention' each step should receive. Softmax then is applied to normalise. RNN outputs from previous n steps are then multiplied by attention mask values and summed together to get $h'_t$ 

Example if output from previous 3 steps are 
- $[1,0,0,1]$ 
- $[0,1,0,1]$ 
- $[0,0,0.5,0]$ 

and $a_i^t=[0.7,0.1,0.2]$, then most recent step gets 20% attention, middle one gets 10% and the earliest one gets 70% .

So we just mulitply the attention value by the vector to get
- $[0.7,0,0,0.7]$
- $[0,0.1,0,0.1]$
- $[0,0,0.1,0]$

Then we sum them up to get $[0.7,0.1,0.1,0.8]$. This vector is then concatedated with RNN output from current step and a linear function is applied to that vector to create output for the current step 