package logger.impl.gui.layout;

import java.awt.*;

/**
 * BetterFlowLayout works just like FlowLayout, except that
 * you can specify a vertical orientation in addition to the
 * usual horizontal orientations.  You can also specify that
 * all the components be sized to the same height and/or width.
 * By default, the behavior is identical to FlowLayout.
 *
 * @author michael@mpowers.net
 * @author $Author: chochos $
 * @version $Revision: 1.2 $
 * $Date: 2003/08/06 23:07:52 $
 */
public class BetterFlowLayout extends FlowLayout {

    /**
     * This value indicates vertical orientation and
     * that each column of components should be top-justified.
     */
    public static final int TOP 	= 32;

    /**
     * This value indicates vertical orientation and
     * that each column of components should be centered.
     */
    public static final int CENTER_VERTICAL	= 16;

    /**
     * This value indicates vertical orientation and
     * that each column of components should be bottom-justified.
     */
    public static final int BOTTOM 	= 8;

    /**
     * Tracks orientation.
     */
    protected boolean isHorizontal = true;

    /**
     * Tracks component sizing of width.
     */
    protected boolean isWidthUniform = false;
    /**
     * Tracks component sizing of height.
     */
    protected boolean isHeightUniform = false;

    /**
     * Constructs a new Flow Layout with a centered alignment and a
     * default 5-unit horizontal and vertical gap.
     */
    public BetterFlowLayout() {
	this(CENTER, 5, 5);
    }

    /**
     * Constructs a new Flow Layout with the specified alignment and a
     * default 5-unit horizontal and vertical gap.
     * The value of the alignment argument must be one of
     * <code>BetterFlowLayout.LEFT</code>, <code>BetterFlowLayout.RIGHT</code>,
     * or <code>BetterFlowLayout.CENTER</code>.
     * @param align the alignment value
     */
    public BetterFlowLayout(int align) {
	this(align, 5, 5);
    }

    /**
     * Creates a new flow layout manager with the indicated alignment
     * and the indicated horizontal and vertical gaps.
     * <p>
     * The value of the alignment argument must be one of
     * <code>BetterFlowLayout.LEFT</code>, <code>BetterFlowLayout.RIGHT</code>,
     * or <code>BetterFlowLayout.CENTER</code>.
     * @param      align   the alignment value.
     * @param      hgap    the horizontal gap between components.
     * @param      vgap    the vertical gap between components.
     */
    public BetterFlowLayout(int align, int hgap, int vgap) {
    	setHgap(hgap);
	    setVgap(vgap);
        setAlignment(align);
    }

    /**
     * Sets whether all components should have the same height.
     * @param isUniform the new value.
     * @see #isHeightUniform
     */
    public void setHeightUniform(boolean isUniform) {
        isHeightUniform = isUniform;
    }

    /**
     * Sets whether all components should have the same width.
     * @param isUniform the new value.
     * @see #isWidthUniform
     */
    public void setWidthUniform(boolean isUniform) {
        isWidthUniform = isUniform;
    }

    /**
     * Determines whether all components will have the same height.
     * The uniform height will be the maximum of the preferred heights
     * of all the components in the container.
     * This value defaults to false.
     * @return whether components will have the same height.
     */
    public boolean isHeightUniform() {
        return isHeightUniform;
    }

    /**
     * Determines whether all components will have the same width.
     * The uniform height will be the maximum of the preferred widths
     * of all the components in the container.
     * This value defaults to false.
     * @return whether components will have the same width.
     */
    public boolean isWidthUniform() {
        return isWidthUniform;
    }

    /**
     * Sets the alignment for this layout.
     * Possible values for horizontal orientation are <code>LEFT</code>,
     * <code>RIGHT</code>, and <code>CENTER</code>.
     * Possible values for vertical orientation are <code>TOP</code>,
     * <code>BOTTOM</code>, and <code>CENTER_VERTICAL</code>.
     * @param      align the alignment value.
     * @see        java.awt.FlowLayout#getAlignment
     */
    public void setAlignment(int align) {
        if ( ( align == TOP ) || ( align == BOTTOM ) || ( align == CENTER_VERTICAL ) )
        {
            isHorizontal = false;
        }
        else
        {
            isHorizontal = true;
        }

        super.setAlignment( align );
    }

    /**
     * Returns the preferred dimensions for this layout given the components
     * in the specified target container.
     * @param target the component which needs to be laid out
     * @return    the preferred dimensions to lay out the
     *                    subcomponents of the specified container.
     * @see Container
     * @see #minimumLayoutSize
     * @see       java.awt.Container#getPreferredSize
     */
    public Dimension preferredLayoutSize(Container target) {
        if ( isHorizontal ) {
            return preferredLayoutSizeHorizontal( target );
        } else {
            return preferredLayoutSizeVertical( target );
        }
    }

    /**
     * Returns the preferred dimensions for this layout given the components
     * in the specified target container.
     * @param target the component which needs to be laid out
     * @return    the preferred dimensions to lay out the
     *                    subcomponents of the specified container.
     * @see Container
     * @see #minimumLayoutSize
     * @see       java.awt.Container#getPreferredSize
     */
    public Dimension preferredLayoutSizeHorizontal(Container target) {
      synchronized (target.getTreeLock()) {
        Dimension dim = new Dimension(0, 0);
        int nmembers = target.getComponentCount();
        int maxWidth = 0;

        for (int i = 0 ; i < nmembers ; i++) {
            Component m = target.getComponent(i);
            if (m.isVisible()) {
            Dimension d = m.getPreferredSize();
            dim.height = Math.max(dim.height, d.height);
            maxWidth = Math.max(maxWidth, d.width);
            if (i > 0) {
                dim.width += getHgap();
            }
            dim.width += d.width;
            }
        }
        if ( isWidthUniform )
            dim.width = ( maxWidth + getHgap() ) * nmembers - getHgap();
        Insets insets = target.getInsets();
        dim.width += insets.left + insets.right + getHgap()*2;
        dim.height += insets.top + insets.bottom + getVgap()*2;
        return dim;
      }
    }

    /**
     * Returns the preferred dimensions for this layout given the components
     * in the specified target container.
     * @param target the component which needs to be laid out
     * @return    the preferred dimensions to lay out the
     *                    subcomponents of the specified container.
     * @see Container
     * @see #minimumLayoutSize
     * @see       java.awt.Container#getPreferredSize
     */
    public Dimension preferredLayoutSizeVertical(Container target) {
      synchronized (target.getTreeLock()) {
        Dimension dim = new Dimension(0, 0);
        int nmembers = target.getComponentCount();
        int maxHeight = 0;

        for (int i = 0 ; i < nmembers ; i++) {
            Component m = target.getComponent(i);
            if (m.isVisible()) {
            Dimension d = m.getPreferredSize();
            dim.width = Math.max(dim.width, d.width);
            maxHeight = Math.max(maxHeight, d.height);
            if (i > 0) {
                dim.height += getVgap();
            }
            dim.height += d.height;
            }
        }
        if ( isHeightUniform )
            dim.height = ( maxHeight + getVgap() ) * nmembers - getVgap();
        Insets insets = target.getInsets();
        dim.width += insets.left + insets.right + getHgap()*2;
        dim.height += insets.top + insets.bottom + getVgap()*2;
        return dim;
      }
    }

    /**
     * Returns the minimum dimensions needed to layout the components
     * contained in the specified target container.
     * @param target the component which needs to be laid out
     * @return    the minimum dimensions to lay out the
     *                    subcomponents of the specified container.
     * @see #preferredLayoutSize
     * @see       java.awt.Container
     * @see       java.awt.Container#doLayout
     */
    public Dimension minimumLayoutSize(Container target) {
        // preferred size is also the minimum size
        if ( isHorizontal ) {
            return preferredLayoutSizeHorizontal( target );
        } else {
            return preferredLayoutSizeVertical( target );
        }
    }

    /**
     * Lays out the container. This method lets each component take
     * its preferred size by reshaping the components in the
     * target container in order to satisfy the constraints of
     * this <code>BetterFlowLayout</code> object.
     * @param target the specified component being laid out.
     * @see Container
     * @see       java.awt.Container#doLayout
     */
    public void layoutContainer(Container target) {
        if ( isHorizontal ) {
            layoutContainerHorizontal( target );
        } else {
            layoutContainerVertical( target );
        }
    }

    /**
     * Lays out the container. This method lets each component take
     * its preferred size by reshaping the components in the
     * target container in order to satisfy the constraints of
     * this <code>BetterFlowLayout</code> object.
     * @param target the specified component being laid out.
     * @see Container
     * @see       java.awt.Container#doLayout
     */
    protected void layoutContainerHorizontal(Container target) {
      synchronized (target.getTreeLock()) {
        Insets insets = target.getInsets();
        int maxwidth = target.getSize().width - (insets.left + insets.right + getHgap()*2);
        int nmembers = target.getComponentCount();
        int x = 0, y = insets.top + getVgap();
        int rowh = 0, start = 0;

        boolean ltr = true; // target.getComponentOrientation().isLeftToRight();
        Dimension uniform = getUniformDimension( target );

        for (int i = 0 ; i < nmembers ; i++) {
            Component m = target.getComponent(i);
            if (m.isVisible()) {
                Dimension d = m.getPreferredSize();
                if ( isWidthUniform )
                    d.width = uniform.width;
                if ( isHeightUniform )
                    d.height = uniform.height;
                m.setSize(d.width, d.height);

                if ((x == 0) || ((x + d.width) <= maxwidth)) {
                    if (x > 0) {
                        x += getHgap();
                    }
                    x += d.width;
                    rowh = Math.max(rowh, d.height);
                } else {
                    moveComponentsHorizontal(target, insets.left + getHgap(), y, maxwidth - x, rowh, start, i, ltr);
                    x = d.width;
                    y += getVgap() + rowh;
                    rowh = d.height;
                    start = i;
                }
            }
        }
        moveComponentsHorizontal(target, insets.left + getHgap(), y, maxwidth - x, rowh, start, nmembers, ltr);
      }
    }

    /**
     * Centers the elements in the specified row, if there is any slack.
     * @param target the component which needs to be moved
     * @param x the x coordinate
     * @param y the y coordinate
     * @param width the width dimensions
     * @param height the height dimensions
     * @param rowStart the beginning of the row
     * @param rowEnd the the ending of the row
     */
    private void moveComponentsHorizontal(Container target, int x, int y, int width, int height,
                                int rowStart, int rowEnd, boolean ltr) {
      synchronized (target.getTreeLock()) {
	switch (getAlignment()) {
	case LEFT:
	    x += ltr ? 0 : width;
	    break;
	case CENTER:
	    x += width / 2;
	    break;
	case RIGHT:
	    x += ltr ? width : 0;
	    break;
//1.2	case LEADING:
//1.2	    break;
//1.2	case TRAILING:
//1.2	    x += width;
//1.2	    break;
	}
	for (int i = rowStart ; i < rowEnd ; i++) {
	    Component m = target.getComponent(i);
	    if (m.isVisible()) {
	        if (ltr) {
        	    m.setLocation(x, y + (height - m.getBounds().height) / 2);
	        } else {
	            m.setLocation(target.getBounds().width - x - m.getBounds().width, y + (height - m.getBounds().height) / 2);
            }
                x += m.getBounds().width + getHgap();
	    }
	}
      }
    }

    /**
     * Lays out the container. This method lets each component take
     * its preferred size by reshaping the components in the
     * target container in order to satisfy the constraints of
     * this <code>BetterFlowLayout</code> object.
     * @param target the specified component being laid out.
     * @see Container
     * @see       java.awt.Container#doLayout
     */
    protected void layoutContainerVertical(Container target) {
      synchronized (target.getTreeLock()) {

        Insets insets = target.getInsets();
        int maxheight = target.getBounds().height - (insets.top + insets.bottom + getVgap()*2);
        int nmembers = target.getComponentCount();
        int y = 0, x = insets.left + getHgap();
        int colw = 0, start = 0;

        Dimension uniform = getUniformDimension( target );
        for (int i = 0 ; i < nmembers ; i++) {
            Component m = target.getComponent(i);
            if (m.isVisible()) {
                Dimension d = m.getPreferredSize();
                if ( isWidthUniform )
                    d.width = uniform.width;
                if ( isHeightUniform )
                    d.height = uniform.height;
                m.setSize(d.width, d.height);

                if ((y == 0) || ((y + d.height) <= maxheight)) {
                    if (y > 0) {
                    y += getVgap();
                    }
                    y += d.height;
                    colw = Math.max(colw, d.width);
                } else {
                    moveComponentsVertical(target, x, insets.top + getVgap(), colw, maxheight - y, start, i );
                    y = d.height;
                    x += getHgap() + colw;
                    colw = d.width;
                    start = i;
                }
            }
        }
        moveComponentsVertical(target, x, insets.top + getVgap(), colw, maxheight - y, start, nmembers );
      }
    }

    /**
     * Centers the elements in the specified row, if there is any slack.
     * @param target the component which needs to be moved
     * @param x the x coordinate
     * @param y the y coordinate
     * @param width the width dimensions
     * @param height the height dimensions
     * @param colStart the beginning of the column
     * @param colEnd the the ending of the column
     */
    private void moveComponentsVertical(Container target, int x, int y, int width, int height,
                                int colStart, int colEnd) {
      synchronized (target.getTreeLock()) {
        switch (getAlignment()) {
        case TOP:
            y += 0;
            break;
        case CENTER_VERTICAL:
            y += ( height / 2 ); // - preferredLayoutSize( target ).height ) / 2 );
            break;
        case BOTTOM:
            y += height;
            break;
        }
        for (int i = colStart ; i < colEnd ; i++) {
            Component m = target.getComponent(i);
            if (m.isVisible()) {
                m.setLocation(x + (width - m.getBounds().width) / 2, y );
//                m.setLocation(x, y );
//                m.setSize( width, m.getBounds().height ); //!
                y += m.getBounds().height + getVgap();
            }
        }
      }
    }

    /**
     * Returns a dimension representing the maximum preferred
     * height and width of all the components in the container.
     * @param target the container to scan.
     * @return a dimension containing the maximum values.
     */
    protected Dimension getUniformDimension(Container target) {
        Component m = null;
        Dimension preferred = null;
        int maxWidth = 0, maxHeight = 0;
        int nmembers = target.getComponentCount();
        for ( int i = 0; i < nmembers; i++ ) {
            m = target.getComponent( i );
            if ( m.isVisible() ) {
                preferred = m.getPreferredSize();
                maxWidth = Math.max( maxWidth, preferred.width );
                maxHeight = Math.max( maxHeight, preferred.height );
            }
        }
        return new Dimension( maxWidth, maxHeight );
    }

    /**
     * Returns a string representation of this <code>BetterFlowLayout</code>
     * object and its values.
     * @return     a string representation of this layout.
     */
    public String toString() {
	String str = "";
	switch (getAlignment()) {
	  case TOP:        str = ",align=top"; break;
	  case CENTER_VERTICAL:      str = ",align=vertical"; break;
	  case BOTTOM:       str = ",align=bottom"; break;
      default: return super.toString();
	}
	return getClass().getName() + "[hgap=" + getHgap() + ",vgap=" + getVgap() + str + "]";
    }


}

