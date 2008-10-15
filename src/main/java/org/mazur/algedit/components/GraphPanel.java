/**
 * 
 */
package org.mazur.algedit.components;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.geom.Rectangle2D;
import java.util.HashMap;
import java.util.List;

import javax.swing.JPanel;

import org.jgraph.JGraph;
import org.jgraph.graph.DefaultEdge;
import org.jgraph.graph.DefaultGraphCell;
import org.jgraph.graph.DefaultGraphModel;
import org.jgraph.graph.DefaultPort;
import org.jgraph.graph.GraphConstants;
import org.mazur.algedit.mili.MiliTransmition;
import org.mazur.algedit.mili.MiliVertex;

/**
 * @author Roman Mazur (IO-52)
 *
 */
public class GraphPanel extends JPanel {

	/** serialVersionUID. */
	private static final long serialVersionUID = 418726633138555848L;

	private JGraph jGraph;
	
	public GraphPanel(final List<MiliVertex> graph) {
		jGraph = new JGraph();
		jGraph.setModel(new DefaultGraphModel());
		jGraph.setGridEnabled(true);
		jGraph.setSize(new Dimension(300, 300));
		setLayout(new BorderLayout());
		buildGraph(graph);
		add(BorderLayout.CENTER, jGraph);
	}

	private void buildGraph(final List<MiliVertex> graph) {
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
		  for (MiliTransmition mt : mv.getOutgoings()) {
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
}
