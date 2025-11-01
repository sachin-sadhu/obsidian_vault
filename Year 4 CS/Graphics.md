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
## Rasterisation
Fragments are potential pixels. Determined by pixel coordinates and depth.
Need to determine colour (through interpolation, texturing, shading)
### DDA Line Drawing 
Based on the derivate, moving by 1 pixel at a time. Either fill in pixel directly to the right or 1 above as well

Doesn't work well for very steep angles
## Bresenham's Algorithm
DDA is efficient, but requires a floating-point addition. Bresenham algorithm uses only integer operations

At each incremental increase of x, need to decide to either go horizontally or go 1 above as well
