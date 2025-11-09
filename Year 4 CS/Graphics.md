Good video for p2
https://www.youtube.com/watch?v=YLHjX2bIOYc&list=PL2935W76vRNHFpPUuqmLoGCzwx_8eq5yK&index=11
## Introduction
* Collection of vertices (points in space)
* Points are combined into primitive shapes (always triangles)
* Light and other surface textures are then applied onto these triangle faces
* Need to decide which pixels should represent which triangle face
## Pipeline

![[Screenshot 2025-09-20 at 3.47.43 PM.png]]
### Vertex Processing
* In charge of computing attributes of individual vertices (colour)
* Performs coordinate transformation (world to camera, camera to display)
### Clipping and primitive assembly
* An image only covers part of the world
* Clips removed fragments that are not visible
### Rasterization
Process of deciding for a triangle which pixels it should cover on the screen. Decides attributes of each pixel such as texture coordinates, depth, normals etc. Like the ingredients for the fragment.
### Fragment Processor
Takes all those attributes of a fragment and decides the final RGBA colour of each pixel that the fragment covers. 
### Shaders
Programs that run on the GPU of a computer. Very good at doing tons of parallel operations per second.
## Viewing
Projection is the idea of dispalying a 3D image on a 2D plane. This 2D plane we project onto is called the projection plane![[Screenshot 2025-10-04 at 5.45.16 PM.png]]
### Parallel Projections
Parallel lines remain parallel even after projection. COP is infinitely far from the projection plane.
#### Orthographic Projections
Projectors are perpendicular to projection pla![[Screenshot 2025-10-04 at 6.09.26 PM.png]]ne.
#### Axonometric Projections
Projectors still perpendicular to projection plane
### Perspective Projections
Parallel lines may not remain parallel after projection. COP is located at a finite distance from the projection plane.
### Camera Matrix
Camera is just another object in the world, with its own coordinate system. Camera matrix defines camera's position and orientation in world space. For example, position the camera at the origin and look at (1,2,3)
### View Matrix
Transforms world coordinates into camera space. Inverse of the camera matrix, fixes the camera at the origin and moves the world with respect to it.$$V=C^{-1}$$ 
## WebGL
### Coordinates
Uses (x,y,z) coordinate system. Centre of clipping space has coords :(0,0,0). 
- x coord grows positively to the right
- y coord grows positively up
- z coord grows positively towards you
### Shaders
Work on processing data per vertex, like calculating where on the screen each vertex should appear. Vertex shader take in input  do some transformations, and then has an output that is passed to next stage of pipeline.
* 'attribute' keyword is used to say this is an input to the vertex shader
* 'varying' keyword is used to say this attribute is used in the fragment shader

### Vertex Shaders

Vertex shaders are responsible for determining where in the screen a vertex should appear. We give it the coordinates of a point and then a bunch of transformations are applied

### Fragment Shaders

Responsible for determining the colour of each pixel

### Normal Matrix

Used to define how to transform a normal in order to keep it in line with how the vertexes were transformed $$\text{NormalMatrix}=(M^{-1}_{model})^T$$
So if you have a normal n in object space. Its world space coordinates are defined as $n'=\text{NormalMatrix}\times n$ 


## Linear Transformations
Linear transformations can be represented as matrices. For example the following 4 by 4 matrix can be thought of as a matrix that defines the unit basis vectors, where each column is the 4D representation of where the new basis vectors are. 
## Illumination 
Describes the overall lighting environment in a scene. Defined by light sources, surface properties, and the interaction between the 2
### Surface Types
- Specular (Shiny): Reflects light strongly around an angle symmetric to incidence angle eg. shiny metals
- Diffuse (matte): Reflects light equally in all directions eg. cotton, carpet 
- Translucent: Lets light through but might affect colour intensity and direction of light. Eg water and glass
### Light 
Source of light can be modelled by its location in the world, direction of emissino, and the intensity of the light. 

Intensity of the light is often modelled by how intense each of RGB is
### Ambient Lighting
Provides same amount of light to every point in the room. Without this, some surfaces not directly in the path of a light source would be completely dark.
### Point Sources
A distant light source can be modelled by a single point which radiates light equally in all directions. Intensity falls with distance according to $$I\propto \frac{1}{d^2}  $$
### Spotlights
Can be approximated by using a point source, and limiting the angle of which light is emitted from it. Intensity is greatest when $\theta =0$, falls to 0 outside of the angle.
## Phong Reflection Model
Combination of ambient + diffuse + specular reflection
Need 4 vectors:
- n is the normal to point P
- v points to the viewer
- l points to the light source
- r is the direction of perfect reflection of l
![[Screenshot 2025-10-22 at 3.42.08 PM.png]]
r and v are need to caculate the specular reflection. if r and v are algined, then the camera should get a lot of specular reflection, however, if they are not aligned, then the camera should not get any specular reflection
### Diffuse reflection
Rough surfaces exhibit diffuse reflection, where light is scattered in multiple directions. Therefore, intensity of reflected light is uniform across surface, and there is no distinct reflection of the light source
#### Lambert's law
$$R_d\propto cos\theta$$
Reflection intensity varies with incidence angle. Strongest when light source is perpendicular to surface, zero when parallel 

$k_s$ represents the reflective of the material. Controls amount of specular reflection
### Specular Reflection
Amount of reflected light can be modelled by $$I_s=k_sL_scos^\alpha\phi$$
where $\phi$ is the angle between the viewer and the angle of reflection. $\alpha$ represents the shininess of surface, as $\alpha$ reaches infinity, we reach a mirror surface. Higher $\alpha$ leads to more concentrated and smaller reflections.
- values 100-500 for metallic 
- values < 100 for broad highlights
## Shading 
Determining how the light a point receives changes its colour.
### Flat Shading
Simplest type of shading. Caclulate illumination once per polygon (middle of polygon), all pixels on the polygon will have the same colour. Gets better as we increase the number of polygons
### Smooth Shading (Gouraud)
Illumination at each vertex is calculated (so is the final colour). All other points are then interpolated from those vertex colours. No need to recompute illumination. If colour at each vertex is the same, then flat shading is performed.  Normals at each vertex must be calculated

Smooth shading is default way of shading in WebGL
### Phong Shading
Normals at each vertex are calculated, and then normals across the entire polygon are interpolated. Once normals are interpolated, then the colour of each pixel is calculated. 
## Buffers 
2D array consisting of $n\times m$ elements of k bits each.

The framebuffer is the set of all buffers used for rendering
### Front and back buffers

Front buffer is the buffer currenlty being displayed on the screen. Typical image that the user sees

Back buffer is where rendering occurs while the current frame is being dislayed. Off screen buffer that the graphics card writes to while front buffer is being displayed to the screen.
### Depth Buffer
Stores how far each pixel is from the camera. When there are multiple objects drawn on the screen, helps determine which objects should appear. 

Example if object A has a depth value greater than object B and they should appear on the same pixel, show object B
## Texture Mapping

Technique used to add colours, patterns, roughness to a 3D object. 3D geometry is not modified, 2D images (called textures) are mapped onto the surface of those 3D objects.

Texels are the individual pixels of a texture, and they are specified in terms of texture coodinates

A texture map associates a texel with a cooresponding vertex on the object

### Linear Texture Mapping

Only vertex have a cooresponding texel. So pixels within a triangle have their textures interpolated using the 3 vertexes. In linear mapping, this is done smoothly across the triangle

### Magnification/Minification

Magnification occurs when the texture is displayed larger on the screen that its actual size. For example, when an object is close to the camera, the texels need to be stretched across many more pixels on the screen

Minification occurs when the texture is displayed smaller on the screen that its original size. For example, when a object is very far from the camera. 

### Mipmaps

Provides pre-computed lower-resolution versions of textures. Start with original texture, and each subsequent version is a scaled down version of the texture. 

Now when rendering a far away object, select the appropriate version of the texture
### Bump Mapping

Creates illusion of a complex surface without increasing geometric complexity. Does this by transforming surface normals on a surface. In classical approach a texture is supplied, where each texel represents height from the surface
### Normal Mapping

Similar to bump mapping, except each pixel of the 2D images encodes a normal vector for the particular vector. Can do this as each pixel has an RGB value, which cooresponds to the X,Y and Z components of a normal
- Red represents X-axis
- Green represents Y-axis
- Blue represents Z-axis

## Clipping

Process of removing or getting rid of part of primitives that lie outside a certain region (usually the camera viewport). No point in perform computation on polygons that wont be dispalyed

### Lines
![[Pasted image 20251101202023.png]]
Different scenaios are 
- line is fully within viewbox
- line is fully outside viewbox
- line is partially inside viewbox

The first 2 scenarios are simple to deal with. However, the partial case requires us to shorten the line.
#### Cohen-Sutherland algorithm

Calculating interesections requires floating point division, when grahpics technqieus were devised, computers were slow at performing this. A much faster appraoch is to use bit operations and FP subtraction

Can do this by dividing viewpoint coords into 9 different regions
![[Pasted image 20251101202428.png]]
The middle section (0000) represents the viewbox, and the regions left/right, above/below that represent what part of the image is outside the region. Example top left corner (1001) means that the line is left of the $x_{min}$ and above $y_{max}$ 

1. If both endpoints are inside, then both points will have a bit value of 0, therefore the entire line segment is accepted.
2. If only one of the endpoints is inside the viewbox, then one of the points will have a bit value not equal to 0, so we need to calculate a new point. Can do this by calculating the intersection with the cooresponding image edge (nonzero outcode tells us which image edge is crossed)
3. If the entre line segment is outside, both points will not be equal to 0, and therefore, we can discard the entire line segment 
4. Fourth case is where both endpoints are outsdie of different edges, therefore a portion of the line is still in the box. Therefore, we need to either reject the line or shorten it.
![[Pasted image 20251101202938.png]]

### Polygons

Much easier to perform clipping with triangles, so complex non-triangle polygons are usually tesselated into triangles first. 

### Clipping in 3D

Clipping can also be done in 3 dimensions, we just need more bits to represent all the different outcodes. 

## Rasterisation

Process or converting triangle or other polygons into actual pixels to be drawn on a device. Involves looping over all objects and vertices to transform them, looping over all pixels to determine their value

- Object oriented approach to rendering. Render one object at a time. doesnt account well for reflections etc.
- Image oriented appraoch involves starting with pixels, and following a scan line. For each pixel, we determine what objects affect its value.

Fragment is a potential pixel, determined by pixel coords and depths. Can be multiple fragments per pixel, so need to determine which one to actually display.

### DDA Line Drawing Algorithm

Stands for Digital Differential Analyser

Algorithm for drawing pixels
![[Pasted image 20251101204400.png]]

Essentially works by increment $x$ 1 pixel at a time, and then knowing how much to increment $y$ by. Pixel location is rounded to the nearest integer
$$dy= mdx=m$$ 
Calculating delta y doesn't work for very large gradients. 
![[Pasted image 20251101204602.png]]
Therefore, if $-1\leq m\leq 1$ , we can use this. Otherwise, we should swap the roles of x and y when $|m|\gt 1$       
### Bresenham's Algorithm

DDA is good, but requires floating-point addition for every pixel, which we do not want. Bresenham on the other hand only uses integer operations. 

To do this, we assume pixel centres are half-way between integers. Eg, assume one pixel has a centre of 3.5, and the one above it has a centre of 4.5, then the boundary between these 2 pixels has a value of 4.

Since the gradient is between 0 and 1, with every increment of x, we only need to consider either going exactly to the right of the current pixel, or the one above the right pixel.

Every time we increase x, want to increase y by m. So we can keep track of the total fractional update by at every time 
- d <- d + m

Once $d\gt \frac{1}{2}$ , the line should move up by 1 pixel, and we should reset d.

### Polygon Filling

Need to draw pixels that are inside a polygon, so we need a way to figure out whether a pixel is inside a polygon or not. 

#### Odd Even Test

Method of determing which pixels are inside by counting how many times a line from the pixel to infitnity cross a polygon edge. 

- Odd number of crossings - pixel is inside (fill it)
- Even number of crossings - pixel is outside (don't fill it)

Usually for each row of pixels, go pixel by pixel, with a flag of whether we have crossed an even or odd number of edges. Every time we cross an edge, switch between inside and outside

### Winding Number Test

Counts how many times a point is encircled, point is consider inside when number of encirclements is non-zero. 

- For a given point, walk around the polygon edges, for a counter clockwise move around the point, add 1.
- Every time you go clockwise around the point, subtract 1

After going around the polygon completely, net number of turns tells you if the point is enclosed.

If the polygon doesn't enclose the point, net number of rotation is not zero, if it does, net rotations is zero.

Being 'inside' the polygon is equivalent to being encircled by the polygon one or more times. 

### Hidden surface removal

For the object-centred calculation. when 2 objects obscure each other, need to figure out which pixels represent which objects. Since we need to check each object against each object, this is $O(k^2)$, so it only works when number of primitives is small 

Image centred appraoch scales with image resolution and the number of objects. Basically, for each pixel, cast a ray through it and see what it sees
![[Pasted image 20251101214932.png]]

### Back-face removal

Back-facing polygons usually aren't visible. So skipping them can half the number of fragments. 

Can figure out whether its visible by calculating dot product between normal to polygon and view vector. If angle is less than 90, then its visible, if its above 90, its not
![[Pasted image 20251101215148.png]]

### z-buffer algorithm

Keep a 2D integer buffer with same resolution as display. Intialised to max depth and some default colour

Rasterise one polygon at a time and evaluate each pixel, for each pixel if new depth is less than old one, set pixel value to current fragment colour, otherwise, ignore current fragment. 
#### Incremental z-buffer algorithm

Computing z-depth for every pixel independetly can be expensive, incremental z-buffer reduces computation by using interpolation as you move across the polygon. 

Basically, compute the depth for the first pixel at a scanline, then incremently update z for each next pixel using derivatives. 
### Painter's algorithm

Alternative to z-buffer.

Analogous to how painters paint their potraits, start by painting stuff at the back first, like background, sky etc. 

So this requires us to sort the polygons then render objects back to front. Since sorting is required, only makes sense when the number of objects is small. 

## Hierarchical models

Representing an object in term of a collection of parts. Example, a car containing a chassis, and in turn the chassis containing four wheels

Can use graphs such as DAGs to model this. Where nodes are the object parts, and edges represent the relationship between the parts.

### Scene Graphs

A blueprint on how to draw the entire scene, contains lighting, camera, object, transformation information

### Constructive Solid Geometry

Technique for building complex 3D shapes by combining smaller similar ones using boolean operations. Start with simple primitive shapes such as cubes shperes etc. Then can use set operations to combine them to make more complex shapes. 
![[Pasted image 20251108230354.png]]

### Binary Space Partition Tree

Method of describing the spatial relationships between different objects

Method of describing the spatial relationships between different objects. Can be used to quickly decide which objects are visible by the camera. Concept is that you can choose an object to be a plane, and then all objects in front of that will go in the left sub-tree, and all objects behind it will go in the right sub-tree. 

### Quad/Oct Trees

Quadtree is used for 2D space partitioning, where each node represents a square region, node is then subdividied into 4 equal quadrants
- top-left
- top-right
- bottom-left
- bottom-right

Keep on recursively dividing until each reigion is simple enough, such as only containing 1 object

Octtree is an extension of a Quadtree but it is used for 3D space partitioning.