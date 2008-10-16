/**
 * 
 */
package org.mazur.algedit.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.geom.Rectangle2D;
import java.net.URL;
import java.util.HashMap;
import java.util.List;

import javax.swing.Icon;
import javax.swing.ImageIcon;

import org.jgraph.JGraph;
import org.jgraph.graph.DefaultEdge;
import org.jgraph.graph.DefaultGraphCell;
import org.jgraph.graph.DefaultGraphModel;
import org.jgraph.graph.DefaultPort;
import org.jgraph.graph.GraphConstants;
import org.mazur.algedit.mili.model.MiliGraphModel;
import org.mazur.algedit.mili.model.MiliTransition;
import org.mazur.algedit.mili.model.MiliVertex;

/**
 * @author Roman Mazur (IO-52)
 *
 */
public class GraphPanel extends ModelPanel<MiliGraphModel> {

	/** serialVersionUID. */
	private static final long serialVersionUID = 418726633138555848L;

	/** Icon. */
	private static Icon icon = null;
	static {
	  URL url = GraphPanel.class.getResource("graph.gif");
	  icon = new ImageIcon(url);
	}
	
	private JGraph jGraph;
	
	public GraphPanel(final MiliGraphModel model) {
	  super(model);
		
	  jGraph = new JGraph();
		jGraph.setModel(new DefaultGraphModel());
		jGraph.setGridEnabled(true);
		jGraph.setSize(new Dimension(300, 300));
		setLayout(new BorderLayout());
		buildGraph(model);
		add(BorderLayout.CENTER, jGraph);
	}

	private void buildGraph(final MiliGraphModel model) {
	  List<MiliVertex> graph = model.getMainObject();
	  Dimension sizes = jGraph.getSize();
	  System.out.println("sizes: " + sizes);
	  int r = sizes.height;
	  if (sizes.width < r && sizes.width > 0) {
	    r = sizes.width;
	  }
	  r /= 2;
	  double delta = 2 * Math.PI / graph.size();
	  double angle = 0;
	  HashMap<MiliVertex, DefaultGraphCell> cellsMap = new HashMap<MiliVertex, DefaultGraphCell>();
		for (MiliVertex mv : graph) {
		  int x = (int)(r * (1 + Math.cos(angle))), y = (int)(r * (1 - Math.sin(angle)));
		  System.out.println(x + " " + y + " " + r);
			DefaultGraphCell cell = new DefaultGraphCell();
			GraphConstants.setBounds(cell.getAttributes(), 
          new Rectangle2D.Double(x, y, 30, 30));
			GraphConstants.setValue(cell.getAttributes(), "Q" + mv.getCode());
			GraphConstants.setOpaque(cell.getAttributes(), true);
      GraphConstants.setBackground(cell.getAttributes(), Color.BLUE);
      GraphConstants.setForeground(cell.getAttributes(), Color.YELLOW);
      Rectangle2D bounds = GraphConstants.getBounds(cell.getAttributes());
      DefaultPort inPort = new DefaultPort(new Point(0, (int)bounds.getWidth() / 2));
      DefaultPort outPort = new DefaultPort(new Point((int)bounds.getWidth() - 1, 
          (int)bounds.getWidth() / 2));
      cell.add(inPort);
      cell.add(outPort);      
      jGraph.getGraphLayoutCache().insert(cell);
      cellsMap.put(mv, cell);
			angle += delta;
		}
		for (MiliVertex mv : graph) {
		  for (MiliTransition mt : mv.getOutgoings()) {
		    DefaultGraphCell source = cellsMap.get(mt.getSource());
		    DefaultGraphCell target = cellsMap.get(mt.getTarget());
		    DefaultEdge edge = new DefaultEdge();
	      DefaultPort sPort = (DefaultPort)source.getChildAt(1); 
	      DefaultPort tPort = (DefaultPort)target.getChildAt(0); 
	      edge.setSource(sPort);
	      edge.setTarget(tPort);
	      sPort.addEdge(edge);
	      tPort.addEdge(edge);
	      
	      GraphConstants.setLineEnd(edge.getAttributes(), GraphConstants.ARROW_CLASSIC);
	      GraphConstants.setEndFill(edge.getAttributes(), true);
	      GraphConstants.setValue(edge.getAttributes(), "y" + mt.getYSignal() + "/" + mt.getConditions());
	      jGraph.getGraphLayoutCache().insert(edge);		    
		  }
		}
	}

  /**
   * {@inheritDoc}
   */
	@Override
  public Icon getIcon() {
	  return GraphPanel.icon;
  }
}
