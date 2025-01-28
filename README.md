<a id="readme-top"></a>

<!-- HEADER -->
<div align="center">
  <h3 align="center">WaterPipes - Game</h3>
  <p align="center">
    Interactive puzzle game built with Java.
  </p>
</div>

<!-- ABOUT THE PROJECT -->
## About The Project

**WaterPipes** is a puzzle game where players connect the start and the end point on a grid by correctly rotating randomly oriented pipes. The game progresses through levels with increasing difficulty and allows customization of the grid size.

## Key Features

### Gameplay
  - **Objective:** The goal of the game is to connect the start and end points by rotating pipes to form a valid path.
  - **Interaction:** 
    - **Hover:** Move the mouse over the grid to highlight cells.
    - **Rotate Pipes:** Click on a pipe to rotate it and adjust its orientation.
  - **Path Checking:** Press `ENTER` or click the "Check" button to verify if the path is correct. Correct pipes will be highlighted up to the first error.
  - **Path Progression:** Successfully connect the start with the end to move on to the next level.

### User Interface

1. **Game Area**:
   - A `JPanel` representing the game grid.
   - Pipes are randomized at the start and must be rotated to form a valid connection.
2. **Menu**:
   - Displays:
     - Current level.
     - Grid size.
   - Controls:
     - Slider to adjust grid size (minimum size: 8x8). Resets the game upon change.
     - Reset button.
     - Check button to verify the correctness of the path.

### Features

- **Dynamic Grid:**
  - Default grid size: 8x8.
  - Adjustable grid size (minimum size: 8x8).
  - Algorithmically generated paths using depth-first random traversal.
  - Randomized start and end positions on opposite sides of the grid.
- **Level Progression:**
  - Advance to the next level by completing the current one.
  - Randomly generate new levels after successful completion.
- **Controls:**
  - Mouse for interaction.
  - Keyboard shortcuts:
    - `R`: Reset the game.
    - `ESC`: Exit the game.
    - `ENTER`: Check the correctness of the current path.

<p align="right">(<a href="#readme-top">back to top</a>)</p>

<!-- TOOLS -->
### Built With

* [![Java][Java.com]][Java-url]
* [![Swing][Swing.com]][Swing-url]
* [![AWT][AWT.com]][AWT-url]

<p align="right">(<a href="#readme-top">back to top</a>)</p>

<!-- LINKS -->
[Java.com]: https://img.shields.io/badge/Java-007396?style=for-the-badge&logo=java&logoColor=white
[Java-url]: https://www.java.com/
[Swing.com]: https://img.shields.io/badge/Swing-007396?style=for-the-badge&logo=java&logoColor=white
[Swing-url]: https://docs.oracle.com/javase/7/docs/api/javax/swing/package-summary.html
[AWT.com]: https://img.shields.io/badge/AWT-007396?style=for-the-badge&logo=java&logoColor=white
[AWT-url]: https://docs.oracle.com/javase/7/docs/api/java/awt/package-summary.html


### Created

2023
