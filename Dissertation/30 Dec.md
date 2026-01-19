Basically what I think I can do is once i have the chords I also know at one time point they occur,
so then i can go into the MIDI file and see what notes accompany that chord.

## Allowed chords

- triads with inversions (maj,min,dim,aug)
- basic sevenths (maj7, min7, 7, dim7) with inversions
- suspended chords (sus2,sus4,sus4(b7))
- sixth chords (maj6, min6)

can probably avoid inversions

can get rid of basic sevenths

probably want to keep suspended chords

probably want to keep sixth chords

### Potential pipeline for processing

Want to get rid of everything after a slash. example 'Eb:min/b3' -> 'Eb:min'

### Chords per bar 

Maybe to keep things simple, only have 1 major chord per bar 

Metrics to choose chord
- Choose chord that lasts the longest in a bar
