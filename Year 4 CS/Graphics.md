Good video for p2
https://www.youtube.com/watch?v=YLHjX2bIOYc&list=PL2935W76vRNHFpPUuqmLoGCzwx_8eq5yK&index=11
## Introduction
* Collection of vertices (points in space)
* Points are combined into primitive shapes (always triangles)
* Light and other surface textures are then applied onto these triangle faces
* Need to decide which pixels should represent which triangle face

## Homogenous Coordinates

Add a fourth coordinate to our 3D world coordinates. For points, we usually add $w=1$, as the fourth point. For directions/vectors we usually add $w=0$ as the fourth point. 
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

Want objects further away to appear smaller. 
![[Pasted image 20251215001354.png]]

The further away an object is from the camera (greater D), then the closer it will be to the center.
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
![[Pasted image 20251214205111.png]]
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
Simplest type of shading. Calculate illumination once per polygon (middle of polygon), all pixels on the polygon will have the same colour. Gets better as we increase the number of polygons
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

Only vertex have a cooresponding texel. So pixels within a triangle have their textures interpolated using the 3 vertexes. In linear mapping, this is done smoothly across the triangle. Let the hardware figure out when performing the rasteriser how points inbetween should be interpolated

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

### Static Shadows

Can perform something called Texture Baking, which is where we pre calculated shadows for only static lights/objects. We can then bake these shadows into something like a texture mapping

### Dynamic Shadows / Shadow Mapping

Essentially, we place a camera at the source of light and point it in the direction it faces. We then figure out what that light source sees. And basically, we store the depth of each surface to figure out which surfaces are closest to the light. We store this as a 2D shadow map. EACH SOURCE OF LIGHT GETS ITS OWN SHADOW MAP

Now when we render the scene from the actual camera, we transform the fragment into light space coords and check what its depth is in the shadow map. If the current fragment depth is greater than the stored depth, means its in a shadow, is its less, it should get lit. 

Multipass rendering as the first pass renders from the light's perspective, and the second pass renders from the camera's perspective. 

If there are multiple light sources, we go into each shadow map and perform this depth comparison individually, summing up the individual contributions to get the overall fragment colour.

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

Method of determing which pixels are inside by counting how many times a line from the pixel to infinity cross a polygon edge. 

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
![[Pasted image 20251214204511.png]]

Basic idea is that if we are in front of the node, then stuff behind the node should be rendered first, followed by the subtree in front of the current node. Since stuff behind is rendered first, stuff rendered in front will appropriately cover anything. 

Similarly if the viewing location is behind the current node, we render stuff infront first, followed by stuff behind. 

### Quad/Oct Trees

Quadtree is used for 2D space partitioning, where each node represents a square region, node is then subdividied into 4 equal quadrants
- top-left
- top-right
- bottom-left
- bottom-right

Keep on recursively dividing until each reigion is simple enough, such as only containing 1 object

Octtree is an extension of a Quadtree but it is used for 3D space partitioning.

### File Formats

.obj format 
![[Screenshot 2025-11-09 at 6.17.01 PM.png]]

Mtl (material template library)

## Fractals

Fractal Dimensions: Measures how detail a fractal is 

### Particle Systems

Common to model particle obeying Newtonian physics, where f = ma and force and acceleration and 3D vectors. The state of each particle is described by its position and velocity, where velocity can be though of as the derivative of position with respect to time. 

### Particle Interactions

Particles are not usually entirely indepedent. They usually depend on the state of other particles, moddeling all pairwise interactions is $O(n^2)$ time complexity. 

Therefore, we usually only consider the effect of closest neighbours
![[Pasted image 20251116194747.png]]

A particle is usually acted on by many foces
- independent forces e.g. Earth's gravity
- local forces extered by a mesh (e.g. elastic fabric stretching)
- distant forced exerted by other particles (e.g. magnetic replusion)

Sum of all these forces will determine the force on the particle

Therefore to calculate the state we need
- Positions of all points $P_i=[x_i y_i z_i]^T$ 
- Velocities of all points $V_i=[v_{xi} v_{yi} v_{zi}]^T$   
- Forces of all points $f_i=[f_{xi} f_{yi} f_{zi}]^T$ 
- Masses of all points $m_i$  

### Solving particle systems

Solving a system of n particles requires solving 6n equations. The 6 parameters are the position and velocity of a particle $$\dot{u}=g(u,t)$$
where u is the initial state of the particle, t is some time delta, and g is a function that outputs $\dot{u}$, which is the new state of the particle. 

u(t+h) can be approximated using Euler's method: $$u(t+h)\approx u(t)+h(g(u(t),t))$$
	![[Pasted image 20251116195602.png]]
## Curves

In graphics, usually represented using parametric representation. The parameter is called $u$ by convention, or $u,v$ for surfaces. $$x=x(u), y=y(u), z=z(u)$$
The parameter $u$ determines position in curve segment. 
In our curve, we want it to be 
- Differentiable at all points
- Smooth
- Easy to specify

### Parametric cubic polynomial curves

Usually use an order of 3 when defining curves as they give desirable properties.
![[Pasted image 20251123220204.png]]

Since the curve has 12 free parameters (elements of c), we can define the curve using 4 3D points.

Best approach is to make the curve pass through 4 points. Place 4 equally spaced points along u (0 to 1)

![[Pasted image 20251123220651.png]]

The interpolating geometry matrix is the matrix that describes the curve that will go through the 4 control points. The interpoloating geometry matrix is the same depending on what type of curve we want. So all we need to do is change which control points we use, and using the same interpoloating geomdtry matrix, it will compute the curve.  
### Joining interpolating segments

To get more complex curves, will have more than 4 control points. Normally done by joining together smaller cubic segments. Use last point of segment as starting point of next. Guarantees continuity of curves at join points, however, does not guarantee continuity of the derivatives.

Could result in sharp corners, which we do not want

### Hermite Curves

Defined by 2 points and 2 derivatives. Usuually smoother since the derivative must match at joins

Ensure continuty between segments. Model the first segment by 2 points p(0) and p(1), second segment by q(0) and q(1). Ensure that curve passes through  start and end points p(0) and p(1). Fix derivatives such that the deriviative of p(1) = q(0)

![[Pasted image 20251123222216.png]]
![[Pasted image 20251123222743.png]]

Conditions:

![[Screenshot 2025-12-09 at 3.25.42 PM.png]]

Hermite geometry matrix is something different.

### Bezier Curve

Type of hermite curve
- fix direction of the deriviative, but not necessarily the magnitude
- define the derivate at start and end points by a straight line between 2 points
![[Pasted image 20251123223335.png]]
Defined by 4 control points
- $P_0$ is the start point
- $P_1$ is the first interior control
- $P_2$ is the second interior control
- $P_3$ is the end point

## Surfaces

Since surfaces are 2D, to define them parametrically, we now are required to use 2 parameters
- u 
- v

All cubic patches (component of a surface) need 48 parameters, as this cooresponds to 16 3D control points.

### Bezier Surface Patch

Similar to bezier curves. However, now we want to approximate the derivatives at each of the 4 corners of a patch.
![[Screenshot 2025-12-09 at 3.49.50 PM.png]]
For each corner, we need to calculate 3 partial derivatives, one going in the $u$ direction, one going in the $v$ direction, and then the final one going in both directions.

Similar to bezier curve, patch will be enclosed by the convex hull defined by the control points. 
![[Screenshot 2025-12-09 at 3.53.48 PM.png]]

### Rendering Curves & Surfaces

Evaludate the curve at some points, calculate the (x,y,z) coordaintes for each of these points. Use these as vertices and build triangles between them 

#### Non-Uniform Rational B-spline (NURBS)

B-spline that has an added 4th w coordinate, w(u,v), taking us into homogenous coordinates. Allows you to now perform perspective matrices without deforming the surfaces

### Creating Surfaces from Data

Scan real objects. Use a depth sensor, where for each x,y coordinate, we get a height data, which represents distance from camera 

### Triangulation

Want to avoid long, thin triangles. 

Uses the Delaunay Triangulation algorithm to perform this. Basically, connect 3 points, draw the circle that goes through all 3 points, if this circle contains any other vertices, need to choose differently. Basically see which vertex is included in that triangle, choose that vertex as your new triangle, kick out other one. 

## Ray Tracing

Send out a ray that goes through a pixel, see what it hits. do this until the ray hits a light source or leaves the clipping volume.

#### Opaque Surfaces

Send out a ray, if it hits an opaque surface, then send out shadow rays where each one leads straight to a light source. If the shadow ray hits another object, drop it. If it hits a light source, calculate illumination from this source.

#### Translucent Surfaces

Surfaces that let light pass through, such as glass and water. 

- Cast ray gets split into 2 components - reflected ray and transmitted ray (light that goes through object)

Transmitted ray may change direction due to refraction of light

### Ray Tree

A single ray can produce many rays. We need to track all of them and see if they are traced to a light source or blocked by a surface. Represent them using a tree that we can traverse 
![[Pasted image 20251209193428.png]]

### Calculating Intersections

Ray tracking requires many intersecting calculations to identify which objects the ray hits. 

Ray can usually be defined as $p(t)=p_0+td$, where $p_0$  is the starting point, and $d$ is some direction vector.

Implicit definition of a surface is very useful here, where $f(x,y,z)=f(p)=0$, therefore, $f(p_0+td)=0$ defines all the intersections between the ray and the surface. Solving this equation involves finding the roots of the equation

Unfortunately, no analytical solution the polynomial surface has a degree of 3. Therefore, ray tracers often use polynomials of degree < 3

### Cubic Polynomial Surface Patches

We want to do ray tracing with these shapes, but they use a polynomial degree >= 3. Therefore, we must use numerical methods to approximate the solutions. 

### Diffuse Surfaces

Ray tracing does not work with diffuse surfaces, because as soon as a ray hits a diffuse surface, there will be loads of feeler rays we will need to send out

### Energy-based model

![[Pasted image 20251209202100.png]]
We can calculate the intensity of light that comes from the point P' at P. The total light emitted from P' might be light if it is a light source, but it could also be reflections from P'

- Attenuation function $v(p,p')$ based on distance
- Emission function $\in(p,p')$ if $p'$ is a light-emitting surface
- Bidirectional reflection distribution function $\rho(p,p',p'')$ 
	- models the light reflected from point $p''$ by $p'$ towards p
	- characterises material surfce properties at p'

Basically, the BRDF tells us how how much light is emitted from P' towards P, where P' includes all the light from other sources

### Radiosity

Instead of looking at each point individually and trying to figure out how much light reaches that point from every other point.

We divide the scene into $n$ diffuse patches, and claculate how much one patch receives from all the other patches. 