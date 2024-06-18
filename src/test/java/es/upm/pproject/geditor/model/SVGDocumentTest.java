package es.upm.pproject.geditor.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.awt.Color;

import org.junit.jupiter.api.Test;

class SVGDocumentTest {
	
	  @Test
	    void testCreateNewImageWithNegativeValues() {
	        int width = -1200;
	        int height = -800;

	        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
	            new SVGDocument(width, height);
	        });

	        String expectedMessage = "Width and height must be positive values.";
	        String actualMessage = exception.getMessage();

	        assertEquals(expectedMessage, actualMessage);
	    }
	  
	   @Test
	    void testCreateNewImageWithNegativeWidth() {
	        int width = -1200;
	        int height = 800;

	        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
	            new SVGDocument(width, height);
	        });

	        String expectedMessage = "Width and height must be positive values.";
	        String actualMessage = exception.getMessage();

	        assertEquals(expectedMessage, actualMessage);
	    }
	   
	   @Test
	    void testCreateNewImageWithNegativeHeight() {
	        int width = 1200;
	        int height = -800;

	        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
	            new SVGDocument(width, height);
	        });

	        String expectedMessage = "Width and height must be positive values.";
	        String actualMessage = exception.getMessage();

	        assertEquals(expectedMessage, actualMessage);
	    }
	  
	  @Test
	    void testCreateNewImage() {
	        int width = 1200;
	        int height = 800;
	        SVGDocument document = new SVGDocument(width, height);

	        assertNotNull(document);
	        assertEquals(width, document.getWidth());
	        assertEquals(height, document.getHeight());
	    }
	  
	  @Test
	    void testResizeImage() {
	        int width = 1200;
	        int height = 800;
	        SVGDocument document = new SVGDocument(width, height);

	        int newWidth = 1600;
	        int newHeight = 1200;
	        document.setWidth(newWidth);
	        document.setHeight(newHeight);

	        assertEquals(newWidth, document.getWidth());
	        assertEquals(newHeight, document.getHeight());
	    }

	    @Test
	    void testResizeImageWithNegativeValues() {
	        int width = 1200;
	        int height = 800;
	        SVGDocument document = new SVGDocument(width, height);

	        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
	            document.setWidth(-1600);
	        });

	        String expectedMessage = "Width must be a positive value.";
	        String actualMessage = exception.getMessage();
	        assertEquals(expectedMessage, actualMessage);

	        exception = assertThrows(IllegalArgumentException.class, () -> {
	            document.setHeight(-1200);
	        });

	        expectedMessage = "Height must be a positive value.";
	        actualMessage = exception.getMessage();
	        assertEquals(expectedMessage, actualMessage);
	    }
	    
	    
	    @Test
	    void testBackgroundColor() {
	        int width = 1200;
	        int height = 800;
	        SVGDocument document = new SVGDocument(width, height);

	       
	        assertEquals(Color.WHITE, document.getBackgroundColor());

	       
	        Color newBackgroundColor = Color.BLUE;
	        document.setBackgroundColor(newBackgroundColor);
	        assertEquals(newBackgroundColor, document.getBackgroundColor());
	    }
}
