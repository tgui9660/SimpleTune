package com.ecm.graphics.exampleCode;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
import java.applet.Applet;

import javax.media.j3d.*;
import javax.vecmath.*;

import com.sun.j3d.utils.applet.MainFrame;
import com.sun.j3d.utils.universe.SimpleUniverse;
import com.sun.j3d.utils.behaviors.vp.OrbitBehavior;

/* Stars: simple animation
 *
 * @author Kevin Duling
 */
public final class Stars
  extends Applet
{
  private SimpleUniverse u = null;

  /* Default constructor.  Here we create the universe.
   */
  public Stars()
  {
    setLayout(new BorderLayout());
    Canvas3D canvas = createCanvas();
    add("Center", canvas);
    u = new SimpleUniverse(canvas);
    BranchGroup scene = createContent();
	 u.getViewingPlatform().setNominalViewingTransform();  // back away from object a little
    OrbitBehavior orbit = new OrbitBehavior(canvas);
    orbit.setSchedulingBounds(new BoundingSphere(new Point3d(0.0, 0.0, 0.0), Double.POSITIVE_INFINITY));
    u.getViewingPlatform().setViewPlatformBehavior(orbit);
    scene.compile();
    u.addBranchGraph(scene);
  }

  /* Create a canvas to draw the 3D world on.
   */
  private Canvas3D createCanvas()
  {
    GraphicsConfigTemplate3D graphicsTemplate = new GraphicsConfigTemplate3D();
    GraphicsConfiguration gc1 =
      GraphicsEnvironment.getLocalGraphicsEnvironment()
        .getDefaultScreenDevice().getBestConfiguration(graphicsTemplate);
    return new Canvas3D(gc1);
  }

  /* Fill your 3D world with content
   */
  private BranchGroup createContent()
  {
    BranchGroup objRoot = new BranchGroup();

	 // Create a triangle with each point a different color.  Remember to
	 // draw the points in counter-clockwise order.  That is the default
	 // way of determining which is the front of a polygon.
	 //        o (1)
	 //       / \
	 //      /   \
	 // (2) o-----o (0)
	 Shape3D shape = new Shape3D();
	 TriangleArray tri = new TriangleArray(3, TriangleArray.COORDINATES|TriangleArray.COLOR_3);
	 tri.setCoordinate(0, new Point3f(0.5f, 0.0f, 0.0f));
	 tri.setCoordinate(1, new Point3f(0.0f, 0.5f, 0.0f));
	 tri.setCoordinate(2, new Point3f(-0.5f, 0.0f, 0.0f));
	 tri.setColor(0, new Color3f(1.0f, 0.0f, 0.0f));
	 tri.setColor(1, new Color3f(0.0f, 1.0f, 0.0f));
	 tri.setColor(2, new Color3f(0.0f, 0.0f, 1.0f));

	 // Because we're about to spin this triangle, be sure to draw
	 // backfaces.  If we don't, the back side of the triangle is invisible.
	 Appearance ap = new Appearance();
	 PolygonAttributes pa = new PolygonAttributes();
	 pa.setCullFace(PolygonAttributes.CULL_NONE);
	 ap.setPolygonAttributes(pa);
	 shape.setAppearance(ap);

	 // Set up a simple RotationInterpolator
	 BoundingSphere bounds = new BoundingSphere(new Point3d(0.0, 0.0, 0.0), 5.0);
	 TransformGroup tg = new TransformGroup();
	 Transform3D yAxis = new Transform3D();
	 Alpha rotationAlpha = new Alpha(-1, 4000);
	 tg.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
	 RotationInterpolator rotator =
		 new RotationInterpolator(rotationAlpha, tg, yAxis,
				                    0.0f, (float)Math.PI * 2.0f);
	 rotator.setSchedulingBounds(bounds);

	 shape.setGeometry(tri);
	 tg.addChild(rotator);
	 tg.addChild(shape);
	 objRoot.addChild(tg);

	 objRoot.addChild(createBackGraph());

    return objRoot;
  }

  /* "My god...it's full of stars!"
	* Create a background of Stars
	*/
  private Background createBackGraph()
  {
    Background background = new Background();
    final BoundingSphere infiniteBounds = new BoundingSphere(new Point3d(0.0, 0.0, 0.0), Double.MAX_VALUE);
    background.setApplicationBounds(infiniteBounds);
    BranchGroup bg = new BranchGroup();

    final java.util.Random rand = new java.util.Random();
    PointArray starfield = new PointArray(20000, PointArray.COORDINATES | PointArray.COLOR_3);
    float[] point = new float[3];
    float[] brightness = new float[3];
    for (int i = 0; i < 20000; i++)
    {
      point[0] = (rand.nextInt(2) == 0) ? rand.nextFloat() * -1.0f : rand.nextFloat();
      point[1] = (rand.nextInt(2) == 0) ? rand.nextFloat() * -1.0f : rand.nextFloat();
      point[2] = (rand.nextInt(2) == 0) ? rand.nextFloat() * -1.0f : rand.nextFloat();
      starfield.setCoordinate(i, point);
      final float mag = rand.nextFloat();
      brightness[0] = mag;
      brightness[1] = mag;
      brightness[2] = mag;
      starfield.setColor(i, brightness);
    }
    bg.addChild(new Shape3D(starfield));
    
    background.setGeometry(bg);
    return background;
  }
    
  /* This is our entrypoint to the application.  This code is not
   * called when the program runs as an applet.
	*/
  public static void main(String args[])
  {
    // MainFrame allows an applet to run as an application
    Frame frame = new MainFrame(new Stars(), 320, 280);
    // Put the title in the application titlebar.  The titlebar
    // isn't visible when running as an applet.
    frame.setTitle("My god...it's full of stars!");
  }
}
