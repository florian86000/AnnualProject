package bacterium;

import java.util.List;

import repast.simphony.query.space.grid.GridCell;
import repast.simphony.query.space.grid.GridCellNgh;
import repast.simphony.random.RandomHelper;
import repast.simphony.space.SpatialMath;
import repast.simphony.space.continuous.ContinuousSpace;
import repast.simphony.space.continuous.NdPoint;
import repast.simphony.space.graph.Network;
import repast.simphony.space.grid.Grid;
import repast.simphony.space.grid.GridPoint;
import repast.simphony.util.ContextUtils;
import repast.simphony.util.SimUtilities;
import repast.simphony.context.Context;
import repast.simphony.engine.schedule.ScheduledMethod;

public class bacteria {
	static Network<Object> phylogeny;
	private ContinuousSpace<Object> space;
	private Grid<Object> grid;
	private boolean moved;
	private Genome genome;

	public bacteria(ContinuousSpace<Object> space, Grid<Object> grid) {
		this.space = space;
		this.grid = grid;
		this.genome = new Genome();
	}
	@ScheduledMethod(start = 1, interval = 1)
	public void step() {
		NdPoint pos = space.getLocation(this);
		pos = space.getLocation(this);
		NdPoint nPos = new NdPoint (pos.getX()+Math.random()*1, (pos.getY()+ Math.random()*1), (pos.getZ() + Math.random()*1));
		space.moveTo(this, nPos.getX(), nPos.getY(), nPos.getZ());
		grid.moveTo(this, (int) nPos.getX(),(int)nPos.getY(),(int) nPos.getZ());
		this.growth();
		
	}
	
	public bacteria (ContinuousSpace<Object> space, Grid<Object> grid, Genome genome){
		this.space = space;
		this.grid = grid;
		this.genome = genome;	
	}

	public void growth (){
		GridPoint pos = grid.getLocation(this);
		bacteria child = new bacteria(space, grid, this.genome);
		Context<Object> c = (Context<Object>) ContextUtils.getContext(this);
		try {
			
			c.add (child); 
			space.moveTo(child, pos.getX(), pos.getY(), pos.getZ());
			grid.moveTo(child,pos.getX(), pos.getY(), pos.getZ());
		}
		catch (java.lang.NullPointerException e){
			System.out.println("context : "+c+"\n"+"child : "+child);
		}
		//Network<Object> net = (Network<Object>)c.getProjection("growth network");
		phylogeny.addEdge(this, child);
		
	}
	
	
	}