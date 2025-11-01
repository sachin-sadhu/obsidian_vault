## Fourier Series
Any signal wave can be represented as the sum of different sin waves.

With regards to signals, this mean that any signal can be represented as loads of different frequences, all with different amplitudes. A frequency with a larger amplitude means that this frequqncy is more powerful in the given signal wave. 

## Frequency Filters  
Once a signal has been broken down to its cooresponding frequencies, one can apply different filters to get rid of certain frequencies.
### Bandwidth
Range of frequencies a given signal contains.
## Continous vs Discrete Signals
### Sampling
Signal waves are continous sine waves, but computer can only deal with discrete values. Therefore, we need to sample the continous wave at specific intervals to convert it into a discrete representation. 
### Aliasing
Problem of sampling at too low a frequency, therefore low frequency waves that do not exist appear![[Screenshot 2025-09-27 at 3.09.15 PM.png]]
### Nyquist-Shannon Sampling Theorem
To avoid the problme of aliasing, make sure you sample the signal at a frequency at least twice the maximum frequency present in the signal.
## Sound
Propogation of particles through a medium. Energy is transferred, not the individual particles themselves

Human ear can hear frequencies from 20Hz - 20000Hz
### Phons
Measures perceived soundness by humans. Humans are more senstive to different frequencies of sound (most sensitive to 2-5kHZ). At very low/high frequencies, human ear is much less sensitive. Therefore, a 3kHZ sound wave of 20 dB might have a perceived loudness of 100 phons, while a 20kHZ sound wave of 100 dB might also have a  perceived loudness of 100 phons.
### Spectral Masking
Phenomenoen where the present of a sound of a certain frequency might make it impossible to hear another sound at a nearby frequency. Happens because human ear groups very similar frequencies together. 
### Temporal Masking
Occurs when a loud sound makes other sounds that come right after it inaudible, even if those sounds are different frequencies 
## Quantisation
When recording sounds, we need to identify the different amplitudes at different points in time. Since we need to convert these values to a bit stream, we need to have a certain number of bits used to represent these sounds (bit depth). 

Since theres only a discrete amount of levels we can record, quantising a sound wave introduces some noise, where noise is defined as the difference in the actual signal and the quantised signal.
![[Screenshot 2025-09-27 at 4.25.40 PM.png]]

Having a higher bit depth leads to less noise, but an increase in the bit rate.
## Audio Compression
### Differential PCM
   Instead of recording the amplitude level at each sample, record the difference from the previous level. Allows fewer bits to be used at a similar level of quality
### Predictive differential PCM
Predict the next sample value based on previous samples, encode only the difference between the actual and predicted values. Leads to a smaller range of values to be transmitted, reducing bit depth.
### Adaptive differential PCM
Predictive differential PCM with an improved predictor
### Sub-band coded  ADPCM
Splits the signal into separate frequencies, then applies ADPCM on these invidciaul bands. 
### Linear predictive coding
Analyse the signal, extract features, transmit features, not values. Receiver then converts these features back into the signal through a synthesiser.
Examples of features:
* Loudness
* Pitch
* Voiced vs automated sound
### Perceptual coding
Dont bother encoding parts of the signal that the human ear can't hear anyway. Examples includes temporal/spectral masking
### MPEG
Stands for Motion Picture Expert Group. Standard for encoding audio and video.
## Companding
Human ear is more sensitive to noise at low amplitudes, and less sensitive to noise at higher amplitudes.

Idea is to compress dynamic range of signal before quantisation. Low amplitudes get boosted, loud parts get squashed, means that low amplitude sounds have most quantifiable steps between them, reducing noise.
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
### PNG
Stands for portable network graphics. Standard for lossless image compression
### Pre-Filtering
Essentially, want to replace each pixel value with the difference between a neighbouring cell. Can use different filters for different lines, for example, if an image has a vertical line with similar pixels, then instead of storing each pixel value, we can store the difference of the actual pixel value with the pixel above it.

Can do something similar horizontally.
#### Prediction
Can take it a step further and use neighbouring cells to make a prediction on what the pixel should be. Then we store the difference between the prediction and and the actual pixel value.

When recomputing the pixel value, we can always recompute what the predicted value was with the neighbouring cell values, and then apply the difference. 
#### Deflate
Form of compression technique. Kind of like huffman coded compressions. Finds reptitions
### JPEG
Form of lossy image compression. Uses a discrete cosine transform (same as DFT but using cosine waves)

High spatial frequency refers to rapid changes in pixel values (sharp edges, fine details)
Low spatial frequency refers to slow changes in pixel values (smooth transitions)

Human eyes are much more susceptible to low frequency changes, while not good at identifying high spatial frequency changes. 

Basic idea in JPEG is to preserve bits for the low frequeny portions, and discard more bits for the high frequency portions.

An image is divided into 8x8 pixel block. A DCT then is run over this, which seperates the image into low and high frequency components
##### Quantisation
Each frequency coefficient is then divided by some integer, and rounded to the nearest integer. Low freq components are typically quantised less aggresively, while high freq components are quantised more (more data is discarded for fine details). This quantisation is where the lossy component is introduced. Once the value is rounded, even if we multiply by that divisor integer, there's no way to get exactly the same number back.

Huffman encoding is then applied.
## Convolutions
## Machine Learning
Having testing set unseen is extremely important. Should only be evaludated model once on testing set. Otherwise, if you use testing set to retrain model, chance you are overfitting to testing set. 

