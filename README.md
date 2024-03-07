# Generative AI Final Project 🛰

## Problem Description


The art style that I opted to select for this particular program included creating a unique piece of work that used generative algorithms to create an image that had a unique perspective but still had a number of points that would allow it to retain the original outlook as well as the outline of the initial target image.

The generative method used in this instance involves creating an initial population of images and then evaluating their fitness based on the absolute pixel-wise difference from the original image. The image is then iteratively evolved through the process of crossover and mutation.

## Method

As denoted previously, I used a simple genetic algorithm and the process of evolution to create a unique image. The process of generation is as follows, the main method is used in order to create and instantiate a number of important variables such as the population, mutation rate as well as the number of generations. In essence, at each generation, the program evaluates the fitness of the created image in the population, it then selects the parents based on the fitness and performs crossover as well as mutation to create a new population and in the process replaces the old population with the new one.

## Different Drawing Techniques

The figures below depict the effect two different drawing styles have on the output, both the figures depict an output after 5200 generations with very similar controls attached. 

<img src="https://github.com/anjanak13/GenerativeAI/assets/102757443/0a4b3ff3-d59f-4f69-a13d-0da1050f2941" width="250">

<img src="https://github.com/anjanak13/GenerativeAI/assets/102757443/53115157-a4fe-42d2-a029-d0eb7a288b0b" width="250">

<img src="https://github.com/anjanak13/GenerativeAI/assets/102757443/ca85fbe3-6e25-4b0e-bbe0-237b366aad00" width="250">


## Evolution through generations

The following figures illustrate the progression across generations, with increments of 100 shown in each step

<p align="center">
  <img src="https://github.com/anjanak13/GenerativeAI/assets/102757443/8089a3a9-e94e-46d9-a3b9-3fd042209f5a" width="413">
</p>


