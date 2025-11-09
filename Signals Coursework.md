# Research

### Key Frequencies
- Delta (0.5-4 Hz)
- Theta (4-8 Hz)
- Alpha (8-12 Hz)
- Beta (12-30 Hz)
- Gamma (>40 Hz)


EEG (electroencephalogram) is a non-invasive technique that records electrical activity from the brain to be analysed. Involves placing   

Very small amplitidues, require an amplification. Amplification must be performed to allow for decent quantisation. Image a 5V range with 16 bit depth, then each bit has a resolution of $$\frac{5}{2^{16}}=76\micro V$$ Hence if we do not amplify the amplitude, amplitudes below 76 micro volts will get quantised down to 0 and lost.


![[Pasted image 20251020155718.png]]
A review of automated sleep stage based on EEG signals
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

### Non-Stationary
Sources of different frequency waves are non constant. For example, a 5Hz Theta wave might only be present for a couple milliseconds at a certain time, then disappear. 

Standard FFT not sufficient because it only tells you which frequencies are present and at what amplitude, not when they occur. 

Sleep analysis requires knowledge of when a certain frequency is present.

Hence Short-Time Fourier Transform is required (STFT). Produces a time-frequency representation, allowing you to see when different freq waves are present

### STFT 

EEG wave are non stationary, sources of different waves come and go

Time Resolution - How precise you can measure when an event occured. Shorter time windows gives better time resolution because you are measuring a smaller window of time.

Frequency Resolution - How precisely you can measure the frequency of a wave. Eg (12 Hz vs 14 Hz). Longer time windows give better frequency resolution since you have more cycles to measure.  
With STFT, trade off between time and frequency resolution, impossible to get a high amount of both.

Works by choosing a time window (epoch) to perform a FFT on, epoch size is a hyperparameter
Also can choose how much of each epoch overlaps with 1 another
To counter the time frequency resolution trade off, can choose different epoch sizes for different frequencies

Ideally want to use low time windows for high freq, and long time windows for low freq.
![[Pasted image 20251020140604.png]]
### Wavelet Transform 
Overcomes limitation of fixed window resolution of STFT. In many real life scenarios, high freq waves often are short bursts, low freq are gradual changes. 

High freq -> Use short time window, allows us to pin point exactly where high freq occurs
Low freq -> Use long time window, exact point in time isnt crtitical, distiniguisinhg between 5 vs 6 hz might be

|                   |             **Meaning**              | **Description**                                                                   |
| :---------------: | :----------------------------------: | :-------------------------------------------------------------------------------- |
|                   |           **Input signal**           | **The signal you want to analyze.**                                               |
|                   |         **_Mother wavelet_**         | **A small wave-like function localized in both time and frequency.**              |
| **( \psi^*(t) )** | **Complex conjugate of the wavelet** | **Ensures correct phase alignment.**                                              |
|     **( s )**     |         **Scale parameter**          | **Controls _stretching_ or _compressing_ of the wavelet → relates to frequency.** |
|   **( \tau )**    |      **Translation parameter**       | **Controls shifting of the wavelet along time → relates to time localization.**   |
|                   |                **s**                 | **}} )**                                                                          |
#### Mother wavelet
Basic idea is to have a mother wavelet, and then either squish/expand it. 

- Expanded wavelets cover a longer time window, and the have oscilliations at a less frequency, more closely matching those low freq components
- Compressed wavelets cover a shorter time window, and the have oscilliations at a high frequency, more closely matching those high freq components

when the frequency of the portion of signal closely matches wavelet freq, then is a high overlapping area, hence integral output is high

- You take a **wavelet** (a short oscillating function like the one shown above).

- At each position and scale, you compute the **overlap** (correlation) between the wavelet and the signal.
    
- You **slide it along the signal** (τ\tauτ) and **stretch/compress it** (sss). this is called a convolution

The result F(τ,s)F(\tau, s)F(τ,s) tells you **how much the signal resembles the wavelet** at a certain time (τ\tauτ) and scale (sss)

### **Sleep Stage Classification: How It’s Performed**

**1. Standard (Manual) Method**

- Based on **EEG, EOG, EMG signals** recorded during polysomnography (PSG).
    
- **AASM Guidelines** define stages: American academy of sleep medicine
    
    - Wake, N1, N2, N3 (non-REM), REM
        
    - Criteria include **frequency bands**, **amplitude**, and **patterns** (e.g., sleep spindles, K-complexes).
        
- Sleep technicians **score 30-second epochs** manually according to these rules.
    

**2. Automated / Machine Learning Methods**

- **Signal preprocessing:** filtering, artifact removal (eye blinks, muscle activity).
    
- **Feature extraction:** power in different frequency bands, statistical measures, or time–frequency transforms.
    
- **Classification algorithms:**
    
    - Traditional: Random Forests, SVMs, KNN
        
    - Deep Learning: CNNs, RNNs, Transformers
        
- Often validated against **manual scoring** as ground truth.
    

**3. Key Notes**

- Classification accuracy depends on **signal quality** and **algorithm choice**.
    
- Can be **epoch-based** (30s windows) or **continuous prediction**.
    
- Some systems integrate **multiple signals** for better accuracy.