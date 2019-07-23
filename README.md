# complex-functions
A simple Java visualizer for functions with complex inputs and outputs that I wrote in 2016. This project has two main uses: showing a 6-second animation of a function acting on the complex plane, and mapping the complex plane to colors based on modulus (brightness) and argument (hue).
The provided code creates an animation of the function  ![equation1](https://latex.codecogs.com/svg.latex?f%28z%29%3D%281&plus;2i%29%5Ez). The complex plane should warp into something like this:
![func1](https://raw.githubusercontent.com/chrisseiler/complex-functions/master/func1.PNG)
In the main class, there is also a commented-out example that maps the complex plane to colors. The brightness, between 0 and 1, is determined as a proportion of the given number's radius to the maximum radius. Below, the function ![equation2](https://latex.codecogs.com/svg.latex?f%28z%29%3D%5Cleft%28%5Csin%5E%7B-1%7D%28z%5E%7B-1%7D%29%29%5Cright%29%5Ez) is graphed using this technique on an 800 x 500 image:
![func2](https://raw.githubusercontent.com/chrisseiler/complex-functions/master/func2.png)
