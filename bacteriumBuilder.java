package bacterium;


import bacterium.bacteria;
import repast.simphony.context.Context;
import repast.simphony.context.DefaultContext;
import repast.simphony.context.space.continuous.ContinuousSpaceFactory;
import repast.simphony.context.space.continuous.ContinuousSpaceFactoryFinder;
import repast.simphony.context.space.graph.NetworkBuilder;
import repast.simphony.context.space.grid.GridFactory;
import repast.simphony.context.space.grid.GridFactoryFinder;
import repast.simphony.dataLoader.ContextBuilder;
import repast.simphony.engine.environment.RunEnvironment;
import repast.simphony.parameter.Parameters;
import repast.simphony.random.RandomHelper;
import repast.simphony.space.continuous.ContinuousSpace;
import repast.simphony.space.continuous.NdPoint;
import repast.simphony.space.continuous.RandomCartesianAdder;
import repast.simphony.space.grid.Grid;
import repast.simphony.space.grid.GridBuilderParameters;
import repast.simphony.space.grid.GridDimensions;
import repast.simphony.space.grid.SimpleGridAdder;
import repast.simphony.space.grid.WrapAroundBorders;

public class bacteriumBuilder implements ContextBuilder<Object> {

	@Override
	public Context build(Context<Object> context) {
		// TODO Auto-generated method stub
		context.setId("bacterium");
		
		NetworkBuilder<Object> netBuilder = new NetworkBuilder<Object>(
				"growth network", context, true);
		bacteria.phylogeny=netBuilder.buildNetwork();
		
		ContinuousSpaceFactory spaceFactory = ContinuousSpaceFactoryFinder.createContinuousSpaceFactory(null);
		ContinuousSpace <Object > space = spaceFactory.createContinuousSpace("space", context ,
		new RandomCartesianAdder <Object >(),
		new repast.simphony.space.continuous.WrapAroundBorders(), 50, 50,50);
		
		
		GridFactory gridFactory = GridFactoryFinder.createGridFactory(null); 
		Grid<Object> grid = gridFactory.createGrid("grid", context, 
		new GridBuilderParameters <Object >(new WrapAroundBorders(), new SimpleGridAdder <Object >(),
		true, 50, 50,50)); 
		
		Parameters params = RunEnvironment.getInstance().getParameters();
		int bacteriaCount = (Integer) params.getValue("bacteria");
		GridDimensions gd = grid.getDimensions();
		
		for (int i = 0; i < bacteriaCount; i++) {
			bacteria b = new bacteria(space,grid);
			context.add(b);
			space.moveTo(b, Math.random()*gd.getWidth(), Math.random()*gd.getHeight(), Math.random()*gd.getDepth());
			grid.moveTo(b, (int)Math.floor(Math.random()*gd.getWidth()), (int)Math.floor(Math.random()*gd.getHeight()), (int)Math.floor(Math.random()*gd.getDepth()));
		}
		
		
		
		
		
		return context;
	}

}

