package fiji.plugin.trackmate.tests;

import java.io.File;

import net.imglib2.type.NativeType;
import net.imglib2.type.numeric.RealType;

import fiji.plugin.trackmate.Logger;
import fiji.plugin.trackmate.TrackMateModel;
import fiji.plugin.trackmate.io.TmXmlReader;

public class TmXmlReaderTestDrive {

	private static final File file = new File("/Users/tinevez/Desktop/Data/FakeTracks.xml");
	//	private static final File file = new File("/Users/tinevez/Projects/ELaplantine/2011-06-29/Dish4_avg-cell1.xml");
	//	private static final File file = new File("/Users/tinevez/Projects/DMontaras/Mutant/20052011_16_20.xml");

	public static <T extends RealType<T> & NativeType<T>> void main(String args[]) {

		//		ij.ImageJ.main(args);

		System.out.println("Opening file: "+file.getAbsolutePath());		
		TmXmlReader<T> reader = new TmXmlReader<T>(file, Logger.DEFAULT_LOGGER);
		TrackMateModel<T> model = null;
		// Parse
		reader.parse();
		model = reader.getModel();

		System.out.println(model.getSettings());

		System.out.println();
		System.out.println("Detector was: "+model.getSettings().detector.toString());
		System.out.println("With settings:");
		System.out.println(model.getSettings().detectorSettings);

		System.out.println();
		System.out.println("Tracker was: "+model.getSettings().tracker.toString());
		System.out.println("With settings:");
		System.out.println(model.getSettings().trackerSettings);
		System.out.println();
		System.out.println("Found "+model.getSpots().getNSpots()+" spots in total.");
		System.out.println("Found "+model.getFilteredSpots().getNSpots()+" filtered spots.");
		System.out.println("Found "+model.getNTracks()+" tracks in total.");
		System.out.println("Found "+model.getNFilteredTracks()+" filtered tracks.");

		System.out.println();
		System.out.println("Track features:");
		System.out.println(model.getFeatureModel().getTrackFeatureValues());

		// Instantiate displayer
//		fiji.plugin.trackmate.visualization.AbstractTrackMateModelView displayer 
//			= new fiji.plugin.trackmate.visualization.hyperstack.HyperStackDisplayer();
//		displayer.setModel(model);
//		displayer.render();
//		displayer.refresh();

	}


}