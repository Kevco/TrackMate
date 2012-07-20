package fiji.plugin.trackmate.gui;

import java.awt.Component;

import net.imglib2.type.NativeType;
import net.imglib2.type.numeric.RealType;

import fiji.plugin.trackmate.Logger;
import fiji.plugin.trackmate.Settings;
import fiji.plugin.trackmate.TrackMate_;

public class DetectorDescriptor <T extends RealType<T> & NativeType<T>> implements WizardPanelDescriptor<T> {
	
	public static final String DESCRIPTOR = "SegmentationPanel";
	protected LogPanel logPanel;
	protected TrackMate_<T> plugin;
	protected TrackMateWizard<T> wizard;
	protected Logger logger;
	

	@Override
	public void setWizard(TrackMateWizard<T> wizard) { 
		this.wizard = wizard;
		this.logPanel = wizard.getLogPanel();
		this.logger = wizard.getLogger();
	}

	@Override
	public void setPlugin(TrackMate_<T> plugin) {
		this.plugin = plugin;
	}

	@Override
	public Component getComponent() {
		return logPanel;
	}

	@Override
	public String getDescriptorID() {
		return DESCRIPTOR;
	}
	
	@Override
	public String getComponentID() {
		return LogPanel.DESCRIPTOR;
	}

	@Override
	public String getNextDescriptorID() {
		return InitFilterPanel.DESCRIPTOR;
	}

	@Override
	public String getPreviousDescriptorID() {
		return DetectorConfigurationPanelDescriptor.DESCRIPTOR;
	}

	@Override
	public void aboutToDisplayPanel() {	}

	@Override
	public void displayingPanel() {
		wizard.setNextButtonEnabled(false);
		final Settings<T> settings = plugin.getModel().getSettings();
		logger.log("Starting segmentation using "+settings.detector.toString()+"\n", Logger.BLUE_COLOR);
		logger.log("with settings:\n");
		logger.log(settings.toString());
		logger.log(settings.detectorSettings.toString());
		new Thread("TrackMate segmentation mother thread") {					
			public void run() {
				long start = System.currentTimeMillis();
				try {
					plugin.execDetection();
				} catch (Exception e) {
					logger.error("An error occured:\n"+e+'\n');
					e.printStackTrace(logger);
				} finally {
					wizard.setNextButtonEnabled(true);
					long end = System.currentTimeMillis();
					logger.log(String.format("Segmentation done in %.1f s.\n", (end-start)/1e3f), Logger.BLUE_COLOR);
				}
			}
		}.start();
	}

	@Override
	public void aboutToHidePanel() { }
}