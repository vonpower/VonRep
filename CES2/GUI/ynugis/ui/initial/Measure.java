package ynugis.ui.initial;

import java.io.IOException;
import java.text.DecimalFormat;

import com.esri.arcgis.carto.IActiveView;
import com.esri.arcgis.carto.IMap;
import com.esri.arcgis.controls.HookHelper;
import com.esri.arcgis.display.ILineSymbol;
import com.esri.arcgis.display.IScreenDisplay;
import com.esri.arcgis.display.ISymbol;
import com.esri.arcgis.display.ISymbolProxy;
import com.esri.arcgis.display.ITextSymbol;
import com.esri.arcgis.display.RgbColor;
import com.esri.arcgis.display.SimpleLineSymbol;
import com.esri.arcgis.display.TextSymbol;
import com.esri.arcgis.display.esriRasterOpCode;
import com.esri.arcgis.display.esriTextHorizontalAlignment;
import com.esri.arcgis.display.esriTextVerticalAlignment;
import com.esri.arcgis.geometry.IGeometry;
import com.esri.arcgis.geometry.IPoint;
import com.esri.arcgis.geometry.IPolyline;
import com.esri.arcgis.geometry.IPolylineProxy;
import com.esri.arcgis.geometry.ITopologicalOperator;
import com.esri.arcgis.geometry.ITopologicalOperatorProxy;
import com.esri.arcgis.geometry.Line;
import com.esri.arcgis.geometry.Point;
import com.esri.arcgis.geometry.Polygon;
import com.esri.arcgis.geometry.Polyline;
import com.esri.arcgis.geometry.esriGeometryDimension;
import com.esri.arcgis.interop.AutomationException;
import com.esri.arcgis.systemUI.ICommand;
import com.esri.arcgis.systemUI.ITool;

public class Measure implements ICommand, ITool {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4616657240695312989L;
	
	private boolean inUse;
	private ILineSymbol lineSymbol;
	private IPolyline polyline;
	private ITextSymbol textSymbol;
	private IPoint sp;
	private IPoint tp;
	private IMap map;
	private IActiveView activeView;
	
	private HookHelper hookHelper;
	
	public boolean isEnabled() throws IOException, AutomationException {
		return true;
	}

	public boolean isChecked() throws IOException, AutomationException {
		// TODO Auto-generated method stub
		return false;
	}

	public String getName() throws IOException, AutomationException {
		return "measure";
	}

	public String getCaption() throws IOException, AutomationException {
		return "Measure";
	}

	public String getTooltip() throws IOException, AutomationException {
		return "measure distance";
	}

	public String getMessage() throws IOException, AutomationException {
		return "measure distance tool";
	}

	public String getHelpFile() throws IOException, AutomationException {
		// TODO Auto-generated method stub
		return null;
	}

	public int getHelpContextID() throws IOException, AutomationException {
		// TODO Auto-generated method stub
		return 0;
	}

	public int getBitmap() throws IOException, AutomationException {
		// TODO Auto-generated method stub
		return 0;
	}

	public String getCategory() throws IOException, AutomationException {
		// TODO Auto-generated method stub
		return "Develper Samples";
	}

	public void onCreate(Object arg0) throws IOException, AutomationException {
		// TODO Auto-generated method stub
		hookHelper=new HookHelper();
		hookHelper.setHookByRef(arg0);
	}

	public void onClick() throws IOException, AutomationException {
		// TODO Auto-generated method stub

	}

	public int getCursor() throws IOException, AutomationException {
		// TODO Auto-generated method stub
		return 0;
	}

	public void onMouseDown(int arg0, int arg1, int arg2, int arg3)
			throws IOException, AutomationException {
		inUse=true;
		System.out.println("mouse down");
		
		activeView=hookHelper.getActiveView();
		sp=activeView.getScreenDisplay().getDisplayTransformation().toMapPoint(arg2,arg3);
	}

	public void onMouseMove(int arg0, int arg1, int arg2, int arg3)
			throws IOException, AutomationException {
		// TODO Auto-generated method stub
		if(!inUse)return;
		boolean firstUse=false;
		if(lineSymbol==null)firstUse=true;
		IPoint movePoint;
		movePoint=activeView.getScreenDisplay().getDisplayTransformation().toMapPoint(arg2,arg3);
		activeView.getScreenDisplay().startDrawing(activeView.getScreenDisplay().getHDC(),(short)-1);
		if(firstUse){
			RgbColor rgbColor=new RgbColor();
			rgbColor.setBlue(223);
			rgbColor.setRed(223);
			rgbColor.setGreen(223);
			
			SimpleLineSymbol sls=new SimpleLineSymbol();
			sls.setColor(rgbColor);
			sls.setWidth(1);
			sls.setROP2(esriRasterOpCode.esriROPXOrPen);
			lineSymbol=sls;
			TextSymbol ts=new TextSymbol();
			ts.setHorizontalAlignment(esriTextHorizontalAlignment.esriTHACenter);
			ts.setVerticalAlignment(esriTextVerticalAlignment.esriTVACenter);
			ts.setSize(12);
			ts.setROP2(esriRasterOpCode.esriROPXOrPen);
			ts.getFont().setName("Arial");
			textSymbol=ts;
			tp=new Point();
		}else{
//			activeView.getScreenDisplay().setSymbol(new ISymbolProxy(textSymbol));
//			activeView.getScreenDisplay().drawText(tp,textSymbol.getText());
//			activeView.getScreenDisplay().setSymbol(new ISymbolProxy(lineSymbol));
//			if(polyline.getLength()>0){
//				activeView.getScreenDisplay().drawPolyline(polyline);
//			}
		}
		Line line=new Line();
		line.putCoords(sp,movePoint);
		double angle=line.getAngle();
		angle=angle*180/3.14159;
		if(angle>90&&angle<180){
			angle+=180;
		}else if(angle<0&&angle>-90){
			angle-=180;
		}else if(angle<-90&&angle>-180){
			angle-=180;
		}else if(angle>180){
			angle-=180;
		}
		double dx,dy,distance;
		dx=movePoint.getX()-sp.getX();
		dy=movePoint.getY()-sp.getY();
		tp.setX(sp.getX()+dx/2);
		tp.setY(sp.getY()+dy/2);
//		textSymbol.setAngle(angle);
		textSymbol.setAngle(0);
		distance=Math.sqrt(dx*dx+dy*dy);
		DecimalFormat format=new DecimalFormat("#.00");
		
		textSymbol.setText(format.format(distance));
//		activeView.getScreenDisplay().setSymbol(new ISymbolProxy(textSymbol));
//		activeView.getScreenDisplay().drawText(tp,textSymbol.getText());
		Polyline pl=new Polyline();
		pl.addSegment(line,null,null);
		polyline=smashLine(activeView.getScreenDisplay(),new ISymbolProxy(textSymbol),tp,pl);
//		activeView.getScreenDisplay().setSymbol(new ISymbolProxy(textSymbol));
//		if(polyline.getLength()>0){
//			activeView.getScreenDisplay().drawPolyline(polyline);
//		}
		activeView.getScreenDisplay().finishDrawing();
		activeView.refresh();
	}

	public void onMouseUp(int arg0, int arg1, int arg2, int arg3)
			throws IOException, AutomationException {
		if(!inUse)return;
		inUse=false;
		if(lineSymbol==null)return;
		activeView.getScreenDisplay().startDrawing(activeView.getScreenDisplay().getHDC(),(short)-1);
		activeView.getScreenDisplay().setSymbol(new ISymbolProxy(textSymbol));
		activeView.getScreenDisplay().drawText(tp,textSymbol.getText());
		activeView.getScreenDisplay().setSymbol(new ISymbolProxy(lineSymbol));
		if(polyline.getLength()>0){
			activeView.getScreenDisplay().drawPolyline(polyline);
		}
		activeView.getScreenDisplay().finishDrawing();
		polyline=null;
		textSymbol=null;
		lineSymbol=null;
		tp=null;
	}

	public void onDblClick() throws IOException, AutomationException {
		// TODO Auto-generated method stub
		polyline=null;
		textSymbol=null;
		lineSymbol=null;
		tp=null;
	}

	public void onKeyDown(int arg0, int arg1) throws IOException,
			AutomationException {
		// TODO Auto-generated method stub

	}

	public void onKeyUp(int arg0, int arg1) throws IOException,
			AutomationException {
		// TODO Auto-generated method stub

	}

	public boolean onContextMenu(int arg0, int arg1) throws IOException,
			AutomationException {
		// TODO Auto-generated method stub
		return false;
	}

	public void refresh(int arg0) throws IOException, AutomationException {
		// TODO Auto-generated method stub

	}

	public boolean deactivate() throws IOException, AutomationException {
		lineSymbol=null;
		polyline=null;
		textSymbol=null;
		tp=null;
		inUse=false;
		return true;
	}
	private IPolyline smashLine(IScreenDisplay sd,ISymbol s,IPoint p,IPolyline pl){
		try {
//			IPolyline smashline;
			Polygon boundary = new Polygon();
			s.queryBoundary(sd.getHDC(),sd.getDisplayTransformation(),p,boundary);
			IGeometry geo=boundary.intersect(pl,esriGeometryDimension.esriGeometry1Dimension);
			ITopologicalOperator to=new ITopologicalOperatorProxy(pl);
			IGeometry resultGeometry=to.difference(geo);
			return new IPolylineProxy(resultGeometry);
		} catch (Exception e) {
			// TODO: handle exception
			return null;
		}
		
	}
}
