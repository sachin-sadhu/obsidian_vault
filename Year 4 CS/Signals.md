## Fourier Series
Any signal wave can be represented as the sum of different sin waves.

With regards to signals, this mean that any signal can be represented as loads of different frequences, all with different amplitudes. A frequency with a larger amplitude means that this frequqncy is more powerful in the given signal wave. 

## Frequency Filters  
Once a signal has been broken down to its cooresponding frequencies, one can apply different filters to get rid of certain frequencies.

High pass filter lets through only high frequencies

Bandpass filter lets frequencies of a certain range 'pass' through

Bandstop filters stops frequencies of a certain range from passing through
### Bandwidth
Range of frequencies a given signal contains.
## Continous vs Discrete Signals
### Sampling
Signal waves are continous sine waves, but computer can only deal with discrete values. Therefore, we need to sample the continous wave at specific intervals to convert it into a discrete representation. 
### Aliasing
Problem of sampling at too low a frequency, therefore low frequency waves that do not exist appear![[Screenshot 2025-09-27 at 3.09.15 PM.png]]
High frequencies get lost if not sampled at high enough rate. Reconstructed signal becomes distorted
### Nyquist-Shannon Sampling Theorem
To avoid the problem of aliasing, make sure you sample the signal at a frequency at least twice the maximum frequency present in the signal.
## Sound
Propogation of particles through a medium. Energy is transferred, not the individual particles themselves

Human ear can hear frequencies from 20Hz - 20000Hz
### Phons
Measures perceived soundness by humans. Humans are more sensitive to different frequencies of sound (most sensitive to 2-5kHZ). At very low/high frequencies, human ear is much less sensitive. Therefore, a 3kHZ sound wave of 20 dB might have a perceived loudness of 100 phons, while a 20kHZ sound wave of 100 dB might also have a  perceived loudness of 100 phons.
### Spectral Masking
Phenomenoen where the present of a sound of a certain frequency might make it impossible to hear another sound at a nearby frequency. Happens because human ear groups very similar frequencies together. 
### Temporal Masking
Occurs when a loud sound makes other sounds that come right after it inaudible, even if those sounds are different frequencies 
## Quantisation
When recording sounds, we need to identify the different amplitudes at different points in time. Since we need to convert these values to a bit stream, we need to have a certain number of bits used to represent these sounds (bit depth). 

Since theres only a discrete amount of levels we can record, quantising a sound wave introduces some noise, where noise is defined as the difference in the actual signal and the quantised signal.
![[Screenshot 2025-09-27 at 4.25.40 PM.png]]

Having a higher bit depth leads to less noise, but an increase in the bit rate.

Quantisation always add some noise. 
## Analog to Digital Conversion
![[Screenshot 2025-11-30 at 3.40.37 PM.png]]

Filtering is crucial to remove very high frequency components that you do not care about. Otherwise, when aliasing, they will be present in a malformed condition.

Sampling is then required to convert to discrete time instants.

Quantisation then converts time values from a continuous range into a number of discrete values that can then be represented as bits.
## Companding (Compression + Exapanding)
Human ear is more sensitive to noise at low amplitudes, and less sensitive to noise at higher amplitudes.

### Compression 

Non-linear function bends the amplitude scale so that
- Small amplitudes get amplified
- Large amplitudes are compressed

Quantiser can now allocate more levels to low-amplitude sections. 
### Expanding

Apply inverse of non-linear function to restore signal to original levels. Quiet sections that were amplified get quitened again. Loud section that were quietened get reamplified. 

Idea is to compress dynamic range of signal before quantisation. Low amplitudes get boosted, loud parts get squashed, means that low amplitude sounds have most quantifiable steps between them, reducing noise.
## Audio Compression
### Differential PCM (Pulse Code Modulation)
   Instead of recording the amplitude level at each sample, record the difference from the previous level. Allows fewer bits to be used at a similar level of quality. Reason fewer bits are needed is because values of difference are likely to be much smaller. Only need enough bits to encode a small number of values now.
### Predictive differential PCM
Predict the next sample value based on previous samples, encode only the difference between the actual and predicted values. Leads to a smaller range of values to be transmitted, reducing bit depth. Since we can always recalculate predicate value and difference, we can calculte what the actual value is.
### Adaptive differential PCM (ADPCM)
Predictive differential PCM with an improved predictor
### Sub-band coded  ADPCM
Split the signal into upper band and lower band. Then perform regular ADPCM on these indivudal bands. 

Now we can apply different sampling and quanitisation techinques for each band according to its frequency and perceptual importance (higher frequencies less important) 
### Linear predictive coding
Analyse the signal, extract features, transmit features, not values. Receiver then converts these features back into the signal through a synthesiser.
Examples of features:
* Loudness
* Pitch
* Voiced vs automated sound
### Perceptual coding
Don't bother encoding parts of the signal that the human ear can't hear anyway. Examples includes temporal/spectral masking
### MPEG
Stands for Motion Picture Expert Group. Standard for encoding audio and video.
## Vision
 * Rods - Highly sensitive to light. Cannot detect colour. Important in low light environments. Roughly 90 million 
 * Cones - Highly sensitive to colour. Works best in bright light. Mostly in the fovea, roughly 4.5 million.

Rods and cones are all across the retina in our eye. Light absorbed by the pigment retina causes a chemical reaction that results in an electrical impulse being sent to the brain.
### Fovea/Peripheral Vision
Central vision of the eye, dominated by cones, almost no rods here. Peripheral vision is dominated by rods, not many cones. 
### Cones
3 different types of cones, which are most sensitive to light of different wavelength

![[Screenshot 2025-10-04 at 7.02.37 PM.png]]
Metamers is where 2 different signals create the same colour perception in humans.
### Hue/Saturation
Hue is what we think of as colour itself. The dominant wavelength of the light we see.
Saturation is how intense or vivid the light is. Low saturation makes the colour look grayed out.
## Cameras
- Made up of a grid of light sensitive cells (pixels)
- When light hits, electrons get excited, creating a charge proportional to intensity of light
### CCD
Stands for charge coupled device. 
Charge generated is not converted to voltage inside the pixel itself. Gets shifted across sensor to output node where charge is converted and then digitised. 
### CMOS
Stands for Complemetary Metal-Oxide-Semiconductor

Each pixel has its own photodector, amplifiers, conversion circuitry in each pixel itself. Charge is converted directly to voltage in the pixel. Because of this, tends to be faster
### Colour - Bayer Filters
Only the intensity of the light is captured, do capture the colour of each light, we can place a filter that covers each pixel. Now, each pixel is in charge of detecting the intensity of light of a certain colour. For example, if a pixel has a red filter on top of it, it is in charge of detecting the intensity of red light.
### Additive Colour Scheme
Involves light. Basic idea is that as more colours are added, closer you get to white. 
Primary colours are : 
- Red
- Green 
- Blue
TV, computer monitors all use this technique where they EMIT light.
### Subtractive Colour Scheme
Involves pigment/ink. As you mix more coloured inks together, get closer to black. This is because each colour pigment absorbs certain wavelengths. 
Primary colours are:
- Cyan
- Magenta
- Yellow
### Posterisation
Happens when we try to represent colours of an image using fewer bits. Therefore, we map more colours to the same one, can lead to images looking weird.
## Vector vs Bitmap Images
### Vector
Vector images are stored as a combination of shapes defined by mathematical equations. Eg. to draw a circle, we can use $x^2+y^2=k$. 

Since they are mathematically defined, they are resolution independant, you can zoom in/out and the shape will be recomputed, therefor they don't lose resolution

However, not ideal for real life images. eg. image of a class of people. 
### Bitmap (Raster) 
Made up of a grid of individual pixels, which each hold a colour and intensity. Fixed resolution, they come with a fixed number of pixels (eg 1920 x 1080 pixels). Zooming in will result in pixels becoming visible (called pixelation.)

Can be quite large in size, best for real life photographs
### PNG (Portable Network Graphics)
Stands for portable network graphics. Standard for lossless image compression
### Pre-Filtering
Essentially, want to replace each pixel value with the difference between a neighbouring cell. Can use different filters for different lines, for example, if an image has a vertical line with similar pixels, then instead of storing each pixel value, we can store the difference of the actual pixel value with the pixel above it.

Can do something similar horizontally.
#### Prediction
Can take it a step further and use neighbouring cells to make a prediction on what the pixel should be. Then we store the difference between the prediction and and the actual pixel value.

When recomputing the pixel value, we can always recompute what the predicted value was with the neighbouring cell values, and then apply the difference. 
#### Deflate
Form of compression technique. Kind of like huffman coded compressions. Finds reptitions
### JPEG (Joint Photographic Expert Group)
Form of lossy image compression. Uses a discrete cosine transform (same as DFT but using cosine waves)

High spatial frequency refers to rapid changes in pixel values (sharp edges, fine details)
Low spatial frequency refers to slow changes in pixel values (smooth transitions)

Human eyes are much more susceptible to low frequency changes, while not good at identifying high spatial frequency changes. 

Basic idea in JPEG is to preserve bits for the low frequeny portions, and discard more bits for the high frequency portions.

An image is divided into 8x8 pixel block. A DCT then is run over this, which seperates the image into low and high frequency components
##### Quantisation
Each frequency coefficient is then divided by some integer, and rounded to the nearest integer. Low freq components are typically quantised less aggresively, while high freq components are quantised more (more data is discarded for fine details). This quantisation is where the lossy component is introduced. Once the value is rounded, even if we multiply by that divisor integer, there's no way to get exactly the same number back.

Huffman encoding is then applied.
### Image Aliasing

Happens when trying to rasterise polygons onto pixels. For example a curve with infinite precision trying to be drawn onto a pixel. Impossible to capture exact curvature

## Convolutions

Operation that combiens 2 functions to poduce a third one. Basically measures how much one function overlaps with another as you slide it across
$$(f*g)(t)=\int_{-\infty}^{\infty} f(\tau)g(t-\tau)d\tau$$
Here 
- f is the signal or image
- g is the kernel or filter
- f*g is the convolved result

In signal procecessing, usually involves sliding a filter over some image. At each step, we take pairwise multiplication of the values and sum them all up. These are our new output values
![[Pasted image 20251101185853.png]]
### Padding
If we have a 5 by 5 matrix, and a 3 by 3 kernel, then since the kernel must fit fully over the section, we will not be able to perform the calculation on some of the edge rows/columns. To fix this issue we introduce padding.

- Zero padding - Introduce a matrix with additional rows and columns with values all set to 0
- Replicate padding - Extend values at edge rows columns all the way out
- Reflect padding

Convolutions allow us to do multiple things with images, such as blurring/sharpening etc. 
## Segmentation

Dividing an image into meaningful parts/regions. Each region could coorespond to a object or an area of interest. For example, with medical imaging, we might want to segment each of the different organs in the image. 

- Semantic Segmentation - Assigning each pixel in the image to a class. Example all blue pixels get assigned 'sky' class. 
- Object Detection - Comes with bounding box showing the object's location, along with a label of what the object is and a confidence score. 
- Instance Segmentation - Combines object and semantic segmentation. Example for an image with 2 cars and a person, instance segmentation would be able to label car 1 and car 2 pixels seperately, along with labelling person pixels. 
- Panoptic Segmentation - Combines semantic and instance segmentation. Is able to assign pixels individually, and for things that are uncoutable (backgroud, sky, grass), will only give it a class label.

## Thresholding

Used to segment an image by turning it into a binary image based on pixel intensity. Basically decides which pixels belong to an object and which belong to the background. 

1. Choose a threshold value T
2. For each pixel in the image, compare its intensity I to T:
	- If I > T, assign it white
	- If I < T, assign it black

Otsu's method is a way of automatically finding optimal threshold value by minisiming the intra-class intensity variance. 

Thresholding on its own usually isn't enough to segment multiple different objects (unless they have very distinct intensity ranges)

## Watershed Segmentation Algorithm

Essentially we want to segment different areas according to their different pixel intensities. Can think of very low pixel intentisy values as valleys 
![[Pasted image 20251101200141.png]]

Essentialy how it works is
 1. Find lowest pixel value
 2. Repeat
	 * Store all pixels with that pixel value in a queue
	 * For every pixel in the queue
		 * If its not in a segment, assign it to a new segment
		 * Add its immediate neighbours to that segment
		 * Remove pixel from queue
		If no pixels in queue, move onto next lowest pixel value

We want to add neighbours to simulate the idea of water flowing. When you drop a drop of water at the valley, it wont just stay at that exact point, it will slowly spread around. 
## Machine Learning
Having testing set unseen is extremely important. Should only be evaludated model once on testing set. Otherwise, if you use testing set to retrain model, chance you are overfitting to testing set. 

Unsupervised Learning : Uses unlabelled datasets

### K-means clustering
![[Pasted image 20251109215511.png]]

Works by placing k centroids randomly among the dataset, and then for each data point, we calculate which centroid it is closest to. Iteratively move each centroid to the mean of the cluster until we reach a conclusion. 

### CNN

Stands for convolution neural networks, involves using convolution layers that look at small regions of the input at a time. Good at capturing spatial patterns. 

convolutional Layer applies filters that convolve over the input pixels to detect local features. 

Features maps are the output of a convolutional layer. Represents where and how strongly certain features appear in the input, like edges, corner, textures etc. 

#### Sub-sampling

Downsampling operation that reduces spatial dimensions of features maps. Basic idea is the make the representation smaller and more manageable, while preserving the key features that matter most. Instead of remembering every pixel, just note where the strongest features are - edges, corners, textures - reducing detail but keeping what's important. 

- Max Pooling : Within each filter, take the maximum value
- Average Pooling : Within each filter, take the average value

## Medical Imaging

### X-rays

Works by sending EM waves thorugh body and measuring how much different tissues absorb them. Different types of tissues absorb different amounts of the EM energy, which allows us to create an image of the internal structure. 

Because they are high energy, they can pass through soft tissue, but are absorbed strongly by dense materials like bone

1. Electrons are fired at a metal target, when they hit the metal, the sudden deceleration releases energy in the form of x-ray photons
2. A beam of these photons are directed at the body, where different tissues absorb the x-ray different (attenuation)
3. Detector on the opposite side of the emitter that records which rays pass through and which were absorbed, producing a 2D grayscale image based on intensity of X-rays that reach the sensor. The more rays that reach the detector -> darker pixel, the fewer rays -> whiter pixels 

### Ultrasound

Works by sending high-frequency sound waves (2-15 MHz), these waves travel through the body and reflect off different tissues, where these echoes are then returned to a detector

1. Probe sends out sound waves
2. As they travel through the body, part of the wave is reflected wave
3. Probe them detects these echoes and converts them back to electrical signals

By measuring the time taken it took to detect the reflected sound wave, the machine can calculate how deep the boundary was $$\text{distance}=\frac{c\times \text{echo time}}{2}$$
where c is the speed of sound in tissue and is approximately 1540 m/s. We dvidie by 2 since the pulse has to travel to the boundary and back. 

Different tissues also refelect a different amount of the sound wave
- fluid doesn't reflect much -> black
- muslce gives medium reflections -> grey
- bone and air reflect strongly -> very bright
#### Limitations

- Doesn't work well with bones since sound can't penetrate
- Doesn't work with air (lungs, bowel gas block sound)
- Lower resolution than CT or MRI

### MRI

Stands for Magnetic Resonance Imaging. Works by aligning hydrogen atoms in our bodies using a strong magnet and then measuring how they respond to radio waves.

Each nucles behaves like a tiny magnet that spins, normally, these spins point in random directions. When the MRI's super strong magnet is turned on, it causes all of these spins to align in a particular direction. 

Next the machine sounds out a radio wave, which knocks these spins out of alignement, when the pusle stops, the atoms go back to their algined state, when they do, they emit radio waves, which the scanner then measures. 

Different tissues return to equilibrium at differen rates, 

### PET/SPECT

PET stands for Positron Emission Tomography. Works by detecting pairs of gamma rays

1. Works by injecting a radioactive tracer that emits positrons into the bloodstream
2. When a positron meets an electron in the body, they annihilate each other
3. This annihilation releases 2 gamma photons that are emitted in opposite directions
4. Detectors detect these coincident photons at the same time

PETS are good for 
- Cancer detection (tumours use lots of glucose)
- Brain activity mapping

SPECT stands for Single Photon Emission Computed Tomography

Detects single gamma photons emitted from a radioactive tracer