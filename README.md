# SVG Editor

## Group Members

| Name                 | Student Credential |
| -------------------- | ------------------ |
| Xila Cai             | 210254             |
| Jorge García Plaza   | 200002             |
| Julia Serrano Arrese | 200119             |

## Installation and evaluation
```bash
git clone git@costa.ls.fi.upm.es:210254/svg-editor.git
mvn test
mvn clean verify sonar:sonar -Dsonar.id=200119 -Dsonar.login=squ_f7fc8e22943a014173b3b496b33603e3dae031e9
```

## Open GUI of the application
```bash
mvn exec:java
```

## System Functionalities

### 1-Create a New Empty Image
The editor allows users to create a new empty image by defining its dimensions, that is, its width and height.

### 2-Change the size of the image
The editor allows users to resize the image by defining its dimensions, that is, its width and height.

### 3-Change the background color of the image
The editor allows users to change the background color of the image through a palette of colors.

### 4-Create different figures on the working file by using the mouse or the keyboard
The editor allows users to create the following figures:

- **Rectangle** 
- **Circle** 
- **Ellipse** 
- **Line** 
- **Polyline** 
- **Polygon** 
- **Path** 

### 5-Change the style of the figure
The editor allows users to style of the figure managing the following properties:

- **Fill color** 
- **Fill opacity** 
- **Line color** 
- **Line width** 
- **Line opacity** 

### 6-Figures can be selected 
The editor allows users to select figures.

### 7-Figures can be moved 
The editor allows users to mode figures.

### 8-Show the figure selected 
The editor show the figure selected.

### 9-Selected figures can be removed 
The editor allows users to remove figures.

### 10-Multiple figures can be selected at a time
The editor allows users to select multiple figures.

### 11-Figures can be grouped on a group
The editor allows users to group multiple figures.

### 12-Figures on a group can be moved as if they were a single figure
The editor allows users to move groups of figures.

### 13-Groups cannot be nested and a figure cannot belong to multiple groups
The editor doesn't allow users to nest groups of figures.

### 14-Figures can be dissolved of a group
The editor allows users to dissolved groups of figures.

### 15-Figures can be saved in SVG format
The editor allows users to saved figures in SVG format.

### 16-SVG files can be loaded by the application
The editor allows users to load figures in SVG format.

### 17-All operations can be undone
The editor allows users to undone operations.



## File Information

### File Storage
- The application reads and writes files in the SVG format.
- Files can be stored locally on the user's device.

### Supported File Formats
- **Read:** The application can read `.svg` files.
- **Write:** The application can save or export images in the `.svg` format.

## Controls to Use the Application

### File button

 **Create New Image:** 

  - Click on the button to create a new image.
  - Enter the width and height then press OK.
  
 **Open Image:**
 
  - Click on the button to open a new image.
  - Browse through the directories on your PC and load the SVG file you want to view in the app.
  - Press OPEN.
  
 **Save Image:**
 
  - Click on the button to save a new image.
  - Browse through the directories on your PC and save the SVG file where you want.
  - Press SAVE.
  
 **Exit application:**
 
  - Click on the button to close the applicaction.
  

### Edit button

 **Resize Image:** 

  - Click on the button to resize the image.
  - Enter the new width and height then press OK.
  
 **Change background color Image:**
 
  - Click on the button to open the palette of colors.
  - Select the new color and press OK.
  
 **Undo:**
 
  - Press on this button to undo the last operation.


### Add shape button

 **Rectangle:** 

  - 1.- Press on the button to draw by mouse and then click and drag.
  - 2.- Press on the button to draw by keyboard enter the parameters X,Y,width and height.
    And finally press OK to draw the figure.
  
  
 **Circle:**
 
  - 1.- Press on the button to draw by mouse and then click and drag.
  - 2.- Press on the button to draw by keyboard enter the parameters X,Y,ad radius.
    And finally press OK to draw the figure.
  
 **Ellipse:**
 
  - 1.- Press on the button to draw by mouse and then click and drag.
  - 2.- Press on the button to draw by keyboard enter the parameters X,Y,width and height.
    And finally press OK to draw the figure.
  
 **Line:**
 
  - 1.- Press on the button to draw by mouse and then click and drag.
  - 2.- Press on the button to draw by keyboard enter the parameters X1,Y1,X2 and XY.
    And finally press OK to draw the figure.

 **Polygon:**
 
  - 1.- Press on the button to draw by mouse and then click to draw lines and doble click to close the figure.
  - 2.- Press on the button to draw by keyboard enter the parameters X,Y and then click on add Vertex.
    Once you have entered all the vertices, confirm the figure with the OK button.

 **Path:**
 
  - 1.- Press on the button to draw by mouse and then click to draw the figure drag the mouse and once you raise your hand from the mouse the figure will be drawn.
  - 2.- Press on the button to draw by keyboard enter the parameters X,Y and then click on add points.
    Once you have entered all the vertices, confirm the figure with the OK button.

 **Polyline:**
 
  - 1.- Press on the button to draw by mouse and then click to draw lines and doble click to confirm the figure.
  - 2.- Press on the button to draw by keyboard enter the parameters X,Y and then click on add points.
    Once you have entered all the vertices, confirm the figure with the OK button.

### Tools button

 **Select figure:** 

  - Click on the button and then click on the figure.
  - To select multiples figures same process + CTRL + LEFT CLICK.
  - Once you select the figure then you can move using the mouse.
  
 **Fill color:**
 
  - First click on the select button to select the figure.
  - Select the new color and press OK.
  
 **Fill opacity:**
 
  - First click on the select button to select the figure.
  - Select the opacity level via the bar and press OK.
  
 **Line color:**
 
  - First click on the select button to select the figure.
  - Select the new color and press OK.
  
 **Line width:**
 
  - First click on the select button to select the figure.
  - Select the new line with introducing the number.

 **Stroke opacity:**
 
  - First click on the select button to select the figure.
  - Select the opacity level via the bar and press OK.
  
 **Delete:**
 
  - First click on the select button to select the figure.
  - Then press on the button to delete the figure.

 **Group:**
 
  - First click on the select button to select the figure.
  - To select multiples figures same process + CTRL + LEFT CLICK.
  - And finaly press the button to group the figures.

 **Ungroup:**
 
  - Press on the button to ungroup the figures.
  
  

 
 
  

  

