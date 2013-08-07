/**
 * @author yddy,create date 2003-11-13 
 * @Blog : http://yddysy.blogcn.net
 */
package ynugis.application;

import java.awt.Frame;

import org.eclipse.jface.resource.ImageRegistry;
import org.eclipse.swt.custom.SashForm;

import ynugis.classifyBusiness.ClassifyProject;
import ynugis.ffsystem.FFTable;
import ynugis.ui.face.BodyCenter;
import ynugis.ui.face.BodyLeft;
import ynugis.ui.face.BodyRight;
import ynugis.ui.image.ImageProvider;

public class CESUI {
//	private CESCORE cescore;
	private SashForm bodyForm;
	private BodyLeft bodyLeft;
	private ImageProvider imageProvider;
	private Frame mapFrame;
	private Frame pageFrame;
	private Frame tocFrame;
	private Frame toolbarViewFrame;
	private Frame toolbarEditFrame;
	public CESUI()throws Exception{
		imageProvider=new ImageProvider();
	}
	public void initializeUI(SashForm bodyform,BodyLeft bodyleft,BodyCenter bodycenter,BodyRight bodyright,Frame toolbarviewframe,Frame toolbareditframe){
//		cescore=core;
		bodyForm=bodyform;
		bodyLeft=bodyleft;
		mapFrame=bodycenter.getMapFrame();
		pageFrame=bodycenter.getPageFrame();
		tocFrame=bodyright.getTocFrame();
		toolbarViewFrame=toolbarviewframe;
		toolbarEditFrame=toolbareditframe;
	}
	public void fullFrameWithAEControls(CESCORE cescore){
		Frame mapFrame=this.getMapFrame();
		Frame pageLayoutFrame=this.getPageFrame();
		Frame tocFrame=this.getTocFrame();
		Frame toolViewFrame=this.getToolbarViewFrame();
		Frame toolEditFrame=this.getToolbarEditFrame();
		cescore.configureAEControls(mapFrame,cescore.getMapControl(),pageLayoutFrame,cescore.getPageLayoutControl(),tocFrame,cescore.getTocControl(),toolViewFrame,cescore.getToolbarControlView(),toolEditFrame,cescore.getToolbarControlEdit());
	}
	 
	public ImageRegistry getImageRegistry(){
		return imageProvider.getImageRegistry();
	}
	public void setPartition(int[] partition){
		if(partition.length==3){
			bodyForm.setWeights(partition);
		}
	}
	
	/**
	 * @param project,relayout UI while loading a project with parameter project,else with parameter 'null'
	 * @usedfor relayout UI
	 */
	public void resetUI(ClassifyProject project){
		if(project==null){
			bodyLeft.initialNew();
			bodyLeft.setVisible(false);
			bodyForm.getParent().layout();
			bodyForm.layout();
			return;
		}
		FFTable[] fftables=project.getAllFFT();
		System.out.println("fftable in ui reset:"+fftables);
		bodyLeft.initializeText(fftables);
		bodyLeft.setVisible(true);
		
		bodyForm.pack();
		bodyForm.getParent().layout();
	}
//	public void layout(Shell shell){
//		Control[] controls=shell.getChildren();
//		System.out.println("control:"+controls.length);
//	}
	/**
	 * @return Returns the bodyForm.
	 */
	public SashForm getBodyForm() {
		return bodyForm;
	}
	/**
	 * @return Returns the mapFrame.
	 */
	public Frame getMapFrame() {
		return mapFrame;
	}
	/**
	 * @return Returns the pageFrame.
	 */
	public Frame getPageFrame() {
		return pageFrame;
	}
	/**
	 * @return Returns the tocFrame.
	 */
	public Frame getTocFrame() {
		return tocFrame;
	}
	/**
	 * @return Returns the toolbarEditFrame.
	 */
	public Frame getToolbarEditFrame() {
		return toolbarEditFrame;
	}
	/**
	 * @return Returns the toolbarViewFrame.
	 */
	public Frame getToolbarViewFrame() {
		return toolbarViewFrame;
	}
	/**
	 * @return Returns the bodyLeft.
	 */
	public BodyLeft getBodyLeft() {
		return bodyLeft;
	}
}
