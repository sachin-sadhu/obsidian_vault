# Research

### Key Frequencies
- Delta (0.5-4 Hz)
- Theta (4-8 Hz)
- Alpha (8-12 Hz)
- Beta (12-30 Hz)
- Gamma (>40 Hz)

### Sleep Stages
https://courses.lumenlearning.com/waymaker-psychology/chapter/stages-of-sleep/
First 3 stages of sleep are NREM, last stage is REM
* 2 main stages, non-rem and rem, both have unique waves of frequency and amplitude.
* Waves during rem very similar to wakeful 
#### Stage 1
First stage of NREM. Transitional stage between wakefulness and sleep. Early stage produces ALPHA waves, later transitions to THETA waves.
#### Stage 2
THETA waves still dominant
#### Stage 3
DELTA waves dominant.
#### Stage 4 (REM sleep)
Stage where REM takes place, dreaming occurs.  THETA and Beta waves most common.

EEG (electroencephalogram) is a non-invasive technique that records electrical activity from the brain to be analysed. Involves placing   

Very small amplitidues, require an amplification. Amplification must be performed to allow for decent quantisation. Image a 5V range with 16 bit depth, then each bit has a resolution of $$\frac{5}{2^{16}}=76\micro V$$ Hence if we do not amplify the amplitude, amplitudes below 76 micro volts will get quantised down to 0 and lost.
### Spotlight on Sleep Stage Classification Based on EEG
* In 1968, a group of sleep researchers led by Allan Rechtschaffen and Anthony Kales was created in order to establish a consensus about the way to classify sleep stages based on electrophysiology recordings
### ADVANCES IN QUANTITATIVE ELECTROENCEPHALOGRAM ANALYSIS METHODS

* Scalp electrodes cannot detect charges outside 6 cm2 of the cortical surface area, and the effective recording depth is several millimeters.
#### Amplification
* The EEG signal bandwidth is 0.5–100 Hz in frequency (although the most interesting range components are below 30 Hz), and typical amplitudes are 10–300 µV 
* This requires an amplifier with specific features: good noise behavior, low leakage current, high CMRR (common mode rejection ratio), high input impedance, high PSRR (power supply rejection ratio), and high IMRR (isolation mode rejection ratio).
#### Sampling Rate
* Rate of 250 Hz, allows frequencies of up to 125 Hz to be deteced according to Nyquist-shannon theorme 
#### Filtering
Occurs at 2 points during pipeline:
- when data is collected, low pass filter will be applied (only allows low frequencies through)

Frequencies exceeding 70-90 Hz are filtered, freq lower than 0.5 Hz are also filtered out. (https://pmc.ncbi.nlm.nih.gov/articles/PMC2698918/)
#### Frequency Analysis
Usually power spectral analysis.
## Noise
Sources of low freq noise include: 
- movement of head and electrode wires
- sweat on scalp

Sources of high freq noise include: 
- EM interference
- muscle contractions (face and neck)

### Non-Stationary
Sources of different frequency waves are non constant. For example, a 5Hz Theta wave might only be present for a couple milliseconds at a certain time, then disappear. 

Standard FFT not sufficient because it only tells you which frequencies are present and at what amplitude, not when they occur. 

Sleep analysis requires knowledge of when a certain frequency is present.

Hence Short-Time Fourier Transform is required (STFT). Produces a time-frequency representation, allowing you to see when different freq waves are present

### STFT 
Time Resolution - How precise you can measure when an event occured. Shorter time windows gives better time resolution because you are measuring a smaller window of time.

Frequency Resolution - How precisely you can measure the frequency of a wave. Eg (12 Hz vs 14 Hz). Longer time windows give better frequency resolution since you have more cycles to measure.  

With STFT, trade off between time and frequency resolution, impossible to get a high amount of both.
